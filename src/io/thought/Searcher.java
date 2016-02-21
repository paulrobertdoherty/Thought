package io.thought;

public interface Searcher {
	public long getDistance(int[] currentNumbs, int index, int value, int[] parameters) throws InvalidNumberException;
}