package ahm.utils;

import com.mojang.datafixers.kinds.IdF;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class KeysUtil {

    public static InputUtil.Key[] keys_list_to_arr(List<InputUtil.Key> keys) {
        InputUtil.Key[] key_arr = new InputUtil.Key[keys.size()];
        keys.toArray(key_arr);

        return key_arr;
    }

    public static MutableText keys_to_text(InputUtil.Key[] keys) {
        MutableText text = Text.empty();

        boolean flag = false;
        for (InputUtil.Key key : keys) {
            if (flag) {
                text.append(Text.literal("+"));
            } else { flag = true; }
            text.append(key.getLocalizedText());
        }

        return text;
    }

    public static MutableText keys_to_text(List<InputUtil.Key> keys) {
        return keys_to_text(keys_list_to_arr(keys));
    }

}
