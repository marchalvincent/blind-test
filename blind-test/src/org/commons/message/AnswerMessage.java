package org.commons.message;


public final class AnswerMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String login = null;
	private String answer = null;

	protected AnswerMessage() {
		this ("", "");
	}
	
	protected AnswerMessage(String login, String answer) {
		super(EnumMessage.ANSWER);
		this.login = login;
		this.answer = answer;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
