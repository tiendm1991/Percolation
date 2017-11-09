package algorithm;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double CONFIDENCE_95 = 1.96;
	private final int n; // size of percolation
	private final int trials; // number independent experiments
	private final double[] results; // stores results of percolation experiments
	private Double mean, stddev; // stores mean and stdev

	public PercolationStats(int n, int trials) {
		if (n <= 0) {
			throw new IllegalArgumentException(n + "is invalid!");
		}
		if (trials <= 0) {
			throw new IllegalArgumentException(n + "is invalid!");
		}
		this.n = n;
		this.trials = trials;
		this.results = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation per = new Percolation(n);
			results[i] = initPercolation(per);
		}
	}

	private double initPercolation(Percolation per) {
		while (!per.percolates()) {
			int r = StdRandom.uniform(1, n + 1);
			int c = StdRandom.uniform(1, n + 1);
			per.open(r, c);
		}
		return (double) per.numberOfOpenSites() / (n * n);
	}

	public double mean() {
		mean = StdStats.mean(results);
		return mean;
	}

	public double stddev() {
		stddev = StdStats.stddev(results);
		return stddev;
	}

	public double confidenceLo() {
		return (mean == null ? mean() : mean)
				- CONFIDENCE_95 * (stddev == null ? stddev() : stddev) / Math.sqrt(trials);
	}

	public double confidenceHi() {
		return (mean == null ? mean() : mean)
				+ CONFIDENCE_95 * (stddev == null ? stddev() : stddev) / Math.sqrt(trials);
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats perStats = new PercolationStats(n, trials);
		System.out.println(String.format("%-23s = %.16f", "mean", perStats.mean()));
		System.out.println(String.format("%-23s = %.16f", "stddev", perStats.stddev()));
		System.out.println(String.format("%-23s = [%.16f, %.16f]", "95% confidence interval", perStats.confidenceLo(),
				perStats.confidenceHi()));
	}
}
