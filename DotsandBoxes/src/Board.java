import java.util.ArrayList;
import java.util.Random;

public class Board {
	int id;
	int rows;
	int columns;
	Coin[][] boardCoins;
	int counter = 1;
	boolean[][] wasHere;
	long[][][] zobristHashes;
	long zobristKey;
	ArrayList<ArrayList<Coin>> components = new ArrayList<ArrayList<Coin>>();
	boolean[][] wasHereAgain;
	
	Random rdm = new Random();
	
	public Board(Board clone) {
		this.id = clone.id;
		this.rows = clone.rows;
		this.columns = clone.columns;
		this.components = clone.components;
		this.zobristKey = clone.zobristKey;
		this.wasHere = clone.wasHere;
		this.wasHereAgain = clone.wasHereAgain;
		this.zobristHashes = clone.zobristHashes;
		this.boardCoins = new Coin[columns][rows];
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				boardCoins[i][j] = new Coin(i,j,clone.boardCoins[i][j].getString(0),clone.boardCoins[i][j].getString(1),
					clone.boardCoins[i][j].getString(2),clone.boardCoins[i][j].getString(3));
			}
		}
	}
	
	public Board(int rows, int columns, int id) {
		this.id = id;
		this.rows = rows;
		this.columns = columns;
		this.boardCoins = new Coin[columns][rows];
		this.wasHere = new boolean[columns][rows];
		this.wasHereAgain = new boolean[columns][rows];
		this.zobristHashes = new long[columns][rows][4];
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				boardCoins[i][j] = new Coin(i,j);
				for (int k = 0; k < 4; k++) {
					zobristHashes[i][j][k] = Math.abs(rdm.nextLong());
					zobristKey = zobristKey ^= zobristHashes[i][j][k];
				}
			}
		}
	}
	
	public long getZobristKey() {
		this.zobristKey = 0;
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				for (int k = 0; k < 4; k++) {
					if (boardCoins[i][j].getString(k) == 1) {
						zobristKey = zobristKey ^= zobristHashes[i][j][k];
					}
				}
			}
		}
		return zobristKey;
	}
	
	public void updateKey(int x, int y, int stringID) {
		zobristKey = zobristKey ^= zobristHashes[x][y][stringID];
	}
	
	public int getTotalDegree() {
		int totalDegree = 0;
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				totalDegree = totalDegree + boardCoins[i][j].degree;
			}
		}
		return totalDegree;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getColumns() {
		return this.columns;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int countChains(Coin coin) {
		if (coin.getDegree() > 2) {
			counter = 0;
			return counter;
		}
		if (wasHere[coin.getX()][coin.getY()]) {
			counter = counter - 1;
			return counter;
		}
		wasHere[coin.getX()][coin.getY()] = true;
		if (coin.getString(0) == 1 & coin.getY() < rows - 1) {
			counter++;
			countChains(boardCoins[coin.getX()][coin.getY() + 1]);
		}
		if (coin.getString(1) == 1 & coin.getX() < columns - 1) {
			counter++;
			countChains(boardCoins[coin.getX() + 1][coin.getY()]);
		}
		if (coin.getString(2) == 1 & coin.getY() > 0) {
			counter++;
			countChains(boardCoins[coin.getX()][coin.getY() - 1]);
		}
		if (coin.getString(3) == 1 & coin.getX() > 0) {
			counter++;
			countChains(boardCoins[coin.getX() - 1][coin.getY()]);
		}
		return counter;
	}
	
	public void getConnectedCoins(Coin coin, int x) { //THERE COULD BE DEEP COPYING PROBLEMS!!!
		//Coin cloneCoin = new Coin(coin.getX(),coin.getY(),coin.getString(0), coin.getString(1),coin.getString(2),coin.getString(3));
		if (wasHereAgain[coin.getX()][coin.getY()] == false) { //If not visited...
			wasHereAgain[coin.getX()][coin.getY()] = true;
			components.get(x).add(coin); 
			if (coin.getString(0) == 1) {
				if (coin.getY() + 1 < rows) {
					getConnectedCoins(boardCoins[coin.getX()][coin.getY() + 1],x);
				}	
			} 
			if (coin.getString(1) == 1){
				if (coin.getX() + 1 < columns) {
					getConnectedCoins(boardCoins[coin.getX() + 1][coin.getY()],x);
				}
			}
			if (coin.getString(2) == 1) {
				if (coin.getY() - 1 >= 0) {
					getConnectedCoins(boardCoins[coin.getX()][coin.getY() - 1],x);
				}
			}
			if (coin.getString(3) == 1){
				if (coin.getX() - 1 >= 0) {
					getConnectedCoins(boardCoins[coin.getX() - 1][coin.getY()],x);
				}
			}
		}
	}
	
	public void setComponents() {
		int x = 0;
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				if (wasHereAgain[i][j] == false) { 
					ArrayList<Coin> component = new ArrayList<Coin>();
					components.add(component);
					getConnectedCoins(boardCoins[i][j], x);
					x++;
				}
			}
		}
	}
}
