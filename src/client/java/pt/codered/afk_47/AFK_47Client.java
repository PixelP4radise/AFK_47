package pt.codered.afk_47;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import pt.codered.afk_47.util.ModLogger;

public class AFK_47Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        ModLogger.info("Starting AFK_47 Client...");

        AFKManager.getInstance().init();

        registerCommands();
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
}