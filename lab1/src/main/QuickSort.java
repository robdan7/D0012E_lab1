package main;

import java.math.*;;

/**
 * Class for quicksort operations.
 * @author Robin
 *
 */
public class QuickSort {
	
	/**
	 * Sort a list using quick-sort. The pivot is chosen randomly.
	 * @param list - The list to sort.
	 * @return
	 */
	public static int[] sort(int[] list) {
		int pivot = (int)(Math.random()*list.length);	// create a random pivot.
		return sort(list, pivot);
	}
	
	
	/**
	 * Sort a list using quick-sort.
	 * @param list - The list to sort.
	 * @param pivot - Pivot element.
	 * @return
	 */
	public static int[] sort(int[] list, int pivot) {
		
		if (list.length == 1) {
			return list;								// return statement.
		}
		

		
		
		return null;
	}

}
