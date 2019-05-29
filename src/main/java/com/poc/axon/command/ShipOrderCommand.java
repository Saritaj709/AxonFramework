
package com.poc.axon.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipOrderCommand {

	@TargetAggregateIdentifier
	private final String orderId;
}
