package sortingAnalyzer.providers;

import quicksort.ArraySort;
import quicksort.QuickSort;

/**
 * Quick-sort provider for arrays.
 * This class is purely done for internal testing.
 * @author Robin
 *
 */
public class ArrayQuickSortProvider implements QuickSortProvider<int[]>{
	private ListProvider<int[]> listProvider;
	private QuickSort<int[]> sorter;
	
	public ArrayQuickSortProvider() {
		this.listProvider = new ArrayListProvider();
		this.sorter = new ArraySort();
	}

	@Override
	public ListProvider<int[]> getListProvider() {
		return this.listProvider;
	}

	@Override
	public QuickSort<int[]> getQuickSorter() {
		return this.sorter;
	}

}
