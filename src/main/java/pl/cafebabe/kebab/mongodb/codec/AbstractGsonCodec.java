package pl.cafebabe.kebab.mongodb.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// http://stackoverflow.com/questions/30569228/mongodb-bson-codec-not-being-used-while-encoding-object
public abstract class AbstractGsonCodec<T> implements Codec<T> {

	private Class<T> _class;
	
	public AbstractGsonCodec(Class<T> _class) {
		this._class = _class;
	}

	@Override
	public void encode(BsonWriter writer, T obj, EncoderContext context) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		Gson gson = new Gson();
		writer.writeString(gson.toJson(obj));
	}

	@Override
	public Class<T> getEncoderClass() {
		return _class;
	}

	@Override
	public T decode(BsonReader reader, DecoderContext context) {
		Gson gson = new Gson();
		T obj = gson.fromJson(reader.readString(), _class);
		return obj;
	}

}
