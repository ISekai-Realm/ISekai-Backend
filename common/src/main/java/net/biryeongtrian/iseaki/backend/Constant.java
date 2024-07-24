package net.biryeongtrian.iseaki.backend;

import me.mrnavastar.protoweaver.api.protocol.CompressionType;
import me.mrnavastar.protoweaver.api.protocol.Protocol;
import me.mrnavastar.protoweaver.api.protocol.velocity.VelocityAuth;
import net.biryeongtrian.iseaki.backend.networking.ISekaiHoldingDataInfo;
import net.biryeongtrian.iseaki.backend.networking.RequestInstanceInfo;
import net.biryeongtrian.iseaki.backend.networking.Setting;

import java.util.List;
import java.util.UUID;

public class Constant {
    public static final String ISEKAI_WORLD_BACKEND_NAME = "ISekai World Backend";
    public static final String ISEKAI_WORLD_BACKEND_ID = "isekai_backend";
    public static final String ISEKAI_WORLD_BACKEND_VERSION = "debug-build";
    public static final String ISEKAI_AUTHOR = "biryeongtrain06";
    public static final String[] DEPENDENCIES = new String[1];
    public static final UUID ISEKAI_WORLD_BACKEND_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static final Protocol.Builder PROTOCOL = Protocol.create(ISEKAI_WORLD_BACKEND_ID, "world")
            .setServerAuthHandler(VelocityAuth.class)
            .setClientAuthHandler(VelocityAuth.class)
            .setCompression(CompressionType.GZIP)
            .setMaxPacketSize(16777216)
            .setMaxConnections(20)
            .addPacket(Setting.class)
            .addPacket(ISekaiHoldingDataInfo.class)
            .addPacket(RequestInstanceInfo.class)
            ;

}
