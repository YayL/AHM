package ahm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static ahm.AHM.logger;

public class ConfigIO {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public final Path keymap_config;

    public ConfigIO(String modId) {
        Path config_dir = FabricLoader.getInstance().getConfigDir();
        Path mod_config_dir = config_dir.resolve(modId);
        keymap_config = mod_config_dir.resolve("keymaps.ahm");

        try {
            Files.createDirectories(mod_config_dir);
        } catch (IOException e) {
            logger.error("Failed to create config directory: " + mod_config_dir, e);
        }
    }

    public void write_to_keymap(JsonElement json) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                keymap_config,
                StandardCharsets.UTF_8
        )) {
            GSON.toJson(json, writer);
            logger.info("Updated keymaps config");
        } catch (IOException e) {
            logger.error("Failed to update keymaps config", e);
        }
    }

}
