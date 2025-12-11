package pt.codered.afk_47;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import pt.codered.afk_47.util.ModLogger;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class AFK_47Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModLogger.info("Starting AFK_47 Client...");

        AFKManager.getInstance().init();

        registerCommands();

        registerMessageDebug();

        ModClientInputs.init();
    }

    private void registerCommands() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            dispatcher.register(literal("afktest")
                    .executes(context -> {
                        AFKManager.getInstance().testCommand(context);
                        return 1;
                    })
            );

            dispatcher.register(
                    literal("afk")
                            // Sub-command: /afk fishing
                            .then(literal("fishing")
                                    .executes(context -> {
                                        return 1;
                                    })
                            )
                            // Sub-command: /afk foraging
                            .then(literal("foraging")
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("§aStarting Foraging Bot..."));
                                        AFKManager.getInstance().startForaging();
                                        return 1;
                                    })
                            )
                            // Sub-command: /afk stop
                            .then(literal("stop")
                                    .executes(context -> {
                                        context.getSource().sendFeedback(Text.of("§aStopping AFK Bot..."));
                                        AFKManager.getInstance().stop();
                                        return 1;
                                    })
                            )
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


}
