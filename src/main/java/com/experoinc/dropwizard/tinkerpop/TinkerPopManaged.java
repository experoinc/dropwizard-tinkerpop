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

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.util.Duration;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Chris Pounds
 */
public class TinkerPopManaged implements Managed {

    private static final Logger LOG = LoggerFactory.getLogger(TinkerPopManaged.class);

    private final Cluster cluster;
    private final Duration shutdownTimeout;

    public TinkerPopManaged(Cluster cluster, Duration shutdownTimeout) {
        this.cluster = cluster;
        this.shutdownTimeout = shutdownTimeout;
    }

    @Override
    public void start() throws Exception {
        // no-op
    }

    @Override
    public void stop() throws Exception {
        LOG.info("Attempting to shutdown TinkerPop cluster connection.");

        CompletableFuture<Void> future = cluster.closeAsync();
        try {
            future.get(shutdownTimeout.toMilliseconds(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException ex) {
            LOG.warn("Unable to close TinkerPop cluster after {}", shutdownTimeout);
        }
    }
}
