package sudokuSolver;

public class SudokuWindows {
	int[][] solver1Data = new int[9][9];
	int[][] solver2Data = new int[9][9];
	int[][] solver3Data = new int[9][9];

	public SudokuWindows(int data[][],String name){
		if(name.equals(Solver1.getWinnerName())){
			solver1Data =  data;
		}
		if(name.equals(Solver2.getWinnerName())){
			solver2Data =  data;
		}
		if(name.equals(Solver3.getWinnerName())){
			solver3Data =  data;
		}
	}
}
