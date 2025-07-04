package ahm.keymaps;

import ahm.utils.TrieNode;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static ahm.AHMClient.chat;
import static ahm.AHMClient.keybind_category;

public class KeymapSearch {

    public static boolean is_searching = false;
    public static KeyBinding keybind_start;
    private static KeyBinding keybind_stop;
    private static TrieNode active_node;

    public static void register() {
        keybind_start = KeyBindingHelper.registerKeyBinding(new KeyBinding( "Start key sequence", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, keybind_category ));
        keybind_stop = KeyBindingHelper.registerKeyBinding(new KeyBinding( "Stop key sequence", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_ESCAPE, keybind_category ));
    }

    public static void update(InputUtil.Key key) {
        if (keybind_stop.boundKey.equals(key)) {
            set_is_searching(false);
            return;
        }

        add_key(key);
    }

    private static void add_key(InputUtil.Key key) {
        KeymapRegister.add_key(key);
        active_node = KeymapsManager.get_child(active_node, key);

        if (active_node == null) {
            chat("Unable to find keymap", true);
            set_is_searching(false);
        } else if (active_node.child_elements == 0) {
            assert MinecraftClient.getInstance().player != null;

            String value = active_node.get_value();
            assert value != null;

            if (value.charAt(0) == '/') {
                MinecraftClient.getInstance().player.networkHandler.sendChatCommand(value.substring(1));
            } else {
                MinecraftClient.getInstance().player.networkHandler.sendChatMessage(active_node.get_value());
            }
            set_is_searching(false);
        }

    }

    public static void set_is_searching(boolean value) {
        is_searching = value;
        if (!is_searching) {
            KeymapRegister.clear_sequence();
            active_node = null;
        }
    }

    public static boolean is_searching() {
        return is_searching;
    }
}
