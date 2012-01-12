package org.commons.message;

public class DisplayMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String fileName = null;
	
	protected DisplayMessage() {
		this("");
	}
	
	protected DisplayMessage(String fileName) {
		super(EnumMessage.DISPLAY);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
