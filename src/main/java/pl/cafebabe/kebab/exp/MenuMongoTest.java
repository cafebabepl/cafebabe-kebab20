package pl.cafebabe.kebab.exp;

import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;
import org.bson.Document;
import org.jongo.Aggregate;
import org.jongo.Jongo;
import org.jongo.ResultHandler;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import pl.cafebabe.kebab.config.ConfigUtils;
import pl.cafebabe.kebab.model.Menu;
import pl.cafebabe.kebab.model.Restauracja;
import pl.cafebabe.kebab.mongodb.MongoUtils;
import pl.cafebabe.kebab.parser.CamelPizzaKebapParser;

public class MenuMongoTest {
	
	public static void test0() {
		try (MongoClient mongoClient = new MongoClient("127.0.0.1", 27017)) {
			MongoDatabase database = mongoClient.getDatabase("kebab20");
			MongoCollection<Document> col = database.getCollection("test");
			
			String obj = "szumią jodły na gór szczycie";
			Document document = new Document("test-" + System.currentTimeMillis(), obj);
			col.insertOne(document);			
			
			System.out.println(col.count());
			for (Document i : col.find()) {
				System.out.println(i.toJson());
			}
		}
	}

	public static void testMongoInsert() throws Exception {
		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
		Menu menu = parser.getMenu();
		
		try (MongoClient mongoClient = MongoUtils.getMongoClient()) {

			MongoDatabase database = mongoClient.getDatabase("kebab20");
			MongoCollection<Document> col = database.getCollection("menu");

			Document document = new Document("tresc", menu);
			col.insertOne(document);
			
			System.out.println(col.count());
			for (Document i : col.find()) {
				System.out.println(i.toJson());
			}
		}
	}

	public static void testMongoGsonFind() throws Exception {		
		try (MongoClient client = MongoUtils.getMongoClient()) {
			
//			MongoCollection<Menu> col = client.getDatabase("kebab20").getCollection("menu", Menu.class);
//			Menu y = col.find().sort(Sorts.descending("_id")).limit(1).first();
			MongoCollection<Document> col = client.getDatabase("kebab20").getCollection("menu");
			
			Document doc = col.find().sort(Sorts.descending("_id")).limit(1).first();
			String tresc = doc.get("tresc", String.class);
			
//			String json = y.toJson();
			Gson gson = new Gson();
			Menu menu = gson.fromJson(tresc, Menu.class);
			if (menu != null) {
				System.out.println(menu.getAktualnosc());
				System.out.println(menu.getRestauracja());
			}
		}
	}

	// Jongo
	public static void testJongoInsert() throws Exception {
		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
		Menu menu = parser.getMenu();

		try (MongoClient mongoClient = MongoUtils.getMongoClient()) {
			@SuppressWarnings("deprecation")
			Jongo jongo = new Jongo(mongoClient.getDB("kebab20"));
			org.jongo.MongoCollection menus2 = jongo.getCollection("menu-jongo");
			WriteResult wr = menus2.insert(menu);
			System.out.println("Wynik zapisu:");
			System.out.println(wr.getN());
			System.out.println(wr.getUpsertedId());
		}
	}

	public static void testJongoFind() throws Exception {

		try (MongoClient mongoClient = MongoUtils.getMongoClient()) {
			@SuppressWarnings("deprecation")
			Jongo jongo = new Jongo(mongoClient.getDB("kebab20"));
			org.jongo.MongoCollection menus2 = jongo.getCollection("menu-jongo");
			Menu menu = menus2.findOne().orderBy("{aktualnosc: -1}").as(Menu.class);
			System.out.println(menu.getAktualnosc());
			System.out.println(menu.getRestauracja());
		}
	}
	
	public static void testConfiguration() throws Exception {
		Configuration c = ConfigUtils.getConfiguration();
		System.out.println(c.getString("mongodb.host"));
		System.out.println(c.getInt("mongodb.port"));
	}
	
	public static void testJongoDistinct() throws Exception {

		try (MongoClient mongoClient = MongoUtils.getMongoClient()) {
			@SuppressWarnings("deprecation")
			Jongo jongo = new Jongo(mongoClient.getDB("kebab20"));
			org.jongo.MongoCollection menus2 = jongo.getCollection("menu-jongo");
//			//Aggregate a = menus2.aggregate("[ { \"$group\" : { _id: null, count: { $sum: 1 } } } ]");
//			System.out.println(a.toString());			
			List<String> names = menus2.distinct("restauracja.nazwa").as(String.class);
			System.out.println("Restauracje:");
			names.forEach(i -> System.out.println(i));

			List<Restauracja> r = menus2.distinct("restauracja").as(Restauracja.class);
			//List<Restauracja> r = menus2.distinct("restauracja").query(WHERE).as(Restauracja.class);
			System.out.println("Restauracje:");
			r.forEach(i -> System.out.println(i));
		}
	}
	
	public static void main(String[] args) throws Exception {
		//testJongoInsert();
		//testJongoFind();
		testJongoDistinct();
	}

}
