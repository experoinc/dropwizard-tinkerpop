package com.experoinc.dropwizard.tinkerpop;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ted Wilmes
 */
@RunWith(Parameterized.class)
public class TinkerPopFactoryTest {

    private final ObjectMapper objectMapper = Jackson.newObjectMapper();
    private final Validator validator = Validators.newValidator();
    private Client client;
    private GraphTraversalSource g;

    @Parameterized.Parameters
    public static Collection<Object[]> serializers() {
        return Arrays.asList(new Object[][] {
                {"gryov1d0.yml", true},
                {"graphsonv1d0.yml", false},
                {"graphsongremlinv1d0.yml", false},
                {"graphsongremlinv2d0.yml", true},
                {"secure.yml", false}
        });
    }

    @Parameterized.Parameter
    public String serializer;

    @Parameterized.Parameter(1)
    public boolean testBytecode;

    @Before
    public void setUp() throws URISyntaxException, IOException, ConfigurationException {
        connect();
        client.submit("g.V().drop()");
    }

    private void connect() throws URISyntaxException, IOException, ConfigurationException {
        final File yml = new File(Resources.getResource(serializer).toURI());
        final YamlConfigurationFactory<TinkerPopFactory> factory =
                new YamlConfigurationFactory<>(TinkerPopFactory.class, validator, objectMapper, "dw");
        final Cluster cluster = factory.build(yml).build();
        final Graph graph = EmptyGraph.instance();
        client = cluster.connect();
        g = graph.traversal().withRemote(DriverRemoteConnection.using(cluster, "g"));
    }

    @Test
    public void testReadWriteClient() throws ConfigurationException, IOException, URISyntaxException {
        final Long expected = client.submit("g.addV('Person').id()").one().getLong();
        final Long person = client.submit("g.V().hasLabel('Person').id()").one().getLong();
        assertEquals(expected, person);

        if(testBytecode) {
            client.submit("g.V().drop()");
            final Long remoteExpected = (Long) g.addV("Person").id().next();
            final Long remotePerson = (Long) g.V().hasLabel("Person").id().next();
            assertEquals(remoteExpected, remotePerson);
        }
    }
}
