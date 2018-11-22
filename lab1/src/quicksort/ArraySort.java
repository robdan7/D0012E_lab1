package quicksort;

public class ArraySort implements QuickSort<int[]>{

	public ArraySort() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void sort(int[] list, PivotPositions pivot) throws UnsupportedPivotException{
		switch(pivot) {
		case FIRST:
			// TODO
			break;
		case RANDOM:
			break;
		case MIDDLE:
			break;
		default:
			throw new UnsupportedPivotException(pivot);
		}
	}
	
	
//	Fungerande quicksortkod typ , work in progress.
	
	/**
	 * @author Oskar
	 * @param list
	 * @param lowIndex
	 * @param highIndex
	 */
	public void sort(int[] list, int lowIndex, int highIndex) {
		if(lowIndex < highIndex) {
			int partitionIndex = partition(list, lowIndex, highIndex);
			 // Sortera vänster och höger om partitionIndex allstå PIVOT.
			sort(list, lowIndex, partitionIndex-1);
			sort(list, partitionIndex+1, highIndex);
			
		}
		
	}
	
	/**
	 * @author Oskar
	 * @param list
	 * @param lowIndex
	 * @param highIndex
	 * @return
	 */
	private int partition(int[] list, int lowIndex, int highIndex) {
		int pivot = list[highIndex];
		int index = lowIndex-1;
		for(int i = lowIndex; i<highIndex;i++) {
			if(list[i] <= pivot) {
				index++;
				//Swap
				int temp = list[index+1];
				list[index+1] = list[i];
				list[i] = temp;
			}
			
			
		}
		int temp = list[index+1];
		list[index+1] = list[highIndex];
		list[highIndex] = temp;
		
		return index+1;
		
	}

	@Override
	public int getComparisons() {
		// TODO Auto-generated method stub
		return 0;
	}

}
