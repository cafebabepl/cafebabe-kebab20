package pl.cafebabe.kebab.model;

import java.math.BigDecimal;

public class Cena {

	private BigDecimal kwota;
	private String waluta;

	public BigDecimal getKwota() {
		return kwota;
	}

	public void setKwota(BigDecimal kwota) {
		this.kwota = kwota;
	}

	public String getWaluta() {
		return waluta;
	}

	public void setWaluta(String waluta) {
		this.waluta = waluta;
	}

	@Override
	public String toString() {
		return String.format("%s %s", kwota, waluta);
	}

}