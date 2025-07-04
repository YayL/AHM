package ahm.keymaps;

import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class KeymapRegister {

    private static final List<InputUtil.Key> active_sequence = new ArrayList<>(5);

    public static void add_key(InputUtil.Key key) {
        active_sequence.add(key);
    }

    public static void clear_sequence() {
        active_sequence.clear();
    }

    public static List<InputUtil.Key> get_sequence() {
        return active_sequence;
    }
}
