package com.algorithms.week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	// converstion is idx = (row-1)*n+(col-1)
	private WeightedQuickUnionUF arr;
	private int sideSize;
	private boolean[] visitedArr;
	private int openedCount;
	// site on 1st row, idx: 0,1,2...n-1

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if (n<= 0) {
			throw new IllegalArgumentException();
		}

		this.sideSize = n;

		this.visitedArr = new boolean[n*n];
		// two virtual cell
		// arr[n*n] top row cell
		// arr[n*n+1] bottom row cell
		this.arr = new WeightedQuickUnionUF(n*n + 2);
	}

	// open site (row, col) if it is not open already
	public    void open(int row, int col) {
		if (!checkIdxBoundary(row, col)) {
			throw new IllegalArgumentException();
		}
		if (isOpen(row, col)) {
			return;
		}

		int currentIdx = transformCordinates(row, col);

		// if in top row or bottom row, need to union with virtual cell
		if (row == 1) {
			arr.union(currentIdx, sideSize*sideSize);
		} else if (row == sideSize) {
			arr.union(currentIdx, sideSize*sideSize +1);
		}

		// mark as visited
		visitedArr[currentIdx] = true;
		openedCount++;
		// try connect with its four neighbors
		int uprow = row-1;
		int upcol = col;
		if (checkIdxBoundary(uprow, upcol)) {
			if (isOpen(uprow, upcol)) {
				arr.union(currentIdx, transformCordinates(uprow, upcol));
			}
		}

		int rightrow = row;
		int rightcol = col+1;
		if (checkIdxBoundary(rightrow, rightcol)) {
			if (isOpen(rightrow, rightcol)) {
				arr.union(currentIdx, transformCordinates(rightrow, rightcol));
			}
		}

		int leftrow = row;
		int leftcol = col-1;
		if (checkIdxBoundary(leftrow, leftcol)) {
			if (isOpen(leftrow, leftcol)) {
				arr.union(currentIdx, transformCordinates(leftrow, leftcol));
			}
		}

		int bottomrow = row+1;
		int bottomcol = col;
		if (checkIdxBoundary(bottomrow, bottomcol)) {
			if (isOpen(bottomrow, bottomcol)) {
				arr.union(currentIdx, transformCordinates(bottomrow, bottomcol));
			}
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (!checkIdxBoundary(row, col)) {
			throw new IllegalArgumentException();
		}

		int currentIdx = transformCordinates(row, col);
		return visitedArr[currentIdx];
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		if (!checkIdxBoundary(row, col)) {
			throw new IllegalArgumentException();
		}
		if (!isOpen(row, col)) {
			return false;
		}

		int currentIdx = transformCordinates(row, col);
		return arr.connected(sideSize*sideSize, currentIdx);
	}

	// number of open sites
	public     int numberOfOpenSites() {
		return openedCount;
	}

	// does the system percolate?
	public boolean percolates() {
		return arr.connected(sideSize*sideSize, sideSize*sideSize + 1);
	}

	private boolean checkIdxBoundary(int row, int col) {
		if (row < 1 || row > this.sideSize ||
				col < 1 || col > this.sideSize) {
			return false;
		} else {
			return true;
		}
	}


	private int transformCordinates(int row, int col) {
		return (row-1)*sideSize + (col -1);
	}

	// test client (optional)
	public static void main(String[] args) {

   }
}