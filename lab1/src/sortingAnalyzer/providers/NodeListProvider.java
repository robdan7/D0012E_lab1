package sortingAnalyzer.providers;

import java.util.Random;

import list.NodeList;

/**
 * Class for generating lists of varying randomness.
 * 
 * @author Robin
 *
 */
public class NodeListProvider implements ListProvider<NodeList<Integer>> {
	

	@Override
	public NodeList<Integer> next(int requestedSize, listCriteria criteria) {
		NodeList<Integer> result = new NodeList<Integer>();
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
	public NodeList<Integer> nextRandom(int size) {
		return this.generateRandom(size);
	}

	@Override
	public NodeList<Integer> nextSorted(int size) {
		return this.generateSorted(size);
	}
	
	@Override
	public NodeList<Integer> nextSorted90(int size) {
		return this.generateSortedPercentage(size, 0.9f);
	}
	
	@Override
	public NodeList<Integer> nextSorted75(int size) {
		return this.generateSortedPercentage(size, 0.75f);
	}
	
	/**
	 * Generate a list with random integers.
	 * @param size - The size.
	 * @return A list with pseudo-random numbers.
	 */
	private NodeList<Integer> generateRandom(int size) {
		NodeList<Integer> list = new NodeList<Integer>();
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			list.appendEnd(r.nextInt(size));
		}
		
		return list;
	}
	
	/**
	 * Generate a sorted list with integers.
	 * @param size
	 * @return
	 */
	private NodeList<Integer> generateSorted(int size) {
		NodeList<Integer> list = new NodeList<Integer>();
		for (int i = 0; i < size; i++) {
			list.appendEnd(i);
		}
		return list;
	}
	
	/**
	 * Generate a partially sorted list with integers.
	 * @param size
	 * @param sortedPercentage - percentage in decimal form.
	 * @return
	 */
	private NodeList<Integer> generateSortedPercentage(int size, float sortedPercentage) {
		
		if (sortedPercentage < 0f || sortedPercentage > 1.0f) {
			throw new IllegalArgumentException("percentage out of range");
		}
		
		NodeList<Integer> list = new NodeList<Integer>();
		Random r = new Random();
		for (int i = 0, num = 0; i < size; i++) {
			num = i;
			if (r.nextFloat() < (1.0f-sortedPercentage)) {
				while (Math.abs(num-i) <= 1) {		// make sure num is a number out of order.
					num = r.nextInt();
				}
			}
			
			list.appendEnd(num);
		}
		
		return list;
	}

	@Override
	public NodeList<Integer> replicate(NodeList<Integer> list) {
		return list.copy();
	}
	
	

}
