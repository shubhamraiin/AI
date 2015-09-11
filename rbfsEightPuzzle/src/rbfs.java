/*
 Assignment No 1
By: Shubham Rai
scr130130
*/

/*
 Implementing the Recursive Best First Search Algorithm.
 This file reads the current state from the EightPuzzle.txt and calls the recursie best first search function defined by
 aima with mahahattan distance as the chosen heuristic.
 
  */

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.RecursiveBestFirstSearch;


public class rbfs {
	
static void recursiveBestFirstSearch() {
		
		System.out.println("EightPuzzle Problem with Recursive Best First Search ");
		int count=0;
		for (int i=0;i <9;i++)
		{
				System.out.print(read.startState[i]+"\t");
				count++;
				if(count==3|| count==6)
					System.out.println();
		}
		System.out.println();
		read.currentState = new EightPuzzleBoard(read.startState);
		
		try {
			   Problem problem = new Problem(read.currentState, EightPuzzleFunctionFactory.getActionsFunction(), EightPuzzleFunctionFactory.getResultFunction(),
			   new EightPuzzleGoalTest());
			   Search search = new RecursiveBestFirstSearch(new AStarEvaluationFunction(new ManhattanHeuristicFunction()));
               SearchAgent agent = new SearchAgent(problem, search);
 									
			   printActions(agent.getActions());
			   printInstrumentation(agent.getInstrumentation());
	  	     } catch (Exception e) {
			   e.printStackTrace();
		}

	}

private static void printInstrumentation(Properties properties) {
	Iterator<Object> keys = properties.keySet().iterator();
	while (keys.hasNext()) {
		String key = (String) keys.next();
		String property = properties.getProperty(key);
		System.out.println(key + " : " + property);
	}

}

private static void printActions(List<Action> actions) {
	for (int i = 0; i < actions.size(); i++) {
		String action = actions.get(i).toString();
		System.out.println(action);
	}
}

}