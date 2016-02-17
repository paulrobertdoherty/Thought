package io.thought;

public interface Searcher {
	public int getDistance(int[] currentNumbs, int index, int value, int[] parameters) throws InvalidNumberException;
}