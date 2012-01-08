package org.commons.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;
import org.commons.util.IWithName;
import org.commons.util.IWithSupport;
import org.commons.util.SystemUtil;

/**
 * Une énumération de toutes les propriétés supportées pour une {@link Configuration}.
 * Cette énumération associe un nom de propriété, un argument et une valeur par défaut à chaque
 * propriété de la {@link Configuration}
 * @author pitton
 *
 */
public enum EnumConfiguration implements IWithName, IWithSupport {

	MIN_LEVEL("min_level", "-l", Level.WARNING) {

		@Override
		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setMinLevel(parValue);
		}
		
		@Override
		public final String getFormattedValue(final Configuration parConfiguration) {
			return String.format("Le niveau minimum des messages est de : %s", parConfiguration.getMinLevel().getName());
		}
	},
	PORT("port", "-p", Integer.valueOf(9999)) {

		@Override
		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setPort(parValue);
		}
		
		@Override
		public final String getFormattedValue(final Configuration parConfiguration) {
			return String.format("Le port d'écoute et d'envoi est : %d", parConfiguration.getPort());
		}
	},
	HOSTNAME("hostname", "-h", "127.0.0.1") {

		@Override
		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setHostName(parValue);
		}

		@Override
		public final String getFormattedValue(final Configuration parConfiguration) {
			return String.format("L'adresse du serveur est : %s", parConfiguration.getHostName());
		}
	},
	CHARSET("charset", "-c", Charset.forName("UTF-8")) {

		@Override
		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setCharset(parValue);
		}

		@Override
		public final String getFormattedValue(final Configuration parConfiguration) {
			return String.format("L'encodage est défini en : %s", parConfiguration.getCharsetName());
		}
	};

	final private String _name;
	final private Object _defaultValue;
	final private String _argument;

	private EnumConfiguration(final String parConstName,
			final String parArgument, final Object parDefaultValue) {
		_name = parConstName;
		_argument = parArgument;
		_defaultValue = parDefaultValue;
	}

	/**
	 * Modifie la {@link Configuration} spécifiée avec la valeur spécifiée.
	 * Si la valeur est invalide, la {@link Configuration} n'est pas modifiée.
	 * @param parConfiguration {@link Configuration} la configuration à modifier.
	 * @param parValue {@link String} la nouvelle valeur de la configuration.
	 */
	abstract public void setConfigurationValue(final Configuration parConfiguration, final String parValue);
	
	/**
	 * Retourne un message contenant la valeur actuelle de la {@link Configuration} spécifiée.
	 * @param parConfiguration {@link Configuration} une configuration.
	 * @return un message contenant la valeur actuelle de la {@link Configuration} spécifiée.
	 */
	abstract public String getFormattedValue(final Configuration parConfiguration);

	@Override
	final public String getConstName() {
		return _name;
	}

	/**
	 * Retourne l'argument associé à une propriété.
	 * @return {@link String} l'argument associé à une propriété.
	 */
	final public String getArgument() {
		return _argument;
	}

	/**
	 * Retourne la valeur par défaut d'une propriété.
	 * @return {@link Object} la valeur par défaut d'une propriété.
	 */
	final public Object getDefaultValue() {
		return _defaultValue;
	}
	
	@Override
	final public String getSupport() {
		return getFormattedValue(ConfigurationManager.getConfiguration());
	}

	@Override
	final public String toString() {
		return getConstName();
	}

	/**
	 * Met à jour une {@link Configuration} depuis la liste des arguments spécifiée.
	 * La {@link Configuration} n'est pas rafraichit, impliquant que le fichier de configuration n'est pas
	 * mis à jour.
	 * Le tableau doit être du type : <argument> <value> <argument> <value> ... 
	 * Si un argument n'existe pas, il est sauté.
	 * Si une valeur spécifiée est considérée comme invalide, est elle sautée.
	 * Si un argument n'a pas de valeur associée une erreur est renvoyée.
	 * Si deux arguments se suivent, le premier est ignoré.
	 * @param parConfiguration {@link Configuration} la configuration à mettre à jour.
	 * @param parValue {@code Sttring[]} un tableau d'éléments.
	 * @throws BlindTestException Si un argument n'a aucune valeur associée.
	 */
	static public final void updateConfiguration(final Configuration parConfiguration, final String[] parValue) throws BlindTestException {
		if (parValue == null || parValue.length == 0 || parConfiguration == null) {
			return;
		}
		final EnumConfiguration[] locConfigurationValues = EnumConfiguration.values();
		final List<String> locValueList = Arrays.asList(parValue);
		for (final EnumConfiguration locConfiguration : locConfigurationValues) {
			// si la valeur n'est pas un argument on le skip
			final int locArgument = locValueList.indexOf(locConfiguration.getArgument());
			if (locArgument == -1) {
				continue;
			}
			// si la valeur de l'argument est vide
			if (locArgument + 1 >= parValue.length) {
				throw new BlindTestException();
			}
			final String locArgValue = parValue[locArgument + 1];
			locConfiguration.setConfigurationValue(parConfiguration, locArgValue);
		}
	}

	/**
	 * Le fichier contenant la documentation des arguments au lancement. Elle est similaire à un "man".
	 */
	static private final String DOCUMENTATION = "conf/documentation.txt";

	/**
	 * Retourne le contenu de la documentation du serveur à propos des arguments et du lancement
	 * du serveur avec ceux-ci. Si le fichier de documentation n'existe pas, un message d'erreur
	 * est spécifié dans la console indiquant que l'on ne peut l'afficher.
	 * @return {@link String} le contenu de la documentation du serveur.
	 */
	static public final String getDocumentation() {
		FileInputStream locStream = null;
		try {
			final File locFile = new File(DOCUMENTATION);
			locStream = new FileInputStream(locFile);
			final byte[] locByteArray = new byte[((int) locFile.length()) + 1];
			locStream.read(locByteArray);
			return new String(locByteArray, "UTF-8");
		} catch (IOException e) {
			final String locMessage = "Le fichier de documentation %s n'est pas accessible. Impossible d'afficher cette dernière.";
			System.out.println(locMessage);
			e.printStackTrace();
			return locMessage;
		} finally {
			SystemUtil.close(locStream);
		}
	}
	
	static public final List<String> getKeys() {
		final EnumConfiguration[] locValues = values();
		final List<String> locKeys = new ArrayList<String>(locValues.length);
		for(final EnumConfiguration locValue : locValues) {
			locKeys.add(locValue.getConstName());
		}
		return locKeys;
	}
}
