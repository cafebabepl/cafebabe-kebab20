package pl.cafebabe.kebab;

import pl.cafebabe.kebab.model.Menu;

public interface IMenuProvider {
	
	Menu getMenu() throws Exception;

}