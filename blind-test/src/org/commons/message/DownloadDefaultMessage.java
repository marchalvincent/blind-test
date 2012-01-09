package org.commons.message;

import java.util.HashMap;
import java.util.Map;


public final class DownloadDefaultMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private Map<String, byte[]> download = null; 

	protected DownloadDefaultMessage() {
		this (new HashMap<String, byte[]>());
	}

	protected DownloadDefaultMessage(Map<String, byte[]> download) {
		super(EnumMessage.DOWNLOAD);
		this.download = download;
	}
	
	public Map<String, byte[]> getDownload() {
		return download;
	}

	public void setDownload(Map<String, byte[]> download) {
		this.download = download;
	}
}
