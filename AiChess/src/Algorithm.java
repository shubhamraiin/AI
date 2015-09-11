
/*This class contains the alpha beta pruning algorithm and the different strategies adopted in the chess engine as specified by the user
The strategy is to create an alpha-beta tree diagram which returns the best outcome
*/
public class Algorithm {

	/* 1234b represents row1,column2 moves to row3, column4 which captured b (a
	 space represents no capture)
*/
	public static String alphaBeta(int depth, int beta, int alpha, String move,
			int player) {
		// The list is returned in the form of 1234b##########
		String list = PiecePosition.attainableMoves();
		if (depth == 0 || list.length() == 0) {
			return move
					+ (Evaluation.evaluate(list.length(), depth) * (player * 2 - 1));
		}

		if (Gui.Global.playAggressive == 1) {
			char[] store = list.toCharArray();
			String prunemove = "";
			for (int i = 4; i < store.length; i += 5) {
				if (store[i] != ' ') {
					for (int j = (i - 4); j <= i; j++) {
						prunemove += store[j];
					}
				}

			}
			if (prunemove.length() != 0) {
				list = prunemove;
			}

		}

		int counterBlockR = 1;
		if (Gui.Global.blockRook == true && depth == Gui.Global.searchDepth
				&& counterBlockR <= 10) {
			char[] temp = list.toCharArray();
			String prunedmoves = "";
			for (int i = 0; i < temp.length; i += 5) {
				if (PiecePosition.board[Character.getNumericValue(temp[i])][Character
						.getNumericValue(temp[i + 1])] != "R") {
					for (int j = i; j <= i + 4; j++) {
						prunedmoves += temp[j];
					}
				}

			}

			if (prunedmoves.length() != 0) {
				list = prunedmoves;
			}
			counterBlockR = counterBlockR + 1;
		}

		int counterBlockK = 1;
		if (Gui.Global.blockKnight == true && depth == Gui.Global.searchDepth
				&& counterBlockK <= 10) {
			char[] temp = list.toCharArray();
			String prunedmoves = "";
			for (int i = 0; i < temp.length; i += 5) {

				if (PiecePosition.board[Character.getNumericValue(temp[i])][Character
						.getNumericValue(temp[i + 1])] != "K") {
					for (int j = i; j <= i + 4; j++) {
						prunedmoves += temp[j];
					}
				}

			}

			if (prunedmoves.length() != 0) {
				list = prunedmoves;
			}
			counterBlockK = counterBlockK + 1;
		}

		int counterBlockBishop = 1;
		if (Gui.Global.blockBishop == true && depth == Gui.Global.searchDepth
				&& counterBlockBishop <= 10) {
			char[] temp = list.toCharArray();
			String prunedmoves = "";
			for (int i = 0; i < temp.length; i += 5) {
				if (PiecePosition.board[Character.getNumericValue(temp[i])][Character
						.getNumericValue(temp[i + 1])] != "B") {
					for (int j = i; j <= i + 4; j++) {
						prunedmoves += temp[j];
					}
				}

			}

			if (prunedmoves.length() != 0) {
				list = prunedmoves;
			}
			counterBlockBishop = counterBlockBishop + 1;
		}
		int counterBlockQ = 1;
		if (Gui.Global.blockQueen == true && depth == Gui.Global.searchDepth
				&& counterBlockQ <= 10) {
			char[] temp = list.toCharArray();
			String prunedmoves = "";
			for (int i = 0; i < temp.length; i += 5) {
				if (PiecePosition.board[Character.getNumericValue(temp[i])][Character
						.getNumericValue(temp[i + 1])] != "Q") {
					for (int j = i; j <= i + 4; j++) {
						prunedmoves += temp[j];
					}
				}

			}

			if (prunedmoves.length() != 0) {
				list = prunedmoves;
			}
			counterBlockQ = counterBlockQ + 1;
		}

		list = PiecePosition.sortMoves(list);
		player = 1 - player;// either 1 or 0
		for (int i = 0; i < list.length(); i += 5) {
			makeMove(list.substring(i, i + 5));
			flipBoard();
			String returnString = alphaBeta(depth - 1, beta, alpha,
					list.substring(i, i + 5), player);
			int value = Integer.valueOf(returnString.substring(5));
			flipBoard();
			undoMove(list.substring(i, i + 5));
			if (player == 0) {
				if (value <= beta) {
					beta = value;
					if (depth == Gui.Global.searchDepth) {
						move = returnString.substring(0, 5);

					}
				}
			} else {
				if (value > alpha) {
					alpha = value;
					if (depth == Gui.Global.searchDepth) {
						move = returnString.substring(0, 5);

					}
				}
			}
			if (alpha >= beta) {
				if (player == 0) {
					return move + beta;

				} else {
					return move + alpha;
				}
			}
		}

		if (player == 0) {
			return move + beta;
		} else {
			return move + alpha;
		}

	}

