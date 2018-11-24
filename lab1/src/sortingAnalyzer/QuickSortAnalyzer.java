package sortingAnalyzer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import list.NodeList;
import misc.Tuple;
import quicksort.ListSort;
import quicksort.QuickSort;
import quicksort.QuickSort.PivotPositions;
import quicksort.QuickSort.UnsupportedPivotException;

/**
 * 
 * @author Robin, Oskar
 *
 */
public class QuickSortAnalyzer {
	private ListSort sorter;
	private int intervalSize, iterations;
	private ListProvider listprovider;
	
	/*
	 * Variables for storing all the data.
	 */
	private NodeList<Tuple<PivotPositions,long[]>> timings;
	private NodeList<Tuple<PivotPositions,int[]>> comparisons;
	private NodeList<Integer> listSizes;
	
	
	
	public QuickSortAnalyzer(int intervalSize, int iterations) {
		this.intervalSize = intervalSize;
		this.iterations = iterations;
		this.sorter = new ListSort();
		this.listprovider = new ListProvider();
		
		this.timings = new NodeList<Tuple<PivotPositions,long[]>>();
		this.comparisons = new NodeList<Tuple<PivotPositions,int[]>>();
		this.listSizes = new NodeList<Integer>();
		
		for (PivotPositions p : PivotPositions.values()) {
			Tuple tup = new Tuple<PivotPositions,long[]>(p, new long[this.iterations]);
			this.timings.appendEnd(tup);
			
			tup = new Tuple<PivotPositions,int[]>(p, new int[this.iterations]);
			this.comparisons.appendEnd(tup);
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
		
		QuickSortAnalyzer analyzer = new QuickSortAnalyzer(interval, iterations);
		analyzer.analyzeListAndExport(expoFile);
	
	}

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
		NodeList<Integer> list;
		Iterator<Tuple<PivotPositions, long[]>> timingIterator;
		Iterator<Tuple<PivotPositions, int[]>> comparisonIterator;
		Tuple<PivotPositions, long[]> timingTuple;
		Tuple<PivotPositions, int[]> comparisonTuple;

		for (int i = 0, j = this.intervalSize; i < this.iterations; i++, j += this.intervalSize) {
			list = this.listprovider.next(j);			// create one list as source.
			this.listSizes.appendEnd(list.getSize());	// this is useful if we have custom list sizes.
			timingIterator = this.timings.iterator();
			comparisonIterator = this.comparisons.iterator();
			
			// we need to iterate over two lists at the same time. Use iterators.
			while (timingIterator.hasNext() && comparisonIterator.hasNext()) {
				timingTuple = timingIterator.next();
				timingTuple.y[i] = this.analyzeSingular(list.copy(), timingTuple.x);	// use a copy of the original list.
				
				comparisonTuple = comparisonIterator.next();
				comparisonTuple.y[i] = this.sorter.getComparisons();
			}
		}
		
	}
	
	private long analyzeSingular(NodeList<Integer> list, PivotPositions pivot) throws UnsupportedPivotException {
		long oldTime = System.currentTimeMillis();
		
		this.sorter.sort(list, pivot);
		
		long newTime = System.currentTimeMillis();
		
		return newTime-oldTime;
	}
	
	@Deprecated
	/**
	 * Sort a random list several times with the same size. The result is the algorithms average time which is put in a
	 * private list obtained by calling {@link #toString()}.
	 * @param pivot - The pivot element.
	 * @throws UnsupportedPivotException 
	 */
	public void analyzeRepeated(PivotPositions pivot) throws UnsupportedPivotException {
		/*NodeList<Integer> list;
		this.timings = new long[1];		
		this.comparisons = new float[1];
		double averageTime = 0;
		float averageComparison = 0;
		double oldTime, newTime;
		for (int i = 0; i < this.iterations; i++) {
			list = generateList(this.intervalSize);
			
			
			oldTime = System.currentTimeMillis();
			this.sorter.sort(list, pivot);
			newTime = System.currentTimeMillis();
			averageTime += (newTime - oldTime);
			averageComparison += this.sorter.getComparisons();
		}
		averageTime /= this.iterations;
		averageComparison /= this.iterations;
		this.timings[0] = averageTime;
		this.comparisons[0] = averageComparison;
		*/
	}
	

	
	/**
	 * Export all data to an auto-generated "Microsoft Excel �" file.
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportToExcel(String filename) throws IOException, RowsExceededException, WriteException {
		int row, column;
		
		
		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
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
