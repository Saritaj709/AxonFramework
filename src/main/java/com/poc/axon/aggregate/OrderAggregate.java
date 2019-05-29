package com.poc.axon.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
//import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.io.Serializable;

import com.poc.axon.command.ConfirmOrderCommand;
import com.poc.axon.command.PlaceOrderCommand;
import com.poc.axon.command.ShipOrderCommand;
//import com.poc.axon.command.ConfirmOrderCommand;
//import com.poc.axon.command.PlaceOrderCommand;
//import com.poc.axon.command.ShipOrderCommand;
import com.poc.axon.event.OrderConfirmedEvent;
import com.poc.axon.event.OrderPlacedEvent;
import com.poc.axon.event.OrderShippedEvent;

@Aggregate
public class OrderAggregate implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AggregateIdentifier
	private String orderId;
	private boolean orderConfirmed;

	@CommandHandler
	public OrderAggregate(PlaceOrderCommand command) {
		String orderId=command.getOrderId();
		Assert.hasLength(orderId, "Missin orderId");
		AggregateLifecycle.apply(new OrderPlacedEvent(orderId, command.getProduct()));
	}

	@CommandHandler
    public void on(ConfirmOrderCommand command) {
		AggregateLifecycle.apply(new OrderConfirmedEvent(orderId));
	//	System.out.println("confirmed");
    }
	
	@CommandHandler
    public void on(ShipOrderCommand command) {
        if (!orderConfirmed) {
            throw new IllegalStateException("Cannot ship an order which has not been confirmed yet.");
        }

        AggregateLifecycle.apply(new OrderShippedEvent(orderId));
    }
	
	@EventSourcingHandler //state change
	public void on(OrderPlacedEvent event) {
		this.orderId = event.getOrderId();
		orderConfirmed = false;
	}
	
	@EventSourcingHandler
	public void on(OrderConfirmedEvent event) {
		orderConfirmed = true;
	}

	public OrderAggregate() {
		  // Required by Axon to build a default Aggregate prior to Event Sourcing
	}

}
