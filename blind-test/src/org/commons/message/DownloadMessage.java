package org.commons.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class DownloadMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private Map<String, byte[]> download; 
	private Map<String, Integer> versioning;

	protected DownloadMessage() {
		this (new HashMap<String, byte[]>(), new HashMap<String, Integer>());
	}

	protected DownloadMessage(final Map<String, byte[]> download, final Map<String, Integer> versioning) {
		super(EnumMessage.DOWNLOAD);
		this.download = download;
		this.versioning = versioning;
	}
	
	public final Map<String, byte[]> getDownload() {
		return new HashMap<String, byte[]>(download);
	}
	
	public final Map<String, Integer> getVersions() {
		return new HashMap<String, Integer>(versioning);
	}
	
	public final void putAllVersion(final Map<String, Integer> parVersionMap) {
		versioning.putAll(parVersionMap);
	}
	
	public final void putAllImage(final Map<String, byte[]> parImagesMap) {
		download.putAll(parImagesMap);
	}

	public final byte[] put(final String parName, final byte[] parValue) {
		return download.put(parName, parValue);
	}
	
	public final Integer put(final String parName, final Integer parVersion) {
		return versioning.put(parName, parVersion);
	}
	
	public final byte[] getImage (final String parName) {
		return download.get(parName);
	}
	
	public final Integer gerVersion(final String parName) {
		return versioning.get(parName);
	}
	
	public final int size() {
		return download.size();
	}
	
	public final List<byte[]> valuesImages() {
		return new ArrayList<byte[]>(download.values());
	}
	
	public final List<Integer> valuesVersion() {
		return new ArrayList<Integer>(versioning.values());
	}
	
	public final List<String> keys() {
		return new ArrayList<String>(download.keySet());
	}
}
