package pl.cafebabe.kebab.exp;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import pl.cafebabe.kebab.model.Menu;
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

	public static void test1() throws Exception {
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

	public static void test2() throws Exception {		
		try (MongoClient client = MongoUtils.getMongoClient()) {
			
//			MongoCollection<Menu> col = client.getDatabase("kebab20").getCollection("menu", Menu.class);
//			Menu y = col.find().sort(Sorts.descending("_id")).limit(1).first();
			MongoCollection<Document> col = client.getDatabase("kebab20").getCollection("menu");
			
			Document doc = col.find().sort(Sorts.descending("_id")).limit(1).first();
			String tresc = doc.get("tresc", String.class);
			
//			String json = y.toJson();
			Gson gson = new Gson();;
			Menu menu = gson.fromJson(tresc, Menu.class);
			if (menu != null) {
				System.out.println(menu.getAktualnosc());
				System.out.println(menu.getRestauracja());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		test2();
	}

}
