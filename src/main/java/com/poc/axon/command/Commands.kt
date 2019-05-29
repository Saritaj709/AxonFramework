package com.poc.axon.command

import org.axonframework.commandhandling.TargetAggregateIdentifier;

abstract class OrderCommands(@TargetAggregateIdentifier open val orderId: String)

data class ConfirmOrderCommand(@TargetAggregateIdentifier final val orderId:String)

data class PlaceOrderCommand1(override val orderId:String,val product:String):OrderCommands(orderId)

data class ShipOrderCommand(override val orderId:String):OrderCommands(orderId)

/*class ConfirmOrderCommand{
	@TargetAggregateIdentifier final val orderId:String
	constructor(orderId:String){
		this.orderId=orderId
	}
}*/