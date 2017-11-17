package sudokuSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Sudoku implements Runnable{
	private static final String FILENAME = "sudoku.txt";
	int sudokuTable[][] = new int[9][9];
	static boolean done = false;
	static long threadTimer;
	static Solver1 thread1 ;
	static Solver2 thread2;
	static Solver3 thread3;
	
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
		//sudoku.showTable();
		 thread1 = new Solver1("thread1",sudokuTable1.sudokuTable,2,1);
		 thread2 = new Solver2("thread2",sudokuTable2.sudokuTable,0,0);
		 thread3 = new Solver3("thread3",sudokuTable3.sudokuTable,5,7);
		thread1.start();
		thread2.start();
		thread3.start();


		threadTimer = System.currentTimeMillis();

		
	/*while(true){
		if(thread1.areWeDone())
			System.out.println(".");
		if(thread2.areWeDone())
			System.out.println("f");
		if(thread3.areWeDone())
			System.out.println("e");
			if(thread1.didThisClassSolvedCorrect){
				thread2.delete();
				thread3.delete();
				thread1.delete();
				System.out.println("WINNER ->>> " + thread1.getThreadName());
				long finishTime= System.currentTimeMillis() - threadTimer;
				showTable(thread1.getSudokuTable());
				System.out.println("----- "+finishTime+ " -----");
				System.out.println("---THREAD2---");
				showTable(thread2.getSudokuTable());
				System.out.println("---THREAD3---");
				showTable(thread3.getSudokuTable());
				
				break;
			}
			 if(thread2.didThisClassSolvedCorrect){
				thread1.delete();
				thread2.delete();
				thread3.delete();

				System.out.println("WINNER ->>> " + thread2.getThreadName());
				long finishTime= System.currentTimeMillis() - threadTimer;
				showTable(thread2.getSudokuTable());
				System.out.println("----- "+finishTime+ " -----");
				System.out.println("---THREAD1---");
				showTable(thread1.getSudokuTable());
				System.out.println("---THREAD3---");
				showTable(thread3.getSudokuTable());
				break;
			}
			 if(thread3.didThisClassSolvedCorrect){
				thread1.delete();
				thread2.delete();
				thread3.delete();

				System.out.println("WINNER ->>> " + thread3.getThreadName());
				long finishTime= System.currentTimeMillis() - threadTimer;
				showTable(thread3.getSudokuTable());
				System.out.println("----- "+finishTime+ " -----");
				System.out.println("---THREAD1---");
				showTable(thread1.getSudokuTable());
				System.out.println("---THREAD2---");
				showTable(thread2.getSudokuTable());

				break;
			}
		}*/
		
	}

		public void thread1Finished(){
			thread2.delete();
			thread3.delete();
			thread1.delete();
			System.out.println("WINNER ->>> " + thread1.getThreadName());
			long finishTime= System.currentTimeMillis() - threadTimer;
			showTable(thread1.getSudokuTable());
			System.out.println("----- "+finishTime+ " -----");
			System.out.println("---THREAD2---");
			showTable(thread2.getSudokuTable());
			System.out.println("---THREAD3---");
			showTable(thread3.getSudokuTable());
		}
		
		public void thread2Finished(){
			/*thread2.delete();
			thread3.delete();
			thread1.delete();*/
			System.out.println("WINNER ->>> " + thread2.getThreadName());
			long finishTime= System.currentTimeMillis() - threadTimer;
			showTable(thread2.getSudokuTable());
			System.out.println("----- "+finishTime+ " -----");
			System.out.println("---THREAD2---");
			showTable(thread2.getSudokuTable());
			System.out.println("---THREAD3---");
			showTable(thread3.getSudokuTable());
		}
		
		public void thread3Finished(){
			thread2.delete();
			thread3.delete();
			thread1.delete();
			System.out.println("WINNER ->>> " + thread3.getThreadName());
			long finishTime= System.currentTimeMillis() - threadTimer;
			showTable(thread3.getSudokuTable());
			System.out.println("----- "+finishTime+ " -----");
			System.out.println("---THREAD2---");
			showTable(thread2.getSudokuTable());
			System.out.println("---THREAD3---");
			showTable(thread3.getSudokuTable());
		}
		
	  public static void showTable(int sudokuTable[][]) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(sudokuTable[i][j]);
				}
				System.out.println();
			}
		}
	
	public Sudoku(){
		
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

