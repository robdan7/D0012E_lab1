package main;

import java.util.Iterator;
import java.util.Random;

import list.*;
import misc.Tuple;


/**
 * Class for quick-sort operations.
 * @author Robin, Oskar
 *
 */
public class QuickSort {
	private int comparisons;
	
	
	public void sort(NodeList<Integer> list, QuickSort.PivotPositions pivot) {
		this.comparisons = 0;
		
		switch(pivot) {
		case LAST:
			// TODO add sorting for the last element.
			break;
		case RANDOM:
			this.sortRandom(list.createNodeChain());
		case MIDDLE:
			// TODO add sorting for the middle element.
			break;
		default:
			break;
		}
	}
	
	/**
	 * Sort a list using quick-sort. The pivot is chosen randomly.
	 * @param list - A {@link NodeChain} containing all elements to sort.
	 * @return
	 */
	private void sortRandom(NodeChain<Integer> list) {
		
		if(list.getSize() <= 1) {									// Return if the list has 1 or 0 elements.
			return;
		}
		
		Integer pivotIndex = (int)(Math.random()*list.getSize());	// Create a random pivot index.
		Node<Integer> pivotNode = list.findNode(pivotIndex);		
		
		pivotIndex = SortFromPivot(list, pivotNode.getValue(), pivotIndex);
		
		if (list.getSize() <= 2) {									// The list is too small for further sorting. It is already sorted.
			return;
		}
		
		// Splits the chain into two parts.
		Tuple<NodeChain<Integer>, NodeChain<Integer>> split = list.splitChain(pivotNode, pivotIndex);

		// Sort the individual chains. (Recursive step)
		sortRandom(split.x);
		sortRandom(split.y);
	}
	
	
	public void sortLastPivot(NodeList<Integer> list) {
		
	}
	
	public void sortFirstPivot(NodeList<Integer> list) {
		
	}
	
	/**
	 * Sort a list using quick-sort with fixed pivot.
	 * @param list - The list to sort.
	 * @param pivot - Pivot element.
	 * @return
	 */
	public static void sort(int[] list, int pivot) {
		//TODO make it work somehow.
	}

	

	/**
	 * Put Large elements after the pivot and small ones before the pivot.
	 * @param list - The list to sort
	 * @param pivot - the pivot number.
	 * @return The new pivot index. Useful if the pivot index is used directly after this function.
	 */
	private int SortFromPivot(NodeChain<Integer> list, int pivot, int pivIndex) {
		if (list.isEmpty()) {
			return 0;
		}
		
		int newIndex = pivIndex;
		int i = 0;
		Iterator<Node<Integer>> iterator = list.iterator();
		Node<Integer> node;
		
		// First iterate from index 0 to pivIndex-1.
		// Move to the right if the value is larger than the pivot value.
		for (; i < pivIndex; i++) {
			node = iterator.next();
			if (node.getValue() > pivot) {
				list.moveLast(node);
				newIndex --;
			}
			this.comparisons ++;
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
			this.comparisons ++;
		}
		return newIndex;
	}
	
	public static enum PivotPositions{
		LAST, RANDOM, MIDDLE;
		
		@Override
		public String toString() {
			String s = "";
			switch (this) {
			case LAST:
				s = "last";
				break;
			case RANDOM:
				s = "random";
				break;
			case MIDDLE:
				s = "middle";
				break;
			}
			return s;
		}
	}
	
	/**
	 * Class for generating unsupported pivot exceptions. The pivot is not supported if
	 * this exception is thrown.
	 * @author Robin
	 *
	 */
	public static class UnsupportedPivotException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 853436763252048434L;
		private final PivotPositions pivot;
		public UnsupportedPivotException(PivotPositions pivot) {
			this.pivot = pivot;
		}
		
		@Override
		public void printStackTrace() {
			System.out.println(pivot);
		}
	}
	
	
	public int getComparisons() {
		return this.comparisons;
	}
	
	
	@Override
	public String toString() {
		return "comparisons: " + this.comparisons;
	}
}
