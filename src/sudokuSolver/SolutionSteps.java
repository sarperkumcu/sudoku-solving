package sudokuSolver;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SolutionSteps extends TimerTask {
	List<Integer[][]> steps;
	long finishTime;
	String winnerThreadName;

	public SolutionSteps(List<Integer[][]> steps, long finishTime, String winnerThreadName) {
		this.steps = steps;
		this.finishTime = finishTime;
		this.winnerThreadName = winnerThreadName;

		Integer dataWinner[][] = steps.get(0);

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				stringDataWinner[i][j] = "" + dataWinner[i][j];
			}

		test.setSize(900, 195);
		test.setResizable(false);
		test.setLocation(300, 200);
		test.setVisible(true);
		test.setTitle("Winner - " + winnerThreadName + "- Solved in " + finishTime + "ms");
		dtm = new DefaultTableModel(stringDataWinner, column);
		threadWinnerJT.setModel(dtm);
		threadWinnerJT.getTableHeader().setUI(null);
		// threadWinnerSP = new JScrollPane(threadWinnerJT);

		test.add(threadWinnerJT);

	}

	JScrollPane threadWinnerSP;

	public SolutionSteps() {

	}

	JFrame test = new JFrame();
	JTable threadWinnerJT = new JTable();
	JPanel tP = new JPanel();
	int stepNumber = 0;
	String column[] = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
	DefaultTableModel dtm;
	String stringDataWinner[][] = new String[9][9];

	public void run() {
		stepNumber++;
		if (stepNumber > steps.size() - 1) {
			return;
		}
		Integer dataWinner[][] = steps.get(stepNumber);

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				stringDataWinner[i][j] = "" + dataWinner[i][j];
			}

		dtm = new DefaultTableModel(stringDataWinner, column);
		threadWinnerJT.setModel(dtm);
		// threadWinnerSP = new JScrollPane(threadWinnerJT);

		test.add(threadWinnerJT);
		// test.add(threadWinnerSP);
	}
}
