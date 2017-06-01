/*
 * Copyright 2017 Expero, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.experoinc.dropwizard.tinkerpop.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.ser.AbstractGryoMessageSerializerV1d0;
import org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Chris Pounds
 */
@JsonTypeName("gryoMessageSerializerV1d0")
public class GryoMessageSerializerV1d0Factory implements MessageSerializerFactory {

    private boolean serializeResultToString = false;

    @Min(1)
    private int bufferSize = 4096;

    @NotNull
    private List<String> ioRegistries = Collections.emptyList();

    @NotNull
    private List<String> customClasses = Collections.emptyList();

    private Optional<String> classResolverSupplier = Optional.empty();

    @JsonProperty
    public boolean isSerializeResultToString() {
        return serializeResultToString;
    }

    @JsonProperty
    public void setSerializeResultToString(boolean serializeResultToString) {
        this.serializeResultToString = serializeResultToString;
    }

    @JsonProperty
    public int getBufferSize() {
        return bufferSize;
    }

    @JsonProperty
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @JsonProperty
    public List<String> getIoRegistries() {
        return ioRegistries;
    }

    @JsonProperty
    public void setIoRegistries(List<String> ioRegistries) {
        this.ioRegistries = ioRegistries;
    }

    @JsonProperty
    public List<String> getCustomClasses() {
        return customClasses;
    }

    @JsonProperty
    public void setCustomClasses(List<String> customClasses) {
        this.customClasses = customClasses;
    }

    @JsonProperty
    public Optional<String> getClassResolverSupplier() {
        return classResolverSupplier;
    }

    @JsonProperty
    public void setClassResolverSupplier(Optional<String> classResolverSupplier) {
        this.classResolverSupplier = classResolverSupplier;
    }

    @Override
    public MessageSerializer build() {
        Map<String, Object> config = new HashMap<>();
        config.put(AbstractGryoMessageSerializerV1d0.TOKEN_CUSTOM, customClasses);
        config.put(AbstractGryoMessageSerializerV1d0.TOKEN_BUFFER_SIZE, Integer.toString(bufferSize));
        config.put(AbstractGryoMessageSerializerV1d0.TOKEN_SERIALIZE_RESULT_TO_STRING, Boolean.toString(serializeResultToString));
        config.put(AbstractGryoMessageSerializerV1d0.TOKEN_IO_REGISTRIES, ioRegistries);
        classResolverSupplier.ifPresent(crs -> config.put(AbstractGryoMessageSerializerV1d0.TOKEN_CLASS_RESOLVER_SUPPLIER, crs));

        GryoMessageSerializerV1d0 serializer = new GryoMessageSerializerV1d0();
        serializer.configure(config, null);

        return serializer;
    }
}
