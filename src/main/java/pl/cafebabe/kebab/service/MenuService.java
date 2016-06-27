package pl.cafebabe.kebab.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import pl.cafebabe.kebab.CamelPizzaKebapParser;
import pl.cafebabe.kebab.model.Menu;

@Path("/")
@Produces({ "application/json" })
public class MenuService {

	@GET
	@Path("menu")
	public Menu menu() throws Exception {

		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
		return parser.getMenu();
	}

}
