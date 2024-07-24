package net.biryeongtrain.isekaibackend.utils;

import net.biryeongtrain.isekaibackend.ISekai_Backend;
import net.biryeongtrain.isekaibackend.config.RuntimeWorldInfoAccessor;
import net.biryeongtrain.isekaibackend.instance.InstanceInfo;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.apache.commons.io.IOUtils;
import xyz.nucleoid.fantasy.RuntimeWorld;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstanceManager {
    public static InstanceInfo getOrCreateInstanceInfo(ServerPlayerEntity requester, String worldName, Identifier biomeId) {
        Path path = ISekai_Backend.config.dataPath.resolve(worldName + ".json");
        try {
            InstanceInfo info;
            File file = new File(path.toUri());

            if (file.exists()) {
                String json = IOUtils.toString(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

                info = BaseGson.GSON.fromJson(json, InstanceInfo.class);
            } else {
                List<UUID> list = new ArrayList<>();
                list.add(requester.getUuid());
                info = InstanceInfo.getFreshInstanceInfo(worldName, biomeId);
                info.players().add(requester.getUuid());
            }
            saveInfo(info);
            return info;
        } catch (IOException e) {
            ISekai_Backend.LOGGER.error("Something went wrong while reading instance info!", e);
            try {
                Files.copy(path, path.getParent().resolve(worldName + ".json.bak"));
            } catch (IOException ex) {
                ISekai_Backend.LOGGER.error("Something went wrong while backing up instance info!", ex);
            }
            var info = InstanceInfo.getFreshInstanceInfo(worldName, biomeId);
            info.players().add(requester.getUuid());
            return info;
        }
    }

    public static void saveInstanceInfo(String worldName, RuntimeWorldHandle handle) {
        RuntimeWorld world = (RuntimeWorld) handle.asWorld();
        RuntimeWorldInfoAccessor accessor = (RuntimeWorldInfoAccessor) world;
        var config = accessor.iseaki_backend$getConfig();

        var rainStatus = config.isThundering() ? 2 : config.isRaining() ? 1 : 0;
        var info = new InstanceInfo(
                worldName,
                world.getChunkManager().getChunkGenerator().getBiomeSource().getBiome(-1, -1, -1, null).getKey().get().getValue(),
                accessor.iseaki_backend$getPlayers(),
                rainStatus,
                config.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE),
                config.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE),
                world.getTimeOfDay()
        );

        saveInfo(info);
    }

    public static void saveInfo(InstanceInfo info) {
        try {
            File file = new File(ISekai_Backend.config.dataPath.resolve(info.instanceName() + ".json").toUri());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            writer.write(BaseGson.GSON.toJson(info));
            writer.close();
        } catch (Exception e) {
            ISekai_Backend.LOGGER.error("Something went wrong while saving instance info!");
            e.printStackTrace();
        }
    }
}
