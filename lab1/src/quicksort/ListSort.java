package quicksort;

import java.util.Iterator;
import list.*;
import misc.Tuple;
import quicksort.QuickSort.PivotPositions;
import sortingAnalyzer.QuickSortAnalyzer;

/**
 * Class for quick-sort operations.
 * 
 * @author Robin, Oskar
 *
 */
public class ListSort implements QuickSort<NodeList<Integer>> {
	
	@Deprecated
	public static void main(String[] args) {
		/*NodeList<Integer> list = QuickSortAnalyzer.generateList(5);
		ListSort sorter = new ListSort();
		try {
			new ListSort().sort(list, PivotPositions.RANDOM);
			sorter.sort(list, PivotPositions.FIRST);
		} catch (UnsupportedPivotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sorter.comparisons);
		*/
	}

	@Override
	public void sort(NodeList<Integer> list, PivotPositions pivot) throws UnsupportedPivotException {
		switch (pivot) {
		case FIRST:
			this.sortFirst(list.createNodeChain());
			break;
		case RANDOM:
			this.sortRandom(list.createNodeChain());
			break;
		case MIDDLE:
			this.sortMiddle(list.createNodeChain());
			break;
		default:
			throw new UnsupportedPivotException(pivot);	// The pivot is neither last, random or middle (it's null etc).
		}
		
	}

	/**
	 * Quicksort with random pivot
	 * 
	 * @param list - A {@link NodeChain} containing all elements to sort.
	 */
	private void sortRandom(NodeChain<Integer> list) {

		if (list == null || list.getSize() <= 1) { // Return if the list has 1 or 0 elements.
			return;
		}
		int pivotIndex = (int)(Math.random() * list.getSize());

		// Splits the chain into two parts.
		Tuple<NodeChain<Integer>, NodeChain<Integer>> split = this.recursiveSorter(list, pivotIndex);

		// Sort the individual chains. (Recursive step)
		sortRandom(split.x);
		sortRandom(split.y);
	}
	
	/**
	 * Quicksort with fixed pivot at index 0.
	 * 
	 * @param list - A {@link NodeChain} containing all elements to sort.
	 */
	private void sortFirst(NodeChain<Integer> list) {
		if (list == null || list.getSize() <= 1) { // Return if the list has 1 or 0 elements.
			return;
		}
		int pivotIndex = 0;

		// Splits the chain into two parts.
		Tuple<NodeChain<Integer>, NodeChain<Integer>> split = this.recursiveSorter(list, pivotIndex);

		// Sort the individual chains. (Recursive step)
		sortFirst(split.x);
		sortFirst(split.y);
	}
	
	
	/**
	 * Quicksort with fixed pivot at the middle.
	 * 
	 * @param list - A {@link NodeChain} containing all elements to sort.
	 */
	private void sortMiddle(NodeChain<Integer> list) {
		if (list == null || list.getSize() <= 1) { // Return if the list has 1 or 0 elements.
			return;
		}
		int pivotIndex = list.getSize()/2;

		// Splits the chain into two parts.
		Tuple<NodeChain<Integer>, NodeChain<Integer>> split = this.recursiveSorter(list, pivotIndex);

		// Sort the individual chains. (Recursive step)
		sortMiddle(split.x);
		sortMiddle(split.y);
	}
	
	/**
	 * Helper method for {@link #sortFirst(NodeChain)},  {@link #sortRandom(NodeChain)} and {@link #sortMiddle(NodeChain)}.
	 * @param list
	 * @param pivotIndex
	 * @return
	 */
	private Tuple<NodeChain<Integer>, NodeChain<Integer>> recursiveSorter(NodeChain<Integer> list, int pivotIndex) {
		Node<Integer> pivotNode = list.findNode(pivotIndex);

		pivotIndex = SortFromPivot(list, pivotNode.getValue(), pivotIndex);

		if (list.getSize() <= 2) { // The list is too small for further sorting. It is already sorted.
			return new Tuple<NodeChain<Integer>, NodeChain<Integer>>(null, null);
		}

		// Splits the chain into two parts.
		return list.splitChain(pivotNode, pivotIndex);
	}

	/**
	 * Put Large elements after the pivot and small ones before the pivot.
	 * 
	 * @param list - The list to sort
	 * @param pivot	- The pivot number.
	 * @param pivIndex - the index for the pivot number.
	 * @return The new pivot index. Useful if the pivot index is used directly after
	 *         this function.
	 */
	private int SortFromPivot(NodeChain<Integer> list, int pivot, int pivIndex) {
		if (list.isEmpty()) {
			return 0;
		}

		int newIndex = pivIndex;
		Iterator<Node<Integer>> iterator = list.iterator();
		Node<Integer> node;
		
		// First iterate from index 0 to pivIndex-1.
		// Move to the right if the value is larger than the pivot value.
		int i = 0;
		for (; i < pivIndex; i++) {
			node = iterator.next();
			if (node.getValue() > pivot) {
				list.moveLast(node);
				newIndex--;
			}
		}

		// We have reached the pivot node. Skip it.
		iterator.next();
		i++;

		// Step 2: Iterate from pivIndex + 1 to the last index.
		// Move to the left if the value is smaller than the pivot value.
		for (; i < list.getSize(); i++) {
			node = iterator.next();
			if (node.getValue() < pivot) {
				list.moveFirst(node);
				newIndex++;
			}
		}
		return newIndex;
	}
}
 