package net.biryeongtrain.iseaki.backend;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.biryeongtrian.iseaki.backend.Constant;
import org.slf4j.Logger;

@Plugin(
        id = Constant.ISEKAI_WORLD_BACKEND_ID,
        name = Constant.ISEKAI_WORLD_BACKEND_NAME,
        version = Constant.ISEKAI_WORLD_BACKEND_VERSION,
        authors = Constant.ISEKAI_AUTHOR,
        dependencies = {
                @Dependency(id = "protoweaver")
        }
)
public class ISekaiProxy {
    @Getter
    private static ProxyServer server;
    @Getter
    private static Logger logger;

    @Inject
    public ISekaiProxy(ProxyServer server, Logger logger) {
        ISekaiProxy.server = server;
        ISekaiProxy.logger = logger;
        Constant.PROTOCOL.setClientHandler(Velocity.class).load();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("ISekai Backend has been initialized");
    }


}
