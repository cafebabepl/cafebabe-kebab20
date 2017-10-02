package pl.cafebabe.kebab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


public class Menu {
	//TODO to działa, ale wolałbym globalnie przez ObjectMappera
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date aktualnosc;
	private Restauracja restauracja;
	private String tytul;
	private Collection<Grupa> grupy;

	public Date getAktualnosc() {
		return aktualnosc;
	}

	public void setAktualnosc(Date aktualnosc) {
		this.aktualnosc = aktualnosc;
	}

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

	public Collection<Grupa> getGrupy() {
		if (grupy == null) {
			grupy = new ArrayList<>();
		}
		return grupy;
	}

}