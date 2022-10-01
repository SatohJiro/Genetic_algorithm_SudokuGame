package Controller;


import java.util.Random;

import model.Population;
import view.PanelTool;

public class GenerateRandomGame {
	private PanelTool panelTool;
	int[][] maze;

	public GenerateRandomGame(PanelTool panelTool) {
		this.panelTool = panelTool;
		maze = new int[9][9];
	}

	public boolean[] checkColumn(int[][] maze) {
		boolean[] check = new boolean[9];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[j][i] != 0) {
					check[i] = true;
					break;
				}
			}
			check[i] = false;
		}
		return check;
	}

	public void printMaze(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j] + "  ");
			}
			System.out.println();
		}
	}

	public int[][] clone() {
		int[][] mazeClone = new int[9][9];
		for (int i = 0; i < mazeClone.length; i++) {
			for (int j = 0; j < mazeClone[i].length; j++) {
				mazeClone[i][j] = maze[i][j];
			}
		}
		return mazeClone;
	}

	public int[][] generateMaze() throws CloneNotSupportedException {
		this.maze = Population.getAnswer(1000, 50, 0);
		int[][] mazeClone = clone();
		Random ran = new Random();
		int count = 0;
		for (int i = 0; i < mazeClone.length; i++) {
			for (int j = 0; j < this.panelTool.getMode(); j++) {
				int index = ran.nextInt(mazeClone.length);
				if (mazeClone[i][index] != 0)
					mazeClone[i][index] = 0;
				else
					j--;
			}
		}
//		System.out.println("////////////////////////////////");
//		printMaze(maze);
//		System.out.println("////////////////////////////////");
//		printMaze(mazeClone);
		boolean[] check = checkColumn(mazeClone);
		for (int i = 0; i < check.length; i++) {
			if (check[i] == false) {
				mazeClone[i][0] = maze[i][0];
			}
		}
//		for (int i = 0; i < mazeClone.length; i++) {
//			for (int j = 0; j < mazeClone[i].length; j++) {
//				System.out.print(mazeClone[i][j] + "  ");
//			}
//			System.out.println();
//		}
//		System.out.println("////////////////////////////////");
//		printMaze(maze);
//		System.out.println("////////////////////////////////");
//		printMaze(mazeClone);
		return mazeClone;
	}

	public int[][] getMaze() {
		return maze;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
//		GenerateRandomGame test = new GenerateRandomGame();
//		test.generateMaze();
	}

}
