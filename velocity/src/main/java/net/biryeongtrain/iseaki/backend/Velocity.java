package net.biryeongtrain.iseaki.backend;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.mrnavastar.protoweaver.api.ProtoConnectionHandler;
import me.mrnavastar.protoweaver.api.ProtoWeaver;
import me.mrnavastar.protoweaver.api.netty.ProtoConnection;
import net.biryeongtrian.iseaki.backend.Constant;
import net.biryeongtrian.iseaki.backend.networking.ISekaiHoldingDataInfo;
import net.biryeongtrian.iseaki.backend.networking.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.LogManager;

public class Velocity implements ProtoConnectionHandler {
    private static final ConcurrentHashMap<InetSocketAddress, ISekaiHoldingDataInfo> data = new ConcurrentHashMap<>();
    private final Logger logger = ISekaiProxy.getLogger();


    @Override
    public void onReady(ProtoConnection connection) {
        logger.info("Connected to ISekai Backend");
    }

    @Override
    public void onDisconnect(ProtoConnection connection) {
        ProtoConnectionHandler.super.onDisconnect(connection);
    }

    @Override
    public void handlePacket(ProtoConnection connection, Object packet) {
        logger.info("Received packet: {}", packet);

        switch (packet) {
            case Setting s -> {
                logger.info("Setting packet received: {}", s.getId());
            }
            default -> logger.info("Unknown packet: {}", packet);
        }
    }


}
