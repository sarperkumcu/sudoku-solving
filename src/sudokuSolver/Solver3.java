package sudokuSolver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Solver3 extends Thread{
	   private Thread t;
	   private String threadName;
	   static int sudokuTable[][] = new int[9][9];
	   public boolean didThisClassSolvedCorrect = false;
	   public int startX,startY;
	   Sudoku controller = new Sudoku();
	   
	   
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
		   t=null;
	   }
	   
	   public synchronized void shutdown() {

		    t = null;

		    //t.notifyAll();
		}
	   boolean stopping = false;

		public synchronized boolean isStopping() {

		    return stopping;
		}
	   
	   public void run() {
	        Thread thisThread = Thread.currentThread();

		   while(t == thisThread){
	      System.out.println("Running " +  threadName );
	      try {
	    	  
	    		 solve(startX,startY);
	         if(!areWeDone()){
	        	 startX = 0;
	        	 startY = 0;
	        	 solve(startX,startY);
	         }
	         
	    	  }
	         
	      catch (Exception e) {
	    	  }
	    	  
	      if(!Sudoku.done){
	    	  Sudoku.done=true;
    		   controller.thread3Finished();

    	  }
	    	  delete();
	      }
}
	      
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
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
		   
		   if(Sudoku.done)
				throw new Exception("ex");

		   
			if (i > 8 || areWeDone()) {
				if(startX == 0 && startY == 0){
					throw new Exception("ex");
				}
				return;
			}

			if (sudokuTable[i][j] != 0)
				nextCell(i, j);
			else {
				for (int x = 1; x < 10; x++) {
					if (!doesRowIncludeThisValue(i, j, x) && !doesColumnIncludeThisValue(i, j, x)
							&& !does3x3GridIncludeThisValue(i, j, x)) {
						sudokuTable[i][j] = x;
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
								System.out.println("hafaf");
							}
						nextCell(i, j);
					}
					
				}
			
				sudokuTable[i][j] = 0;
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
						System.out.println("hafaf");
					}
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
