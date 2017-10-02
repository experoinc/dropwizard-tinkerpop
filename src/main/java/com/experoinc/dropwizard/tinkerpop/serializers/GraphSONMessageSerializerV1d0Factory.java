package com.experoinc.dropwizard.tinkerpop.serializers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerV1d0;

/**
 * @author Ted Wilmes
 */
@JsonTypeName("graphSONMessageSerializerV1d0")
public class GraphSONMessageSerializerV1d0Factory implements MessageSerializerFactory {

    @Override
    public MessageSerializer build() {
        GraphSONMessageSerializerV1d0 serializer = new GraphSONMessageSerializerV1d0();

        return serializer;
    }
}
