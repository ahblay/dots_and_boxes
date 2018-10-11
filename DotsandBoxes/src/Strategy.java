import java.util.ArrayList;
import java.util.Hashtable;

public class Strategy {
	int id;
	Board bestBoard;
	int[] bestMove = new int[3];
	Hashtable<Long, Transposition> transpositionTable = new Hashtable<Long, Transposition>();
	int computerScore = 0;
	int opponentScore = 0;
	 
	public Strategy(int playerID) {
		this.id = playerID;
	}
	 
	public double alphaBeta(Board board, int depth, double alpha, double beta, boolean maximizingPlayer) {
		//System.out.println("Computer Score: " + computerScore + " & Opponent Score: " + opponentScore + " & Depth: " + depth);
		//this isn't working b/c it sets external variable equal to null in each recursion. !!!Temp solution might be sketchy
		Board tempBestBoard = null; 
		//temp solution might be sketchy!!!!!
		int[] tempBestMove = {-1,-1,-1};
		double value;
		Transposition entry = transpositionTable.get(board.zobristKey);
		//if the board zobrist key in the TT equals the current board key
		if (entry != null) {
			if (entry.zobristKey == board.zobristKey) {	
				if (entry.depth >= depth) {
					if (entry.compScore == computerScore && entry.oppScore == opponentScore) {
						//Get bestBoard from TT
						tempBestBoard = entry.bestBoard; 
						tempBestMove = entry.bestMove;
						if (entry.flag == 0) {
							value = entry.value;
							//Somehow encode the score here...???
							return value;
						}
						if (entry.flag == 1 & entry.value >= alpha) {
							//alpha, beta are fns of the score...
							alpha = entry.value;
						}
						if (entry.flag == 2 & entry.value <= beta) {
							beta = entry.value;
						}
					}	
				}
			}
		}
		//getTotalDegree function is inefficient
		if (board.getTotalDegree() == 0) { 
			value = nimEval(board);
			//puts the value in the TT. Evaluates zobrist key of entire board. 0 means that we store the real value
			storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove); 
			return value;
		}
		else if (depth == 0) {
			value = heuristicEarly(board);
			//puts the value in the TT. Evaluates zobrist key of entire board. 0 means that we store the real value
			storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove); 
			return value;
		}
		else if (maximizingPlayer == true) {
			value = Double.NEGATIVE_INFINITY;
			ArrayList<int[]> moves = DBUtilities.orderMoves(board);
			for (int[] array : moves) {
				if (board.boardCoins[array[0]][array[1]].getString(array[2]) == 1) {
					//PROBLEM! No deep copying
					Board newBoard = DBUtilities.nextMove(board, board.boardCoins[array[0]][array[1]], array[2]); 
					if (newBoard.boardCoins[array[0]][array[1]].getDegree() == 0){
						computerScore = computerScore + 1;
						value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
						computerScore = computerScore - 1;
					} else {
						value = Math.max(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
					} 
					if (value > alpha) {
						alpha = value;
						tempBestBoard = newBoard;
						tempBestMove = array;
						//stores real value of board
						storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove);
					}
					if (beta <= alpha) {
						//In case of a TT-invoked alpha-change
						value = Math.max(value, alpha); 
						//In case of a TT-invoked alpha-change
						storeHash(board, depth, 1, value, false, tempBestBoard, tempBestMove);
						break;
					}
				}
			}
			//finally sets the proper best board. Worth rechecking later!!!!!
			bestBoard = tempBestBoard; 
			bestMove = tempBestMove;
			//System.out.println("************VALUE: " + value);
			return value;
		}
		else {
			value = Double.POSITIVE_INFINITY;
			ArrayList<int[]> moves = DBUtilities.orderMoves(board);
			for (int[] array : moves) {
				if (board.boardCoins[array[0]][array[1]].getString(array[2]) == 1) {
					Board newBoard = DBUtilities.nextMove(board, board.boardCoins[array[0]][array[1]], array[2]); 
					if (newBoard.boardCoins[array[0]][array[1]].getDegree() == 0){
						opponentScore = opponentScore + 1;
						value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, false));
						opponentScore = opponentScore - 1;
					} else {
						value = Math.min(value, alphaBeta(newBoard, depth - 1, alpha, beta, true));
					}
					if (value < beta) {
						beta = value;
						tempBestBoard = newBoard;
						tempBestMove = array;
						//stores real value of board
						storeHash(board, depth, 0, value, false, tempBestBoard, tempBestMove); 
					}
					if (beta <= alpha) {
						//In case of a TT-invoked beta-change
						value = Math.min(value, beta); 
						//In case of a TT-invoked beta-change
						storeHash(board, depth, 2, value, false, tempBestBoard, tempBestMove); 
						break;
					}
				}
			}
			return value;
		}
	}
	
	public void storeHash(Board board, int depth, int flag, double value, boolean ancient, Board tempBestBoard, int[] tempBestMove) {
		Transposition tableEntry = new Transposition(board.zobristKey, depth, flag, value, ancient, computerScore, opponentScore, tempBestBoard, tempBestMove); 
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
	
	public double nimEval(Board board) {
		double result = computerScore - opponentScore;
		return result;
	}
	
	public double heuristicEarly(Board board) {
		double result = computerScore - opponentScore;
		return result;
	}
}
