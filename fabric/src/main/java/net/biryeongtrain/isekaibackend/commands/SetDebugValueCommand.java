package net.biryeongtrain.isekaibackend.commands;

import com.mojang.brigadier.context.CommandContext;
import net.biryeongtrain.isekaibackend.protocol.ISekaiProtocolServerHandler;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SetDebugValueCommand {
    public static int execute(CommandContext<ServerCommandSource> ctx, boolean value) {
        ISekaiProtocolServerHandler.getSetting().setDebugMode(value);

        ctx.getSource().sendFeedback(() -> Text.literal(value ? "Debug mode enabled" : "Debug mode disabled"), true);

        ISekaiProtocolServerHandler.updateSetting();
        return 1;
    }
}
