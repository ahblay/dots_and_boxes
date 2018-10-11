
public class Coin {
	int x;
	int y;
	int[] strings = new int[4];
	int degree;
	
	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		strings[0] = 1;
		strings[1] = 1;
		strings[2] = 1;
		strings[3] = 1;
		this.degree = strings[0] + strings[1] + strings[2] + strings[3];
	}
	
	public Coin(int x, int y, int a, int b, int c, int d) {
		this.x = x;
		this.y = y;
		strings[0] = a;
		strings[1] = b;
		strings[2] = c;
		strings[3] = d;
		this.degree = strings[0] + strings[1] + strings[2] + strings[3];
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getString(int x) {
		return this.strings[x];
	}
	public void setString(int x) {
		this.strings[x] = 0;
		this.degree = strings[0] + strings[1] + strings[2] + strings[3];
	}
	public int getDegree() {
		return this.degree;
	}
}
