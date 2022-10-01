package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.GenerateRandomGame;
import model.Population;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
	private JButton[][] jbt;
	private int[][] mazeInterface;
	private int[][] mazeAnswer;
	private int I, J;
	private int mistake;

	private GenerateRandomGame generateGame;
	private Sudoku sudoku;

	private boolean check = false;

	public GamePlay(Sudoku sudoku) {

		this.sudoku = sudoku;
		jbt = new JButton[9][9];
		mazeInterface = new int[9][9];
		mistake = 0;
		this.generateGame = new GenerateRandomGame(sudoku.p2);

		this.setLayout(new GridLayout(9, 9));
		Font font = new Font("Serif", Font.BOLD, 25);
		for (int i = 0; i < jbt.length; i++) {
			for (int j = 0; j < jbt.length; j++) {
				jbt[i][j] = new JButton();
				jbt[i][j].setFont(font);
				jbt[i][j].setHorizontalAlignment(JTextField.CENTER);
				jbt[i][j].setBackground(Color.white);
				jbt[i][j].setActionCommand(i + "|" + j);
				jbt[i][j].addActionListener(this);
				jbt[i][j].addKeyListener(this);
				this.add(jbt[i][j]);
			}
		}
		for (int i = 0; i < 9; i += 3)
			for (int j = 0; j < 9; j += 3) {
				jbt[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.black));
				jbt[i][j + 2].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.black));
				jbt[i + 2][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.black));
				jbt[i + 2][j].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.black));
				jbt[i][j + 1].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.black));
				jbt[i + 1][j + 2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.black));
				jbt[i + 2][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.black));
				jbt[i + 1][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.black));
				jbt[i + 1][j + 1].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			}

	}

	public void resetMaze() {
		for (int i = 0; i < jbt.length; i++) {
			for (int j = 0; j < jbt[i].length; j++) {
				jbt[i][j].setText("");
			}
		}
	}

	public void createSudoku(int[][] array) {
		mazeInterface = array;
		resetMaze();
		for (int i = 0; i < mazeInterface.length; i++) {
			for (int j = 0; j < mazeInterface[i].length; j++) {
				if (mazeInterface[i][j] != 0) {
					jbt[i][j].setText(mazeInterface[i][j] + "");
					jbt[i][j].setEnabled(false);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String temp = e.getActionCommand();
		String[] index = temp.split("|");
		I = Integer.parseInt(index[0]);
		J = Integer.parseInt(index[2]);
		for (int i = 0; i < jbt.length; i++) {
			for (int j = 0; j < jbt.length; j++) {
				if (i == I && j == J)
					jbt[i][j].setBackground(Color.lightGray);
				else if (mazeInterface[i][j] != 0) {
				} else
					jbt[i][j].setBackground(Color.white);
			}
		}
		// TODO Auto-generated method stub

	}

	public void check() {
		if (check) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (mazeAnswer[i][j] == mazeInterface[i][j])
						jbt[i][j].setForeground(Color.BLUE);
					else {
						jbt[i][j].setForeground(Color.RED);
						mistake++;
					}
				}
			}
		}
		if (mistake == 0) {
			int opition = JOptionPane.showConfirmDialog(null, "Bạn Đã thắng, muốn thử lại chứ", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (opition == JOptionPane.YES_OPTION) {
				sudoku.dispose();
				sudoku = new Sudoku();
			} else if (opition == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		}
		if (mistake > 0) {
			int opition = JOptionPane.showConfirmDialog(null, "Bạn Đã thua với "+mistake+" lỗi, muốn thử lại chứ", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (opition == JOptionPane.YES_OPTION) {
				sudoku.dispose();
				sudoku = new Sudoku();
			} else if (opition == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int v = e.getKeyCode();
		if ((v >= 49 && v <= 57)) {
			v -= 48;
			if (mazeInterface[I][J] == 0) {
				jbt[I][J].setText(v + "");
				mazeInterface[I][J] = v;
				if (mazeAnswer != null)
					System.out.println("Bạn đã nhập " + v + " đáp án là " + mazeAnswer[I][J]);
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public int[][] getMazeInterface() {
		return mazeInterface;
	}

	public void setMazeInterface(int[][] mazeInterface) {
		this.mazeInterface = mazeInterface;
	}

	public int[][] getMazeAnswer() {
		return mazeAnswer;
	}

	public void setMazeAnswer(int[][] mazeAnswer) {
		this.mazeAnswer = mazeAnswer;
	}

	public int getMistake() {
		return mistake;
	}

	public void setMistake(int mistake) {
		this.mistake = mistake;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setMazeInterByImport(int[][] maze) throws CloneNotSupportedException {
		createSudoku(maze);
		setMazeAnswer(Population.getAnswer(1000, 100, 0, getMazeInterface()));
		printMaze(getMazeInterface());
	}

	public void setMazeInterByHand() throws CloneNotSupportedException {
		createSudoku(getMazeInterface());
		setMazeAnswer(Population.getAnswer(1000, 100, 0, getMazeInterface()));
		printMaze(getMazeInterface());
	}

	public void printMaze(int[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}

}
