package sortingAnalyzer.providers;

import quicksort.QuickSort;

public interface QuickSortProvider<A> {

	public ListProvider<A> getListProvider();
	
	public QuickSort<A> getQuickSorter();

}
