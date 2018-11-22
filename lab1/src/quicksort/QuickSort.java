package quicksort;

import quicksort.QuickSort.PivotPositions;

public interface QuickSort<A> {
	
	public void sort(A list, PivotPositions pivot) throws UnsupportedPivotException;
	
	public int getComparisons();
	
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
			default:
				throw new UnsupportedPivotException(pivot);
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
