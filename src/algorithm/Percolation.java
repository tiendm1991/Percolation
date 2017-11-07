package algorithm;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int n;
	private boolean[][] site;
	private final WeightedQuickUnionUF quickUnion;

	public Percolation(int n) {
		this.n = n;
		this.site = new boolean[n][n];
		quickUnion = new WeightedQuickUnionUF(n * n);
	}

	public boolean isOpen(int row, int col) {
		return site[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		if (row == 1 && isOpen(row, col))
			return true;
		int point = xyTo1D(row, col);
		for (int i = 1; i <= n; i++) {
			if (isOpen(1, i) && quickUnion.connected(point, i - 1)) {
				return true;
			}
		}
		return false;
	}

	public void open(int row, int col) throws IllegalArgumentException {
		if (row < 1 || row > n)
			throw new IllegalArgumentException(row + "outside its prescribed range");
		if (col < 1 || col > n)
			throw new IllegalArgumentException(col + "outside its prescribed range");
		site[row - 1][col - 1] = true;
		if (isValid(row, col - 1))
			connect(row, col, row, col - 1);
		if (isValid(row, col + 1))
			connect(row, col, row, col + 1);
		if (isValid(row - 1, col))
			connect(row, col, row - 1, col);
		if (isValid(row + 1, col))
			connect(row, col, row + 1, col);
	}

	public boolean percolates() {
		for (int i = 0; i < n; i++) {
			if (isFull(n, i))
				return true;
		}
		return false;
	}

	public int numberOfOpenSites() {
		int s = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (isOpen(i, j))
					s++;
			}
		}
		return s;
	}

	private boolean isBlock(int row, int col) {
		return !site[row - 1][col - 1];
	}

	private void connect(int r1, int c1, int r2, int c2) {
		if (isBlock(r2, c2))
			return;
		int point1 = xyTo1D(r1, c1);
		int point2 = xyTo1D(r2, c2);
		if (!quickUnion.connected(point1, point2)) {
			quickUnion.union(point1, point2);
		}
	}

	private boolean isValid(int row, int col) {
		return row >= 1 && row <= n && col >= 1 && col <= n;
	}

	private int xyTo1D(int row, int col) {
		return (row - 1) * n + col - 1;
	}

	public static void main(String[] args) {
		In in = new In(args[0]); // input file
		int n = in.readInt(); // n-by-n percolation system
		Percolation perc = new Percolation(n);
		while (!in.isEmpty()) {
			int i = in.readInt();
			int j = in.readInt();
			perc.open(i, j);
		}
		System.out.println(perc.numberOfOpenSites());
		System.out.println(perc.percolates());
	}
}
