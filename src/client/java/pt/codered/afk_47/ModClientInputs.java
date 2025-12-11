package pt.codered.afk_47;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pt.codered.afk_47.util.TreeRecorder;

public class ModClientInputs {

    private static KeyBinding recordKey;
    private static KeyBinding addRootKey;
    private static KeyBinding addLogKey;

    public static void init() {
        // Register Keys (Using Fabric API logic as example)
        recordKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.forage.record", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10, "category.forage"
        ));
        addRootKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.forage.add_root", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F11, "category.forage"
        ));
        addLogKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.forage.add_log", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F12, "category.forage"
        ));

        // Register Tick Event to check presses
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            // 1. Toggle Record
            while (recordKey.wasPressed()) {
                TreeRecorder.get().toggleRecording();
            }

            // 2. Add Root
            while (addRootKey.wasPressed()) {
                TreeRecorder.get().addRoot();
            }

            // 3. Add Log
            while (addLogKey.wasPressed()) {
                TreeRecorder.get().addLog();
            }
        });
    }
}
