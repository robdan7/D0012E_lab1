package quicksort;

import java.util.Random;

/**
 * This class is purely made for internal testing. It does the same thing as {@link ListSort}, but it was not as fun to code.
 * @author Robin, Oskar
 *
 */
public class ArraySort implements QuickSort<int[]>{
	private Random r;
	
	public ArraySort() {
		r = new Random();
	}
	
	@Override
	public void sort(int[] list, PivotPositions pivot) throws UnsupportedPivotException{
		switch(pivot) {
		case FIRST:
			this.sortFirst(list, 0, list.length-1);
			break;
		case MIDDLE:
			this.sortMiddle(list, 0, list.length-1);
			break;
		case RANDOM:
			this.sortRandom(list, 0, list.length-1);
			break;
		default:
			throw new UnsupportedPivotException(pivot);
		}
	}
	
	private void sortFirst(int[] list, int lowIndex, int highIndex) {
		if(lowIndex < highIndex) {
			
			// Swap out the last number and the first.
			int temp = list[highIndex];
			list[highIndex] = list[lowIndex];
			list[lowIndex] = temp;
			
			int partitionIndex = partition(list, lowIndex, highIndex);
			 // Sortera vänster och höger om partitionIndex allstå PIVOT.
			this.sortFirst(list, lowIndex, partitionIndex-1);
			this.sortFirst(list, partitionIndex+1, highIndex);
			
		}
	}
	
	private void sortRandom(int[] list, int lowIndex, int highIndex) {
		if(lowIndex < highIndex) {
			
			int rand = r.nextInt(highIndex+1-lowIndex)+lowIndex;
			int temp = list[highIndex];
			list[highIndex] = list[rand];
			list[rand] = temp;

			int partitionIndex = partition(list, lowIndex, highIndex);
			 // Sortera vänster och höger om partitionIndex allstå PIVOT.
			this.sortRandom(list, lowIndex, partitionIndex-1);
			this.sortRandom(list, partitionIndex+1, highIndex);
			
		}
	}
	
	private void sortMiddle(int[] list, int lowIndex, int highIndex) {
		if(lowIndex < highIndex) {
			
			// swap out the last number and the middle one.
			int middleIndex = (lowIndex + highIndex)/2;
			int temp = list[highIndex];
			list[highIndex] = list[middleIndex];
			list[middleIndex] = temp;
			
			int partitionIndex = partition(list, lowIndex, highIndex);
			 // Sortera vänster och höger om partitionIndex allstå PIVOT.
			this.sortMiddle(list, lowIndex, partitionIndex-1);
			this.sortMiddle(list, partitionIndex+1, highIndex);
			
		}
	}

	
	/**
	 * @author Oskar
	 * @param list - integer list.
	 * @param lowIndex - lowest index.
	 * @param highIndex - highest index (included).
	 * @return the index of the pivot element.
	 */
	private int partition(int[] list, int lowIndex, int highIndex) {
		int pivot = list[highIndex];
		int index = lowIndex-1;
		for(int i = lowIndex; i<highIndex;i++) {
			if(list[i] <= pivot) {
				index++;
				//Swap
				int temp = list[index];
				list[index] = list[i];
				list[i] = temp;
			}
			
			
		}
		int temp = list[index+1];
		list[index+1] = list[highIndex];
		list[highIndex] = temp;
		
		return index+1;
		
	}
}
