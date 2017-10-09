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

package com.experoinc.dropwizard.tinkerpop;

import com.experoinc.dropwizard.tinkerpop.auth.AuthFactory;
import com.experoinc.dropwizard.tinkerpop.serializers.GryoMessageSerializerV1d0Factory;
import com.experoinc.dropwizard.tinkerpop.serializers.MessageSerializerFactory;
import com.experoinc.dropwizard.tinkerpop.ssl.SslConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import io.dropwizard.validation.MinDuration;
import org.apache.tinkerpop.gremlin.driver.Channelizer;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Chris Pounds
 */
public class TinkerPopFactory {

    private static final int DEFAULT_PORT = 8182;

    @NotEmpty
    private String[] contactPoints;

    @Min(1)
    private int port = DEFAULT_PORT;

    @Valid
    private MessageSerializerFactory serializer = new GryoMessageSerializerV1d0Factory();

    @Min(1)
    private int nioPoolSize = Runtime.getRuntime().availableProcessors();

    @Min(1)
    private int workerPoolSize = Runtime.getRuntime().availableProcessors() * 2;

    @Min(1)
    private int minConnectionPoolSize = 2;

    @Min(1)
    private int maxConnectionPoolSize = 8;

    @Min(1)
    private int minSimultaneousUsagePerConnection = 8;

    @Min(1)
    private int maxSimultaneousUsagePerConnection = 16;

    @Min(1)
    private int maxInProcessPerConnection = 4;

    @Min(1)
    private int minInProcessPerConnection = 1;

    @NotNull @MinDuration(value = 1, unit = TimeUnit.MILLISECONDS)
    private Duration maxWaitForConnection = Duration.milliseconds(3000);

    @NotNull @MinDuration(value = 1, unit = TimeUnit.MILLISECONDS)
    private Duration maxWaitForSessionClose = Duration.milliseconds(3000);

    @Min(1)
    private int maxContentLength = 65536;

    @NotNull @MinDuration(value = 1, unit = TimeUnit.MILLISECONDS)
    private Duration reconnectInterval = Duration.milliseconds(1000);

    @Min(1)
    private int resultIterationBatchSize = 64;

    @NotNull @MinDuration(value = 1, unit = TimeUnit.MILLISECONDS)
    private Duration keepAliveInterval = Duration.minutes(30);

    @NotEmpty
    private String channelizer = Channelizer.WebSocketChannelizer.class.getName();

    @Valid
    private Optional<AuthFactory> authFactory = Optional.empty();

    @NotNull @Valid
    private SslConfiguration ssl = new SslConfiguration();

    @NotEmpty
    private String validationQuery = "g.V().hasNext()";

    @NotNull @MinDuration(value = 1, unit = TimeUnit.MILLISECONDS)
    private Duration validationQueryTimeout = Duration.seconds(10);

//    TODO: Implement factories to support this
//    private SslContext sslContext = null;
//    private LoadBalancingStrategy loadBalancingStrategy = new LoadBalancingStrategy.RoundRobin();

    @NotNull
    private Duration shutdownTimeout = Duration.seconds(60);

    @JsonProperty
    public String[] getContactPoints() {
        return contactPoints;
    }

    @JsonProperty
    public void setContactPoints(String[] contactPoints) {
        this.contactPoints = contactPoints;
    }

    @JsonProperty
    public int getPort() {
        return port;
    }

    @JsonProperty
    public void setPort(int port) {
        this.port = port;
    }

    @JsonProperty
    public MessageSerializerFactory getSerializer() {
        return serializer;
    }

    @JsonProperty
    public Duration getShutdownTimeout() {
        return shutdownTimeout;
    }

