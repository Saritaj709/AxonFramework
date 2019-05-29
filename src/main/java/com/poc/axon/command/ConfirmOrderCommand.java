
package com.poc.axon.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ConfirmOrderCommand {

	@TargetAggregateIdentifier
	private final String orderId;

}
