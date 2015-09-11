/*
 Assignment No 1
By: Shubham Rai
scr130130
*/
/*
  This class reads the files provided as the argument i.e eightpuzzle.txt file and call the recursive best first search method.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;


public class read 
   {
	
	static EightPuzzleBoard currentState;
	static int  startState[]= new int[9];
	static String eightpuzzlefile;
	
	public static void main(String[] args) throws Exception 
	{
		eightpuzzlefile=args[0];
		read puzzle= new read();
		puzzle.getStart(eightpuzzlefile);
		rbfs.recursiveBestFirstSearch();
	}
	
	public void getStart( String puzzleFile) throws Exception
	{
		File file = new File(puzzleFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		int ix=0,jx;
			
		while( (line = reader.readLine())!= null){
			String[] tokens=line.split("\\s");
			for (jx=0;jx<3;jx++ ){
				startState[ix]= Integer.parseInt(tokens[jx]);
				ix++;
				}
			}
		reader.close();
		}
  }
