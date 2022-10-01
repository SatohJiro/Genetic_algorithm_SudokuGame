package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.GenerateRandomGame;
import Controller.ImportFile;
import model.Population;

public class PanelTool extends JPanel implements ActionListener {

	// format
	private JLabel jlbMargin, jlbPadding;

	/////// main///////

	// tool AI
	private JLabel jlbTitleAI, jlbResource, jlbSize, jlbStandard, jlbRecommended, jlbReResource, jlbReSize,
			jlbReStandard;
	private JTextField jtfResource, jtfSize, jtfStandard;
	private JButton jbtRunAI;

	///////////// create gameplay///////////////////
	private JLabel jlbCreateGameplay;

	// mode auto
	private JLabel jlbAutoMode, jlbMode;
	private String[] modes = { "Dễ", "Trung Bình", "Khó" };
	private JComboBox<String> modeCombo;
	private int mode;
	private JLabel jlbCreate;
	private JButton jbtCreateRandom;

	// import
	private JLabel jlbChooseFile, statusLabel;
	private JFileChooser fileDialog;
	private JButton jbtCreateFile;
	private JButton showFileDialogButton;

	// handmade
	private JLabel jlbHandmade;
	private JButton jbtHandMade, jbtFinish;

	// Check game
	private JLabel jlbCheck;
	private JButton jbtCheck;

	////// player Mode
	private JLabel jlbSelectMode;
	private JButton jbtPlayer, jbtAI;

	//////////////////////
	private GamePlay gamePlay;
	private GenerateRandomGame generateGame;

