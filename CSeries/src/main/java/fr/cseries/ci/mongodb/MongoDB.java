package fr.cseries.ci.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import fr.cseries.ci.Config;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class MongoDB {

	public static MongoDB db = new MongoDB().connect();

	@Getter @Setter
	private MongoClient   client;
	@Getter @Setter
	private MongoDatabase database;

	public MongoDB connect() {
		this.client = new MongoClient(new ServerAddress(Config.MONGO_IP), Arrays.asList(new MongoCredential[]{MongoCredential.createCredential(Config.MONGO_USER, "CSeries", Config.MONGO_PASSWORD.toCharArray())}));
		this.database = this.client.getDatabase("CSeries");
		return this;
	}
}
