package ahm.keymaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.input.Input;
import net.minecraft.client.util.InputUtil;

import java.util.Arrays;
import java.util.List;

public record KeymapEntry(InputUtil.Key[] keys, String value) {
    private static final Codec<InputUtil.Key> KEY_CODEC = Codec.STRING.xmap(InputUtil::fromTranslationKey, InputUtil.Key::getTranslationKey);
    private static final Codec<List<InputUtil.Key>> KEY_LIST_CODEC = KEY_CODEC.listOf();
    private static final Codec<InputUtil.Key[]> KEY_ARR_CODEC = KEY_LIST_CODEC.xmap(list -> list.toArray(new InputUtil.Key[list.size()]), Arrays::asList);

    public static final Codec<KeymapEntry> CODEC = RecordCodecBuilder.create(keymapEntryInstance ->
            keymapEntryInstance.group(
                    KEY_ARR_CODEC.fieldOf("keys").forGetter(KeymapEntry::keys),
                    Codec.STRING.fieldOf("value").forGetter(KeymapEntry::value)
            ).apply(keymapEntryInstance, KeymapEntry::new)
    );
}
