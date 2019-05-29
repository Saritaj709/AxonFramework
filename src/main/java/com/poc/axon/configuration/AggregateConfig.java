
package com.poc.axon.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotter;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.poc.axon.service.OrderedProductsEventHandler;

@Configuration
public class AggregateConfig {
	
	@Autowired
	private EventStore eventStore;

	@Bean
	public EventStorageEngine eventStore(MongoClient client) {
		return new MongoEventStorageEngine(new DefaultMongoTemplate(client));
	}

	// snapshots
	@Bean
	public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
		return new SpringAggregateSnapshotterFactoryBean();
	}

	@Bean
	public SpringAggregateSnapshotter snapshotter(ParameterResolverFactory parameterResolverFactory,
			EventStore eventStore, TransactionManager transactionManager) {
		Executor executor = Executors.newFixedThreadPool(10);
		return new SpringAggregateSnapshotter(eventStore, parameterResolverFactory, executor, transactionManager);
	}

	/*
	 * //@Bean("BankAccountRepository")
	 * 
	 * @Bean public EventSourcingRepository<OrderAggregate>
	 * reservationRepository(Snapshotter snapshotter, ParameterResolverFactory
	 * parameterResolverFactory) { return new
	 * EventSourcingRepository<OrderAggregate>(reservationAggregateFactory(),
	 * eventStore, parameterResolverFactory, new
	 * EventCountSnapshotTriggerDefinition(snapshotter, 50)); }
	 * 
	 * //@Bean(name = "BankAccountAggregateFactory")
	 * 
	 * @Bean public AggregateFactory<OrderAggregate> reservationAggregateFactory() {
	 * SpringPrototypeAggregateFactory<OrderAggregate> aggregateFactory = new
	 * SpringPrototypeAggregateFactory<>();
	 * aggregateFactory.setPrototypeBeanName("orderAggregate"); return
	 * aggregateFactory; }
	 */
	
	@Bean("snapRepo")
	public EventSourcingRepository<OrderedProductsEventHandler> reservationRepository(Snapshotter snapshotter,
			ParameterResolverFactory parameterResolverFactory) {
		return new EventSourcingRepository<OrderedProductsEventHandler>(reservationAggregateFactory(), eventStore,
				parameterResolverFactory, new EventCountSnapshotTriggerDefinition(snapshotter, 50));
	}

	//@Bean(name = "BankAccountAggregateFactory")
	@Bean
	public AggregateFactory<OrderedProductsEventHandler> reservationAggregateFactory() {
		SpringPrototypeAggregateFactory<OrderedProductsEventHandler> aggregateFactory = new SpringPrototypeAggregateFactory<>();
		aggregateFactory.setPrototypeBeanName("orderedProductsEventHandler");
		return aggregateFactory;
	}
}
