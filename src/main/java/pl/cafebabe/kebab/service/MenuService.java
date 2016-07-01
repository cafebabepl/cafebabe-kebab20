package pl.cafebabe.kebab.service;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;

import pl.cafebabe.kebab.model.Grupa;
import pl.cafebabe.kebab.model.Menu;
import pl.cafebabe.kebab.model.Pozycja;
import pl.cafebabe.kebab.mongodb.MongoUtils;
import pl.cafebabe.kebab.parser.CamelPizzaKebapParser;

@Path("/")
//@Produces({ "application/json" })
@Produces({ "application/json;charset=utf-8"})
public class MenuService {

	@GET
	@Path("menu")
	public Menu menu() throws Exception {
		// A. parsowanie za każdym razem
		//TODO Inject
//		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
//		return parser.getMenu();
		
		//TODO pousuwać te nazwy do kongiracji albo metody jakiejś
		
		// TODO to jest bardzo brzydkie, nauczyć mongoquery, przerobić na DTO/Biznes
		// B. pobranie z bazy
		try (MongoClient client = MongoUtils.getMongoClient()) {
			MongoCollection<Document> col = client.getDatabase("kebab20").getCollection("menu");
			Document doc = col.find().sort(Sorts.descending("_id")).limit(1).first();
			String tresc = doc.get("tresc", String.class);
			Gson gson = new Gson();;
			Menu menu = gson.fromJson(tresc, Menu.class);
			return menu;
		}
	}

	//TODO i to uporządkować z bazy
	@GET
	@Path("menu/{grupa}")
	public Collection<Pozycja> pozycje(@PathParam("grupa") String grupa) throws Exception {
		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
		for (Grupa i : parser.getMenu().getGrupy()) {
			if (i.getNazwa().equalsIgnoreCase(grupa)) {
				return i.getPozycje();
			}
		}
		return Collections.emptyList();
	}
}
