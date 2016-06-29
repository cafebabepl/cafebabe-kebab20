package pl.cafebabe.kebab.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Wariant pozycji menu.
 * 
 * @author Włodzimierz Kozłowski
 */
public class Wariant {
	private String opis;
	private Set<Cena> ceny;

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Set<Cena> getCeny() {
		if (ceny == null) {
			ceny = new LinkedHashSet<>();
		}
		return ceny;
	}

}