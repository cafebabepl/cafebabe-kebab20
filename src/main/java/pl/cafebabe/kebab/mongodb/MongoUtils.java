package pl.cafebabe.kebab.mongodb;

import static pl.cafebabe.kebab.Constants.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import pl.cafebabe.kebab.config.ConfigUtils;
import pl.cafebabe.kebab.mongodb.codec.MenuCodec;

public class MongoUtils {

	private MongoUtils() {
	}

	public static MongoClient getMongoClient() {
		Configuration configuration = ConfigUtils.getConfiguration();
		
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
				MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(
						new MenuCodec()));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();

		List<MongoCredential> credentials = Arrays.asList(
				MongoCredential.createCredential(
						configuration.getString(MONGODB_USERNAME),
						configuration.getString(MONGODB_DATABASE),
						configuration.getString(MONGODB_PASSWORD).toCharArray()));

		MongoClient mongoClient = new MongoClient(
				new ServerAddress(
						configuration.getString(MONGODB_HOST),
						configuration.getInt(MONGODB_PORT)), 
				credentials, 
				options);

		return mongoClient;
	}

}
