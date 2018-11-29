package sortingAnalyzer.providers;

import list.NodeList;
import quicksort.ListSort;
import quicksort.QuickSort;

/**
 * Quick-sort provider for linked lists.
 * @author Robin
 *
 */
public class ListQuickSortProvider implements QuickSortProvider<NodeList<Integer>> {
	private ListProvider<NodeList<Integer>> listProvider;
	private QuickSort<NodeList<Integer>> sorter;
	
	public ListQuickSortProvider() {
		this.listProvider = new NodeListProvider();
		this.sorter = new ListSort();
	}

	@Override
	public ListProvider<NodeList<Integer>> getListProvider() {
		return this.listProvider;
	}

	@Override
	public QuickSort<NodeList<Integer>> getQuickSorter() {
		return this.sorter;
	}

}
