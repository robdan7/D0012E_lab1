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
	//private static final String regex = ",";
	

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
			case ALMOST_SORTED_80:
				result = this.nextSorted80(requestedSize);
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
		NodeList<Integer> list = new NodeList<Integer>();

		for (int i = 0; i < size; i++) {
			list.appendEnd(i);
		}
		return list;

	}
	
	@Override
	public NodeList<Integer> nextSorted80(int size) {
		return null;
	}
	
	@Override
	public NodeList<Integer> nextSorted75(int size) {
		return null;
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
	
	private NodeList<Integer> generateSorted() {
		return null;
	}

	@Override
	public NodeList<Integer> replicate(NodeList<Integer> list) {
		return list.clone();
	}
	
	

}
