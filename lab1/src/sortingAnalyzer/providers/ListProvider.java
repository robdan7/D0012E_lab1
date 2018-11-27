package sortingAnalyzer.providers;

/**
 * Generic interface for list providers.
 * @author Robin
 *
 * @param <A>
 */
public interface ListProvider<A> {
	
	/**
	 * Retrieve a new list.
	 * @param requestedSize	- The size that will be returned.
	 * @param criteria - of type {@link listCriteria}
	 * @return
	 */
	public A next(int requestedSize, listCriteria criteria);
	
	/**
	 * Retrieve a completely random list of specified size.
	 * @param size - the size.
	 * @return
	 */
	public A nextRandom(int size);
	
	/**
	 * Retrieve a completely sorted list of specified size.
	 * @param size - the size;
	 * @return
	 */
	public A nextSorted(int size);
	
	/**
	 * Retrieve an 80% sorted list. Some elements are swapped out at random (20%).
	 * @param size
	 * @return
	 */
	public A nextSorted80(int size);
	
	/**
	 * Retrieve an 75% sorted list. Some elements are swapped out at random (25%).
	 * @param size
	 * @return
	 */
	public A nextSorted75(int size);
	
	
	/**
	 * this copies a list and returns another list equal to the first one.
	 * @return
	 */
	public A replicate(A list);
	
	
	/**
	 * Enumerates for choosing what kind of randomness a list should have.
	 * @author Robin
	 *
	 */
	public static enum listCriteria {
		RANDOM, SORTED, ALMOST_SORTED_80, ALMOST_SORTED_75;
	}

}
