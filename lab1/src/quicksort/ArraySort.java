package quicksort;

import java.util.Random;

public class ArraySort implements QuickSort<int[]>{
	
	@Override
	public void sort(int[] list, PivotPositions pivot) throws UnsupportedPivotException{
		switch(pivot) {
		case BENCHMARK:
			this.sort(list, 0,list.length-1);
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
	private void sort(int[] list, int lowIndex, int highIndex) {
		if(lowIndex < highIndex) {
			
			int rand = new Random().nextInt(highIndex+1-lowIndex)+lowIndex;
			int temp = list[highIndex];
			list[highIndex] = list[rand];
			list[rand] = temp;
			
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
}
