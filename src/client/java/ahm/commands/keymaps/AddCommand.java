package ahm.commands.keymaps;

import ahm.keymaps.KeymapRecorder;
import ahm.keymaps.KeymapsManager;
import ahm.utils.KeysUtil;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.List;

import static ahm.AHMClient.chat;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class AddCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> register() {
        return literal("add")
                .then(argument("message", StringArgumentType.greedyString())
                        .executes(AddCommand::run));
    }

    private static int run(CommandContext<FabricClientCommandSource> context) {
        assert MinecraftClient.getInstance().player != null;

        String message = StringArgumentType.getString(context, "message");

        KeymapRecorder.record((InputUtil.Key[] keys) -> {
            if (keys == null) {
                chat("Is null");
            } else if (keys.length == 0) {
                chat("Is empty");
            } else {
                KeymapsManager.add(keys, message);
                chat("Added: \"" + KeymapsManager.get_command(keys) + "\" at [" + KeysUtil.keys_to_text(keys).getString() + "]");
            }
        });

        return 0;
    }
}
