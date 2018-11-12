package main;

import java.util.Iterator;
import java.util.Random;

import list.*;
import misc.Tuple;


/**
 * Class for quick-sort operations.
 * @author Robin
 *
 */
public class QuickSort {
	//private static int pivotIndex;	// this is used to keep track of the pivot node.
	
	public static void main(String[] args) {
		NodeList<Integer> list = new NodeList<Integer>();
		Random r = new Random();
		
		int stop = 10;
		for (int i = 0; i < stop; i++) {
			list.appendEnd(r.nextInt(stop));
		}

		sort(list.createNodeChain());
		System.out.println(list);
		//System.out.println();
	}	
	
	/**
	 * Sort a list using quick-sort. The pivot is chosen randomly.
	 * @param list - A {@link NodeChain} containing all elements to sort.
	 * @return
	 */
	public static void sort(NodeChain<Integer> list) {
		
		if(list.getSize() <= 1) {				// return if the list has 1 or 0 elements.
			return;
		}
		
		Integer pivotIndex = (int)(Math.random()*list.getSize());	// create a random pivot index.
		Node<Integer> pivotNode = list.findNode(pivotIndex);		
		
		pivotIndex = SortFromPivot(list, pivotNode.getValue(), pivotIndex);

		if (list.getSize() <= 2) {				// the list is too small for further sorting. It is already sorted.
			return;
		}
		
		// split the chain in two parts.
		Tuple<NodeChain<Integer>, NodeChain<Integer>> split = list.splitChain(pivotNode, pivotIndex);

		// sort the individual chains.
		sort(split.x);
		sort(split.y);
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
	private static int SortFromPivot(NodeChain<Integer> list, int pivot, int pivIndex) {
		if (list.isEmpty()) {
			return 0;
		}
		
		int newIndex = pivIndex;
		int i = 0;
		Iterator<Node<Integer>> iterator = list.iterator();
		Node<Integer> node;
		
		// first iterate from index 0 to pivIndex-1.
		// move to right if the value is larger than the pivot value.
		for (; i < pivIndex; i++) {
			node = iterator.next();
			if (node.getValue() > pivot) {
				list.moveLast(node);
				newIndex --;
			}
		}
		
		// we have reached the pivot node. Skip it.
		iterator.next();
		i++;
		
		// step 2: iterate from pivIndex + 1 to the last index.
		// move to left if the value is smaller than the pivot value.
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
