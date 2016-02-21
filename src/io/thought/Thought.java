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
				
				//Find the middle distance.  Keep changing c if the current c is invalid
				long distance = 0;
				while (true) {
					try {
						distance = s.getDistance(toReturn, i, c2, parameters);
						break;
					} catch (InvalidNumberException e) {
						c2 = e.getValue();
						//Swapping c if necesary
						if (c2 > c3) {
						    int temp = c2;
						    c2 = c3;
						    c3 = temp;
						} else if (c2 < c1) {
						    int temp = c2;
						    c2 = c1;
						    c1 = temp;
						}
						continue;
					}
				}
				
				if (distance == 0) {
					break;
				}
				
				//Same for largest
				long twiceDistance = 0;
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
				long halfDistance = 0;
				while (true) {
					try {
						halfDistance = s.getDistance(toReturn, i, c1, parameters);
						break;
					} catch (InvalidNumberException e) {
						c1 = e.getValue();
						continue;
					}
				}
				
				if (halfDistance == 0) {
					c2 = c1;
					break;
				}
				
				//Find the distance between the distances.  If it's small, then say it's a success
				int dist = c3 - c1;
				if (dist < 2) {
					break;
				}
				
				//Set the new c values depending on the distance
				if (halfDistance > 0) {
					c3 = c1;
					c1 -= dist * 2;
				} else if (twiceDistance < 0) {
					c1 = c3;
					c3 += dist * 2;
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
		int[] params = new int[]{6000};
		long start = System.currentTimeMillis();
		int[] results = findInts(
				params,
				1, 1, params[0],
				new Searcher() {
					@Override
					public long getDistance(int[] currentNums, int index, int value, int[] parameters) throws InvalidNumberException {
						//Something I did to find factors
						/*
						if (value < 5) {
							throw new InvalidNumberException(5);
						}
						long total = value;
						for (int i = 0; i < currentNums.length; i++) {
							if (i != index) {
								total *= currentNums[i];
							}
						}
						return total - parameters[0];
						*/
						
						//This solves a quadratic equation
						return (((2 * value) + 15) * ((5 * value) - 1)) - parameters[0];
					}
				});
		System.out.println("Found the answer for " + Arrays.toString(params) + " in " + 
	    (System.currentTimeMillis() - start) + " milliseconds");
		System.out.println("The answer is " + Arrays.toString(results));
	}
}