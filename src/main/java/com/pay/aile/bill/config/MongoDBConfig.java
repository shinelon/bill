package com.pay.aile.bill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;

@Configuration
@ConditionalOnProperty(prefix = "environment", value = "production")
public class MongoDBConfig extends AbstractMongoConfiguration {

	@Value("${mongodb.databaseName}")
	private String databaseName;
	@Value("${mongodb.dataSourceName}")
	private String dataSourceName;

	@Bean
	@Override
	public Mongo mongo() throws Exception {
		// MongoPoolClient mongoPoolClient = new MongoPoolClient();
		// mongoPoolClient.setDataSourceType("mongoDb");
		// mongoPoolClient.setDataSourceName(dataSourceName);
		// mongoPoolClient.getDataSource();
		// return mongoPoolClient.getMongoClient();
		return null;
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

}
