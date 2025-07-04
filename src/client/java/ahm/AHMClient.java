package ahm;

import ahm.keymaps.KeymapRecorder;
import ahm.keymaps.KeymapSearch;
import ahm.commands.keymaps.*;
import ahm.keymaps.KeymapsManager;
import ahm.utils.ConfigIO;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static ahm.AHM.logger;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class AHMClient implements ClientModInitializer {
	public static final String keybind_category = "A Hypixel Mod";
	public static final String mod_id = "AHM";

	public static final ConfigIO config = new ConfigIO(mod_id);

	@Override
	public void onInitializeClient() {
		KeymapRecorder.register();
		KeymapSearch.register();

		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			LiteralArgumentBuilder<FabricClientCommandSource> keybind_command = literal("keymap");

			keybind_command.then(AddCommand.register());
			keybind_command.then(ListCommand.register());
			keybind_command.then(SaveCommand.register());
			keybind_command.then(LoadCommand.register());

			dispatcher.register(keybind_command);
		}));

		KeymapsManager.load();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				KeymapsManager.save();
				logger.info("Saved before exit");
			}
		});
	}

	public static void chat(String message) {
		chat(message, false);
	}

	public static void chat(String message, boolean overlay) {
		assert MinecraftClient.getInstance().player != null;
		MinecraftClient.getInstance().player.sendMessage(Text.literal(message), overlay);
	}
}