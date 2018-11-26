package sortingAnalyzer.providers;

import list.NodeList;
import quicksort.ArraySort;
import quicksort.QuickSort;

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
