package sortingAnalyzer.providers;

import java.util.Random;

/**
 * Class for generating arrays of varying randomness.
 * 
 * @author Robin
 *
 */
public class ArrayListProvider implements ListProvider<int[]> {

	@Override
	public int[] next(int requestedSize, listCriteria criteria) {
		int[] result = new int[requestedSize];
		switch (criteria) {
		case RANDOM:
			result = this.nextRandom(requestedSize);
			break;
		case SORTED:
			result = this.nextSorted(requestedSize);
			break;
		case ALMOST_SORTED_90:
			result = this.nextSorted90(requestedSize);
			break;
		case ALMOST_SORTED_75:
			result = this.nextSorted75(requestedSize);
			break;
		default:
			// TODO some sort of error.
			break;
		}
		return result;
	}

	@Override
	public int[] nextRandom(int size) {
		return this.generateRandom(size);
	}

	@Override
	public int[] nextSorted(int size) {
		return this.generateSorted(size);
	}

	@Override
	public int[] nextSorted90(int size) {
		// this method and nextSorted75 could be put together, but I don't care enough to do that.
		int[] list = this.generateSorted(size);
		Random r = new Random();
		for (int i = 0; i < list.length; i++) {
			if (r.nextFloat() < 0.1f) {
				int temp = r.nextInt();
				while (Math.abs(temp - list[i]) <= 1) {
					temp = r.nextInt();
				}
				list[i] = temp;
			}
		}
		return list;
	}

	@Override
	public int[] nextSorted75(int size) {
		int[] list = this.generateSorted(size);
		Random r = new Random();
		for (int i = 0; i < list.length; i++) {
			if (r.nextFloat() < 0.25f) {
				int temp = r.nextInt();
				while (Math.abs(temp - list[i]) <= 1) {
					temp = r.nextInt();
				}
				list[i] = temp;
			}
		}
		return list;
	}
	
	/**
	 * Generate a list with random integer.
	 * @param size
	 * @return
	 */
	private int[] generateRandom(int size) {
		int[] list = new int[size];
		Random r = new Random();
		for (int i = 0; i < list.length; i++) {
			list[i] = r.nextInt();
		}
		return list;
	}
	
	/**
	 * Generate a list with sorted integers.
	 * @param size
	 * @return
	 */
	private int[] generateSorted(int size) {
		int[] list = new int[size];
		
		for (int i = 0 ; i < list.length; i++) {
			list[i] = i;
		}
		
		return list;
	}

	@Override
	public int[] replicate(int[] list) {
		int[] result = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			result[i] = list[i];
		}
		return result;
	}

}
