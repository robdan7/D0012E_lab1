package sortingAnalyzer;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import list.NodeList;
import misc.Tuple;
import quicksort.*;
import quicksort.QuickSort.*;
import sortingAnalyzer.providers.ArrayQuickSortProvider;
import sortingAnalyzer.providers.ListProvider;
import sortingAnalyzer.providers.ListQuickSortProvider;
import sortingAnalyzer.providers.NodeListProvider;
import sortingAnalyzer.providers.QuickSortProvider;

/**
 * Class for testing the quicksort algorithm.
 * @author Robin, Oskar
 *
 */
public class QuickSortAnalyzer<A> {
	//private QuickSort<NodeList<Integer>> sorter;
	//private QuickSort<int[]> benchmarkSorter;
	private int intervalSize, iterations;
	//private ListProvider<NodeList<Integer>> listprovider;
	
	@SuppressWarnings("rawtypes")
	private QuickSortProvider<A> sortProvider;
	
	/*
	 * Variables for storing all the data.
	 */
	private NodeList<Tuple<PivotPositions,long[]>> timings;
	private NodeList<Integer> listSizes;	// stores the list size for every iteration of testing.
	
	
	
	public QuickSortAnalyzer(int intervalSize, int iterations, QuickSortProvider<A> sortProvider) {
		this.intervalSize = intervalSize;
		this.iterations = iterations;
		this.sortProvider = sortProvider;
		//this.sorter = new ListSort();
		//benchmarkSorter = new ArraySort();
		//this.listprovider = new NodeListProvider();
		
		this.timings = new NodeList<Tuple<PivotPositions,long[]>>();
		this.listSizes = new NodeList<Integer>();
		
		// Create tuples with pivot and an array. Put them in the timing list.
		for (PivotPositions p : PivotPositions.values()) {
			this.timings.appendEnd(new Tuple<PivotPositions,long[]>(p, new long[this.iterations]));
		}
	}

	/**
	 * Main method
	 */
	public static void main(String[] args) {		
		Scanner consoleScanner = new Scanner(System.in);

		System.out.print("Enter sorting interval size:");
		int interval = Integer.parseInt(consoleScanner.nextLine());	// The scanner does weird things if we input integers instead.
		
		System.out.print("Enter iterations:");
		int iterations = Integer.parseInt(consoleScanner.nextLine());
		
		System.out.print("Enter Excel export file:");
		String expoFile = consoleScanner.nextLine();

		consoleScanner.close();
		
		QuickSortProvider<int[]> provider = new ArrayQuickSortProvider();
		
		@SuppressWarnings("unchecked")
		QuickSortAnalyzer<int[]> analyzer = new QuickSortAnalyzer(interval, iterations, provider);
		analyzer.analyzeListAndExport(expoFile);
	
	}

	/**
	 * First test the algorithm and then export the data to an .xls file.
	 * @param exportFile
	 */
	private void analyzeListAndExport(String exportFile) {
		System.out.println("Sorting...");
		try {
			this.analyzeInterval();
		} catch (UnsupportedPivotException e) {
			e.printStackTrace();
		}

		try {
			this.exportToExcel(exportFile);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
	
	/**
	 * Sort a random list several times with different sizes. The result is put in a private list 
	 * obtained by calling {@link #toString()}.
	 * @throws UnsupportedPivotException 
	 */
	public void analyzeInterval() throws UnsupportedPivotException {
		A list;

		for (int i = 0, j = this.intervalSize; i < this.iterations; i++, j += this.intervalSize) {
			list = this.sortProvider.getListProvider().next(j, NodeListProvider.listCriteria.RANDOM);			// create one list as source.
			this.listSizes.appendEnd(j);	// this is useful if we have custom list sizes.
			
			for (Tuple<PivotPositions,long[]> timingTuple : this.timings) {
				
				timingTuple.y[i] = this.analyzeSingular(this.sortProvider.getListProvider().replicate(list), timingTuple.x);	// use a copy of the original list.
			}
		}
		
	}
	
	private long analyzeSingular(A list, PivotPositions pivot) throws UnsupportedPivotException {
	
		long oldTime = System.currentTimeMillis();
		
		//this.sorter.sort(list, pivot);
		this.sortProvider.getQuickSorter().sort(list, pivot);
		
		long newTime = System.currentTimeMillis();
		
		return newTime-oldTime;
	}
	
	@Deprecated
	private long analyzeArray(int[] array, PivotPositions pivot) throws UnsupportedPivotException {
		long oldTime = System.currentTimeMillis();
		
		//this.benchmarkSorter.sort(array, pivot);
		NodeList<Integer> list = new NodeList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.appendEnd(array[i]);
		}
		
		long newTime = System.currentTimeMillis();
		
		return newTime-oldTime;
	}
	
	/**
	 * Helper method for {@link #analyzeArray(int[])}.
	 * @return
	 */
	private int[] createArray(NodeList<Integer> list) {
		int[] resultArray = new int[list.getSize()];
		int i = 0;
		for (int num : list) {
			resultArray[i] = num;
			i ++;
		}
		
		return resultArray;
	}
	
	/**
	 * Export all data to an auto-generated .xls file.
	 * @param filename - the name of the file.
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportToExcel(String filename) throws IOException, RowsExceededException, WriteException {
		int row, column;
		
		
		WritableWorkbook book = Workbook.createWorkbook(new File(filename + ".xls"));
		WritableSheet sheet = book.createSheet("sheet1", 0);
		
		row = 0;
		column = 0;
		Label label = new Label(column,row,"sizes");
		sheet.addCell(label);
		
		Number number;
		
		Number num;
		for (int i = 1; i <= this.iterations; i++) {
			row ++;
			num = new Number(column, row, i*this.intervalSize);
			sheet.addCell(num);
		}
		

		for (Tuple<PivotPositions, long[]> tup : this.timings) {
			
			row = 0;
			column += 2;
			label = new Label(column,row,tup.x.toString());
			sheet.addCell(label);
			
			for (int i = 0; i< tup.y.length; i++) {
				row ++;
				number = new Number(column, row, tup.y[i]);
				sheet.addCell(number);
			}
		}
		
		book.write();
		book.close();
	}
	
	@Deprecated
	@Override
	public String toString() {
		String s = "Timings: \n";
		/*for (int i = 0 ; i < this.timings.length; i++) {
			s += "size: " + ((i+1)*this.intervalSize) + ". time: " + Double.toString(this.timings[i]) + 
					". comparisons: " + Float.toString(this.comparisons[i]) + "\n";
		}*/
		return s;
	}

}
