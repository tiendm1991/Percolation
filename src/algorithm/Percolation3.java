package algorithm;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation3 {
	private final int n;
	private boolean[][] site;
	private final WeightedQuickUnionUF quickUnionIsFull;
	public final WeightedQuickUnionUF quickUnionConnect;
	private int nbOpen;

	public Percolation3(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(n + "is invalid!");
		}
		this.n = n;
		this.site = new boolean[n][n];
		this.quickUnionIsFull = new WeightedQuickUnionUF(n * n);
		this.quickUnionConnect = new WeightedQuickUnionUF(n * n);
		this.nbOpen = 0;
	}

	public boolean isOpen(int row, int col) {
		checkValid(row, col);
		return site[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		checkValid(row, col);
		if ((row == 1 && !isBlock(row, col))) {
			return true;
		}
		int point = xyTo1D(row, col);
		for (int i = 1; i <= n; i++) {
			if (isOpen(1, i) && quickUnionIsFull.find(point) != point) {
				return true;
			}
			if (isOpen(1, i) && quickUnionConnect.connected(point, i - 1)) {
				return true;
			}
		}
		return false;
	}

	public void open(int row, int col) {
		checkValid(row, col);
		if (!isOpen(row, col)) {
			site[row - 1][col - 1] = true;
			nbOpen++;
			if (row == 1) {
				quickUnionIsFull.union(col - 1, col - 1);
			}
			if (isValid(row, col - 1) && isOpen(row, col - 1)) {
				quickUnionConnect.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				if (isFull(row, col) || isFull(row, col - 1)) {
					quickUnionIsFull.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				}
			}
			if (isValid(row, col + 1) && isOpen(row, col + 1)) {
				quickUnionConnect.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				if (isFull(row, col) || isFull(row, col + 1)) {
					quickUnionIsFull.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				}
			}
			if (isValid(row - 1, col) && isOpen(row - 1, col)) {
				quickUnionConnect.union(xyTo1D(row, col), xyTo1D(row - 1, col));
				if (isFull(row, col) || isFull(row - 1, col)) {
					quickUnionIsFull.union(xyTo1D(row, col), xyTo1D(row - 1, col));
				}
			}
			if (isValid(row + 1, col) && isOpen(row + 1, col)) {
				quickUnionConnect.union(xyTo1D(row, col), xyTo1D(row + 1, col));
				if (isFull(row, col) || isFull(row + 1, col)) {
					quickUnionIsFull.union(xyTo1D(row, col), xyTo1D(row + 1, col));
				}
			}
		}
	}

	public boolean percolates() {
		for (int i = 1; i <= n; i++) {
			if (isFull(n, i)) {
				return true;
			}
		}
		return false;
	}

	public int numberOfOpenSites() {
		return nbOpen;
	}

	private void checkValid(int row, int col) {
		if (row < 1 || row > n)
			throw new IllegalArgumentException(row + "outside its prescribed range");
		if (col < 1 || col > n)
			throw new IllegalArgumentException(col + "outside its prescribed range");
	}

	private boolean isBlock(int row, int col) {
		return !site[row - 1][col - 1];
	}

	private boolean isValid(int row, int col) {
		return row >= 1 && row <= n && col >= 1 && col <= n;
	}

	private int xyTo1D(int row, int col) {
		return (row - 1) * n + col - 1;
	}

	public static void main(String[] args) {
		In in = new In("input8.txt"); // input file
		int n = in.readInt(); // n-by-n percolation system
		Percolation3 perc = new Percolation3(n);
		while (!in.isEmpty()) {
			int i = in.readInt();
			int j = in.readInt();
			perc.open(i, j);
		}
		System.out.println(perc.numberOfOpenSites());
		System.out.println(perc.percolates());
		for (int i = 0; i < n * n; i++) {
			System.out.println(perc.quickUnionConnect.find(i));
		}
	}
}
