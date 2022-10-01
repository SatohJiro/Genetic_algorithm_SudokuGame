package model;

import java.util.ArrayList;
import java.util.Random;

public class Solution implements Cloneable {
	private Gene gene;
	private ArrayList<Gene> solution;
	private int sizeSolution;
	private final int mutationRate = 20;

	public Solution() {
		this.solution = new ArrayList<Gene>();
		this.sizeSolution = 9;
		GenerateSolution();
	}

	public Solution(int[][] array) {
		this.solution = new ArrayList<Gene>();
		this.sizeSolution = 9;
		GenerateSolution(array);
//		System.out.println(gene);
	}

	public Solution(ArrayList<Gene> array) {
		this.solution = (ArrayList<Gene>) array.clone();
		this.sizeSolution = 9;
	}

	public void GenerateSolution() {
		for (int i = 0; i < sizeSolution; i++) {
			gene = new Gene();
			solution.add(gene);
		}
	}

	public void GenerateSolution(int[][] array) {
		for (int i = 0; i < sizeSolution; i++) {
			gene = new Gene(array[i]);
			solution.add(gene);
		}
	}

	public boolean same(Solution other) {
		for (int i = 0; i < solution.size(); i++) {
			if (!solution.get(i).same(other.getSolution().get(i)))
				return false;
		}
		return true;
	}

/////////////////////// Collision Box ///////////////////////////////////
	public int collisionBox(int numBox) {
		switch (numBox) {
		case 1: {
			return collisionBox(0, 0);
		}
		case 2: {
			return collisionBox(0, 3);
		}
		case 3: {
			return collisionBox(0, 6);
		}
		case 4: {
			return collisionBox(3, 0);
		}
		case 5: {
			return collisionBox(3, 3);
		}
		case 6: {
			return collisionBox(3, 6);
		}
		case 7: {
			return collisionBox(6, 0);
		}
		case 8: {
			return collisionBox(6, 3);
		}
		case 9: {
			return collisionBox(6, 6);
		}
		default:
			return -1;
		}
	}

	public int[][] convertToArray() {
		int[][] array = new int[9][9];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				array[i][j] = solution.get(i).getGene().get(j);
			}
		}
		return array;
	}

	public int[][] convertToArray(int numRow, int numCol) {
		int[][] array = new int[3][3];
		for (int i = numRow; i < numRow + 3; i++) {
			for (int j = numCol; j < numCol + 3; j++) {
				array[i - numRow][j - numCol] = solution.get(i).getGene().get(j);
			}
		}
//		for (int i = 0; i < array.length; i++) {
//			for (int j = 0; j < array.length; j++) {
//				System.out.print(array[i][j] + " ");
//			}
//			System.out.println();
//		}
		return array;
	}

	public int collisionArray(int[][] array, int number, int rowOfNumber, int colOfNumber) {
		int collision = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[i][j] == number) {
//					System.out.println("không in mặc dù bằng "+"collision number is: " + number + " index " + rowOfNumber + " "
//							+ colOfNumber + " array i " + i + " j " + j);
					if (i != rowOfNumber || j != colOfNumber) {
						collision++;
//						System.out.println("collision number is: " + number + " index " + rowOfNumber + " "
//								+ colOfNumber + " array i " + i + " j " + j);
					}
				}
			}
		}
		return collision;
	}

	public int collisionBox(int numRow, int numCol) {
		int collision = 0;
		int[][] array = convertToArray(numRow, numCol);
		for (int i = numRow; i < numRow + 3; i++) {
			for (int j = numCol; j < numCol + 3; j++) {
				int number = solution.get(i).getGene().get(j);
				collision += collisionArray(array, number, i - numRow, j - numCol);
			}
		}
		return collision / 2;
	}

/////////////////////// Collision Column ///////////////////////////////////
	public int totalCollisionColumn() {
		int collision = 0;
		for (int i = 0; i < sizeSolution; i++) {
//			System.out.println("Collision Column " + i + " is " + collisionColumn(i));
			collision += collisionColumn(i);
		}
		return collision;
	}

	public int collisionColumn(int numCol) {
		int collision = 0;
		for (int numRow = 0; numRow < sizeSolution; numRow++) {
			ArrayList<Integer> gene = solution.get(numRow).getGene();
			int first = gene.get(numCol);
			for (int index = 0; index < sizeSolution; index++) {
				if (numRow == index)
					continue;
				ArrayList<Integer> geneOther = solution.get(index).getGene();
				int second = geneOther.get(numCol);
				if (first == second) {
					collision++;
				}
			}
		}
		return collision / 2;
	}

///////////////////////////////////////////////////////////
	public int totalCollisionBox() {
		int collision = 0;
		for (int i = 1; i < 10; i++) {
//			System.out.println("Collision Box " + i + " is " + collisionBox(i));
			collision += collisionBox(i);
		}
		return collision;
	}

	public int totalCollision() {
		return totalCollisionColumn() + totalCollisionBox();
	}

	public ArrayList<Gene> getSolution() {
		return solution;
	}

