package com.poc.axon.event;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderShippedEvent {

	private final String orderId;

	// default constructor, getters, equals/hashCode and toString
	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final OrderShippedEvent other = (OrderShippedEvent) obj;
		return Objects.equals(this.orderId, other.orderId);
	}

	@Override
	public String toString() {
		return "OrderShippedEvent{" + "orderId='" + orderId + '\'' + '}';
	}
}
