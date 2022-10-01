package model;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class Population {
	private ArrayList<Solution> population;
	private int populationSize;
	private int standard;
	private int[][] root;

	public Population(int populationSize, int standard) {
		this.population = new ArrayList<Solution>();
		this.populationSize = populationSize;
		for (int i = 0; i < populationSize; i++) {
			population.add(new Solution());
		}
		this.standard = standard;
	}

	public Population(int populationSize, int standard, int[][] array) {
		this.population = new ArrayList<Solution>();
		this.populationSize = populationSize;
		for (int i = 0; i < populationSize; i++) {
			population.add(new Solution(array));
		}
		this.standard = standard;
	}

	public boolean isEvenNumber(int number) {
		if (number % 2 == 0)
			return true;
		return false;
	}

	public Solution crossoverInterleaved(Solution a, Solution b) {
		Solution child = new Solution();
		for (int i = 0; i < a.getSolution().size(); i++) {
			if (isEvenNumber(i)) {
				child.getSolution().set(i, b.getSolution().get(i));
			} else {
				child.getSolution().set(i, a.getSolution().get(i));
			}
		}
//		System.out.println("father ");
//		System.out.println(a);
//		System.out.println("mother ");
//		System.out.println(b);
//		System.out.println("child ");
//		System.out.println(child);
		return child;
	}

	public Solution crossoverHalf(Solution a, Solution b) {
		Solution child = new Solution();
		for (int i = 0; i < a.getSolution().size(); i++) {
			if (i > a.getSolution().size() / 2) {
				child.getSolution().set(i, b.getSolution().get(i));
			} else {
				child.getSolution().set(i, a.getSolution().get(i));
			}
		}
//		System.out.println("father ");
//		System.out.println(a);
//		System.out.println("mother ");
//		System.out.println(b);
//		System.out.println("child ");
//		System.out.println(child);
		return child;
	}

	public static Solution selectBest(ArrayList<Solution> array) {
		Solution min = array.get(0);
		for (int i = 1; i < array.size(); i++) {
			if (array.get(i).totalCollision() < min.totalCollision()) {
				min = array.get(i);
			}
		}
		array.remove(min);
		return min;
	}

	public Solution selectRandom(ArrayList<Solution> array) {
		Random ran = new Random();
		Solution solotion = array.get(ran.nextInt(array.size()));
		array.remove(solotion);
		return solotion;
	}

	public boolean checkDestination() {
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).totalCollision() == 0) {
				Solution best = selectBest(population);
				population.add(best);
				System.out.println("đã tìm thấy đáp án");
				System.out.println(best.getSolution());
				return true;
			}
		}
		return false;
	}

	public void mutant() throws CloneNotSupportedException {
		ArrayList<Solution> mutantSolu = new ArrayList<Solution>();
		Solution ranSolu = null;
		for (int i = 0; i < 5; i++) {
			ranSolu = selectRandom(population);
			mutantSolu.add(ranSolu);
		}
		for (int i = 0; i < 5; i++) {
			ranSolu = selectBest(population);
			mutantSolu.add(ranSolu);
		}
		for (int i = 0; i < mutantSolu.size(); i++) {
			mutantSolu.get(i).mutant();
			population.add(mutantSolu.get(i));
		}
	}

	public void selection() {
		ArrayList<Solution> newPopution = new ArrayList<Solution>();
		for (int i = 0; i < standard; i++) {
			newPopution.add(selectBest(population));
		}
		population = newPopution;
	}

	public int removeSolution() {
		int count = 0;
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).totalCollision() > standard) {
				population.remove(i);
				count++;
				i--;
				standard++;
			}
		}
		standard--;
		return count;
	}

	public boolean checkImprovement() {
		int count = 1;
		int temp = population.get(population.size() - 1).totalCollision();
		for (int i = 0; i < population.size() - 1; i++) {
			if (population.get(i).totalCollision() == temp) {
				count++;
			}
		}
		if (count >= population.size() - 10) {
			return false;
		}
		return true;
	}

	public void clearPopulation() {
		if (!checkImprovement()) {
			this.population = new ArrayList<Solution>();
			if (root == null) {
				for (int i = 0; i < populationSize; i++) {
					population.add(new Solution());
				}
			} else {
				for (int i = 0; i < populationSize; i++) {
					population.add(new Solution(root));
				}
			}
		}
	}

	public void crossover(int i) throws CloneNotSupportedException {
		int count = 0;
		System.out.println("Quần thể ban đầu");
		System.out.println(population);
		while (count < i && !checkDestination()) {
			ArrayList<Solution> fathers = new ArrayList<Solution>();
			ArrayList<Solution> mothers = new ArrayList<Solution>();
			ArrayList<Solution> childs = new ArrayList<Solution>();
			count++;
//			ArrayList<Solution> check = new ArrayList<Solution>();
			Solution father = null;
			Solution mother = null;
			for (int j = 0; j < population.size() / 3; j++) {
				father = selectRandom(population);
				mother = selectRandom(population);
				fathers.add(father);
				mothers.add(mother);
			}
			Solution child1 = null;
			Solution child2 = null;
			Solution child3 = null;
			Solution child4 = null;
			for (int k = 0; k < fathers.size(); k++) {
				child1 = crossoverHalf(fathers.get(k), mothers.get(k));
				child2 = crossoverHalf(mothers.get(k), fathers.get(k));
				child3 = crossoverInterleaved(fathers.get(k), mothers.get(k));
				child4 = crossoverInterleaved(mothers.get(k), fathers.get(k));
//				check.add(child1);
//				check.add(child2);
//				check.add(child3);
//				check.add(child4);
				childs.add(child1);
				childs.add(child2);
				childs.add(child3);
				childs.add(child4);
			}
//			check.add(father);
//			check.add(mother);
//			System.out.println("father ");
//			System.out.print(father);
//			System.out.println("mother ");
//			System.out.print(mother);
//			System.out.println("child ");
//			System.out.println(check);
//			population.add(selectBest(check));
//			population.add(selectBest(check));
			population.addAll(fathers);
			population.addAll(mothers);
			population.addAll(childs);
			System.out.println("sau lần lai thứ " + count + "---------------------------------------------");
			System.out.println(population);

			//////////////////////////////////////////
//			int number = removeSolution();
//			System.out.println("sau chọn lọc gene với standar "+standard+" "+number+" cá thể đã bị loại bỏ, còn lại ");
//			System.out.println(population);
			selection();
			System.out.println("sau chọn lọc gene với standar " + standard + " các cá thể đã bị loại bỏ, còn lại ");
			System.out.println(population);

			/////////////////////////////////////////
			System.out.println("mutant ");
			mutant();
			System.out.println(population);
			if (!checkImprovement()) {
				System.out.println("Tối Ưu Cục Bộ, reset quần thể");
				clearPopulation();
			}
		}

	}

	public void crossover() throws CloneNotSupportedException {
		int count = 0;
		System.out.println("Quần thể ban đầu");
		System.out.println(population);
		while (!checkDestination()) {
			ArrayList<Solution> fathers = new ArrayList<Solution>();
			ArrayList<Solution> mothers = new ArrayList<Solution>();
			ArrayList<Solution> childs = new ArrayList<Solution>();
			count++;
//			ArrayList<Solution> check = new ArrayList<Solution>();
			Solution father = null;
			Solution mother = null;
			for (int j = 0; j < population.size() / 3; j++) {
				father = selectRandom(population);
				mother = selectRandom(population);
				fathers.add(father);
				mothers.add(mother);
			}
			Solution child1 = null;
			Solution child2 = null;
			Solution child3 = null;
			Solution child4 = null;
			for (int k = 0; k < fathers.size(); k++) {
				child1 = crossoverHalf(fathers.get(k), mothers.get(k));
				child2 = crossoverHalf(mothers.get(k), fathers.get(k));
				child3 = crossoverInterleaved(fathers.get(k), mothers.get(k));
				child4 = crossoverInterleaved(mothers.get(k), fathers.get(k));
//				check.add(child1);
//				check.add(child2);
//				check.add(child3);
//				check.add(child4);
				childs.add(child1);
				childs.add(child2);
				childs.add(child3);
				childs.add(child4);
			}
//			check.add(father);
//			check.add(mother);
//			System.out.println("father ");
//			System.out.print(father);
//			System.out.println("mother ");
//			System.out.print(mother);
//			System.out.println("child ");
//			System.out.println(check);
//			population.add(selectBest(check));
//			population.add(selectBest(check));
			population.addAll(fathers);
			population.addAll(mothers);
			population.addAll(childs);
			System.out.println("sau lần lai thứ " + count + "---------------------------------------------");
			System.out.println(population);

			//////////////////////////////////////////
//			int number = removeSolution();
//			System.out.println("sau chọn lọc gene với standar "+standard+" "+number+" cá thể đã bị loại bỏ, còn lại ");
//			System.out.println(population);
			selection();
			System.out.println("sau chọn lọc gene với standar " + standard + " các cá thể đã bị loại bỏ, còn lại ");
			System.out.println(population);

			/////////////////////////////////////////
			System.out.println("mutant ");
			mutant();
			System.out.println(population);
			if (!checkImprovement()) {
				System.out.println("Tối Ưu Cục Bộ, reset quần thể");
				clearPopulation();
			}
		}
	}

	public static int[][] getAnswer(int populationSize, int standard, int resource) throws CloneNotSupportedException {
		Population test = new Population(populationSize, standard);
		if (resource == 0) {
			test.crossover();
		} else {
			test.crossover(resource);
		}
		Solution best = null;
		try {
			best = selectBest(test.population);
//			System.out.println(best.getSolution());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[][] answer = new int[9][9];
		answer = best.convertToArray();
		return answer;
	}

	public static int[][] getAnswer(int populationSize, int standard, int resource, int[][] array)
			throws CloneNotSupportedException {
		Population test = new Population(populationSize, standard, array);
		test.root = array;
		if (resource == 0) {
			test.crossover();
		} else {
			test.crossover(resource);
		}
		Solution best = null;
		try {
			best = selectBest(test.population);
//			System.out.println(best.getSolution());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[][] answer = new int[9][9];
		answer = best.convertToArray();
		return answer;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		int[] array0 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] array1 = { 0, 0, 0, 0, 0, 0, 0, 0, 6 };
		int[] array2 = { 0, 9, 0, 0, 0, 0, 0, 0, 0 };
		int[] array3 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] array4 = { 0, 0, 0, 0, 0, 0, 6, 0, 0 };
		int[] array5 = { 0, 3, 0, 0, 0, 0, 0, 0, 0 };
		int[] array6 = { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
		int[] array7 = { 8, 6, 0, 0, 0, 0, 0, 0, 0 };
		int[] array8 = { 0, 0, 0, 0, 0, 2, 0, 0, 3 };
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
		getAnswer(1000, 100, 0, array);
//		System.out.println("abc");

	}
}
