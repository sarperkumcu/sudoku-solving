package sudokuSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Sudoku implements Runnable{
	private static final String FILENAME = "sudoku.txt";
	int sudokuTable[][] = new int[9][9];
	static boolean done = false;
	static long threadTimer;
	static Solver1 thread1 ;
	static Solver2 thread2;
	static Solver3 thread3;
	
	JFrame thread1Frame = new JFrame();
	JFrame thread2Frame = new JFrame();
	JFrame thread3Frame = new JFrame();
	
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
			showFrames();
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
			showFrames();
			
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
	  
	  public void showFrames(){
		  String column[]={" "," "," "," "," "," "," "," "," "};
			int dataT1[][] = thread1.getSudokuTable();
			String stringDataT1[][] = new String[9][9];
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++){
					stringDataT1[i][j] = "" + dataT1[i][j];
				}
			  JTable thread1JT=new JTable(stringDataT1,column);
			  thread1JT.getTableHeader().setUI(null);
		        JScrollPane thread1SP=new JScrollPane(thread1JT);
		        thread1Frame.setTitle("thread1");
		        thread1Frame.add(thread1SP);
		        thread1Frame.setSize(500,195);
		        thread1Frame.setResizable(false);
		        thread1Frame.setLocation(150,200);
		        thread1Frame.setVisible(true);
		        thread1Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        
		        int dataT2[][] = thread2.getSudokuTable();
				String stringDataT2[][] = new String[9][9];

				for(int i=0;i<9;i++)
					for(int j=0;j<9;j++){
						stringDataT2[i][j] = "" + dataT2[i][j];
					}
				  JTable thread2JT=new JTable(stringDataT2,column);
				  thread2JT.getTableHeader().setUI(null);
			        JScrollPane thread2SP=new JScrollPane(thread2JT);
			        thread2Frame.setTitle("thread2");
			        thread2Frame.add(thread2SP);
			        thread2Frame.setSize(500,195);
			        thread2Frame.setResizable(false);
			        thread2Frame.setLocation(300,200);
			        thread2Frame.setVisible(true);
			        thread2Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        
			        int dataT3[][] = thread3.getSudokuTable();
					String stringDataT3[][] = new String[9][9];

					for(int i=0;i<9;i++)
						for(int j=0;j<9;j++){
							stringDataT3[i][j] = "" + dataT3[i][j];
						}
					  JTable thread3JT=new JTable(stringDataT3,column);
					  thread3JT.getTableHeader().setUI(null);
				        JScrollPane thread3SP=new JScrollPane(thread3JT);
				        thread3Frame.setTitle("thread3");
				        thread3Frame.add(thread3SP);
				        thread3Frame.setSize(500,195);
				        thread3Frame.setResizable(false);
				        thread3Frame.setLocation(300,200);
				        thread3Frame.setVisible(true);
				        thread3Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

