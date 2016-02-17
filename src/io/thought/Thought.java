package io.thought;

import java.util.Arrays;

public class Thought {
	public static int[] findInts(int[] parameters, int size, int min, int max, Searcher s) {
		int[] toReturn = new int[size];
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = max;
		}
		
		for (int i = 0; i < toReturn.length; i++) {
			int c1 = min;
			int c2 = 0;
			int c3 = max;
			
			while (true) {
				c2 = (c3 + c1) / 2;
				System.out.println(c1 + ", " + c2 + ", " + c3);
				
				int dist = c3 - c1;
				if (dist < 2) {
					break;
				}
				
				//Find the middle distance.  Keep changing c if the current c is invalid
				int distance = 0;
				while (true) {
					try {
						distance = s.getDistance(toReturn, i, c2, parameters);
						break;
					} catch (InvalidNumberException e) {
						c2 = e.getValue();
						continue;
					}
				}
				
				if (distance == 0) {
					break;
				}
				
				//Same for largest
				int twiceDistance = 0;
				while (true) {
					try {
						twiceDistance = s.getDistance(toReturn, i, c3, parameters);
						break;
					} catch (InvalidNumberException e) {
						c3 = e.getValue();
						continue;
					}
				}
				if (twiceDistance == 0) {
					c2 = c3;
					break;
				}
				
				//And for smallest
				int halfDistance = 0;
				while (true) {
					try {
						halfDistance = s.getDistance(toReturn, i, c1, parameters);
						break;
					} catch (InvalidNumberException e) {
						c1 = e.getValue();
						continue;
					}
				}
				System.out.println("@" + c1 + ", " + c2 + ", " + c3);
				System.out.println("$" + halfDistance + ", " + distance + ", " + twiceDistance);
				
				if (halfDistance == 0) {
					c2 = c1;
					break;
				}
				
				//Set the new c values depending on the distance
				if (twiceDistance < 0) {
					c1 = c3;
					c3 += dist * 2;
				} else if (halfDistance > 0) {
					c3 = c1;
					c1 -= dist * 2;
				} else if (distance > 0) {
					c3 = c2;
				} else {
					c1 = c2;
				}
			}
			toReturn[i] = c2;
		}
		return toReturn;
	}
	
	public static void main(String[] args) {
		int sq = 25;
		System.out.println(Arrays.toString(findInts(
				new int[]{sq},
				2, 1, sq,
				new Searcher() {
					@Override
					public int getDistance(int[] currentNums, int index, int value, int[] parameters) throws InvalidNumberException {
						if (value < 2) {
							throw new InvalidNumberException(2);
						}
						int total = value;
						for (int i = 0; i < currentNums.length; i++) {
							if (i != index) {
								total *= currentNums[i];
							}
						}
						return total - parameters[0];
					}
				})));
	}
}
