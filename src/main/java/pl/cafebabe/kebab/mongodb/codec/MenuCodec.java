package pl.cafebabe.kebab.mongodb.codec;

import pl.cafebabe.kebab.model.Menu;

// http://stackoverflow.com/questions/30569228/mongodb-bson-codec-not-being-used-while-encoding-object
public class MenuCodec extends AbstractGsonCodec<Menu> {

	public MenuCodec() {
		super(Menu.class);
	}

}
