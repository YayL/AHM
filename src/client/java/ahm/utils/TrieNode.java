package ahm.utils;

import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ahm.AHMClient.chat;

public class TrieNode {
    private InputUtil.Key[] keys = null;
    private String value = null;
    private HashMap<InputUtil.Key, TrieNode> children = null;

    public int child_elements = 0; // Total children below this branch

    public boolean has_child(InputUtil.Key key) {
        return children != null && children.containsKey(key);
    }

    public List<TrieNode> get_children() {
        if (child_elements == 0) {
            return List.of(this);
        }

        return children.values().stream()
                .flatMap(node -> node.get_children().stream())
                .toList();
    }

    public void add_child(InputUtil.Key key, TrieNode value) {
        if (children == null) {
            children = new HashMap<>();
        }

        children.put(key, value);
    }

    public TrieNode get_child(InputUtil.Key key) {
        if (key == null || children == null) {
            return null;
        }

        return children.get(key);
    }

    public TrieNode get_first_child() {
        return (TrieNode) children.values().toArray()[0];
    }

    public void set_key(InputUtil.Key[] key) {
        this.keys = key;
    }

    public void set_key(List<InputUtil.Key> key) {
        set_key(KeysUtil.keys_list_to_arr(key));
    }

    public InputUtil.Key[] get_key() {
        return keys;
    }

    public void set_value(String value) {
        this.value = value;
    }

    public String get_value() {
        return value;
    }
}
