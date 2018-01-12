package com.algorithms.week1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats {
	private double[] counts;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		counts = new double[trials];
		for (int i =0; i< trials; i++) {
			Percolation pc = new Percolation(n);
			double count = 0;
			while (!pc.percolates()) {
				int row = StdRandom.uniform(n)+1;
				int col = StdRandom.uniform(n)+1;
				if (!pc.isOpen(row, col)) {
					pc.open(row, col);
					count ++;
				}
			}
			counts[i] = count/(n*n);
		}

	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(counts);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(counts);
	}

	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean()-1.96*stddev()/Math.sqrt(counts.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean()+1.96*stddev()/Math.sqrt(counts.length);
	}

	// test client (described below)
	public static void main(String[] args) {
			int a1 = Integer.parseInt(args[0]);
			int a2 = Integer.parseInt(args[1]);
			PercolationStats pcs = new PercolationStats(a1, a2);
			StdOut.println("mean: " + pcs.mean() + "\nstddev:" + pcs.stddev());
			StdOut.println("95% confidence interval: " + pcs.confidenceLo() + ":" + pcs.confidenceHi());
	}
}