package net.biryeongtrian.iseaki.backend.networking;

import lombok.Getter;
import lombok.Setter;
import net.biryeongtrian.iseaki.backend.Constant;

public class Setting {
    @Getter
    @Setter
    private int id = -1;
    @Getter
    @Setter
    private boolean isDebugMode = Constant.ISEKAI_WORLD_BACKEND_VERSION.equals("debug-build");
}
