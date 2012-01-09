package org.commons.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class DownloadDefaultMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private Map<String, byte[]> download; 

	protected DownloadDefaultMessage() {
		this (new HashMap<String, byte[]>());
	}

	protected DownloadDefaultMessage(final Map<String, byte[]> download) {
		super(EnumMessage.DOWNLOAD);
		this.download = download;
	}
	
	public Map<String, byte[]> getDownload() {
		return download;
	}

	public final byte[] put(final String parName, final byte[] parValue) throws IOException {
		return download.put(parName, parValue);
	}
	
	public final byte[] get (final String parName) {
		return download.get(parName);
	}
	
	public final int size() {
		return download.size();
	}
	
	public final List<byte[]> values() {
		return new ArrayList<byte[]>(download.values());
	}
	
	public final List<String> keys() {
		return new ArrayList<String>(download.keySet());
	}
}
