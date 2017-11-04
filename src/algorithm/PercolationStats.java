package algorithm;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private int n; 	// 	size of percolation
	private int trials;			//	number independent experiments
	private double[] results;	// 	stores results of percolation experiments
	
	public PercolationStats(int n, int trials) {
		this.n = n;
		this.trials = trials;
		this.results = new double[trials];
		
		for(int i = 0; i < trials; i++){
			Percolation per = new Percolation(n);
			results[i] = initPercolation(i, per);
		}
	}

	private double initPercolation(int i, Percolation per) {
		int nbOpen = 0;
		while (!per.percolates()) {
			int r = StdRandom.uniform(1,n+1);
			int c = StdRandom.uniform(1,n+1);
			if(!per.isOpen(r, c)){
				System.out.println("exp" + (i+1) + ": " + "open (" + r + "," + c + ")");
				per.open(r, c);
				nbOpen++;
			}
		}
		return (double) nbOpen/(n*n);
	}

	public double mean() { 
		return StdStats.mean(results);
	}

	public double stddev() {   
		return StdStats.stddev(results);
	}

	public double confidenceLo() {         
		return mean() - 1.96 * stddev() / Math.sqrt(trials);
	}

	public double confidenceHi() {    
		return mean() + 1.96 * stddev() / Math.sqrt(trials);
	}

	public static void main(String[] args) {      
		PercolationStats perStats = new PercolationStats(200, 100);
		System.out.println(String.format("%-23s = %.16f", "mean", perStats.mean()));
		System.out.println(String.format("%-23s = %.16f", "stddev", perStats.stddev()));
		System.out.println(String.format("%-23s = [%.16f, %.16f]", "95% confidence interval",perStats.confidenceLo(), perStats.confidenceHi()));
	}
}
