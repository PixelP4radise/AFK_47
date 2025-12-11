package pt.codered.afk_47;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import pt.codered.afk_47.util.BoxRenderer;
import pt.codered.afk_47.util.ModLogger;
import pt.codered.afk_47.util.TreeRecorder;

public class AFK_47Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModLogger.info("Starting AFK_47 Client...");

        AFKManager.getInstance().init();

        registerCommands();

        registerMessageDebug();

        registerDrawing();

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

    private void registerDrawing() {
        WorldRenderEvents.LAST.register(context -> {
            TreeRecorder recorder = TreeRecorder.get();

            // Only render if we have data
            if (!recorder.getLogsToRender().isEmpty() || !recorder.getRootsToRender().isEmpty()) {

                MatrixStack matrices = context.matrixStack();
                VertexConsumerProvider consumers = context.consumers();

                // Render Logs (Red)
                BoxRenderer.renderBoxes(
                        matrices,
                        consumers,
                        recorder.getLogsToRender(),
                        1.0f, 0.0f, 0.0f, 1.0f // Red
                );

                // Render Roots (Green)
                BoxRenderer.renderBoxes(
                        matrices,
                        consumers,
                        recorder.getRootsToRender(),
                        0.0f, 1.0f, 0.0f, 1.0f // Green
                );
            }
        });
    }


}
