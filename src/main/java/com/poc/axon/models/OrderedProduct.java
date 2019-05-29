package com.poc.axon.models;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
//@Document(collection="orders")
public class OrderedProduct {
	
	@Id
	private String id;

	@TargetAggregateIdentifier
	private final String orderId;
	private final String product;
	private OrderStatus orderStatus;

	public OrderedProduct(String orderId, String product) {
		this.orderId = orderId;
		this.product = product;
		orderStatus = OrderStatus.PLACED;
	}

	public void setOrderConfirmed() {
		this.orderStatus = OrderStatus.CONFIRMED;
	}

	public void setOrderShipped() {
		this.orderStatus = OrderStatus.SHIPPED;
	}

}

