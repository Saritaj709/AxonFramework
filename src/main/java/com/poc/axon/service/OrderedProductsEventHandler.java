package com.poc.axon.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.data.domain.AbstractAggregateRoot;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.axon.event.OrderConfirmedEvent;
import com.poc.axon.event.OrderPlacedEvent;
import com.poc.axon.event.OrderShippedEvent;
import com.poc.axon.models.FindAllOrderedProductsQuery;
import com.poc.axon.models.OrderedProduct;
//import com.poc.axon.repository.OrderRepository;

@Service
@Aggregate(repository="snapRepo")
public class OrderedProductsEventHandler extends AbstractAggregateRoot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Autowired
	//private OrderRepository orderRepository;
	
	private final Map<String, OrderedProduct> orderedProducts = new HashMap<>();

	@EventHandler //decision making
	public void applySnapshot(OrderPlacedEvent event) {
		String orderId = event.getOrderId();
		OrderedProduct order=new OrderedProduct(orderId, event.getProduct());
		orderedProducts.put(orderId, order);
		//orderRepository.save(order);
	}

	// Event Handlers for OrderConfirmedEvent and OrderShippedEvent...

	@EventHandler
	public void applySnapshot(OrderConfirmedEvent event) {
		orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
			orderedProduct.setOrderConfirmed();
			return orderedProduct;
		});
	}

	@EventHandler
	public void applySnapshot(OrderShippedEvent event) {
		orderedProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
			orderedProduct.setOrderShipped();
			return orderedProduct;
		});
	}

	@QueryHandler
	public List<OrderedProduct> handle(FindAllOrderedProductsQuery query) {
		return new ArrayList<>(orderedProducts.values());
	}
}
