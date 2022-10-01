package model;

import java.util.ArrayList;

public class Chromosome {
	private final int[] chromosomePrimitive = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private ArrayList<Integer> chromosome;
	private ArrayList<Integer> chromosomeArrayList;

	public Chromosome() {
		generateChromo();
		chromosomeArrayList = new ArrayList<Integer>();
		for (int i = 0; i < chromosome.size(); i++) {
			chromosomeArrayList.add(chromosome.get(i));
		}
	}

	public Chromosome(int[] array) {
		generateChromo(array);
		chromosomeArrayList = new ArrayList<Integer>();
		for (int i = 0; i < chromosome.size(); i++) {
			chromosomeArrayList.add(chromosome.get(i));
		}
//		System.out.println(chromosomeArrayList);
	}

	public boolean checkContain(int number, int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (number == array[i]) {
				return true;
			}
		}
		return false;
	}

	public void generateChromo() {
		chromosome = new ArrayList<Integer>();
		for (int i = 0; i < chromosomePrimitive.length; i++) {
			chromosome.add(chromosomePrimitive[i]);
		}
	}

	public void generateChromo(int[] array) {
		chromosome = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			if (!checkContain(chromosomePrimitive[i], array)) {
				chromosome.add(chromosomePrimitive[i]);
			}
		}
	}

	public ArrayList<Integer> getChromosomeArrayList() {
		return chromosomeArrayList;
	}

	public void setChromosomeArrayList(ArrayList<Integer> chromosomeArrayList) {
		this.chromosomeArrayList = chromosomeArrayList;
	}

	public static void main(String[] args) {
		int[] array = { 0, 2, 4, 0, 5, 7, 9, 1, 0 };
		Chromosome test = new Chromosome(array);
		System.out.println(test.checkContain(4, array));
		
	}
}
