//This class list board and the possible moves for each of the pieces
public class PiecePosition {
	static String board[][] = { { "r", "k", "b", "q", "a", "b", "k", "r" },
			{ "p", "p", "p", "p", "p", "p", "p", "p" },
			{ " ", " ", " ", " ", " ", " ", " ", " " },
			{ " ", " ", " ", " ", " ", " ", " ", " " },
			{ " ", " ", " ", " ", " ", " ", " ", " " },
			{ " ", " ", " ", " ", " ", " ", " ", " " },
			{ "P", "P", "P", "P", "P", "P", "P", "P" },
			{ "R", "K", "B", "Q", "A", "B", "K", "R" } };
	static int kingPositionC, kingPositionL;

	public static String attainableMoves() {
		String list = "";
		for (int i = 0; i < 64; i++) {
			switch (board[i / 8][i % 8]) {
			case "P":
				list += feasiblePawnMove(i);
				break;
			case "R":
				list += feasibleRookMove(i);
				break;
			case "K":
				list += feasibleKnightMove(i);
				break;
			case "B":
				list += feasibleBishopMove(i);
				break;
			case "Q":
				list += feasibleQueenMove(i);
				break;
			case "A":
				list += feasibleKingMove(i);
				break;
			}
		}
		return list;// x1,y1,x2,y2,captured piece
	}

	public static String feasiblePawnMove(int i) {
		String list = "", oldPiece;
		int r = i / 8, c = i % 8;
		for (int j = -1; j <= 1; j += 2) {
			try {// capture
				if (Character.isLowerCase(board[r - 1][c + j].charAt(0))
						&& i >= 16) {
					oldPiece = board[r - 1][c + j];
					board[r][c] = " ";
					board[r - 1][c + j] = "P";
					if (kingSecure()) {
						list = list + r + c + (r - 1) + (c + j) + oldPiece;
					}
					board[r][c] = "P";
					board[r - 1][c + j] = oldPiece;
				}
			} catch (Exception e) {
			}
			try {// promotion && capture
				if (Character.isLowerCase(board[r - 1][c + j].charAt(0))
						&& i < 16) {
					String[] temp = { "Q", "R", "B", "K" };
					for (int k = 0; k < 4; k++) {
						oldPiece = board[r - 1][c + j];
						board[r][c] = " ";
						board[r - 1][c + j] = temp[k];
						if (kingSecure()) {
							// column1,column2,captured-piece,new-piece,P
							list = list + c + (c + j) + oldPiece + temp[k]
									+ "P";
						}
						board[r][c] = "P";
						board[r - 1][c + j] = oldPiece;
					}
				}
			} catch (Exception e) {
			}
		}
		try {// move one up
			if (" ".equals(board[r - 1][c]) && i >= 16) {
				oldPiece = board[r - 1][c];
				board[r][c] = " ";
				board[r - 1][c] = "P";
				if (kingSecure()) {
					list = list + r + c + (r - 1) + c + oldPiece;
				}
				board[r][c] = "P";
				board[r - 1][c] = oldPiece;
			}
		} catch (Exception e) {
		}
		try {// promotion && no capture
			if (" ".equals(board[r - 1][c]) && i < 16) {
				String[] temp = { "Q", "R", "B", "K" };
				for (int k = 0; k < 4; k++) {
					oldPiece = board[r - 1][c];
					board[r][c] = " ";
					board[r - 1][c] = temp[k];
					if (kingSecure()) {
						// column1,column2,captured-piece,new-piece,P
						list = list + c + c + oldPiece + temp[k] + "P";
					}
					board[r][c] = "P";
					board[r - 1][c] = oldPiece;
				}
			}
		} catch (Exception e) {
		}
		try {// move two up
			if (" ".equals(board[r - 1][c]) && " ".equals(board[r - 2][c])
					&& i >= 48) {
				oldPiece = board[r - 2][c];
				board[r][c] = " ";
				board[r - 2][c] = "P";
				if (kingSecure()) {
					list = list + r + c + (r - 2) + c + oldPiece;
				}
				board[r][c] = "P";
				board[r - 2][c] = oldPiece;
			}
		} catch (Exception e) {
		}
		return list;
	}

