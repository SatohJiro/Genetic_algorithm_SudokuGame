package model;

import java.util.ArrayList;
import java.util.Random;

public class Gene implements Cloneable {
	private Chromosome chromosome;
	private ArrayList<Integer> gene;
	private boolean[] root;
	private int sizeGene;

	public Gene() {
		sizeGene = 9;
		chromosome = new Chromosome();
		gene = new ArrayList<Integer>();
		generateRoot();
		generateGene();
	}

	public Gene(int[] array) {
		sizeGene = 9;
		chromosome = new Chromosome(array);
		gene = new ArrayList<Integer>();
		generateRoot(array);
		generateGene(array);
//		System.out.println(gene);
	}

	public Gene(ArrayList<Integer> array, boolean[] root) {
		sizeGene = 9;
		chromosome = new Chromosome();
		gene = (ArrayList<Integer>) array.clone();
		this.root = root.clone();
	}

	public void generateGene() {
		Random ran = new Random();
		for (int i = 0; i < sizeGene; i++) {
			int numRan = ran.nextInt(chromosome.getChromosomeArrayList().size());
			gene.add(chromosome.getChromosomeArrayList().get(numRan));
			chromosome.getChromosomeArrayList().remove(numRan);
		}
	}

	public void generateGene(int[] array) {
		Random ran = new Random();
		for (int i = 0; i < sizeGene; i++) {
			if (array[i] == 0) {
				int numRan = ran.nextInt(chromosome.getChromosomeArrayList().size());
				gene.add(chromosome.getChromosomeArrayList().get(numRan));
				chromosome.getChromosomeArrayList().remove(numRan);
			} else {
				gene.add(array[i]);
			}
		}
	}

	public ArrayList<Integer> getGene() {
		return gene;
	}

	public void setGene(ArrayList<Integer> gene) {
		this.gene = gene;
	}

	public boolean same(Gene other) {
		for (int i = 0; i < gene.size(); i++) {
			if (gene.get(i) != other.getGene().get(i)) {
				return false;
			}
		}
		return true;
	}

	public void swapChromo(int i, int j) {
//		System.out.println("swap " + i + " " + j);
		if (root[i] == false && root[j] == false) {
			int temp = gene.get(i);
			gene.set(i, gene.get(j));
			gene.set(j, temp);
//			System.out.println("can swap");
		}
//		else
//			System.out.println("can't swap");
	}

	@Override
	public String toString() {
		return gene + "\n";
	}

	public ArrayList<Integer> cloneGene() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for (int i = 0; i < gene.size(); i++) {
			array.add(gene.get(i));
		}
		return array;
	}



	public void mutant() {
		Random ran = new Random();
		int index1 = ran.nextInt(gene.size());
		int index2 = ran.nextInt(gene.size());
		while (index2 == index1)
			index2 = ran.nextInt(gene.size());
		swapChromo(index1, index2);
	}

	public void generateRoot() {
		root = new boolean[sizeGene];
		for (int i = 0; i < sizeGene; i++) {
			root[i] = false;
		}
	}

	public void generateRoot(ArrayList<Integer> array) {
		root = new boolean[sizeGene];
		for (int i = 0; i < sizeGene; i++) {
			if (array.get(i) != 0)
				root[i] = true;
			else
				root[i] = false;
		}

	}

	public void generateRoot(int[] array) {
		root = new boolean[sizeGene];
		for (int i = 0; i < sizeGene; i++) {
			if (array[i] != 0)
				root[i] = true;
			else
				root[i] = false;
		}
	}
	

	public boolean[] getRoot() {
		return root;
	}

	public static void main(String[] args) {
		int[] array = { 0, 2, 4, 0, 5, 7, 9, 1, 0 };
		Gene gene = new Gene(array);
		gene.swapChromo(0, 3);
	}

}
