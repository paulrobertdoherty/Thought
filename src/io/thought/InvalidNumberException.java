package io.thought;

public class InvalidNumberException extends Exception {
	private static final long serialVersionUID = 514326855132879706L;
	
	private int value;

	public int getValue() {
		return value;
	}

	public InvalidNumberException(int value) {
		this.value = value;
	}
}