	public static String feasibleRookMove(int i) {
		String list = "", oldPiece;
		int r = i / 8, c = i % 8;
		int temp = 1;
		for (int j = -1; j <= 1; j += 2) {
			try {
				while (" ".equals(board[r][c + temp * j])) {
					oldPiece = board[r][c + temp * j];
					board[r][c] = " ";
					board[r][c + temp * j] = "R";
					if (kingSecure()) {
						list = list + r + c + r + (c + temp * j) + oldPiece;
					}
					board[r][c] = "R";
					board[r][c + temp * j] = oldPiece;
					temp++;
				}
				if (Character.isLowerCase(board[r][c + temp * j].charAt(0))) {
					oldPiece = board[r][c + temp * j];
					board[r][c] = " ";
					board[r][c + temp * j] = "R";
					if (kingSecure()) {
						list = list + r + c + r + (c + temp * j) + oldPiece;
					}
					board[r][c] = "R";
					board[r][c + temp * j] = oldPiece;
				}
			} catch (Exception e) {
			}
			temp = 1;
			try {
				while (" ".equals(board[r + temp * j][c])) {
					oldPiece = board[r + temp * j][c];
					board[r][c] = " ";
					board[r + temp * j][c] = "R";
					if (kingSecure()) {
						list = list + r + c + (r + temp * j) + c + oldPiece;
					}
					board[r][c] = "R";
					board[r + temp * j][c] = oldPiece;
					temp++;
				}
				if (Character.isLowerCase(board[r + temp * j][c].charAt(0))) {
					oldPiece = board[r + temp * j][c];
					board[r][c] = " ";
					board[r + temp * j][c] = "R";
					if (kingSecure()) {
						list = list + r + c + (r + temp * j) + c + oldPiece;
					}
					board[r][c] = "R";
					board[r + temp * j][c] = oldPiece;
				}
			} catch (Exception e) {
			}
			temp = 1;
		}
		return list;
	}

	public static String feasibleKnightMove(int i) {
		String list = "", oldPiece;
		int r = i / 8, c = i % 8;
		for (int j = -1; j <= 1; j += 2) {
			for (int k = -1; k <= 1; k += 2) {
				try {
					if (Character
							.isLowerCase(board[r + j][c + k * 2].charAt(0))
							|| " ".equals(board[r + j][c + k * 2])) {
						oldPiece = board[r + j][c + k * 2];
						board[r][c] = " ";
						if (kingSecure()) {
							list = list + r + c + (r + j) + (c + k * 2)
									+ oldPiece;
						}
						board[r][c] = "K";
						board[r + j][c + k * 2] = oldPiece;
					}
				} catch (Exception e) {
				}
				try {
					if (Character
							.isLowerCase(board[r + j * 2][c + k].charAt(0))
							|| " ".equals(board[r + j * 2][c + k])) {
						oldPiece = board[r + j * 2][c + k];
						board[r][c] = " ";
						if (kingSecure()) {
							list = list + r + c + (r + j * 2) + (c + k)
									+ oldPiece;
						}
						board[r][c] = "K";
						board[r + j * 2][c + k] = oldPiece;
					}
				} catch (Exception e) {
				}
			}
		}
		return list;
	}

	public static String feasibleBishopMove(int i) {
		String list = "", oldPiece;
		int r = i / 8, c = i % 8;
		int temp = 1;
		for (int j = -1; j <= 1; j += 2) {
			for (int k = -1; k <= 1; k += 2) {
				try {
					while (" ".equals(board[r + temp * j][c + temp * k])) {
						oldPiece = board[r + temp * j][c + temp * k];
						board[r][c] = " ";
						board[r + temp * j][c + temp * k] = "B";
						if (kingSecure()) {
							list = list + r + c + (r + temp * j)
									+ (c + temp * k) + oldPiece;
						}
						board[r][c] = "B";
						board[r + temp * j][c + temp * k] = oldPiece;
						temp++;
					}
					if (Character.isLowerCase(board[r + temp * j][c + temp * k]
							.charAt(0))) {
						oldPiece = board[r + temp * j][c + temp * k];
						board[r][c] = " ";
						board[r + temp * j][c + temp * k] = "B";
						if (kingSecure()) {
							list = list + r + c + (r + temp * j)
									+ (c + temp * k) + oldPiece;
						}
						board[r][c] = "B";
						board[r + temp * j][c + temp * k] = oldPiece;
					}
				} catch (Exception e) {
				}
				temp = 1;
			}
		}
		return list;
	}

	public static String feasibleQueenMove(int i) {
		String list = "", oldPiece;
		int r = i / 8, c = i % 8;
		int temp = 1;
		for (int j = -1; j <= 1; j++) {
			for (int k = -1; k <= 1; k++) {
				if (j != 0 || k != 0) {
					try {
						while (" ".equals(board[r + temp * j][c + temp * k])) {
							oldPiece = board[r + temp * j][c + temp * k];
							board[r][c] = " ";
							board[r + temp * j][c + temp * k] = "Q";
							if (kingSecure()) {
								list = list + r + c + (r + temp * j)
										+ (c + temp * k) + oldPiece;
							}
							board[r][c] = "Q";
							board[r + temp * j][c + temp * k] = oldPiece;
							temp++;
						}
						if (Character.isLowerCase(board[r + temp * j][c + temp
								* k].charAt(0))) {
							oldPiece = board[r + temp * j][c + temp * k];
							board[r][c] = " ";
							board[r + temp * j][c + temp * k] = "Q";
							if (kingSecure()) {
								list = list + r + c + (r + temp * j)
										+ (c + temp * k) + oldPiece;
							}
							board[r][c] = "Q";
							board[r + temp * j][c + temp * k] = oldPiece;
						}
					} catch (Exception e) {
					}
					temp = 1;
				}
			}
		}
		return list;
	}

