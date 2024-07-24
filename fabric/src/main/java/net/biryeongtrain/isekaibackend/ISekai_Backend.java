package net.biryeongtrain.isekaibackend;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.biryeongtrain.isekaibackend.commands.SetDebugValueCommand;
import net.biryeongtrain.isekaibackend.config.Config;
import net.biryeongtrain.isekaibackend.protocol.ISekaiProtocolServerHandler;
import net.biryeongtrian.iseaki.backend.Constant;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISekai_Backend implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("ISekai Backend");
    public static Config config;
    @Override
    public void onInitialize() {
        Constant.PROTOCOL.setServerHandler(ISekaiProtocolServerHandler.class).load();

        ServerLifecycleEvents.SERVER_STARTED.register(ISekaiProtocolServerHandler::setServer);
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> backendNode = CommandManager
                    .literal("isekai_backend")
                    .requires(source -> source.hasPermissionLevel(4))
                    .build();

            LiteralCommandNode<ServerCommandSource> setDebugNode = CommandManager
                    .literal("debug")
                    .requires(source -> source.hasPermissionLevel(4))
                    .then(CommandManager.argument("value", BoolArgumentType.bool())
                            .executes(ctx -> SetDebugValueCommand.execute(ctx, BoolArgumentType.getBool(ctx, "value"))))
                    .build();

            dispatcher.getRoot().addChild(backendNode);
            backendNode.addChild(setDebugNode);
        });

        config = Config.loadOrCreateConfig();
    }
}
