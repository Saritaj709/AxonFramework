package com.poc.axon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.poc.axon.aggregate.OrderAggregate;
import com.poc.axon.command.ConfirmOrderCommand;
import com.poc.axon.command.PlaceOrderCommand;
import com.poc.axon.command.ShipOrderCommand;
import com.poc.axon.event.OrderConfirmedEvent;
import com.poc.axon.event.OrderPlacedEvent;
import com.poc.axon.event.OrderShippedEvent;

import java.util.UUID;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class AxonApplicationTests {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void giveNoPriorActivity_whenPlaceOrderCommand_thenShouldPublishOrderPlacedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.givenNoPriorActivity()
               .when(new PlaceOrderCommand(orderId, product))
               .expectEvents(new OrderPlacedEvent(orderId, product));
    }

    @Test
    public void givenOrderPlacedEvent_whenConfirmOrderCommand_thenShouldPublishOrderConfirmedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product))
               .when(new ConfirmOrderCommand(orderId))
               .expectEvents(new OrderConfirmedEvent(orderId));
    }

    @Test
    public void givenOrderPlacedEvent_whenShipOrderCommand_thenShouldThrowIllegalStateException() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product))
               .when(new ShipOrderCommand(orderId))
               .expectException(IllegalStateException.class);
    }

    @Test
    public void givenOrderPlacedEventAndOrderConfirmedEvent_whenShipOrderCommand_thenShouldPublishOrderShippedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
               .when(new ShipOrderCommand(orderId))
               .expectEvents(new OrderShippedEvent(orderId));
    }

}

