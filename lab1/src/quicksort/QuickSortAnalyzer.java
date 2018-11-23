package quicksort;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import list.NodeList;
import misc.Tuple;
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
	private NodeList<Tuple<PivotPositions,long[]>> timings;
	private NodeList<Tuple<PivotPositions,int[]>> comparisons;
	
	
	public QuickSortAnalyzer(int intervalSize, int iterations) {
		this.intervalSize = intervalSize;
		this.iterations = iterations;
		this.sorter = new ListSort();
		
		this.timings = new NodeList<Tuple<PivotPositions,long[]>>();
		this.comparisons = new NodeList<Tuple<PivotPositions,int[]>>();
		
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
		QuickSortAnalyzer analyzer = new QuickSortAnalyzer(10, 5);
		
		try {
			analyzer.analyzeInterval();
		} catch (UnsupportedPivotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			analyzer.exportToExcel("analyzed.xls");
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Deprecated
	private void analyzeListAndExport() {
		/*String[] file = {"first.xls"," middle.xls"," random.xls"};
		
		try {
			this.analyzeInterval();
		} catch (UnsupportedPivotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println("Exporting...");
			for (int k = 0; k < this.timings.length; k++) {
				this.exportToExcel(k,file[k]);
			}
		} catch (WriteException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
		*/
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
			list = generateList(j);
			timingIterator = this.timings.iterator();
			comparisonIterator = this.comparisons.iterator();
			
			// we need to iterate over two list at the same time. Use iterators.
			while (timingIterator.hasNext() && comparisonIterator.hasNext()) {
				timingTuple = timingIterator.next();
				timingTuple.y[i] = this.analyzeSingular(list.copy(), timingTuple.x);	// use a copy of the original list.
				
				comparisonTuple = comparisonIterator.next();
				comparisonTuple.y[i] = this.sorter.getComparisons();
			}
			
			/*
			
			oldTime = System.currentTimeMillis();
			this.sorter.sort(list, pivot);
			newTime = System.currentTimeMillis();
			timings[i] = newTime-oldTime;
			*/
			//this.comparisons[0] = this.sorter.getComparisons();

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
	
	public static NodeList<Integer> generateList(int size) {
		NodeList<Integer> list = new NodeList<Integer>();
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			list.appendEnd(r.nextInt(size));
		}
		
		return list;
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
