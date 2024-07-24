package net.biryeongtrian.iseaki.backend.networking;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.Serializer;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.esotericsoftware.kryo.kryo5.objenesis.strategy.StdInstantiatorStrategy;
import com.esotericsoftware.kryo.kryo5.util.DefaultInstantiatorStrategy;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
public class ISekaiHoldingDataInfo {
    @Getter
    private static final Kryo kryo = new Kryo();
    @Getter
    private final List<String> registeredDimensions = new ArrayList<>();
    @Getter
    private Date date;

    static {
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
    }

    public void put(String data) {
        registeredDimensions.add(data);
    }

    public boolean contains(String data) {
        return registeredDimensions.contains(data);
    }

    public boolean remove(String key) {
        return registeredDimensions.remove(key);
    }
}
