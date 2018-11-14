package com.example.swarmaxon;

import io.axoniq.axonserver.grpc.SerializedObject;
import io.axoniq.axonserver.grpc.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.axonserver.connector.AxonServerConfiguration;
import org.axonframework.axonserver.connector.event.AxonServerEventStoreClient;
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.serialization.Serializer;

import java.io.IOException;
import java.util.Collections;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
@Slf4j
public class EventLoggingServlet extends HttpServlet {

    @Inject
    private EventStore eventStore;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello! Your visit will be logged to the event store " + new java.util.Date());
        ServletVisitedEvt evt = new ServletVisitedEvt(req.getServletPath(), req.getRemoteAddr());
        eventStore.publish(
                GenericEventMessage.asEventMessage(evt)
                        /* Adding metadata just for illustration, not required. */
                        .withMetaData(Collections.singletonMap("migrationevent", "true"))
        );
    }

}