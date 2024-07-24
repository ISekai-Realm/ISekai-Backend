package net.biryeongtrain.isekaibackend.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.nucleoid.fantasy.Fantasy;

public class OpenPersistentWorldCommand {
    public static int execute(CommandContext<ServerCommandSource> ctx, String name, String requester) {
        Fantasy fantasy = Fantasy.get(ctx.getSource().getServer());
        fantasy.getOrOpenPersistentWorld(name, );

    }
}
