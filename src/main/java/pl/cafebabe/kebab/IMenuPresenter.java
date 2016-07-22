package pl.cafebabe.kebab;

import java.util.Collection;

import pl.cafebabe.kebab.model.Grupa;
import pl.cafebabe.kebab.model.Menu;
import pl.cafebabe.kebab.model.Pozycja;
import pl.cafebabe.kebab.model.Wariant;

public interface IMenuPresenter {

	Menu getMenu();

	Collection<Grupa> getGrupy();
	Collection<Pozycja> getPozycje(String grupa);
	Collection<Wariant> getWarianty(String grupa, String pozycja);

}