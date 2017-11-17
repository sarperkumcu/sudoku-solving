package sudokuSolver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Solver3 extends Thread{
	   private Thread t;
	   private String threadName;
	   int sudokuTable[][] = new int[9][9];
	   static String winnerName = "null";
	   public int startX,startY;
	   public boolean didThisClassSolvedCorrect = false;
	   Sudoku controller =  new Sudoku();
	   
	   public static String getWinnerName() {
		return winnerName;
	}

	public static void setWinnerName(String winnerName) {
		Solver3.winnerName = winnerName;
	}

	public int[][] getSudokuTable() {
		return sudokuTable;
	}

	public void setSudokuTable(int[][] sudokuTable) {
		this.sudokuTable = sudokuTable;
	}

	public String getThreadName(){
		   return threadName;
	   }
	   
	   public Thread getT(){
		   return t;
	   }
	   
	   public Solver3( String name,int[][] sudokuTable,int startX,int startY) {
	      threadName = name;
	      this.startX = startX;
	      this.startY = startY;
	      this.sudokuTable = sudokuTable;
	      System.out.println("Creating " +  threadName );
	   }
	   
	   public void showTable() {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(sudokuTable[i][j]);
				}
				System.out.println();
			}
		}
	   
	   public void delete(){
		   t.interrupt();
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      try {
	         solve(startX,startY);
	         if(!areWeDone()){
	        	 startX = 0;
	        	 startY = 0;
	        	 solve(startX,startY);
	         }
	      } catch (Exception e) {
	    	  /*System.out.println("----" + threadName +"----");
				showTable();
				System.out.println("--------------------------");*/
			//if(t.isInterrupted())
	        // System.out.println("Thread " +  threadName + " interrupted.");
	    	  if(winnerName.equals("null")){
	    		  winnerName = threadName;
	    		  delete();
	    	  }
	    	  if(!Sudoku.done)
	   		   controller.thread3Finished();

	    	  Sudoku.done=true;
	    	  delete();
	      }
	      //showTable();
	      
	      //System.out.println("Thread " +  threadName + " exiting.");
	      
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	         t.setPriority(Thread.NORM_PRIORITY);
	      }
	   }
	   
	   
		public boolean areWeDone() {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (sudokuTable[i][j] == 0) {
						return false;

					}
				}
			}
			didThisClassSolvedCorrect = true;
			return true;
		}
	   
	   public void solve(int i, int j) throws Exception {
			// Throw an exception to stop the process if the puzzle is solved
		   
		   try(FileWriter fw = new FileWriter("thread3.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				   for(int a=0;a<9;a++){
					   for(int b=0; b<9; b++){
						   out.print(sudokuTable[a][b]);
					   }
					   out.println();
				   }
				   out.println();
				   out.println();
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
		   
		   if(Sudoku.done)
				throw new Exception("ex");

			if (i > 8 || areWeDone()) {
				if(startX == 0 && startY == 0){
					throw new Exception("ex");
				}
				return;
			}

			// If the cell is not empty, continue with the next cell
			if (sudokuTable[i][j] != 0)
				nextCell(i, j);
			else {
				// Find a valid number for the empty cell
				for (int x = 1; x < 10; x++) {
					if (!doesRowIncludeThisValue(i, j, x) && !doesColumnIncludeThisValue(i, j, x)
							&& !does3x3GridIncludeThisValue(i, j, x)) {
						sudokuTable[i][j] = x;
						nextCell(i, j);
					}
				}

				// No valid number was found, clean up and return to caller
				sudokuTable[i][j] = 0;
			}
		}

		private boolean does3x3GridIncludeThisValue(int i, int j, int x) {
			int iCellStartPoint = i;
			int jCellStartPoint = j;

			while (iCellStartPoint % 3 != 0) {
				iCellStartPoint--;
			}
			while (jCellStartPoint % 3 != 0) {
				jCellStartPoint--;
			}

			for (int i_iterator = iCellStartPoint; i_iterator < iCellStartPoint + 3; i_iterator++) {
				for (int j_iterator = jCellStartPoint; j_iterator < jCellStartPoint + 3; j_iterator++) {
					if (sudokuTable[i_iterator][j_iterator] == x && i_iterator != i && j_iterator != j) {
						return true;
					}
				}
			}

			return false;
		}

		private boolean doesColumnIncludeThisValue(int i, int j, int x) {
			for (int k = 0; k < 9; k++) {
				if (sudokuTable[k][j] == x) {
					return true;
				}
			}
			return false;
		}

		private boolean doesRowIncludeThisValue(int i, int j, int x) {
			for (int k = 0; k < 9; k++) {
				if (sudokuTable[i][k] == x) {
					return true;
				}
			}
			return false;
		}

		public void nextCell(int i, int j) throws Exception {
			if (j < 8)
				solve(i, j + 1);
			else
				solve(i + 1, 0);
		}
	}
