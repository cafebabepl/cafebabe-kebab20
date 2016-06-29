package pl.cafebabe.kebab.model;

import java.util.ArrayList;
import java.util.Collection;

public class Pozycja {

	private String nazwa;
	// TODO parsowanie opisu
	private String opis;
	private Collection<Wariant> warianty;

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Collection<Wariant> getWarianty() {
		if (warianty == null) {
			warianty = new ArrayList<>();
		}
		return warianty;
	}

}