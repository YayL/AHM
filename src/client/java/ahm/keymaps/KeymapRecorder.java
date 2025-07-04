package ahm.keymaps;

import ahm.utils.KeysUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Consumer;

import static ahm.AHM.logger;
import static ahm.AHMClient.chat;
import static ahm.AHMClient.keybind_category;

public class KeymapRecorder {

    private static KeyBinding record_finish, record_stop;
    private static boolean is_recording = false;
    private static Consumer<InputUtil.Key[]> callback;

    public static void register() {
        record_finish = KeyBindingHelper.registerKeyBinding(new KeyBinding( "Finish recording", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_ENTER, keybind_category ));
        record_stop = KeyBindingHelper.registerKeyBinding(new KeyBinding( "Stop recording", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_ESCAPE, keybind_category ));
    }

    public static void update(InputUtil.Key key) {
        if (record_stop.boundKey.equals(key)) {
            is_recording = false;
            callback = null;
        } else if (record_finish.boundKey.equals(key)) {
            Consumer<InputUtil.Key[]> temp = callback;
            is_recording = false;
            callback = null;

            assert MinecraftClient.getInstance().player != null;

            InputUtil.Key[] sequence = KeysUtil.keys_list_to_arr(KeymapRegister.get_sequence());
            KeymapRegister.clear_sequence();

            try {
                temp.accept(sequence);
            } catch (Exception e) {
                chat("An error occurred", true);
                logger.error(e.toString());
            }
        } else {
            KeymapRegister.add_key(key);
        }
    }

    public static void record(Consumer<InputUtil.Key[]> callable) {
        is_recording = true;
        callback = callable;
    }

    public static boolean is_recording() {
        return is_recording;
    }
}
