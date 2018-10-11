import java.util.Hashtable;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input = new Scanner(System.in);
		Strategy helper = new Strategy(1);
		
		System.out.println("Player 1 or player 2?");
		int id = input.nextInt();
		System.out.println("How many rows?");
		int rows = input.nextInt();
		System.out.println("How many columns?");
		int columns = input.nextInt();
		
		int movesRemaining = 2*rows*columns + rows + columns;
		int depth = 5;
		
		Board startingBoard = new Board(rows, columns, 1);
		Board afterMove = startingBoard;
		
		//getTotalDegree function is inefficient and WRONG
		while (movesRemaining != 0) {
			//if (movesRemaining < 12) depth = 9;
			if (afterMove.getID() == id) {
				afterMove = makeOppMove(afterMove);
				movesRemaining--;
				depth = (int) Math.pow(1.25, 2*rows*columns + rows + columns - movesRemaining);
			}
			//if (movesRemaining < 12) depth = 15; //REDUNDANT
			if (afterMove.getID() != id) {
				helper.alphaBeta(afterMove, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
				afterMove = helper.bestBoard;
				movesRemaining--;
				System.out.println("The computer removed the following string: \nString ID = " + helper.bestMove[2] + 
						"\nx = " + helper.bestMove[0] + "\ny = " + helper.bestMove[1]);
			}
		}
		
		//scoring is wrong!
		System.out.println("Game over! Final score:\nComputer: " + helper.computerScore + "\nYou: " + helper.opponentScore);
	}
	
	public static Board makeOppMove(Board board) {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the x-coordinate of the coin whose string you wish to cut:");
		int x = input.nextInt();
		System.out.println("Please enter the y-coordinate of the coin whose string you wish to cut:");
		int y = input.nextInt();
		System.out.println("Please enter the ID of the string you wish to cut:");
		int stringID = input.nextInt();
		//Add error statement here!!!
		return DBUtilities.nextMove(board, board.boardCoins[x][y], stringID);	
	}

}
