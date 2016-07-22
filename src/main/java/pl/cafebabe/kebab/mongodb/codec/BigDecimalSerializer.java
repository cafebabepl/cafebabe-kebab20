//TODO trzeba zmienić nazwę pakietu
package pl.cafebabe.kebab.mongodb.codec;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

// https://github.com/bguerout/jongo/issues/220
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.toPlainString());
	}

}