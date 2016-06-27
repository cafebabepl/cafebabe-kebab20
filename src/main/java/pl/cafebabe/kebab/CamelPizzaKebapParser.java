package pl.cafebabe.kebab;

import java.io.File;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.cafebabe.kebab.model.Cena;
import pl.cafebabe.kebab.model.Menu;
import pl.cafebabe.kebab.model.Pozycja;
import pl.cafebabe.kebab.model.Restauracja;

//TODO trzeba zrobic wersjonowanie api v1, v2 itd.
//TODO REST np. /menu/{restauracja}, /menu/{restaurcja}/{grupa} "/menu/camel/kebab"
public class CamelPizzaKebapParser {

	public static void parse() throws Exception {
		File input = new File("c:/temp/KEBAP _ CamelPizza - Bydgoszcz.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

		// <div class="post-entry">
		Elements e = doc.select("div.post-entry").select("tr");
		// e.stream().forEach(i -> System.out.println(i.toString()));

		for (Element i : e) {
			Elements td = i.select("td");
			if (td.size() >= 2) {
				String nazwa = td.get(0).text();
				String cena = td.get(1).text();

				if (StringUtils.isNoneBlank(nazwa, cena)) {
					System.out.printf("nazwa: %s, cena: %s\n", nazwa, cena);
				}
			}
		}

	}

	//TODO zamiana na JSONa
	public Menu getMenu() throws Exception {
		Menu menu = new Menu();
		
		Restauracja restauracja = new Restauracja();
		restauracja.setNazwa("CamelPizza");
		restauracja.setLogo("http://camelpizza.pl/wp-content/uploads/2014/06/logo.png");
		restauracja.setUrl("http://camelpizza.pl");
		menu.setRestauracja(restauracja);

				
//		File input = new File("c:/temp/KEBAP _ CamelPizza - Bydgoszcz.html");
//		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		
		Document doc = Jsoup.connect("http://camelpizza.pl/kebap").get();
		
		Elements e = doc.select("div.post-entry").select("tr");
		// e.stream().forEach(i -> System.out.println(i.toString()));

		for (Element i : e) {
			Elements td = i.select("td");
			if (td.size() >= 2) {
				String nazwa = td.get(0).text().trim();
				String _cena = td.get(1).text().trim();

				if (StringUtils.isNoneBlank(nazwa, _cena)) {
					//TODO log4j
					System.out.printf("nazwa: %s, cena: %s\n", nazwa, _cena);
						
					String kwota = _cena.replaceAll("zł", "").trim().replace(",", ".");
					Cena cena = new Cena();
					cena.setKwota(new BigDecimal(kwota));
					cena.setWaluta("PLN"); //TODO enum z walutami
					
					//TODO pozycje powinny byc pogrupowane, np. pizza, kebab itd.
					Pozycja pozycja = new Pozycja();
					pozycja.setNazwa(nazwa);
					pozycja.setCena(cena);
					
					menu.getPozycje().add(pozycja);
				}
			}
		}

		return menu;
	}

	public static void main(String[] args) throws Exception {
		parse();
	}

}
