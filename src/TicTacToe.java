import java.util.Scanner;

enum Type {
	MIN,
	MAX
}

public class TicTacToe extends MiniMax {
	public int[][] board = new int[3][3];
	public int turn;

	public TicTacToe(int depthMax, int depth, Type type) {
		this.type = type;
		this.depthMax = depthMax;
		this.depth = depth;
	}

	public void run() {
		Scanner in = new Scanner(System.in);

		printBoard();
		
		boolean first = true;

		while (score == 0) {
			turn = -1;
			move(in.nextInt() - 1);
			evaluateScore();
			printBoard();
			System.out.println();

			if (score != 0) break;

			turn = 1;
			if (first) {
				move(4);
				first = false;
			}
			else {
				move(getBestMove());
			}
			
			evaluateScore();
			printBoard();
		}

		in.close();
	}

	public void move(int move) {
		if (board[move / 3][move % 3] != 0) return;
		board[move / 3][move % 3] = turn;
	}

	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String value = Integer.toString((i * 3) + j + 1);
				if (board[i][j] == -1) value = "X";
				else if (board[i][j] == 1) value = "O";
				
				System.out.print("|" + value);
			}
			System.out.println("|");
		}
	}

	public void evaluateScore() {
		// Horizontal and vertical
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0) {
				score = board[i][0];
				return;
			}
			else if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != 0) {
				score = board[0][i];
				return;
			}
		}

		// Diagonals
		if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != 0) {
			score = board[0][0];
		}
		else if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != 0) {
			score = board[0][2];
		}
	}

	public void createChildren() {
		children.clear();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != 0) {
					continue;
				}
				
				TicTacToe x = new TicTacToe(depthMax, depth + 1, type == Type.MAX ? Type.MIN : Type.MAX);
				
				for (int a = 0; a < 3; a++) {
					for (int b = 0; b < 3; b++) {
						x.board[a][b] = board[a][b];
					}
				}

				x.turn = type == Type.MAX ? 1 : -1;
				x.parentMove = i * 3 + j;
				x.move(x.parentMove);
				evaluateScore();

				children.add(x);
			}
		}
	}

	public static void main(String[] args) {
		TicTacToe x = new TicTacToe(9, 1, Type.MAX);
		x.run();
	}
}