	public static void flipBoard() {
		String temp;
		for (int i = 0; i < 32; i++) {
			int r = i / 8, c = i % 8;
			if (Character.isUpperCase(PiecePosition.board[r][c].charAt(0))) {
				temp = PiecePosition.board[r][c].toLowerCase();
			} else {
				temp = PiecePosition.board[r][c].toUpperCase();
			}
			if (Character.isUpperCase(PiecePosition.board[7 - r][7 - c]
					.charAt(0))) {
				PiecePosition.board[r][c] = PiecePosition.board[7 - r][7 - c]
						.toLowerCase();
			} else {
				PiecePosition.board[r][c] = PiecePosition.board[7 - r][7 - c]
						.toUpperCase();
			}
			PiecePosition.board[7 - r][7 - c] = temp;
		}
		int kingTemp = PiecePosition.kingPositionC;
		PiecePosition.kingPositionC = 63 - PiecePosition.kingPositionL;
		PiecePosition.kingPositionL = 63 - kingTemp;
	}

	public static void makeMove(String move) {
		if (move.charAt(4) != 'P') {
			PiecePosition.board[Character.getNumericValue(move.charAt(2))][Character
					.getNumericValue(move.charAt(3))] = PiecePosition.board[Character
					.getNumericValue(move.charAt(0))][Character
					.getNumericValue(move.charAt(1))];
			PiecePosition.board[Character.getNumericValue(move.charAt(0))][Character
					.getNumericValue(move.charAt(1))] = " ";
			if ("A".equals(PiecePosition.board[Character.getNumericValue(move
					.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
				PiecePosition.kingPositionC = 8
						* Character.getNumericValue(move.charAt(2))
						+ Character.getNumericValue(move.charAt(3));
			}
		} else {
			// if pawn promotion
			PiecePosition.board[1][Character.getNumericValue(move.charAt(0))] = " ";
			PiecePosition.board[0][Character.getNumericValue(move.charAt(1))] = String
					.valueOf(move.charAt(3));
		}
	}

	public static void undoMove(String move) {
		if (move.charAt(4) != 'P') {
			PiecePosition.board[Character.getNumericValue(move.charAt(0))][Character
					.getNumericValue(move.charAt(1))] = PiecePosition.board[Character
					.getNumericValue(move.charAt(2))][Character
					.getNumericValue(move.charAt(3))];
			PiecePosition.board[Character.getNumericValue(move.charAt(2))][Character
					.getNumericValue(move.charAt(3))] = String.valueOf(move
					.charAt(4));
			if ("A".equals(PiecePosition.board[Character.getNumericValue(move
					.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
				PiecePosition.kingPositionC = 8
						* Character.getNumericValue(move.charAt(0))
						+ Character.getNumericValue(move.charAt(1));
			}
		} else {
			// if pawn promotion
			PiecePosition.board[1][Character.getNumericValue(move.charAt(0))] = "P";
			PiecePosition.board[0][Character.getNumericValue(move.charAt(1))] = String
					.valueOf(move.charAt(2));
		}
	}

}