//	  2   3    5      6     7      9
//	[29] [92] [59]  [95]   [38]   [83]
	public void setSolution(ArrayList<Gene> solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return /* solution + */"===== " + totalCollision() + "\n";
	}

	public ArrayList<Gene> cloneSolution() {
		ArrayList<Gene> clone = new ArrayList<Gene>();
		for (int i = 0; i < solution.size(); i++) {
			ArrayList<Integer> cloneGene = solution.get(i).cloneGene();
			boolean[] cloneRoot = solution.get(i).getRoot().clone();
			clone.add(new Gene(cloneGene, cloneRoot));
		}
		return clone;
	}

//////////////////////////////////mutation //////////////////////
	public void mutant() throws CloneNotSupportedException {
		Solution cloneSolution = null;
		for (int i = 0; i < solution.size(); i++) {
			for (int j = 0; j < mutationRate; j++) {
				// clone 1 gen
//				ArrayList<Integer> clone = solution.get(i).cloneGene();
//				boolean[] cloneRoot = solution.get(i).cloneRoot();
				// clone 1 bo gen
				ArrayList<Gene> cloneSetGene = cloneSolution();
				// clone solution
				cloneSolution = new Solution(cloneSetGene);
				System.out.println("ban goc " + this.totalCollision());
				cloneSolution.getSolution().get(i).mutant();
				System.out.println("clone mutant " + cloneSolution.totalCollision());
				if (cloneSolution.totalCollision() < this.totalCollision() && cloneSolution.totalCollision() >= 0) {
					this.solution = cloneSolution.solution;
				}
			}
		}
	}

	public int[] twoColunmHaveBestCollsion() {
		int[] temp = new int[2];
		int max;
		int indexMax;
		int second;
		int indexSecond;
		if (collisionColumn(0) > collisionColumn(1)) {
			max = collisionColumn(0);
			indexMax = 0;
			second = collisionColumn(1);
			indexSecond = 1;
		} else {
			max = collisionColumn(1);
			indexMax = 1;
			;
			second = collisionColumn(0);
			indexSecond = 0;
		}
		for (int i = 2; i < 9; i++) {
			if (collisionColumn(i) >= max) {
				second = max;
				indexSecond = indexMax;
				max = collisionColumn(i);
				indexMax = i;
			} else if (collisionColumn(i) > second) {
				second = collisionColumn(i);
				indexSecond = i;
			}
		}
		temp[0] = indexMax;
		temp[1] = indexSecond;
		System.out.print(temp[0] + " " + temp[1]);
		System.out.println();
		return temp;
	}

	public int[] sortASC(int[] arrCollision, int[] arrIndex) {
		int temp = arrCollision[0];
		int tempIndex;
		for (int i = 0; i < arrCollision.length - 1; i++) {
			for (int j = i + 1; j < arrCollision.length; j++) {
				if (arrCollision[i] < arrCollision[j]) {
					temp = arrCollision[j];
					arrCollision[j] = arrCollision[i];
					arrCollision[i] = temp;
					////////////////
					tempIndex = j;
					arrIndex[j] = arrIndex[i];
					arrIndex[i] = tempIndex;
				}
			}
		}
		return arrIndex;
	}

	public int[] getArrayCollision() {
		int[] temp = new int[9];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = collisionColumn(i);
		}
		return temp;
	}

	public int[] generateArrayIndex() {
		int[] temp = new int[9];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = i;
		}
		return temp;
	}

//	public void mutant() {
//
//		int[] temp = sortASC(getArrayCollision(), generateArrayIndex());
//		for (int i = 0; i < solution.size(); i++) {
//			// clone 1 gen
//			ArrayList<Integer> clone = solution.get(i).cloneGene();
//			// clone 1 bo gen
//			ArrayList<Gene> cloneSetGene = cloneSolution();
//			// clone solution
//			Solution cloneSolution = new Solution(cloneSetGene);
//
//			Outer: for (int j = 0; j < temp.length; j++) {
//				for (int k = 0; k < temp.length; k++) {
//					if (j == k)
//						continue;
//					cloneSolution.getSolution().get(i).swapChromo(temp[j], temp[k]);
//					if (cloneSolution.totalCollision() < this.totalCollision()) {
//						System.out.println("ban goc " + this.totalCollision());
//						System.out.println("clone mutant " + cloneSolution.totalCollision());
//						System.out.println("đã swap vị trí "+j+" với "+k);
//						this.solution = cloneSolution.solution;
//						break Outer;
//					}
//				}
//			}
//		}
//	}

	public static void main(String[] args) throws CloneNotSupportedException {
		int[] array0 = { 1, 0, 3, 4, 0, 6, 7, 0, 9 };
		int[] array1 = { 0, 2, 0, 0, 5, 0, 0, 8, 0 };
		int[] array2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] array3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] array4 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] array5 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] array6 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] array7 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] array8 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[][] array = new int[9][9];
		array[0] = array0;
		array[1] = array1;
		array[2] = array2;
		array[3] = array3;
		array[4] = array4;
		array[5] = array5;
		array[6] = array6;
		array[7] = array7;
		array[8] = array8;
		Solution solution = new Solution(array);

	}
}
