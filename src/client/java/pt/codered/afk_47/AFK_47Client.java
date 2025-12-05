package pt.codered.afk_47;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class AFK_47Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            
            dispatcher.register(ClientCommandManager.literal("afktest")
                    .executes(context -> {
                        context.getSource().sendFeedback(Text.literal("§aAFK_47 Client is working!"));
                        return 1;
                    })
            );
        });
    }
}