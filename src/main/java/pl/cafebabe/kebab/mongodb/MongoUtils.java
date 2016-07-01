package pl.cafebabe.kebab.mongodb;

import java.util.Arrays;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import pl.cafebabe.kebab.mongodb.codec.MenuCodec;

public class MongoUtils {

	private MongoUtils() {
	}

	public static MongoClient getMongoClient() {
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(new MenuCodec()));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();

		// TODO przenieść do konfiguracji
		List<MongoCredential> credentials = Arrays
				.asList(MongoCredential.createCredential("admin", "kebab20", "iZ6T3PViNyrz".toCharArray()));

		// try (MongoClient mongoClient = new MongoClient("127.0.0.1", 27017)) {
		// try (MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), options)) {
		MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), credentials, options);
		return mongoClient;
	}

}
