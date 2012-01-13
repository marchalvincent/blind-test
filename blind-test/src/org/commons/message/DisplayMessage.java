package org.commons.message;

import org.client.ui.display.EnumDisplayImage;

public class DisplayMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String fileName;
	private Integer _type;
	
	protected DisplayMessage() {
		this("");
	}
	
	protected DisplayMessage(String fileName) {
		super(EnumMessage.DISPLAY);
		this.fileName = fileName;
		_type = EnumDisplayImage.NONE.getId();
	}
	
	public final Integer getType() {
		return _type;
	}
	
	public final void setType(final Integer parType) {
		_type = parType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
