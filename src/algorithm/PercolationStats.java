package algorithm;

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
			results[i] = initPercolation(per);
		}
	}

	private double initPercolation(Percolation per) {
		return 0;
	}

	public double mean() { 
		return StdStats.mean(results);
	}

	public double stddev() {   
		return StdStats.stddev(results);
	}

	public double confidenceLo() {         
		return mean() - ((1.96 * stddev() / Math.sqrt(trials)));
	}

	public double confidenceHi() {    
		return mean() + ((1.96 * stddev() / Math.sqrt(trials)));
	}

	public static void main(String[] args) {      
	}
}
