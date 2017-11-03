package algorithm;

public class PercolationRecursion {
	private int n;
	private int[][] site; 
	
	public PercolationRecursion(int size) {
		super();
		this.n = size;
		this.site = new int[size][size];
	}
	
	public boolean isBlock(int row, int col){
		return site[row-1][col-1] == 0;
	}
	
	public boolean isOpen(int row, int col){
		return site[row-1][col-1] == 1;
	}
	
	public boolean isFull(int row, int col){
		if (site[row-1][col-1] == 2) return true;
		for(int i = 1; i <= n; i++){
			if(isOpen(1, i)) {
				site[row-1][col-1] = 2;
				return true;
			}
		}
		return false;
	}
	
	
	public void open(int row, int col){
		site[row-1][col-1] = 1;
		if(row == 1) {
			site[row-1][col-1] = 2;
		}
		checkNeighbor(row,col);
	}
	
	private void checkNeighbor(int row, int col){
		if(isValid(row, col-1)) connect(row, col, row, col-1);
		if(isValid(row, col+1)) connect(row, col, row, col+1);
		if(isValid(row-1, col)) connect(row, col, row-1, col);
		if(isValid(row+1, col)) connect(row, col, row+1, col);
		
	}
	
	private void connect(int r1, int c1, int r2, int c2){
		if(isBlock(r2, c2)) return;
		if(isFull(r2, c2) && isOpen(r1, c1)) {
			site[r1-1][c1-1] = 2;
			checkNeighbor(r1, c1);
		}
		if(isFull(r1, c1) && isOpen(r2, c2)) {
			site[r2-1][c2-1] = 2;
			checkNeighbor(r2, c2);
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
}
