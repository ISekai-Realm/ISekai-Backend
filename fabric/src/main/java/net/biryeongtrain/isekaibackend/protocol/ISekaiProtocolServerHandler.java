package net.biryeongtrain.isekaibackend.protocol;

import lombok.Getter;
import me.mrnavastar.protoweaver.api.ProtoConnectionHandler;
import me.mrnavastar.protoweaver.api.netty.ProtoConnection;
import net.biryeongtrain.isekaibackend.ISekai_Backend;
import net.biryeongtrian.iseaki.backend.Constant;
import net.biryeongtrian.iseaki.backend.networking.ISekaiHoldingDataInfo;
import net.biryeongtrian.iseaki.backend.networking.RequestInstanceInfo;
import net.biryeongtrian.iseaki.backend.networking.Setting;
import net.minecraft.server.MinecraftServer;
import xyz.nucleoid.fantasy.Fantasy;

import java.util.Date;
import java.util.UUID;

public class ISekaiProtocolServerHandler implements ProtoConnectionHandler {
    @Getter
    private static final ISekaiHoldingDataInfo info = new ISekaiHoldingDataInfo(new Date());
    @Getter
    private static Setting setting = new Setting();
    public static MinecraftServer server;
    protected static ProtoConnection proxy;

    public static void setServer(MinecraftServer server) {
        ISekaiProtocolServerHandler.server = server;
    }

    @Override
    public void onReady(ProtoConnection connection) {
        proxy = connection;
        ISekai_Backend.LOGGER.info("Connected to ISekai Backend");
        connection.send(setting);

    }

    @Override
    public void onDisconnect(ProtoConnection connection) {
        ISekai_Backend.LOGGER.info("Disconnected from ISekai Backend");
    }

    @Override
    public void handlePacket(ProtoConnection connection, Object packet) {
        switch (packet) {
            case Setting s -> setting = s;
            case RequestInstanceInfo info -> {
                UUID requester = info.requester;
                if (requester.equals(Constant.ISEKAI_WORLD_BACKEND_UUID)) {
                    connection.send(setting);
                }
                connection.send(collect());
            }
            default -> {
                ISekai_Backend.LOGGER.info("Unknown packet {}, skipping...", packet);
            }
        }
    }

    public ISekaiProtocolServerHandler collect() {
        return null;
    }

    public static void init() {

    }

    public static void updateSetting() {
        proxy.send(setting);

    }
}
