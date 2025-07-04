package ahm.keymaps;

import ahm.AHMClient;
import ahm.utils.Trie;
import ahm.utils.TrieNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.util.InputUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static ahm.AHM.logger;
import static ahm.AHMClient.chat;

public class KeymapsManager {

    private static Trie mappings = new Trie();

    public static void add(InputUtil.Key[] keys, String value) {
        if (keys == null || keys.length == 0) {
            chat("Unable to add keymap to empty sequence");
            return;
        }
        if (value == null || value.isEmpty()) {
            chat("Unable to add empty keymap");
            return;
        }

        if (mappings.insert(keys, value)) {
            chat("Added keymap", true);
        } else {
            chat("Unable to add keymap because parent is a keymap.");
        }
    }

    public static String get_command(InputUtil.Key[] keys) {
        assert keys != null;
        TrieNode node = mappings.search(keys, true);

        if (node == null) {
            return null;
        }

        return node.get_value();
    }

    public static TrieNode search(InputUtil.Key[] keys) {
        if (keys == null) {
            return null;
        }

        return mappings.search(keys, false);
    }

    public static TrieNode get_child(TrieNode node, InputUtil.Key key) {
        if (node == null) {
            assert mappings.get_head() != null;
            return mappings.get_head().get_child(key);
        } else {
            return node.get_child(key);
        }
    }

    public static int get_possibilities(InputUtil.Key[] keys) {
        TrieNode node = search(keys);
        if (node == null) {
            return 0;
        }

        return node.child_elements;
    }

    public static List<TrieNode> get_all_keymaps() {
        return mappings.get_head().get_children();
    }

    private static final Codec<List<KeymapEntry>> KEYMAP_LIST_CODEC = KeymapEntry.CODEC.listOf();

    public static void save() {
        List<KeymapEntry> entries = get_all_keymaps().stream()
                .map(node -> new KeymapEntry(node.get_key(), node.get_value()))
                .toList();

        DataResult<JsonElement> result = KEYMAP_LIST_CODEC.encodeStart(JsonOps.INSTANCE, entries);
        result.resultOrPartial(logger::error).ifPresent(AHMClient.config::write_to_keymap);
    }

    public static void load() {
        if (!AHMClient.config.keymap_config.toFile().exists()) {
            return;
        }

        mappings = new Trie();

        try (FileReader reader = new FileReader(AHMClient.config.keymap_config.toFile())) {
            JsonElement json = JsonParser.parseReader(reader);

            DataResult<List<KeymapEntry>> result = KEYMAP_LIST_CODEC.parse(JsonOps.INSTANCE, json);
            List<KeymapEntry> entries = result.resultOrPartial(logger::error).orElseGet(List::of);

            for (KeymapEntry entry : entries) {
                mappings.insert(entry.keys(), entry.value());
            }

        } catch (IOException e) {
            logger.error("Error occurred while trying to load keymap config");
        }
    }

}
