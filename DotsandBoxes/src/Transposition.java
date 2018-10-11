
public class Transposition {

	long zobristKey;
	int depth;
	int flag;
	double value;
	boolean ancient;
	int compScore;
	int oppScore;
	Board bestBoard;
	int[] bestMove;
	
	public Transposition(long zobristKey, int depth, int flag, double value, boolean ancient, int compScore, int oppScore, Board bestBoard, int[] bestMove) {
		this.zobristKey = zobristKey;
		this.depth = depth;
		this.flag = flag;
		this.value = value;
		this.ancient = ancient;
		this.compScore = compScore;
		this.oppScore = oppScore;
		this.bestBoard = bestBoard;
		this.bestMove = bestMove;
	}
}
