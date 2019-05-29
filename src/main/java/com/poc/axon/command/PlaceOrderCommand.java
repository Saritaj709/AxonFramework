
package com.poc.axon.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceOrderCommand {

	@TargetAggregateIdentifier
	private final String orderId;
	private final String product;

}
