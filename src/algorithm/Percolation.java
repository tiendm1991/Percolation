package algorithm;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int n;
	private boolean[][] site;
	private final WeightedQuickUnionUF quickUnion;
	private final WeightedQuickUnionUF quickUnionFull;
	private int nbOpen, topSite, bottomSite;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(n + "is invalid!");
		}
		this.n = n;
		this.site = new boolean[n][n];
		this.quickUnion = new WeightedQuickUnionUF(n * n);
		this.quickUnionFull = new WeightedQuickUnionUF(n * n);
		this.nbOpen = 0;
		this.topSite = -1;
		this.bottomSite = -1;
	}

	public boolean isOpen(int row, int col) {
		checkValid(row, col);
		return site[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		checkValid(row, col);
		int point = xyTo1D(row, col);
		if (isOpen(row, col) && quickUnionFull.find(point) != point) {
			return true;
		}
		int parentPoint = quickUnion.find(point);
		for (int i = 1; i <= n; i++) {
			if (isOpen(1, i) && quickUnion.find(i - 1) == parentPoint) {
				full(row, col);
				quickUnionFull.union(point, i - 1);
				return true;
			}
		}
		return false;
	}

	public void open(int row, int col) {
		checkValid(row, col);
		if (!isOpen(row, col)) {
			site[row - 1][col - 1] = true;
			int point1 = xyTo1D(row, col);
			if (row == 1) {
				int pre = topSite;
				topSite = point1;
				if (pre != -1) {
					quickUnion.union(pre, topSite);
				}
				full(row, col);
			}
			if (row == n) {
				int pre = bottomSite;
				bottomSite = point1;
				if (pre != -1) {
					quickUnion.union(pre, bottomSite);
				}
				full(row, col);
			}
			nbOpen++;
			if (isValid(row, col - 1))
				connect(point1, row, col - 1);
			if (isValid(row, col + 1))
				connect(point1, row, col + 1);
			if (isValid(row - 1, col))
				connect(point1, row - 1, col);
			if (isValid(row + 1, col))
				connect(point1, row + 1, col);
		}
	}

	public boolean percolates() {
		if (topSite == -1 || bottomSite == -1)
			return false;
		System.out.println(quickUnion.find(topSite));
		System.out.println(quickUnion.find(bottomSite));
		return quickUnion.connected(topSite, bottomSite);
	}

	public int numberOfOpenSites() {
		return nbOpen;
	}

	private void full(int row, int col) {
		quickUnionFull.union(xyTo1D(row, col), col - 1);
		fullNeghbor(row, col);
	}

	private void fullNeghbor(int row, int col) {
		if (isValid(row, col - 1) && isOpen(row, col - 1)) {
			quickUnionFull.union(xyTo1D(row, col - 1), col - 1);
		}
		if (isValid(row, col + 1) && isOpen(row, col + 1)) {
			quickUnionFull.union(xyTo1D(row, col + 1), col - 1);
		}
		if (isValid(row - 1, col) && isOpen(row - 1, col)) {
			quickUnionFull.union(xyTo1D(row - 1, col), col - 1);
		}
		if (isValid(row + 1, col) && isOpen(row + 1, col)) {
			quickUnionFull.union(xyTo1D(row + 1, col), col - 1);
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

	private void connect(int point1, int r2, int c2) {
		if (isBlock(r2, c2))
			return;
		int point2 = xyTo1D(r2, c2);
		if (quickUnion.find(point2) != point1) {
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
