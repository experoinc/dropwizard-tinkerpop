package com.experoinc.dropwizard.tinkerpop.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.ser.AbstractGraphSONMessageSerializerV1d0;
import org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerGremlinV1d0;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ted Wilmes
 */
@JsonTypeName("graphSONMessageSerializerGremlinV1d0")
public class GraphSONMessageSerializerGremlinV1d0Factory implements MessageSerializerFactory {

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
        config.put(AbstractGraphSONMessageSerializerV1d0.TOKEN_IO_REGISTRIES, ioRegistries);

        GraphSONMessageSerializerGremlinV1d0 serializer = new GraphSONMessageSerializerGremlinV1d0();
        serializer.configure(config, null);
        return serializer;
    }
}

