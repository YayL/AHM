package ahm.screens.keymaplist;

import ahm.keymaps.KeymapsManager;
import ahm.utils.TrieNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;

import java.util.List;

import static ahm.AHMClient.chat;

public class KeymapListScreen extends Screen {

    private TextWidget headerWidget;
    private KeymapListScreenWidget keymap_list;
    public ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);

    public KeymapListScreen() {
        super(Text.literal("Keymap List"));
    }

    @Override
    protected void init() {
        List<TrieNode> keymaps = KeymapsManager.get_all_keymaps();
        headerWidget = layout.addHeader(new TextWidget( Text.of("Your keymaps(" + keymaps.size() + ")"), this.textRenderer));
        keymap_list = layout.addBody(new KeymapListScreenWidget(MinecraftClient.getInstance(), this, keymaps));
        layout.forEachChild(this::addDrawableChild);
    }

    @Override
    protected void refreshWidgetPositions() {
        layout.refreshPositions();
        keymap_list.position(this.width, layout);
        headerWidget.setPosition((this.width - headerWidget.getWidth()) / 2, (layout.getHeaderHeight() - headerWidget.getHeight()) / 2);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

}