    @JsonProperty
    public void setShutdownTimeout(Duration shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    @JsonProperty
    public void setSerializer(MessageSerializerFactory serializer) {
        this.serializer = serializer;
    }

    @JsonProperty
    public int getNioPoolSize() {
        return nioPoolSize;
    }

    @JsonProperty
    public void setNioPoolSize(int nioPoolSize) {
        this.nioPoolSize = nioPoolSize;
    }

    @JsonProperty
    public int getWorkerPoolSize() {
        return workerPoolSize;
    }

    @JsonProperty
    public void setWorkerPoolSize(int workerPoolSize) {
        this.workerPoolSize = workerPoolSize;
    }

    @JsonProperty
    public int getMinConnectionPoolSize() {
        return minConnectionPoolSize;
    }

    @JsonProperty
    public void setMinConnectionPoolSize(int minConnectionPoolSize) {
        this.minConnectionPoolSize = minConnectionPoolSize;
    }

    @JsonProperty
    public int getMaxConnectionPoolSize() {
        return maxConnectionPoolSize;
    }

    @JsonProperty
    public void setMaxConnectionPoolSize(int maxConnectionPoolSize) {
        this.maxConnectionPoolSize = maxConnectionPoolSize;
    }

    @JsonProperty
    public int getMinSimultaneousUsagePerConnection() {
        return minSimultaneousUsagePerConnection;
    }

    @JsonProperty
    public void setMinSimultaneousUsagePerConnection(int minSimultaneousUsagePerConnection) {
        this.minSimultaneousUsagePerConnection = minSimultaneousUsagePerConnection;
    }

    @JsonProperty
    public int getMaxSimultaneousUsagePerConnection() {
        return maxSimultaneousUsagePerConnection;
    }

    @JsonProperty
    public void setMaxSimultaneousUsagePerConnection(int maxSimultaneousUsagePerConnection) {
        this.maxSimultaneousUsagePerConnection = maxSimultaneousUsagePerConnection;
    }

    @JsonProperty
    public int getMaxInProcessPerConnection() {
        return maxInProcessPerConnection;
    }

    @JsonProperty
    public void setMaxInProcessPerConnection(int maxInProcessPerConnection) {
        this.maxInProcessPerConnection = maxInProcessPerConnection;
    }

    @JsonProperty
    public int getMinInProcessPerConnection() {
        return minInProcessPerConnection;
    }

    @JsonProperty
    public void setMinInProcessPerConnection(int minInProcessPerConnection) {
        this.minInProcessPerConnection = minInProcessPerConnection;
    }

    @JsonProperty
    public Duration getMaxWaitForConnection() {
        return maxWaitForConnection;
    }

    @JsonProperty
    public void setMaxWaitForConnection(Duration maxWaitForConnection) {
        this.maxWaitForConnection = maxWaitForConnection;
    }

    @JsonProperty
    public Duration getMaxWaitForSessionClose() {
        return maxWaitForSessionClose;
    }

    @JsonProperty
    public void setMaxWaitForSessionClose(Duration maxWaitForSessionClose) {
        this.maxWaitForSessionClose = maxWaitForSessionClose;
    }

    @JsonProperty
    public int getMaxContentLength() {
        return maxContentLength;
    }

    @JsonProperty
    public void setMaxContentLength(int maxContentLength) {
        this.maxContentLength = maxContentLength;
    }

    @JsonProperty
    public Duration getReconnectInterval() {
        return reconnectInterval;
    }

    @JsonProperty
    public void setReconnectInterval(Duration reconnectInterval) {
        this.reconnectInterval = reconnectInterval;
    }

    @JsonProperty
    public int getResultIterationBatchSize() {
        return resultIterationBatchSize;
    }

    @JsonProperty
    public void setResultIterationBatchSize(int resultIterationBatchSize) {
        this.resultIterationBatchSize = resultIterationBatchSize;
    }

    @JsonProperty
    public Duration getKeepAliveInterval() {
        return keepAliveInterval;
    }

    @JsonProperty
    public void setKeepAliveInterval(Duration keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    @JsonProperty
    public String getChannelizer() {
        return channelizer;
    }

    @JsonProperty
    public void setChannelizer(String channelizer) {
        this.channelizer = channelizer;
    }

    @JsonProperty("auth")
    public Optional<AuthFactory> getAuthFactory() {
        return authFactory;
    }

    @JsonProperty("auth")
    public void setAuthFactory(Optional<AuthFactory> authFactory) {
        this.authFactory = authFactory;
    }

    @JsonProperty
    public SslConfiguration getSsl() {
        return ssl;
    }

    @JsonProperty
    public void setSsl(SslConfiguration ssl) {
        this.ssl = ssl;
    }

    @JsonProperty
    public String getValidationQuery() {
        return validationQuery;
    }

    @JsonProperty
    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    @JsonProperty
    public Duration getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    @JsonProperty
    public void setValidationQueryTimeout(Duration validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    protected Cluster build() {
        Cluster.Builder builder = Cluster.build()
                .addContactPoints(contactPoints)
                .port(port)
                .serializer(serializer.build())
                .authProperties(authFactory.map(af -> af.build()).orElse(null))
                .channelizer(channelizer)
                // ssl settings
                .enableSsl(ssl.isEnabled())
                .keyCertChainFile(ssl.getKeyCertChainFile())
                .keyFile(ssl.getKeyFile())
                .keyPassword(ssl.getKeyPassword())
                .trustCertificateChainFile(ssl.getTrustCertChainFile())
                // pool settings
                .nioPoolSize(nioPoolSize)
                .workerPoolSize(workerPoolSize)
                .maxConnectionPoolSize(maxConnectionPoolSize)
                .maxWaitForConnection((int)maxWaitForConnection.toMilliseconds())
                .maxWaitForSessionClose((int)maxWaitForSessionClose.toMilliseconds())
                .maxInProcessPerConnection(maxInProcessPerConnection)
                .maxSimultaneousUsagePerConnection(maxSimultaneousUsagePerConnection)
                .keepAliveInterval(keepAliveInterval.toMilliseconds())
                .reconnectInterval((int)reconnectInterval.toMilliseconds())
                .resultIterationBatchSize(resultIterationBatchSize)
                .maxContentLength(maxContentLength);

        Cluster cluster = builder.create();

        return cluster;
    }

    @JsonIgnore
    public Cluster build(Environment environment) {
        final Cluster cluster = build();

        environment.lifecycle().manage(new TinkerPopManaged(cluster, getShutdownTimeout()));
        environment.healthChecks().register("tinkerpop", new TinkerPopHealthCheck(cluster, validationQuery, validationQueryTimeout));

        return cluster;
    }
}
