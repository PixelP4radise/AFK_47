package pt.codered.afk_47;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import pt.codered.afk_47.model.data.AFKData;
import pt.codered.afk_47.model.fsm.AFKFSMContext;
import pt.codered.afk_47.util.ModLogger;

public class AFKManager {

    // Singleton Instance
    private static final AFKManager INSTANCE = new AFKManager();

    public static AFKManager getInstance() {
        return INSTANCE;
    }

    private AFKFSMContext FSMcontext;

    private AFKData data;

    private AFKManager() {
        this.FSMcontext = new AFKFSMContext();
        this.data = new AFKData();
    }


    public void init() {
        ModLogger.info("AFKManager initialized.");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                FSMcontext.tick();
            }
        });
    }

    public void testCommand(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.literal("§aAFK_47 Client is working!"));
    }
}
