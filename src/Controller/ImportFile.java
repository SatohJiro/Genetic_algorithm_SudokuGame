package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportFile {
	public int[][] readFile(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		int[][] maze = new int[9][9];
		int count = 0;
		while ((line = br.readLine()) != null) {
			String[] lineMaze = line.split(" ");
			for (int i = 0; i < lineMaze.length; i++) {
				maze[count][i] = Integer.parseInt(lineMaze[i]);
			}
			count++;
		}
		printMaze(maze);
		return maze;
	}
	public void printMaze(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j]+" ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) throws IOException {
		ImportFile test = new ImportFile();
		test.readFile("C:\\Users\\Admin\\Desktop\\de1.txt");
	}
}
