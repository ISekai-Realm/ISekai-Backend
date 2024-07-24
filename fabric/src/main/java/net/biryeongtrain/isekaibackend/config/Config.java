package net.biryeongtrain.isekaibackend.config;

import com.google.gson.annotations.SerializedName;
import net.biryeongtrain.isekaibackend.ISekai_Backend;
import net.biryeongtrain.isekaibackend.protocol.ISekaiProtocolServerHandler;
import net.biryeongtrain.isekaibackend.utils.BaseGson;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    public Path savePath = ISekaiProtocolServerHandler.server.getRunDirectory().toAbsolutePath().getParent();
    public Path dataPath = savePath.resolve("worldData");
    public boolean isLobby = false;

    public static Config loadOrCreateConfig() {
        try {
            Config config;
            File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "isekai_backend.json");

            if (configFile.exists()) {
                String json = IOUtils.toString(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));

                config = BaseGson.GSON.fromJson(json, Config.class);
            } else {
                return new Config();
            }

            saveConfig(config);
            return config;
        } catch (IOException e) {
            ISekai_Backend.LOGGER.error("Something went wrong while reading config!", e);
            return new Config();
        }
    }

    public static void saveConfig(Config config) {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "serveruifix.json");
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8));
            writer.write(BaseGson.GSON.toJson(config));
            writer.close();
        } catch (Exception e) {
            ISekai_Backend.LOGGER.error("Something went wrong while saving config!");
            e.printStackTrace();
        }
    }
}
