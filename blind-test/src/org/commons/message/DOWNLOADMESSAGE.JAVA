package org.commons.message;

import java.util.HashMap;
import java.util.Map;


public final class DownloadMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private Map<String, byte[]> download = null; 

	protected DownloadMessage() {
		this (new HashMap<String, byte[]>());
	}

	protected DownloadMessage(Map<String, byte[]> download) {
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
