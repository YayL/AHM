package ahm.screens.keymaplist;

import ahm.keymaps.KeymapsManager;
import ahm.utils.KeysUtil;
import ahm.utils.TrieNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ahm.AHMClient.chat;

public class KeymapListScreenWidget extends ElementListWidget<KeymapListScreenWidget.KeymapListElement> {

    public KeymapListScreenWidget(MinecraftClient client, KeymapListScreen parent, List<TrieNode> keymaps) {
        // last parameter is item_height
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 20);

        for (TrieNode node : keymaps) {
            this.addEntry(new KeymapListElement(node));
        }
    }

    public class KeymapListElement extends ElementListWidget.Entry<KeymapListElement> {

        private final Text message;

        public KeymapListElement(TrieNode node) {
            this.message = Text.of(node.get_value());
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
            context.drawTextWithShadow(KeymapListScreenWidget.this.client.textRenderer, this.message, x, y + entryHeight / 2, Colors.WHITE);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }
    }
}
