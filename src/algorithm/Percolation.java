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
	
	public boolean isOpen(int row, int col){
		return site[row-1][col-1] == 1;
	}
	
	public boolean isFull(int row, int col){
		if (site[row-1][col-1] == 2) return true;
		else {
			int checkPoint = xyTo1D(row, col);
			for(int i = 1; i <= n; i++){
				int top = xyTo1D(1, i);
				if(site[0][i-1] != 0 && quickUnion.connected(checkPoint, top)){
					site[row-1][col-1] = 2;
					return true;
				}
			}
		}
		return false;
	}
	
	public void open(int row, int col){
		site[row-1][col-1] = 1;
		if(row == 1) {
			site[row-1][col-1] = 2;
		}
		fullNeightbor(row,col);
	}
	
	private void fullNeightbor(int row, int col) {
		int checkPoint = xyTo1D(row, col);
		for(int i = row-1; i <= row+1; i++){
			for(int j = col-1; j <= col+1; j++){
				if(isValid(i, j)){
					int neightbor = xyTo1D(i, j);
					if(isOpen(i, j)) {
						quickUnion.union(checkPoint, neightbor);
						if(isFull(row, col) || isFull(i, j)){
							site[row-1][col-1] = 2;
							site[i-1][j-1] = 2;
							fullNeightbor(i, j);
						}
					}
				}
			}
		}
		
	}

	public boolean percolates(){
		for(int i = 0; i < n; i++){
			if(site[n-1][i] == 2) return true;
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
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getSite() {
		return site;
	}

	public void setSite(int[][] site) {
		this.site = site;
	}

	public WeightedQuickUnionUF getQuickUnion() {
		return quickUnion;
	}

	public void setQuickUnion(WeightedQuickUnionUF quickUnion) {
		this.quickUnion = quickUnion;
	}
	
}
