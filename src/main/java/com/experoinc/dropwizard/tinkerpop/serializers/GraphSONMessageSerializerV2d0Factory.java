package com.experoinc.dropwizard.tinkerpop.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.ser.AbstractGraphSONMessageSerializerV2d0;
import org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerGremlinV2d0;
import org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerV2d0;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ted Wilmes
 */
@JsonTypeName("graphSONMessageSerializerV2d0")
public class GraphSONMessageSerializerV2d0Factory implements MessageSerializerFactory {

    @NotNull
    private List<String> ioRegistries = Collections.emptyList();

    @JsonProperty
    public List<String> getIoRegistries() {
        return ioRegistries;
    }

    @JsonProperty
    public void setIoRegistries(List<String> ioRegistries) {
        this.ioRegistries = ioRegistries;
    }

    @Override
    public MessageSerializer build() {
        Map<String, Object> config = new HashMap<>();
        config.put(AbstractGraphSONMessageSerializerV2d0.TOKEN_IO_REGISTRIES, ioRegistries);

        GraphSONMessageSerializerV2d0 serializer = new GraphSONMessageSerializerV2d0();
        serializer.configure(config, null);
        return serializer;
    }
}