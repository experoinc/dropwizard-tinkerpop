# dropwizard-tinkerpop #

[![Build Status](https://travis-ci.org/experoinc/dropwizard-tinkerpop.svg?branch=master)](https://travis-ci.org/experoinc/dropwizard-tinkerpop)

A [Dropwizard][DW] library that enables connectivity to [Apache TinkerPop][TP] enabled gremlin 
servers.

## Components ##

* Configuration
* Health Check
* Managed Cluster

### Configuration ###

The configuration class includes all of the parameters required to configure a remote connection to
a TinkerPop server. The default values correspond to all of the defaults specified in the TinkerPop
cluster builder.

### Health Check ###

A health check is setup using the dropwizard health check registry. It executes the 
`validationQuery` specified in the configuration. It will succeed if the query responds within the
`validationQueryTimeout` specified.

### Managed Connection ###

The `Cluster` built from the `TinkerPopFactory` is included in dropwizard's managed lifecycle. This
will allow dropwizard to try and gracefully shut down the TinkerPop client connections on shutdown.

## Usage ##

Include the dropwizard-tinkerpop library as a dependency

```xml
<dependency>
  <groupId>com.experoinc</groupId>
  <artifactId>dropwizard-tinkerpop</artifactId>
  <version>${dropwizard-tinkerpop.version}</version>
</dependency>
```

Include a `TinkerPopFactory` instance in your application config

```java
public class AppConfig extends Configuration {

    @Valid
    @NotNull
    private TinkerPopFactory tinkerPopFactory = new TinkerPopFactory();

    @JsonProperty("tinkerPop")
    public TinkerPopFactory getTinkerPopFactory() {
        return tinkerPopFactory;
    }

    @JsonProperty("tinkerPop")
    public void setTinkerPopFactory(TinkerPopFactory tinkerPopFactory) {
        this.tinkerPopFactory = tinkerPopFactory;
    }
}
```

Build the TinkerPop cluster in your applications `run(AppConfig ac, Environment environment)` method.

```java
public class App extends Application<AppConfig> {
    
    @Override
    public void run(AppConfig configuration, Environment environment) throws Exception {
        Cluster cluster = configuration.getTinkerPopFactory().build(environment);
    }
}
```

[DW]: https://dropwizard.io
[TP]: http://tinkerpop.apache.org
