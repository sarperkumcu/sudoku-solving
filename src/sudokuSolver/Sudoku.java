package sudokuSolver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Sudoku implements Runnable {
	private static final String FILENAME = "sudoku.txt";
	int sudokuTable[][] = new int[9][9];
	static boolean done = false;
	static long threadTimer;
	static Solver1 thread1;
	static Solver2 thread2;
	static Solver3 thread3;

	public String winnerThreadName;
	
	public List<Integer[][]> winnerSolutionWay = new ArrayList<Integer[][]>();

	int counter = 0;

	boolean alreadyCreatedFrames = false;

	JFrame thread1Frame = new JFrame();
	JFrame thread2Frame = new JFrame();
	JFrame thread3Frame = new JFrame();
	JFrame winnerFrame = new JFrame();
	
	long finishTime;
	
	public static void main(String[] args) throws Exception {

		PrintWriter writer = new PrintWriter("thread1.txt");
		writer.print("");
		writer.close();
		writer = new PrintWriter("thread2.txt");
		writer.print("");
		writer.close();
		writer = new PrintWriter("thread3.txt");
		writer.print("");
		writer.close();

		Sudoku sudokuTable1 = new Sudoku();
		Sudoku sudokuTable2 = new Sudoku();
		Sudoku sudokuTable3 = new Sudoku();
		sudokuTable1.setTable();
		sudokuTable2.setTable();
		sudokuTable3.setTable();
		// sudoku.showTable();
		thread1 = new Solver1("thread1", sudokuTable1.sudokuTable, 7, 0);
		thread2 = new Solver2("thread2", sudokuTable2.sudokuTable, 8, 1);
		thread3 = new Solver3("thread3", sudokuTable3.sudokuTable, 5, 0);
		sudokuTable1.thread1.start();
		thread2.start();
		thread3.start();
		threadTimer = System.currentTimeMillis();
	}

	public void thread1Finished() {
		thread1.delete();
		thread2.delete();
		thread3.delete();
		winnerThreadName = thread1.getThreadName();
		System.out.println("WINNER ->>> " + thread1.getThreadName());
		finishTime = System.currentTimeMillis() - threadTimer;
		System.out.println("----- " + finishTime + "ms -----");
		showFrames();

	}

	public void thread2Finished() {
		thread1.delete();
		thread2.delete();
		thread3.delete();
		winnerThreadName = thread2.getThreadName();
		System.out.println("WINNER ->>> " + thread2.getThreadName());
		finishTime = System.currentTimeMillis() - threadTimer;
		System.out.println("----- " + finishTime + "ms -----");

		showFrames();
	}

	public void thread3Finished() {
		thread1.delete();
		thread2.delete();
		thread3.delete();
		winnerThreadName = thread3.getThreadName();
		System.out.println("WINNER ->>> " + thread3.getThreadName());
		finishTime = System.currentTimeMillis() - threadTimer;
		System.out.println("----- " + finishTime + "ms -----");
		showFrames();
	}

	public static void showTable(int sudokuTable[][]) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sudokuTable[i][j]);
			}
			System.out.println();
		}
	}

	public void showFrames() {
		String column[] = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
		int dataT1[][] = thread1.getSudokuTable();
		String stringDataT1[][] = new String[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				stringDataT1[i][j] = "" + dataT1[i][j];
			}
		JTable thread1JT = new JTable(stringDataT1, column);
		thread1JT.getTableHeader().setUI(null);
		JScrollPane thread1SP = new JScrollPane(thread1JT);
		thread1Frame.setTitle("thread1");
		thread1Frame.add(thread1SP);
		thread1Frame.setSize(500, 195);
		thread1Frame.setResizable(false);
		thread1Frame.setLocation(150, 200);
		thread1Frame.setVisible(true);
		thread1Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int dataT2[][] = thread2.getSudokuTable();
		String stringDataT2[][] = new String[9][9];

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				stringDataT2[i][j] = "" + dataT2[i][j];
			}
		JTable thread2JT = new JTable(stringDataT2, column);
		thread2JT.getTableHeader().setUI(null);
		JScrollPane thread2SP = new JScrollPane(thread2JT);
		thread2Frame.setTitle("thread2");
		thread2Frame.add(thread2SP);
		thread2Frame.setSize(500, 195);
		thread2Frame.setResizable(false);
		thread2Frame.setLocation(300, 200);
		thread2Frame.setVisible(true);
		thread2Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int dataT3[][] = thread3.getSudokuTable();
		String stringDataT3[][] = new String[9][9];

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				stringDataT3[i][j] = "" + dataT3[i][j];
			}
		JTable thread3JT = new JTable(stringDataT3, column);
		thread3JT.getTableHeader().setUI(null);
		JScrollPane thread3SP = new JScrollPane(thread3JT);
		thread3Frame.setTitle("thread3");
		thread3Frame.add(thread3SP);
		thread3Frame.setSize(500, 195);
		thread3Frame.setResizable(false);
		thread3Frame.setLocation(300, 200);
		thread3Frame.setVisible(true);
		thread3Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedReader br = null;
		FileReader fr = null;
		try{
			fr = new FileReader(winnerThreadName + ".txt");
			br = new BufferedReader(fr);
			String sCurrentLine;
			int i = 0;
			Integer[][] fa = new Integer[9][9];
			while ((sCurrentLine = br.readLine()) != null) {
				for (int j = 0; j < 9; j++) {
					if (sCurrentLine.trim().isEmpty()) {
						winnerSolutionWay.add(fa);
						fa = new Integer[9][9];
						i = -1;
						break;
					} else {
						fa[i][j] = (Integer.parseInt(sCurrentLine.substring(j, j + 1)));
					}
				}
				i++;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

		for (int i = 0; i < winnerSolutionWay.size(); i++) {
			Integer[][] f = winnerSolutionWay.get(i);
			for (int a = 0; a < 9; a++) {
				for (int b = 0; b < 9; b++) {
					System.out.print(f[a][b]);
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
		}
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		 for(int i=0;i<winnerSolutionWay.size();i++)
			 comboBox.addItem(i);
		 
		 comboBox.addActionListener(new ActionListener() {
			 
			    @Override
			    public void actionPerformed(ActionEvent event) {
			        JComboBox<Integer> combo = (JComboBox<Integer>) event.getSource();
			        Integer selectedStep = (Integer) combo.getSelectedItem();
			        JFrame test = new JFrame();
			        
			        Integer dataWinner[][] = winnerSolutionWay.get(selectedStep);
					String stringDataWiner[][] = new String[9][9];

					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++) {
							stringDataWiner[i][j] = "" + dataWinner[i][j];
						}
					JTable threadWinnerJT = new JTable(stringDataWiner, column);
					threadWinnerJT.getTableHeader().setUI(null);
					JScrollPane threadWinnerSP = new JScrollPane(threadWinnerJT);
					test.setTitle("" + selectedStep + ". step");
					test.add(threadWinnerSP);
					test.setSize(500, 195);
					test.setResizable(false);
					test.setLocation(300, 200);
					test.setVisible(true);
			    }
			});
		 
			winnerFrame.setTitle("Winner - " + winnerThreadName + "- Solved in " + finishTime + "ms");
			winnerFrame.add(comboBox);
			winnerFrame.setSize(500, 195);
			winnerFrame.setResizable(false);
			winnerFrame.setLocation(300, 200);
			winnerFrame.setVisible(true);
			winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public Sudoku() {

	}

	@Override
	public void run() {

	}

	public void setTable() {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				for (int j = 0; j < 9; j++) {
					if (sCurrentLine.substring(j, j + 1).equals("*")) {
						sudokuTable[i][j] = 0;
					} else {
						sudokuTable[i][j] = (Integer.parseInt(sCurrentLine.substring(j, j + 1)));
					}
				}
				i++;

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}

}
