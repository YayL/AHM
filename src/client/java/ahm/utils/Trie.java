package ahm.utils;

import ahm.AHMClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.List;

import static ahm.AHMClient.chat;

public class Trie {
    private final TrieNode head = new TrieNode();

    public int size() {
        return head.child_elements;
    }

    public boolean insert(InputUtil.Key[] keys, String value) {
        TrieNode node = head;

        assert MinecraftClient.getInstance().player != null;
        for (InputUtil.Key key : keys) {
            node.child_elements += 1;

            if (!node.has_child(key)) {
                node.add_child(key, new TrieNode());
            }

            node = node.get_child(key);

            if (node.get_value() != null) {
                return false;
            }
        }

        node.set_key(keys);
        node.set_value(value);

        return true;
    }

    public TrieNode search(InputUtil.Key[] keys, boolean allow_shortcircuit) {
        TrieNode next = head;

        for (InputUtil.Key key : keys) {
            next = next.get_child(key);
            if (next == null) {
                return null;
            }
        }

        if (allow_shortcircuit) {
            while (next.child_elements == 1) {
                next = next.get_first_child();
            }
        }

        return next;
    }

    public TrieNode get_head() {
        return head;
    }
}
