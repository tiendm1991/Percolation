package algorithm;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int n;
	private boolean[][] site;
	private boolean[][] fullMark;
	private final WeightedQuickUnionUF quickUnion;
	private int nbOpen;
	private boolean percolates;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(n + "is invalid!");
		}
		this.n = n;
		this.site = new boolean[n][n];
		this.fullMark = new boolean[n][n];
		this.quickUnion = new WeightedQuickUnionUF(n * n);
		this.nbOpen = 0;
	}

	public boolean isOpen(int row, int col) {
		checkValid(row, col);
		return site[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		checkValid(row, col);
		if ((row == 1 && !isBlock(row, col)) || fullMark[row - 1][col - 1]) {
			full(row, col);
			return true;
		}
		int point = xyTo1D(row, col);
		for (int i = 1; i <= n; i++) {
			if (isOpen(1, i) && quickUnion.connected(point, i - 1)) {
				full(row, col);
				return true;
			}
		}
		return false;
	}

	public void open(int row, int col) {
		checkValid(row, col);
		if (!isOpen(row, col)) {
			site[row - 1][col - 1] = true;
			if (row == 1) {
				full(row, col);
			}
			nbOpen++;
			if (isValid(row, col - 1))
				connect(row, col, row, col - 1);
			if (isValid(row, col + 1))
				connect(row, col, row, col + 1);
			if (isValid(row - 1, col))
				connect(row, col, row - 1, col);
			if (isValid(row + 1, col))
				connect(row, col, row + 1, col);
			int point = xyTo1D(row, col);
			if (isFull(row, col)) {
				for (int j = 1; j <= n; j++) {
					if (!percolates && !isBlock(n, j) && quickUnion.connected(point, n * (n - 1) + j - 1)) {
						percolates = true;
					}
				}
			}
		}
	}

	public boolean percolates() {
		return percolates;
	}

	public int numberOfOpenSites() {
		return nbOpen;
	}

	private void full(int row, int col) {
		if (!fullMark[row - 1][col - 1]) {
			fullMark[row - 1][col - 1] = true;
			fullNeghbor(row, col);
		}

	}

	private void fullNeghbor(int row, int col) {
		if (isValid(row, col - 1) && isOpen(row, col - 1)) {
			fullMark[row - 1][col - 2] = true;
		}
		if (isValid(row, col + 1) && isOpen(row, col + 1)) {
			fullMark[row - 1][col] = true;
		}
		if (isValid(row - 1, col) && isOpen(row - 1, col)) {
			fullMark[row - 2][col - 1] = true;
		}
		if (isValid(row + 1, col) && isOpen(row + 1, col)) {
			fullMark[row][col - 1] = true;
		}

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
