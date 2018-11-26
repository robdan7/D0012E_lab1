package sortingAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import list.NodeList;

/**
 * class for generating lists from either a text file or pseudo-randomly.
 * @author Robin
 *
 */
public class ListProvider {
	NodeList<NodeList<Integer>> lists;
	private static final String regex = ",";

	/**
	 * Creates a list provider.
	 */
	public ListProvider() {
		this.lists = new NodeList<NodeList<Integer>>();
	}
	
	/**
	 * Creates a list provider with pre-loaded lists.
	 * @param file - The file to load from.
	 */
	public ListProvider(String file) {
		this();
		try {
			this.readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load lists from a file.
	 * @param file
	 * @throws IOException
	 */
	private void readFile(String file) throws IOException {
		File f = new File(file);
		//FileReader in = new FileReader(f);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		
		String s;
		while((s = reader.readLine()) != null) {
			String[] integers = s.split(regex);
			NodeList<Integer> tempList = new NodeList<Integer>();
			for (int i = 0 ; i < integers.length; i++) {
				tempList.appendEnd(Integer.parseInt(integers[i]));
			}
			this.lists.appendEnd(tempList);
		}
		reader.close();
	}
	
	/**
	 * @param requestedSize
	 * @return The next list in order.
	 */
	public NodeList<Integer> next(int requestedSize, listCriteria criteria) {
		NodeList<Integer> result = new NodeList<Integer>();
		switch (criteria) {
			case RANDOM:
				result = this.nextRandom(requestedSize);
				break;
			case SORTED:
				result = this.nextSorted(requestedSize);
				break;
			case ALMOST_SORTED_75:
				result = this.nextSorted75(requestedSize);
				break;
			case ALMOST_SORTED_50:
				result = this.nextSorted50(requestedSize);
				break;
			default:
				// TODO
				break;
		}
		
		return result;
	}
	
	private NodeList<Integer> nextRandom(int size) {
		NodeList<Integer> list = new NodeList<Integer>();
		if (this.lists.isEmpty()) {
			list = this.generateRandom(size);
		}
		
		list = this.lists.getFirst();
		this.lists.rmFirst();
		
		return list;
	}
	
	private NodeList<Integer> nextSorted(int size) {
		NodeList<Integer> list = new NodeList<Integer>();
		if (this.lists.isEmpty()) {
			for (int i = 0; i < size; i++) {
				list.appendEnd(i);
			}
			return list;
		}
		
		list = this.lists.getFirst();
		this.lists.rmFirst();
		
		return list;
	}
	
	private NodeList<Integer> nextSorted75(int size) {
		return null;
	}
	
	private NodeList<Integer> nextSorted50(int size) {
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
	
	public static enum listCriteria {
		RANDOM, SORTED, ALMOST_SORTED_75, ALMOST_SORTED_50;
	}

}
