/*
 * package com.poc.axon.configuration;
 * 
 * import org.axonframework.mongo.DefaultMongoTemplate; import
 * org.axonframework.mongo.MongoTemplate; import
 * org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * com.mongodb.MongoClient; import org.axonframework.serialization.Serializer;
 * 
 * import com.mongodb.ServerAddress;
 * 
 * import java.util.Collections;
 * 
 * import org.axonframework.eventhandling.tokenstore.TokenStore; import
 * org.axonframework.eventsourcing.eventstore.EventStorageEngine; import
 * org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
 * import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory; import
 * org.axonframework.mongo.eventsourcing.eventstore.documentperevent.
 * DocumentPerEventStorageStrategy; import
 * org.axonframework.mongo.eventsourcing.tokenstore.MongoTokenStore;
 * 
 * @Configuration public class MongoConfiguration {
 * 
 * 
 * @Value("${mongo.host:127.0.0.1}") private String mongoHost;
 * 
 * @Value("${mongo.port:27017}") private int mongoPort;
 * 
 * @Value("${mongo.db:test}") private String mongoDB;
 * 
 * 
 * @Value("${spring.data.mongodb.host}") private String mongoHost;
 * 
 * @Value("${spring.data.mongodb.port}") private int mongoPort;
 * 
 * @Value("${spring.data.mongodb.database}") private String mongoDB;
 * 
 * 
 * @Autowired private MongoClient client;
 * 
 * public MongoTemplate axonMongoTemplate(MongoClient client) { return new
 * DefaultMongoTemplate(client, mongoDB); }
 * 
 * @Bean public MongoSagaStore sagaStore() { return new
 * MongoSagaStore(axonMongoTemplate(client)); }
 * 
 * @Bean public TokenStore tokenStore(Serializer serializer) { return new
 * MongoTokenStore(axonMongoTemplate(client), serializer); }
 * 
 * @Bean public EventStorageEngine eventStorageEngine(Serializer serializer) {
 * return new MongoEventStorageEngine(serializer, null,
 * axonMongoTemplate(client), new DocumentPerEventStorageStrategy()); }
 * 
 * @Bean public MongoClient mongo() { MongoFactory mongoFactory = new
 * MongoFactory(); mongoFactory.setMongoAddresses(Collections.singletonList(new
 * ServerAddress(mongoHost, mongoPort))); return mongoFactory.createMongo(); } }
 */