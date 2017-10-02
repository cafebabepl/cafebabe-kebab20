package pl.cafebabe.kebab.schedule;

import static pl.cafebabe.kebab.Constants.*;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.apache.commons.configuration.Configuration;
import org.jongo.Jongo;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import pl.cafebabe.kebab.IMenuProvider;
import pl.cafebabe.kebab.config.ConfigUtils;
import pl.cafebabe.kebab.model.Menu;
import pl.cafebabe.kebab.mongodb.MongoUtils;
import pl.cafebabe.kebab.parser.CamelPizzaKebapParser;

@Singleton
public class MenuGenerateScheduler {
	
	Configuration configuration = ConfigUtils.getConfiguration();	

	@Schedule(hour = "8-16", minute = "0")
	public void execute() {
		try {
			// 1. pobranie menu dostawcy (sparsowanie)
			IMenuProvider provider = new CamelPizzaKebapParser();
			Menu menu = provider.getMenu();

			// 2. trwałe zapisanie menu w szeroko pojętej bazie
			// TODO przerobić na interfejs IMenuStorer ??? nazwa jakaś sensowna
			try (MongoClient mongoClient = MongoUtils.getMongoClient()) {
				@SuppressWarnings("deprecation")
				Jongo jongo = new Jongo(mongoClient.getDB(configuration.getString(MONGODB_DATABASE)));
				org.jongo.MongoCollection menus2 = jongo.getCollection(configuration.getString(MONGODB_COLLECTION));
				WriteResult wr = menus2.insert(menu);
				System.out.println("Wynik zapisu:");
				System.out.println(wr.getN());
				System.out.println(wr.getUpsertedId());
			}
		} catch (Exception ex) {
			//TODO zrobić powiadamianie, np. przez emaila, jeśli wystąpi błąd
			ex.printStackTrace();
		}
	}

}