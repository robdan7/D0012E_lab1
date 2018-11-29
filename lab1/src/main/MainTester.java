package main;

import java.util.Scanner;

import list.NodeList;
import sortingAnalyzer.QuickSortAnalyzer;
import sortingAnalyzer.providers.ListQuickSortProvider;
import sortingAnalyzer.providers.QuickSortProvider;

public class MainTester {

	/**
	 * Internal testing code for our program.
	 */
	public static void main(String[] args) {		
		Scanner consoleScanner = new Scanner(System.in);

		System.out.print("Enter sorting interval size:");
		int interval = Integer.parseInt(consoleScanner.nextLine());	// The scanner does weird things if we input integers directly. dunno why.
		
		System.out.print("Enter iterations:");
		int iterations = Integer.parseInt(consoleScanner.nextLine());
		
		System.out.print("Enter Excel export file:");
		String expoFile = consoleScanner.nextLine();

		consoleScanner.close();
		
		QuickSortProvider<NodeList<Integer>> provider = new ListQuickSortProvider();
		provider.getQuickSorter();
		QuickSortAnalyzer<NodeList<Integer>> analyzer = new QuickSortAnalyzer<NodeList<Integer>>(interval, iterations, provider);
		analyzer.analyzeListAndExport(expoFile);
	
	}

}
