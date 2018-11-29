package sortingAnalyzer.providers;

import quicksort.QuickSort;

/**
 * This class holds a list provider and a quick-sorter class.
 * @author Robin
 *
 * @param <A>
 */
public interface QuickSortProvider<A> {

	/**
	 * 
	 * @return The list provider.
	 */
	public ListProvider<A> getListProvider();
	
	/**
	 * 
	 * @return The quick-sort provider.
	 */
	public QuickSort<A> getQuickSorter();

}
