
public class Strategy2 {
	int id;
	 
	public Strategy2(int playerID) {
		playerID = id;
	}
	 
	public static double alphaBeta(Board board, int depth, double alpha, double beta, boolean maximizingPlayer) {
		double value = 0;
		if (depth == 0 /*|| node is terminal*/) {
			value = heuristic(board);
			return value;
		}
		else if (maximizingPlayer == true) {
			value = Double.NEGATIVE_INFINITY;
			for (int j = 0; j < board.rows; j++) { 
				for (int i = 0; i < board.columns; i++) {
					if (i != board.columns - 1 & j != board.rows - 1) { //The next giant bunch of code exists to cut each available string exactly once
						for (int k = 2; k < 4; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
								alpha = Math.max(alpha, value);
								if (beta <= alpha) break;	
							}
						}
					}
					else if (i == board.columns - 1 & j != board.rows - 1) {
						for (int k = 1; k < 4; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
								alpha = Math.max(alpha, value);
								if (beta <= alpha) break;	
							}
						}
					}
					else if (i == 0 & j == board.rows - 1) {
						for (int k = 0; k < 4; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
								alpha = Math.max(alpha, value);
								if (beta <= alpha) break;	
							}
						}
					}
					else {
						for (int k = 0; k < 3; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
								alpha = Math.max(alpha, value);
								if (beta <= alpha) break;	
							}
						}
					}
				}
			}
			return value;
		}
		else {
			value = Double.POSITIVE_INFINITY;
			for (int j = 0; j < board.rows; j++) { 
				for (int i = 0; i < board.columns; i++) {
					if (i != board.columns - 1 & j != board.rows - 1) { //The next giant bunch of code exists to cut each available string exactly once
						for (int k = 2; k < 4; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
								alpha = Math.min(beta, value);
								if (beta <= alpha) break;	
							}
						}
					}
					else if (i == board.columns - 1 & j != board.rows - 1) {
						for (int k = 1; k < 4; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
								alpha = Math.min(beta, value);
								if (beta <= alpha) break;
							}
						}
					}
					else if (i == 0 & j == board.rows - 1) {
						for (int k = 0; k < 4; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
								alpha = Math.min(beta, value);
								if (beta <= alpha) break;
							}
						}
					}
					else {
						for (int k = 0; k < 3; k++) {
							if (board.boardCoins[i][j].getString(k) == 1) {
								Board newBoard = DBUtilities.nextMove(/*id,*/ board, board.boardCoins[i][j], k); 
								value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
								alpha = Math.min(beta, value);
								if (beta <= alpha) break;
							}
						}
					}
				}
			}
			return value;
		}
	}
	
	public double iterativeDeepening(Board board, int d) {
		for (int depth = 0; depth <= d; depth++) {
			alphaBeta(board, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		}
	}
	
	public static double heuristic(Board board) {
		return 1;
	}
}
