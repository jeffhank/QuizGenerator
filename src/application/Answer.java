package application;

public class Answer {
	private boolean isCorrect;
	private String choice;

	public Answer(boolean isCorrect, String choice) {
		this.isCorrect = isCorrect;
		this.choice = choice;
	}

	public boolean getIsCorrect() {
		return this.isCorrect;
	}

	public String getChoice() {
		return this.choice;
	}
}
