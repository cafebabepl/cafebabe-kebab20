package pl.cafebabe.kebab.service;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import pl.cafebabe.kebab.CamelPizzaKebapParser;
import pl.cafebabe.kebab.model.*;

@Path("/")
@Produces({ "application/json" })
public class MenuService {

	@GET
	@Path("menu")
	public Menu menu() throws Exception {
		CamelPizzaKebapParser parser = new CamelPizzaKebapParser();
		return parser.getMenu();
	}

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
