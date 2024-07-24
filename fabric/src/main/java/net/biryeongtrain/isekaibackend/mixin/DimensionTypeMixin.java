package net.biryeongtrain.isekaibackend.mixin;

import net.biryeongtrain.isekaibackend.ISekai_Backend;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
    @Inject(method = "getSaveDirectory", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    private static void redirectSaveDirectory(RegistryKey<World> worldRef, Path worldDirectory, CallbackInfoReturnable<Path> cir) {
        if (worldRef != World.NETHER) {
            cir.setReturnValue(ISekai_Backend.config.savePath.resolve(worldRef.getValue().getNamespace()).resolve(worldRef.getValue().getPath()));
        }
    }
}
