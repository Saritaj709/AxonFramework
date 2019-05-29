package com.poc.axon.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.axon.command.ConfirmOrderCommand;
import com.poc.axon.command.PlaceOrderCommand;
import com.poc.axon.command.ShipOrderCommand;
import com.poc.axon.models.FindAllOrderedProductsQuery;
import com.poc.axon.models.OrderedProduct;

@RestController
public class OrderRestEndpoint {

	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	
	private final EventStore eventStore;

	public OrderRestEndpoint(CommandGateway commandGateway, QueryGateway queryGateway,EventStore eventStore) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
		this.eventStore=eventStore;
	}
	// Autowiring constructor and POST/GET endpoints

	@PostMapping("/ship-order")
	public String shipOrder(@RequestParam(value="itemName")String itemName) {
		String orderId = UUID.randomUUID().toString();
		commandGateway.send(new PlaceOrderCommand(orderId, itemName));
		commandGateway.send(new ConfirmOrderCommand(orderId));
		commandGateway.send(new ShipOrderCommand(orderId));
		return orderId;
		
	}
	
	@GetMapping("{id}/events")
	public List<Object> getEvents(@PathVariable String id) {
		return eventStore.readEvents(id).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
	}


	@PostMapping("/ship-unconfirmed-order")
	public void shipUnconfirmedOrder(@RequestParam(value="itemName")String itemName) {
		String orderId = UUID.randomUUID().toString();
		commandGateway.send(new PlaceOrderCommand(orderId, itemName));
		// This throws an exception, as an Order cannot be shipped if it has not been
		// confirmed yet.
		commandGateway.send(new ShipOrderCommand(orderId));
	}

	@GetMapping("/all-orders")
	public List<OrderedProduct> findAllOrderedProducts() {
		return queryGateway
				.query(new FindAllOrderedProductsQuery(), ResponseTypes.multipleInstancesOf(OrderedProduct.class))
				.join();
	}
}