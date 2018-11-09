package main;

import java.util.Random;
import java.util.Iterator;

import main.NodeList.*;

/**
 * Class for quick-sort operations.
 * @author Robin
 *
 */
public class QuickSort {
	private static int pivotIndex;	// this is used to keep track of the pivot node.
	
	public static void main(String[] args) {
		NodeList<Integer> list = new NodeList<Integer>();
		Random r = new Random();
		for (int i = 0; i < 30; i++) {
			list.appendEnd(r.nextInt(1000));
		}
		sort(list);
		clean(list);
		System.out.println(list);
	}
	
	/**
	 * Dirty cleanup function. We need to remove this.
	 * @param list
	 */
	private static void clean(NodeList<Integer> list) {
		for (Node<Integer> n : list) {
			if (n.getValue() == null && !(n.equals(list.getFirst().getPrevious()) || n.equals(list.getLast().getNext()))) {
				//list.removeNode(n);
				n.removeThis();
			}
		}
	}
	
	
	/**
	 * Sort a list using quick-sort. The pivot is chosen randomly.
	 * @param list - The list to sort.
	 * @return
	 */
	public static void sort(NodeList<Integer> list) {
		pivotIndex = (int)(Math.random()*list.getSize());	// create a random pivot.
		
		Node<Integer> pivotNode = list.findNode(pivotIndex);
		
		lazySort(list, pivotNode.getValue());
		
		if (list.getSize() <= 2) {				// the list is too small for sorting.
			return;
		}
		
		
		if (pivotIndex == 0) {													// the pivot is on index 0
			sort(list.createListSubset(pivotNode.getNext(), list.getLast()));
			
		} else if (pivotIndex == (list.getSize()-1)) {							// the pivot is on the last index.
			sort(list.createListSubset(list.getFirst(), pivotNode.getPrevious()));
		}
		
		else {
			sort(list.createListSubset(list.getFirst(), pivotNode.getPrevious()));
			sort(list.createListSubset(pivotNode.getNext(), list.getLast()));
		
		}
	}
	
	/**
	 * Sort a list using quick-sort with fixed pivot.
	 * @param list - The list to sort.
	 * @param pivot - Pivot element.
	 * @return
	 */
	public static int[] sort(int[] list, int pivot) {
		//TODO make it work somehow.
		return null;
	}

	

	/**
	 * Sort a {@link NodeList} with a size larger than 1. Every integer larger than the pivot is put last, and
	 * every integer smaller is put first.
	 * @param list - The list to sort
	 * @param pivot - the pivot number.
	 * @return
	 */
	private static void lazySort(NodeList<Integer> list, int pivot) {
		if (list.getSize() <= 1) {
			return;
		}
		
		Iterator<Node<Integer>> iterator = list.iterator();
		Node<Integer> n = iterator.next();
		int previousValue = n.getValue();
		int newPivotIndex = pivotIndex;
		
		for (int i = 1; i < list.getSize(); i++) {		// N.B. Start on index 1
			n = iterator.next();			// skip ahead one node and perform operations on the previous one.
			
			
			// large numbers are move to higher index and small number are move to lower index,
			// but only if the are on the wrong "side" of the pivot.
			if (i-1 < pivotIndex && previousValue > pivot) {	// put large numbers after pivot
				list.appendEnd(list.removeNode(n.getPrevious()));
				newPivotIndex --;
			} else if (i-1 > pivotIndex && previousValue < pivot) {		// put small numbers before pivot.
				list.appendStart(list.removeNode(n.getPrevious()));
				newPivotIndex ++;
			}		
			
			previousValue = n.getValue();	// update the previous value for next iteration.
		}
		
		if (list.getSize()-1 > pivotIndex && previousValue < pivot) {		// one extra if for the last index.
			list.appendStart(list.removeNode(n));
			newPivotIndex ++;
		}
		
		// update pivot index.
		pivotIndex = newPivotIndex;
	}
}
