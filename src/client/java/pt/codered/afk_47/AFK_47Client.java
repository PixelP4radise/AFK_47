package pt.codered.afk_47;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import pt.codered.afk_47.model.data.TreePosition;
import pt.codered.afk_47.util.BoxRenderer;
import pt.codered.afk_47.util.ModLogger;
import pt.codered.afk_47.util.TreeRecorder;

import java.awt.*;
import java.util.List;

public class AFK_47Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModLogger.info("Starting AFK_47 Client...");

        AFKManager.getInstance().init();

        registerCommands();

        registerMessageDebug();

        registerTreeDrawing();

        ModClientInputs.init();
    }

    private void registerCommands() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            dispatcher.register(ClientCommandManager.literal("afktest")
                    .executes(context -> {
                        AFKManager.getInstance().testCommand(context);
                        return 1;
                    })
            );
        });
    }

    private void registerMessageDebug() {
        // 1. Server Messages (System messages, game info, and Hypixel chat)
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            if (!overlay) {
                // message.getString() removes formatting codes for clean logging
                System.out.println("[server] " + message.getString());
            }
            return true; // Return true to let the message display in-game
        });

        // 2. Action Bar Messages (The text above your hotbar)
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            if (overlay) {
                System.out.println("[action] " + message.getString());
            }
            return true;
        });

        // 3. Player Chat (Secure/Signed chat from vanilla clients)
        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            System.out.println("[chat] " + message.getString());
            return true;
        });
    }

    private void registerTreeDrawing() {
        WorldRenderEvents.LAST.register(context -> {
            TreeRecorder recorder = TreeRecorder.get();

            // 1. Get data (Assuming you added the helper to convert Map -> List<TreePosition>)
            List<TreePosition> groups = recorder.getRecordedPositions();
            if (groups.isEmpty()) return;

            // 2. Get the Immediate Provider from the Client
            // This allows us to draw lines on top of the world
            VertexConsumerProvider.Immediate consumers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

            int index = 0;
            int totalGroups = groups.size();

            // 3. Iterate and Render
            for (TreePosition group : groups) {

                // Generate Rainbow Color
                float hue = (totalGroups > 1) ? (float) index / totalGroups : 0.33f; // Default Green
                int colorInt = Color.HSBtoRGB(hue, 1.0f, 1.0f);
                float r = ((colorInt >> 16) & 0xFF) / 255.0f;
                float g = ((colorInt >> 8) & 0xFF) / 255.0f;
                float b = (colorInt & 0xFF) / 255.0f;

                // Render ROOT (Solid / Darker)
                BoxRenderer.renderBoxes(
                        context.matrixStack(),
                        consumers,             // Pass the provider here
                        List.of(group.root()), // Root is a single block
                        r, g, b, 0.8f
                );

                // Render LOGS (Transparent)
                BoxRenderer.renderBoxes(
                        context.matrixStack(),
                        consumers,
                        group.logs(),
                        r, g, b, 0.3f
                );

                index++;
            }

            // 4. IMPORTANT: Force the buffer to draw immediately
            // Since we are in the 'LAST' event, we must flush the buffer or it might not appear.
            consumers.draw();
        });
    }


}
