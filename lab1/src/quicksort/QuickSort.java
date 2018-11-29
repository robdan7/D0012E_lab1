package quicksort;

/**
 * Interface for creating quick-sorter classes. Every class that inherits from this 
 * interface can be used for sorting.
 * @author Robin, Oskar
 *
 * @param <A>
 */
public interface QuickSort<A> {
	
	public void sort(A list, PivotPositions pivot) throws UnsupportedPivotException;
	
	/**
	 * These values represents all possible pivots.
	 * @author Robin
	 *
	 */
	public static enum PivotPositions {
		FIRST, RANDOM, MIDDLE;

		@Override
		public String toString() {
			String s = "";
			switch (this) {
			case FIRST:
				s = "first";
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
	 * Class for generating unsupported pivot exceptions. This
	 * exception is thrown if the input cannot be interpreted as a pivot.
	 * 
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
}
