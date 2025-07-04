package ahm.commands.keymaps;

import ahm.screens.keymaplist.KeymapListScreen;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ListCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> register() {
        return literal("list").executes(ListCommand::run);
    }

    public static int run(CommandContext<FabricClientCommandSource> context) {
        MinecraftClient.getInstance().send(() -> {
            MinecraftClient.getInstance().setScreen(new KeymapListScreen());
        });
        return 0;
    }

    // public static String keys_to_string

}
