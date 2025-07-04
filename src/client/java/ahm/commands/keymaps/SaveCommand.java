package ahm.commands.keymaps;

import ahm.keymaps.KeymapsManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class SaveCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> register() {
        return literal("save").executes(SaveCommand::run);
    }

    public static int run(CommandContext<FabricClientCommandSource> context) {
        KeymapsManager.save();
        return 0;
    }

    // public static String keys_to_string

}
