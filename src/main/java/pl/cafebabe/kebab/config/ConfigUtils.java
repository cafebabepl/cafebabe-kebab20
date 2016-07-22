package pl.cafebabe.kebab.config;

import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigUtils {

	private static Configuration config = null;
	private static org.apache.commons.configuration.Configuration config1 = null;

	private ConfigUtils() {
	}
	
/*
	public synchronized static Configuration getConfiguration() {
		if (config == null) {
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder = 
					new FileBasedConfigurationBuilder<FileBasedConfiguration>(FileBasedConfiguration.class)
						.configure(params.properties()
						.setFileName("application.properties"));
			try {
				config = builder.getConfiguration();
			} catch (ConfigurationException cex) {
				throw new RuntimeException("Nie można załadować konfiguracji.", cex);
			}
		}
		return config;
	}
*/

	//TODO przerobić żeby nie było deprecated albo na configuration2
	@SuppressWarnings("deprecation")
	public synchronized static org.apache.commons.configuration.Configuration getConfiguration() {
		if (config1 == null) {
			ConfigurationFactory factory = new ConfigurationFactory("config.xml");
			try {
				config1 = factory.getConfiguration();
			} catch (org.apache.commons.configuration.ConfigurationException ex) {
				throw new RuntimeException("Nie można załadować konfiguracji.", ex);
			}
		}
		return config1;
	}
}
