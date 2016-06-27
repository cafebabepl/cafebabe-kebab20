package pl.cafebabe.kebab.model;

import java.util.ArrayList;
import java.util.Collection;

public class Menu {

	private Restauracja restauracja;
	private String tytul;
	private Collection<Pozycja> pozycje;

	public Restauracja getRestauracja() {
		return restauracja;
	}

	public void setRestauracja(Restauracja restauracja) {
		this.restauracja = restauracja;
	}

	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public Collection<Pozycja> getPozycje() {
		if (pozycje == null) {
			pozycje = new ArrayList<>();
		}
		return pozycje;
	}

}