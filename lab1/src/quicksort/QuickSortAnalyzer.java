package quicksort;

import java.util.Random;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import list.NodeList;
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
	private double[] timings;
	private float[] comparisons;
	

	/**
	 * Main method
	 */
	public static void main(String[] args) {
		QuickSortAnalyzer analyzer = new QuickSortAnalyzer(5000, 100);
		
		System.out.println("Analyzing interval");
		try {
			analyzer.analyzeInterval(PivotPositions.FIRST);
		} catch (UnsupportedPivotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println("Exporting...");
			analyzer.exportToExcel("test.xls");
		} catch (WriteException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
		
	}	

	public QuickSortAnalyzer(int interval, int iterations) {
		this.intervalSize = interval;
		this.iterations = iterations;
		this.sorter = new ListSort();
	}
	
	/**
	 * Sort a random list several times with different sizes. The result is put in a private list 
	 * obtained by calling {@link #toString()}.
	 * @param pivot - The pivot element
	 * @throws UnsupportedPivotException 
	 */
	public void analyzeInterval(PivotPositions pivot) throws UnsupportedPivotException {
		NodeList<Integer> list;
		this.timings = new double[this.iterations];
		this.comparisons = new float[this.iterations];
		double oldTime, newTime;
		for (int i = 0, j = this.intervalSize; i < this.iterations; i++, j += this.intervalSize) {
			list = generateList(j);
			
			oldTime = System.currentTimeMillis();
			this.sorter.sort(list, pivot);
			newTime = System.currentTimeMillis();
			timings[i] = newTime-oldTime;
			this.comparisons[i] = this.sorter.getComparisons();
		}
	}
	
	/**
	 * Sort a random list several times with the same size. The result is the algorithms average time which is put in a
	 * private list obtained by calling {@link #toString()}.
	 * @param pivot - The pivot element.
	 * @throws UnsupportedPivotException 
	 */
	public void analyzeRepeated(PivotPositions pivot) throws UnsupportedPivotException {
		NodeList<Integer> list;
		this.timings = new double[1];		
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
	}
	
	private NodeList<Integer> generateList(int size) {
		NodeList<Integer> list = new NodeList<Integer>();
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			list.appendEnd(r.nextInt(size));
		}
		
		return list;
	}
	
	/**
	 * Export all data to an auto-generated "Microsoft Excel ©" file.
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
		for (int i = 0; i < this.timings.length; i++) {
			row ++;
			number = new Number(column, row, (i+1)*this.intervalSize);
			sheet.addCell(number);
		}
		
		row = 0;
		column ++;
		label = new Label(column, row, "time");
		sheet.addCell(label);
		for (int i = 0; i < this.timings.length; i++) {
			row ++;
			number = new Number(column, row, this.timings[i]);
			sheet.addCell(number);
		}
		
		row = 0;
		column ++;
		label = new Label(column, row, "comparisons");
		sheet.addCell(label);
		for (int i = 0; i < this.comparisons.length; i++) {
			row ++;
			number = new Number(column, row, this.comparisons[i]);
			sheet.addCell(number);
		}
		
		book.write();
		book.close();
	}
	
	@Override
	public String toString() {
		String s = "Timings: \n";
		for (int i = 0 ; i < this.timings.length; i++) {
			s += "size: " + ((i+1)*this.intervalSize) + ". time: " + Double.toString(this.timings[i]) + 
					". comparisons: " + Float.toString(this.comparisons[i]) + "\n";
		}
		return s;
	}

}
