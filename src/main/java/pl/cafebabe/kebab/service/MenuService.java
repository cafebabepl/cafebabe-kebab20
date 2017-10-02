package pl.cafebabe.kebab.service;

import static pl.cafebabe.kebab.Constants.*;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.configuration.Configuration;
import org.jongo.Jongo;

import com.mongodb.MongoClient;

import pl.cafebabe.kebab.config.ConfigUtils;
import pl.cafebabe.kebab.model.Menu;
import pl.cafebabe.kebab.mongodb.MongoUtils;
import pl.cafebabe.kebab.schedule.MenuGenerateScheduler;

@Path("/")
@Produces({ "application/json;charset=utf-8"})
public class MenuService {
	
	Configuration configuration = ConfigUtils.getConfiguration();	
	
	//TODO usunąć po przeniesieniu do obiektu biznesowego
	@EJB
	MenuGenerateScheduler scheduler;
	
//	public Menu menu1() throws Exception {
//		// A. parsowanie za każdym razem
//		//TODO Inject
////		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
////		return parser.getMenu();
//		
//		//TODO pousuwać te nazwy do kongiracji albo metody jakiejś
//		
//		// TODO to jest bardzo brzydkie, nauczyć mongoquery, przerobić na DTO/Biznes
//		// B. pobranie z bazy
//		try (MongoClient client = MongoUtils.getMongoClient()) {
//			MongoCollection<Document> col = client.getDatabase("kebab20").getCollection("menu");
//			Document doc = col.find().sort(Sorts.descending("_id")).limit(1).first();
//			String tresc = doc.get("tresc", String.class);
//			Gson gson = new Gson();
//			Menu menu = gson.fromJson(tresc, Menu.class);
//			return menu;
//		}
//	}
	
	
	@GET
	@Path("parse")
	public String parse() throws Exception {
		scheduler.execute();
		return "Done";
	}

	@GET
	@Path("menu")
	public Menu menu2() throws Exception {
		// TODO trzeba to wszystko przenieść do jakiegoś obiektu biznesowego
		try (MongoClient client = MongoUtils.getMongoClient()) {
			@SuppressWarnings("deprecation")
			// TODO uporządkować konfigurację
			Jongo jongo = new Jongo(client.getDB(configuration.getString(MONGODB_DATABASE)));
			org.jongo.MongoCollection menus2 = jongo.getCollection(configuration.getString(MONGODB_COLLECTION));
			Menu menu = menus2.findOne().orderBy("{aktualnosc: -1}").as(Menu.class);
			//TODO jak Jackson datę parsuje bo w jax-rs dostaję liczbę!
			return menu;
		}
	}

	//TODO i to uporządkować z bazy
//	@GET
//	@Path("menu/{grupa}")
//	public Collection<Pozycja> pozycje(@PathParam("grupa") String grupa) throws Exception {
//		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
//		for (Grupa i : parser.getMenu().getGrupy()) {
//			if (i.getNazwa().equalsIgnoreCase(grupa)) {
//				return i.getPozycje();
//			}
//		}
//		return Collections.emptyList();
//	}
	
//	@GET
//	@Path("test")
//	public Menu test() throws Exception {
//		
//		Jongo jongo = new Jongo(MongoUtils.getMongoClient().getDB("kebab20"));
//		org.jongo.MongoCollection menus = jongo.getCollection("menu");
////		menus.findOne().
//		
//		// TODO to jest bardzo brzydkie, nauczyć mongoquery, przerobić na DTO/Biznes
//		// B. pobranie z bazy
//		try (MongoClient client = MongoUtils.getMongoClient()) {
//			MongoCollection<Document> col = client.getDatabase("kebab20").getCollection("menu");
//			Document doc = col.find().sort(Sorts.descending("_id")).limit(1).first();
//			String tresc = doc.get("tresc", String.class);
//			Gson gson = new Gson();
//			Menu menu = gson.fromJson(tresc, Menu.class);
//			return menu;
//		}
//	}	
}
