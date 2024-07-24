package net.biryeongtrain.isekaibackend.mixin;

import net.biryeongtrain.isekaibackend.config.RuntimeWorldInfoAccessor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nucleoid.fantasy.RuntimeWorld;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;

import java.util.List;
import java.util.UUID;

@Mixin(RuntimeWorld.class)
public class RuntimeWorldMixin implements RuntimeWorldInfoAccessor {
    @Unique
    private RuntimeWorldConfig config;
    @Unique
    private List<UUID> players;
    @Inject(method ="<init>(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/registry/RegistryKey;Lxyz/nucleoid/fantasy/RuntimeWorldConfig;Lxyz/nucleoid/fantasy/RuntimeWorld$Style;)V", at = @At("TAIL"))
    private void onInit(MinecraftServer server, RegistryKey registryKey, RuntimeWorldConfig config, RuntimeWorld.Style style, CallbackInfo ci) {
        this.config = config;
    }

    @Override
    public RuntimeWorldConfig iseaki_backend$getConfig() {
        return this.config;
    }

    @Override
    public List<UUID> iseaki_backend$getPlayers() {
        return this.players;
    }
}