	public void generateComponent() {
		jlbCreateGameplay = new JLabel("Create Gameplay");
		addMargin();
		this.add(jlbCreateGameplay);

		/// mode auto
		jlbAutoMode = new JLabel("Tạo Tự Động ------------------------------------");
		this.add(jlbAutoMode);
		jlbMode = new JLabel("Độ Khó : ");
		this.add(jlbMode);
		this.add(modeCombo);
		jbtCreateRandom = new JButton("Create");
		jbtCreateRandom.addActionListener(this);
		this.add(jbtCreateRandom);
		/// mode import
		jlbChooseFile = new JLabel("Import File ----------------------------------------");
		this.add(jlbChooseFile);
		fileDialog = new JFileChooser();
		showFileDialogButton = new JButton("Open File");
		statusLabel = new JLabel();
		showFileDialogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileDialog.showOpenDialog(PanelTool.this.gamePlay);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					java.io.File file = fileDialog.getSelectedFile();
//					statusLabel.setText("File Selected :" + file.getName());
					showFileDialogButton.setText(file.getName());
				} else {
					statusLabel.setText("you haven't choose file");
				}
			}
		});
		this.add(showFileDialogButton);
		jbtCreateFile = new JButton("Create");
		this.add(jbtCreateFile);
		jbtCreateFile.addActionListener(this);
		this.add(statusLabel);
		// handmade mode
		jlbHandmade = new JLabel("Handmade mode ------------------------------");
		this.add(jlbHandmade);
		jbtHandMade = new JButton("Start  ");
		jbtFinish = new JButton("Finish");
		jbtFinish.setEnabled(false);
		jbtHandMade.addActionListener(this);
		jbtFinish.addActionListener(this);
		this.add(jbtHandMade);
		this.add(jbtFinish);

		/////////// MODE PLAY
		addPadding();
		jlbSelectMode = new JLabel("------------ Select Mode Play ------------");
		this.add(jlbSelectMode);
		jbtPlayer = new JButton("Player");
		jbtAI = new JButton("  AI  ");
		jbtPlayer.addActionListener(this);
		jbtAI.addActionListener(this);
		this.add(jbtPlayer);
		this.add(jbtAI);
	}

	public void generateCheckComponent() {
		///// btn check game
		addPadding();
		jbtCheck = new JButton("Check");
		this.add(jlbCheck = new JLabel("Check Game --------------------------"));
		addMargin();
		jbtCheck.addActionListener(this);
		this.add(jbtCheck);

	}

	public PanelTool(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
		modeCombo = new JComboBox<String>(modes);
		this.generateGame = new GenerateRandomGame(this);
		//////////////////////////////////////////////////
		generateComponent();
		this.setPreferredSize(new Dimension(220, 600));
	}

	public void addMargin() {
		Dimension dimeMargin = new Dimension(20, 20);
		jlbMargin = new JLabel();
		jlbMargin.setPreferredSize(dimeMargin);
		this.add(jlbMargin);
	}

	public void addPadding() {
		Dimension dimePadding = new Dimension(200, 20);
		jlbPadding = new JLabel();
		jlbPadding.setPreferredSize(dimePadding);
		this.add(jlbPadding);
	}

	public void playerMode() {
		this.removeAll();
		generateComponent();
		generateCheckComponent();
		showFileDialogButton.setEnabled(false);
		jbtCreateFile.setEnabled(false);
		jbtCreateRandom.setEnabled(false);
		jbtHandMade.setEnabled(false);
		jbtFinish.setEnabled(false);
		validate();
		repaint();
	}

	public void AIMode() {
		jlbTitleAI = new JLabel("-------------  Genetic Algorithms  ----------");
		this.add(jlbTitleAI);
		addMargin();
		addMargin();
		addMargin();
		addMargin();
		addMargin();
		jlbRecommended = new JLabel("Recommended");
		this.add(jlbRecommended);

		jlbSize = new JLabel("Population Size: ");
		this.add(jlbSize);
		jtfSize = new JTextField();
		jtfSize.setPreferredSize(new Dimension(45, 20));
		this.add(jtfSize);
		jlbReSize = new JLabel(" >300");
		jlbReSize.setPreferredSize(new Dimension(40, 20));
		this.add(jlbReSize);
		addMargin();

		jlbStandard = new JLabel("Enter Standard:  ");
		this.add(jlbStandard);
		jtfStandard = new JTextField();
		jtfStandard.setPreferredSize(new Dimension(45, 20));
		this.add(jtfStandard);
		jlbReStandard = new JLabel(" >100");
		jlbReStandard.setPreferredSize(new Dimension(40, 20));
		this.add(jlbReStandard);
		addMargin();

		jlbResource = new JLabel("Enter Resource: ");
		this.add(jlbResource);
		jtfResource = new JTextField();
		jtfResource.setPreferredSize(new Dimension(45, 20));
		this.add(jtfResource);
		jlbReResource = new JLabel(" >1000");
		jlbReResource.setPreferredSize(new Dimension(40, 20));
		this.add(jlbReResource);
		addMargin();

		this.add(jbtRunAI = new JButton("RUN"));
		jbtRunAI.addActionListener(this);
		validate();
		repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// auto create
		if (e.getSource() == jbtCreateRandom) {
			if (modeCombo.getSelectedItem().equals("Dễ")) {
				mode = 3;
			}
			if (modeCombo.getSelectedItem().equals("Trung Bình")) {
				mode = 4;
			}
			if (modeCombo.getSelectedItem().equals("Khó")) {
				mode = 5;
			}
			try {
				gamePlay.createSudoku(generateGame.generateMaze());
				gamePlay.setMazeAnswer(generateGame.getMaze());
				generateGame.printMaze(gamePlay.getMazeAnswer());

				showFileDialogButton.setEnabled(false);
				jbtCreateRandom.setEnabled(false);
				jbtCreateFile.setEnabled(false);
				jbtHandMade.setEnabled(false);
				jbtFinish.setEnabled(false);

			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// import
		if (e.getSource() == jbtCreateFile) {
			ImportFile im = new ImportFile();
			try {
				int[][] maze = im.readFile(fileDialog.getSelectedFile().getPath());
				try {
					gamePlay.setMazeInterByImport(maze);

					showFileDialogButton.setEnabled(false);
					jbtCreateFile.setEnabled(false);
					jbtCreateRandom.setEnabled(false);
					jbtHandMade.setEnabled(false);
					jbtFinish.setEnabled(false);
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		// handmade
		if (e.getSource() == jbtHandMade) {
			showFileDialogButton.setEnabled(false);
			jbtCreateFile.setEnabled(false);
			jbtCreateRandom.setEnabled(false);
			jbtHandMade.setEnabled(false);
			jbtFinish.setEnabled(true);
		}
		if (e.getSource() == jbtFinish) {
			try {
				gamePlay.setMazeInterByHand();
				jbtFinish.setEnabled(false);
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		/// event mode play
		if (e.getSource() == jbtPlayer) {
			playerMode();
			jbtAI.setEnabled(false);
		}
		if (e.getSource() == jbtAI) {
			AIMode();
			jbtPlayer.setEnabled(false);
		}

		// event check

		if (e.getSource() == jbtCheck) {
			gamePlay.setCheck(true);
			gamePlay.check();
		}

		// event AI
		if (e.getSource() == jbtRunAI) {
			if (!jtfSize.getText().equals("") && !jtfStandard.getText().equals("")
					&& !jtfResource.getText().equals("")) {
				int size = Integer.parseInt(jtfSize.getText());
//				jtfSize.setText("");
				int standard = Integer.parseInt(jtfStandard.getText());
//				jtfStandard.setText("");
				int resource = Integer.parseInt(jtfResource.getText());
//				jtfResource.setText("");
				try {
					gamePlay.createSudoku(Population.getAnswer(size, standard, resource, gamePlay.getMazeInterface()));
					jbtRunAI.setEnabled(false);
//					jbtCreateRandom.setEnabled(true);
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	public JLabel getJlbCreate() {
		return jlbCreate;
	}

	public int getMode() {
		return mode;
	}

}
