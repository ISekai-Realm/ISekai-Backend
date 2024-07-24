package net.biryeongtrain.isekaibackend.config;

import xyz.nucleoid.fantasy.RuntimeWorldConfig;

import java.util.List;
import java.util.UUID;

public interface RuntimeWorldInfoAccessor {
    RuntimeWorldConfig iseaki_backend$getConfig();
    List<UUID> iseaki_backend$getPlayers();
}
