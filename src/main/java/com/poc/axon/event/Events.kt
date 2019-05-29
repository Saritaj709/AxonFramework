package com.poc.axon.event

abstract class OrderEvent(open val orderId:String)

data class OrderConfirmedEvent(override val orderId:String):OrderEvent(orderId)

data class OrderPlacedEvent(override val orderId:String,val product:String):OrderEvent(orderId)

data class OrderShippedEvent(override val orderId:String):OrderEvent(orderId)

