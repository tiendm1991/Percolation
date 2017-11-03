package algorithm;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int n;
	private int[][] site; 
	WeightedQuickUnionUF quickUnion;
	
	public Percolation(int size) {
		super();
		this.n = size;
		this.site = new int[size][size];
		quickUnion = new WeightedQuickUnionUF(n*n);
	}
	
	public boolean isBlock(int row, int col){
		return site[row-1][col-1] == 0;
	}
	
	public boolean isOpen(int row, int col){
		return site[row-1][col-1] == 1;
	}
	
	public boolean isFull(int row, int col){
		if(row == 1 && isOpen(row, col)) return true;
		int point = xyTo1D(row, col);
		for(int i = 1; i <= n; i++){
			int pointCheck = xyTo1D(1, i);
			if(isOpen(1, i) && quickUnion.connected(point, pointCheck)) {
				return true;
			}
		}
		return false;
	}
	
	public void open(int row, int col){
		site[row-1][col-1] = 1;
		if(isValid(row, col-1)) connect(row, col, row, col-1);
		if(isValid(row, col+1)) connect(row, col, row, col+1);
		if(isValid(row-1, col)) connect(row, col, row-1, col);
		if(isValid(row+1, col)) connect(row, col, row+1, col);
	}
	
	private void connect(int r1, int c1, int r2, int c2){
		if(isBlock(r2, c2)) return;
		int point1 = xyTo1D(r1, c1);
		int point2 = xyTo1D(r2, c2);
		if(!quickUnion.connected(point1, point2)){
			quickUnion.union(point1, point2);
		}
	}

	public boolean percolates(){
		for(int i = 0; i < n; i++){
			if(isFull(n, i)) return true;
		}
		return false;
	}
	
	public int numberOfOpenSites(){
		int s = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(isFull(i, j)) s++;
			}
		}
		return s;
	}
	
	private boolean isValid(int row, int col){
		return row >= 1 && row <= n && col >= 1 && col <= n;
	}
	
	private int xyTo1D(int row, int col){
		return (row-1) * n + col-1;
	}
}
