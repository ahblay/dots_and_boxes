import java.util.Scanner;

public class Main2 {

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
		
		Board startingBoard = new Board(rows, columns, 1);
		Board afterMove = startingBoard;
		
		afterMove = makeOppMove(afterMove);
		afterMove = makeOppMove(afterMove);
		//afterMove = makeOppMove(afterMove);
		//afterMove = makeOppMove(afterMove);
		//afterMove = makeOppMove(afterMove);
		//afterMove = makeOppMove(afterMove);
		
		afterMove.setComponents();
		
		int degree= afterMove.components.get(0).get(0).getDegree();
		
		System.out.println("Done." + degree);

	}
	
	public static Board makeOppMove(Board board) {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the x-coordinate of the coin whose string you wish to cut:");
		int x = input.nextInt();
		System.out.println("Please enter the y-coordinate of the coin whose string you wish to cut:");
		int y = input.nextInt();
		System.out.println("Please enter the ID of the string you wish to cut:");
		int stringID = input.nextInt();
		return DBUtilities.nextMove(board, board.boardCoins[x][y], stringID);	
	}

}
