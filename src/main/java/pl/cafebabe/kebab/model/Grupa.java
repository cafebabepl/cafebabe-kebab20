package pl.cafebabe.kebab.model;

import java.util.ArrayList;
import java.util.Collection;

public class Grupa {

	private String nazwa;
	private Collection<Pozycja> pozycje;

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Collection<Pozycja> getPozycje() {
		if (pozycje == null) {
			pozycje = new ArrayList<>();
		}
		return pozycje;
	}

}
