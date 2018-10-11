import java.util.Hashtable;

public class Strategy4 {
	int id;
	Board bestBoard;
	int[] bestMove = new int[3];
	Hashtable<Long, Transposition> transpositionTable = new Hashtable<Long, Transposition>();
	int computerScore = 0;
	int opponentScore = 0;
	 
	public Strategy4(int playerID) {
		this.id = playerID;
	}
	 
	public double alphaBeta(Board board, int depth, double alpha, double beta, boolean maximizingPlayer) {
		System.out.println("Computer Score: " + computerScore + " & Opponent Score: " + opponentScore + " & Depth: " + depth);
		Board tempBestBoard = null; //this isn't working b/c it sets external variable equal to null in each recursion. !!!Temp solution might be sketchy
		int[] tempBestMove = {-1,-1,-1}; //temp solution might be sketchy!!!!!
		double value;
		if (transpositionTable.get(board.zobristKey) != null) { //if the board zobrist key in the TT equals the current board key
			if (transpositionTable.get(board.zobristKey).zobristKey == board.zobristKey) {	
				if (transpositionTable.get(board.zobristKey).depth >= depth) {
					if (transpositionTable.get(board.zobristKey).compScore == computerScore && transpositionTable.get(board.zobristKey).oppScore == opponentScore) {
						tempBestBoard = transpositionTable.get(board.zobristKey).bestBoard; //Get bestBoard from TT
						tempBestMove = transpositionTable.get(board.zobristKey).bestMove;
						if (transpositionTable.get(board.zobristKey).flag == 0) {
							value = transpositionTable.get(board.zobristKey).value;
							return value; //Somehow encode the score here...???
						}
						if (transpositionTable.get(board.zobristKey).flag == 1 & transpositionTable.get(board.zobristKey).value >= alpha) {
							alpha = transpositionTable.get(board.zobristKey).value; //alpha, beta are fns of the score...
						}
						if (transpositionTable.get(board.zobristKey).flag == 2 & transpositionTable.get(board.zobristKey).value <= beta) {
							beta = transpositionTable.get(board.zobristKey).value;
						}
					}	
				}
			}
		}
		if (depth == 0 || board.getTotalDegree() == 0) { //getTotalDegree function is inefficient
			value = heuristic(board);
			storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove); //puts the value in the TT. Evaluates zobrist key of entire board. 0 means that we store the real value
			return value;
		}
		else if (maximizingPlayer == true) {
			value = Double.NEGATIVE_INFINITY;
			for (int j = 0; j < board.rows; j++) { 
				for (int i = 0; i < board.columns; i++) {
					for (int k = 0; k < 4; k++) {
						if (board.boardCoins[i][j].getString(k) == 1) {
							Board newBoard = DBUtilities.nextMove(board, board.boardCoins[i][j], k); //PROBLEM! No deep copying
							if (newBoard.boardCoins[i][j].getDegree() == 0){
								computerScore = computerScore + 1;
								value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
								computerScore = computerScore - 1;
							} else {
								value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
							} 
							if (value > alpha) {
								alpha = value;
								tempBestBoard = newBoard;
								tempBestMove[0] = i;
								tempBestMove[1] = j;
								tempBestMove[2] = k;
							}
							storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove); //stores real value of board
							if (beta <= alpha) {
								storeHash(board, depth, 1, value, false, tempBestBoard, tempBestMove); //stores alpha value of board
								break;
							}
						}
					}
				}
			}
			bestBoard = tempBestBoard; //finally sets the proper best board. Worth rechecking later!!!!!
			for (int i = 0; i < 3; i++) {
				bestMove[i] = tempBestMove[i];
			}
			return value;
		}
		else {
			value = Double.POSITIVE_INFINITY;
			for (int j = 0; j < board.rows; j++) { 
				for (int i = 0; i < board.columns; i++) {
					for (int k = 0; k < 4; k++) {
						if (board.boardCoins[i][j].getString(k) == 1) {
							Board newBoard = DBUtilities.nextMove(board, board.boardCoins[i][j], k); 
							if (newBoard.boardCoins[i][j].getDegree() == 0){
								opponentScore = opponentScore + 1;
								value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
								opponentScore = opponentScore - 1;
							} else {
								value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
							}
							if (value < beta) {
								beta = value;
								tempBestBoard = newBoard;
								tempBestMove[0] = i;
								tempBestMove[1] = j;
								tempBestMove[2] = k;
							}
							storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove); //stores real value of board
							if (beta <= alpha) {
								storeHash(board, depth, 2, value, false, tempBestBoard, tempBestMove); //stores beta value of board
								break;
							}
						}
					}
				}
			}
			return value;
		}
	}
	
	public void storeHash(Board board, int depth, int flag, double value, boolean ancient, Board tempBestBoard, int[] tempBestMove) { //Don't forget you've included oppScore and compScore here!!!
		Transposition tableEntry = new Transposition(board.zobristKey, depth, flag, value, ancient, computerScore, opponentScore, tempBestBoard, tempBestMove); //I think that bestBoard doesn't belong here. Should be tempBestBoard...
		transpositionTable.put(board.zobristKey, tableEntry);
	}
	
	public double iterativeDeepening(Board board, int d) {
		bestBoard = board;
		double value = 0;
		for (int depth = 1; depth <= d; depth++) {
			value = alphaBeta(bestBoard, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		}
		return value;
	}
	
	public double heuristic(Board board) {
		double result = computerScore - opponentScore;
		return result;
	}
}
