package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sudoku extends JFrame {
	GamePlay p1;
	PanelTool p2;

	public Sudoku() {
		p1 = new GamePlay(this);
		p1.setPreferredSize(new Dimension(600, 600));
		this.add(p1, BorderLayout.WEST);

		p2 = new PanelTool(p1);
		this.add(p2, BorderLayout.EAST);

		this.setTitle("Sudoku");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Sudoku();
	}
}
