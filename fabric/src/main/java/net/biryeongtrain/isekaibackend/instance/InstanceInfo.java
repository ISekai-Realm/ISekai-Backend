package net.biryeongtrain.isekaibackend.instance;

import net.biryeongtrain.isekaibackend.protocol.ISekaiProtocolServerHandler;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record InstanceInfo(
        String instanceName,
        Identifier biomeID,
        List<UUID> players,
        int weatherStatus,
        boolean shouldTimeChange,
        boolean shouldWeatherChange,
        long timeOfDay
) {
    public RuntimeWorldConfig getConfig() {
        var registryManager = ISekaiProtocolServerHandler.server.getRegistryManager();
        var optionalBiomeEntry = registryManager.get(RegistryKeys.BIOME).getEntry(biomeID);

        if (optionalBiomeEntry.isEmpty()) {
            throw new RuntimeException("Biome not found");
        }

        var source = new FixedBiomeSource(optionalBiomeEntry.get());
        var generator = new NoiseChunkGenerator(source, registryManager.get(RegistryKeys.CHUNK_GENERATOR_SETTINGS).getEntry(Identifier.ofVanilla("overworld")).get());

        var config =  new RuntimeWorldConfig()
                .setGameRule(GameRules.DO_MOB_SPAWNING, false)
                .setGameRule(GameRules.DO_WEATHER_CYCLE, shouldWeatherChange)
                .setGameRule(GameRules.DO_DAYLIGHT_CYCLE, shouldTimeChange)
                .setGenerator(generator)
                .setDifficulty(Difficulty.HARD)
                .setFlat(false)
                .setTimeOfDay(timeOfDay)
                ;

        switch (weatherStatus) {
            case 0 -> config.setSunny(Integer.MAX_VALUE);
            case 1 -> config.setRaining(true);
            case 2 -> config.setThundering(true);
        }

        return config;
    }

    public static InstanceInfo getFreshInstanceInfo(String instanceName, Identifier biomeID) {
        return new InstanceInfo(instanceName, biomeID, new ArrayList<>(), 0, true, true, 0L);
    }
}