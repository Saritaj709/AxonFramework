package com.poc.axon.event;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPlacedEvent {

	private final String orderId;
	private final String product;

	// default constructor, getters, equals/hashCode and toString

	@Override
	public int hashCode() {
		return Objects.hash(orderId, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final OrderPlacedEvent other = (OrderPlacedEvent) obj;
		return Objects.equals(this.orderId, other.orderId) && Objects.equals(this.product, other.product);
	}

	@Override
	public String toString() {
		return "OrderPlacedEvent{" + "orderId='" + orderId + '\'' + ", product='" + product + '\'' + '}';
	}
}
