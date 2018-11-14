package com.example.swarmaxon;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.axonserver.connector.AxonServerConfiguration;
import org.axonframework.axonserver.connector.AxonServerConnectionManager;
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.serialization.json.JacksonSerializer;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class AxonServerConfigurer {

    @Produces
    @Singleton
    public EventStore getAxonServerEventStore() {
        log.debug("starting production of AxonServerEventStore");

        log.debug("instantiating AxonServerConfiguration");
        AxonServerConfiguration axonServerConfiguration = new AxonServerConfiguration();

        /* Might set many other props here. */
        axonServerConfiguration.setServers("localhost");
        axonServerConfiguration.setComponentName("swarm-axonserver");

        log.debug("instantiating AxonServerConnectionManager");
        AxonServerConnectionManager axonServerConnectionManager = new AxonServerConnectionManager(axonServerConfiguration);

        log.debug("instantiating Jackson ObjectMapper");
        ObjectMapper objectMapper = new ObjectMapper();

        log.debug("instantiating JacksonSerializer");
        JacksonSerializer serializer = JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .build();

        log.debug("instantiating AxonServerEventStore");
        AxonServerEventStore axonServerEventStore = AxonServerEventStore.builder()
                .configuration(axonServerConfiguration)
                .platformConnectionManager(axonServerConnectionManager)
                .eventSerializer(serializer)
                .build();

        return axonServerEventStore;
    }

}