	public static String feasibleKingMove(int i) {
		String list = "", oldPiece;
		int r = i / 8, c = i % 8;
		for (int j = 0; j < 9; j++) {
			if (j != 4) {
				try {
					if (Character.isLowerCase(board[r - 1 + j / 3][c - 1 + j
							% 3].charAt(0))
							|| " ".equals(board[r - 1 + j / 3][c - 1 + j % 3])) {
						oldPiece = board[r - 1 + j / 3][c - 1 + j % 3];
						board[r][c] = " ";
						board[r - 1 + j / 3][c - 1 + j % 3] = "A";
						int kingTemp = kingPositionC;
						kingPositionC = i + (j / 3) * 8 + j % 3 - 9;
						if (kingSecure()) {
							list = list + r + c + (r - 1 + j / 3)
									+ (c - 1 + j % 3) + oldPiece;
						}
						board[r][c] = "A";
						board[r - 1 + j / 3][c - 1 + j % 3] = oldPiece;
						kingPositionC = kingTemp;
					}
				} catch (Exception e) {
				}
			}
		}

		return list;
	}

	public static boolean kingSecure() {

		int temp = 1;
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
				try {
					while (" "
							.equals(board[kingPositionC / 8 + temp * i][kingPositionC
									% 8 + temp * j])) {
						temp++;
					}
					if ("b".equals(board[kingPositionC / 8 + temp * i][kingPositionC
							% 8 + temp * j])
							|| "q".equals(board[kingPositionC / 8 + temp * i][kingPositionC
									% 8 + temp * j])) {
						return false;
					}
				} catch (Exception e) {
				}
				temp = 1;
			}
		}
		// rook/queen
		for (int i = -1; i <= 1; i += 2) {
			try {
				while (" ".equals(board[kingPositionC / 8][kingPositionC % 8
						+ temp * i])) {
					temp++;
				}
				if ("r".equals(board[kingPositionC / 8][kingPositionC % 8
						+ temp * i])
						|| "q".equals(board[kingPositionC / 8][kingPositionC
								% 8 + temp * i])) {
					return false;
				}
			} catch (Exception e) {
			}
			temp = 1;
			try {
				while (" "
						.equals(board[kingPositionC / 8 + temp * i][kingPositionC % 8])) {
					temp++;
				}
				if ("r".equals(board[kingPositionC / 8 + temp * i][kingPositionC % 8])
						|| "q".equals(board[kingPositionC / 8 + temp * i][kingPositionC % 8])) {
					return false;
				}
			} catch (Exception e) {
			}
			temp = 1;
		}
		// knight
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
				try {
					if ("k".equals(board[kingPositionC / 8 + i][kingPositionC
							% 8 + j * 2])) {
						return false;
					}
				} catch (Exception e) {
				}
				try {
					if ("k".equals(board[kingPositionC / 8 + i * 2][kingPositionC
							% 8 + j])) {
						return false;
					}
				} catch (Exception e) {
				}
			}
		}
		// pawn
		if (kingPositionC >= 16) {
			try {
				if ("p".equals(board[kingPositionC / 8 - 1][kingPositionC % 8 - 1])) {
					return false;
				}
			} catch (Exception e) {
			}
			try {
				if ("p".equals(board[kingPositionC / 8 - 1][kingPositionC % 8 + 1])) {
					return false;
				}
			} catch (Exception e) {
			}
			// king
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i != 0 || j != 0) {
						try {
							if ("a".equals(board[kingPositionC / 8 + i][kingPositionC
									% 8 + j])) {
								return false;
							}
						} catch (Exception e) {
						}
					}
				}
			}
		}
		return true;
	}

	public static String sortMoves(String list) {
		int[] score = new int[list.length() / 5];
		for (int i = 0; i < list.length(); i += 5) {
			Algorithm.makeMove(list.substring(i, i + 5));
			score[i / 5] = -Evaluation.evaluate(-1, 0);
			Algorithm.undoMove(list.substring(i, i + 5));
		}
		String newListA = "", newListB = list;
		/*// Take in the branching factor from the user, in our case we are limiting it from 3 to 8  moves only
		i.e Ai has at max 8 legal moves at his disposal at each turn*/
		
		for (int i = 0; i < Math.min(Gui.Global.branchFac, list.length() / 5); i++) {
			int max = -1000000, maxLocation = 0;
			for (int j = 0; j < list.length() / 5; j++) {
				if (score[j] > max) {
					max = score[j];
					maxLocation = j;
				}
			}
			score[maxLocation] = -1000000;
			newListA += list.substring(maxLocation * 5, maxLocation * 5 + 5);
			newListB = newListB.replace(
					list.substring(maxLocation * 5, maxLocation * 5 + 5), "");
		}
		return newListA + newListB;
	}
}