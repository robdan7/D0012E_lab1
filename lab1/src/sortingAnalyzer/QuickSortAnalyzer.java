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
import quicksort.QuickSort.*;
import sortingAnalyzer.providers.ArrayQuickSortProvider;
import sortingAnalyzer.providers.ListQuickSortProvider;
import sortingAnalyzer.providers.NodeListProvider;
import sortingAnalyzer.providers.QuickSortProvider;

/**
 * Class for testing the quicksort algorithm.
 * This program uses the Java Excel API downloaded from <a href = "http://jexcelapi.sourceforge.net/">http://jexcelapi.sourceforge.net/</a>.
 * @author Robin, Oskar
 *
 */
public class QuickSortAnalyzer<A> {
	private int intervalSize, iterations;

	
	private QuickSortProvider<A> sortProvider;
	
	/*
	 * Variables for storing all the data.
	 */
	private NodeList<Tuple<PivotPositions,long[]>> timings;
	private NodeList<Integer> listSizes;	// stores the list size for every iteration of testing.
	
	
	/**
	 * Create a quick-sort analyzer that can analyze stuff.
	 * @param intervalSize - The interval size for every sorting iteration.
	 * @param iterations - The total number of iterations to perform.
	 * @param sortProvider - The sorter to use. This can be basically anything.
	 */
	public QuickSortAnalyzer(int intervalSize, int iterations, QuickSortProvider<A> sortProvider) {
		this.intervalSize = intervalSize;
		this.iterations = iterations;
		this.sortProvider = sortProvider;
		
		this.timings = new NodeList<Tuple<PivotPositions,long[]>>();
		this.listSizes = new NodeList<Integer>();
		
		// Create tuples with pivot and an array. Put them in the timing list.
		for (PivotPositions p : PivotPositions.values()) {
			this.timings.appendEnd(new Tuple<PivotPositions,long[]>(p, new long[this.iterations]));
		}
	}

	/**
	 * First test the algorithm and then export the data to an .xls file.
	 * @param exportFile
	 */
	public void analyzeListAndExport(String exportFile) {
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

		for (int i = 0, j = this.intervalSize, k = 0; i < this.iterations; i++, j += this.intervalSize, k++) {
			
			if (k >= this.iterations/10) {
				System.out.print("+");
				k = 0;
			}
			
			list = this.sortProvider.getListProvider().next(j, NodeListProvider.listCriteria.ALMOST_SORTED_75);			// create one list as source.
			
			this.listSizes.appendEnd(j);	// this is useful if we have custom list sizes.
			
			for (Tuple<PivotPositions,long[]> timingTuple : this.timings) {
				A listCopy = this.sortProvider.getListProvider().replicate(list);
				timingTuple.y[i] = this.analyzeSingular(listCopy, timingTuple.x);	// use a copy of the original list.
			}
		}
		
	}
	
	/**
	 * Analyze one list with one pivot. This is a helper function for {@link #analyzeInterval()} 
	 * that doesn't do much.
	 * @param list
	 * @param pivot
	 * @return
	 * @throws UnsupportedPivotException
	 */
	private long analyzeSingular(A list, PivotPositions pivot) throws UnsupportedPivotException {
	
		long oldTime = System.currentTimeMillis();
		this.sortProvider.getQuickSorter().sort(list, pivot);
		long newTime = System.currentTimeMillis();
		
		return newTime-oldTime;
	}
	
	/**
	 * Export all data to an auto-generated .xls file. This is so much easier than doing it manually.
	 * check out <a href = "http://jexcelapi.sourceforge.net/">http://jexcelapi.sourceforge.net/</a>
	 * for more info about excel exporting.
	 * @param filename - the name of the file.
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void exportToExcel(String filename) throws IOException, RowsExceededException, WriteException {
		int row, column;
		
		
		WritableWorkbook book = Workbook.createWorkbook(new File(filename + ".xls"));
		WritableSheet sheet = book.createSheet("sheet1", 0);
		
		// This is just some basic stuff we have yet not automated.
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
		

		// for every pivot: add all data.
		for (Tuple<PivotPositions, long[]> tup : this.timings) {
			
			row = 0;
			column += 1;
			label = new Label(column,row,tup.x.toString());
			sheet.addCell(label);
			
			// add the sorting times.
			for (int i = 0; i< tup.y.length; i++) {
				row ++;
				number = new Number(column, row, tup.y[i]);
				sheet.addCell(number);
			}
		}
		
		// Note to self: Remember to write and close.
		book.write();
		book.close();
	}
}
