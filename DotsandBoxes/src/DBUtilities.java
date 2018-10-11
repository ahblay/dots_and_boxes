import java.util.ArrayList;

public class DBUtilities {

	//this could use coin.x and coin.y as parameters instead of coin
	public static Board nextMove(Board board, Coin coin, int stringID) {
		Board nextMove = new Board(board); 
		nextMove.updateKey(coin.getX(), coin.getY(), stringID); 
		nextMove.boardCoins[coin.getX()][coin.getY()].setString(stringID);
		
		if (stringID == 0 & coin.getY() < nextMove.rows - 1) {
			nextMove.boardCoins[coin.getX()][coin.getY() + 1].setString((stringID + 2) % 4);
			nextMove.updateKey(coin.getX(), coin.getY() + 1, (stringID + 2) % 4);
		}
		else if (stringID == 1 & coin.getX() < nextMove.columns - 1) {
			nextMove.boardCoins[coin.getX() + 1][coin.getY()].setString((stringID + 2) % 4);
			nextMove.updateKey(coin.getX() + 1, coin.getY(), (stringID + 2) % 4);
		}
		else if (stringID == 2 & coin.getY() > 0) {
			nextMove.boardCoins[coin.getX()][coin.getY() - 1].setString((stringID + 2) % 4);
			nextMove.updateKey(coin.getX(), coin.getY() - 1, (stringID + 2) % 4);
		}
		else if (stringID == 3 & coin.getX() > 0) {
			nextMove.boardCoins[coin.getX() - 1][coin.getY()].setString((stringID + 2) % 4);
			nextMove.updateKey(coin.getX() - 1, coin.getY(), (stringID + 2) % 4);
		}
		
		//if removes coin, keeps same ID
		if (nextMove.boardCoins[coin.getX()][coin.getY()].degree == 0) { 
			nextMove.setID(board.getID());
		} else {
			//otherwise, changes ID from one player to other
			if (board.getID() == 1) nextMove.setID(2); 
			if (board.getID() == 2) nextMove.setID(1);
		}
		
		return nextMove;
	}
	
	public static ArrayList<int[]> orderMoves(Board board) {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		for (int j = 0; j < board.rows; j++) { 
			for (int i = 0; i < board.columns; i++) {
				for (int k = 0; k < 4; k++) {
					int[] toMove = {i,j,k};
					moves.add(toMove);
				}
			}
		}
		return moves;
	}
}
