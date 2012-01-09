package org.commons.incremental;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.commons.configuration.ConfigurationManager;
import org.commons.util.StringUtil;

public final class ImageIndex {

	private final Map<String, Integer> _images;
	
	public ImageIndex() {
		_images = new HashMap<String, Integer>();
	}
	
	public final void load() throws IOException {
		final String locIndexFile = ConfigurationManager.getConfiguration().getIndexFile();
		final Properties locProperties = new Properties();
		locProperties.load(new FileInputStream(locIndexFile));
		for(final Map.Entry<Object, Object> locEntry : locProperties.entrySet()) {
			final String locValue = locEntry.getValue().toString();
			if(false == StringUtil.isNumeric(locValue)) {
				continue;
			}
			final String locKey = locEntry.getKey().toString();
			final Integer locVersion = StringUtil.toInteger(locValue);
			_images.put(locKey, locVersion);
		}
	}
	
	public final Map<String, Integer> getData() {
		return new HashMap<String, Integer>(_images);
	}
	
	public final void put(final String parFile, final Integer parVersion) {
		_images.put(parFile, parVersion);
	}
	
	public final void putAll (final Map<String, Integer> parMap) {
		_images.putAll(parMap);
	}
	
	public final void write() throws IOException {
		final Properties locProperties = new Properties();
		for(final Map.Entry<String, Integer> locEntry : _images.entrySet()) {
			locProperties.setProperty(locEntry.getKey(), locEntry.getValue().toString());
		}
		if(locProperties.isEmpty() == true) {
			return;
		}
		final String locIndexFile = ConfigurationManager.getConfiguration().getIndexFile();
		locProperties.store(new FileOutputStream(locIndexFile), "");
	}
	
}
