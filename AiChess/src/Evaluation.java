//This class contains all the evaluation functions and ratings for each movement

public class Evaluation {
	 
	// attribute from http://chessprogramming.wikispaces.com/Simplified+evaluation+function

	static int pawnBoard[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 50, 50, 50, 50, 50, 50, 50, 50 },
			{ 10, 10, 20, 30, 30, 20, 10, 10 }, { 5, 5, 10, 25, 25, 10, 5, 5 },
			{ 0, 0, 0, 20, 20, 0, 0, 0 }, { 5, -5, -10, 0, 0, -10, -5, 5 },
			{ 5, 10, 10, -20, -20, 10, 10, 5 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
	static int rookBoard[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 5, 10, 10, 10, 10, 10, 10, 5 }, { -5, 0, 0, 0, 0, 0, 0, -5 },
			{ -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 },
			{ -5, 0, 0, 0, 0, 0, 0, -5 }, { -5, 0, 0, 0, 0, 0, 0, -5 },
			{ 0, 0, 0, 5, 5, 0, 0, 0 } };
	static int knightBoard[][] = { { -50, -40, -30, -30, -30, -30, -40, -50 },
			{ -40, -20, 0, 0, 0, 0, -20, -40 },
			{ -30, 0, 10, 15, 15, 10, 0, -30 },
			{ -30, 5, 15, 20, 20, 15, 5, -30 },
			{ -30, 0, 15, 20, 20, 15, 0, -30 },
			{ -30, 5, 10, 15, 15, 10, 5, -30 },
			{ -40, -20, 0, 5, 5, 0, -20, -40 },
			{ -50, -40, -30, -30, -30, -30, -40, -50 } };
	static int bishopBoard[][] = { { -20, -10, -10, -10, -10, -10, -10, -20 },
			{ -10, 0, 0, 0, 0, 0, 0, -10 }, { -10, 0, 5, 10, 10, 5, 0, -10 },
			{ -10, 5, 5, 10, 10, 5, 5, -10 },
			{ -10, 0, 10, 10, 10, 10, 0, -10 },
			{ -10, 10, 10, 10, 10, 10, 10, -10 },
			{ -10, 5, 0, 0, 0, 0, 5, -10 },
			{ -20, -10, -10, -10, -10, -10, -10, -20 } };
	static int queenBoard[][] = { { -20, -10, -10, -5, -5, -10, -10, -20 },
			{ -10, 0, 0, 0, 0, 0, 0, -10 }, { -10, 0, 5, 5, 5, 5, 0, -10 },
			{ -5, 0, 5, 5, 5, 5, 0, -5 }, { 0, 0, 5, 5, 5, 5, 0, -5 },
			{ -10, 5, 5, 5, 5, 5, 0, -10 }, { -10, 0, 5, 0, 0, 0, 0, -10 },
			{ -20, -10, -10, -5, -5, -10, -10, -20 } };
	static int kingMidBoard[][] = { { -30, -40, -40, -50, -50, -40, -40, -30 },
			{ -30, -40, -40, -50, -50, -40, -40, -30 },
			{ -30, -40, -40, -50, -50, -40, -40, -30 },
			{ -30, -40, -40, -50, -50, -40, -40, -30 },
			{ -20, -30, -30, -40, -40, -30, -30, -20 },
			{ -10, -20, -20, -20, -20, -20, -20, -10 },
			{ 20, 20, 0, 0, 0, 0, 20, 20 }, { 20, 30, 10, 0, 0, 10, 30, 20 } };
	static int kingEndBoard[][] = { { -50, -40, -30, -20, -20, -30, -40, -50 },
			{ -30, -20, -10, 0, 0, -10, -20, -30 },
			{ -30, -10, 20, 30, 30, 20, -10, -30 },
			{ -30, -10, 30, 40, 40, 30, -10, -30 },
			{ -30, -10, 30, 40, 40, 30, -10, -30 },
			{ -30, -10, 20, 30, 30, 20, -10, -30 },
			{ -30, -30, 0, 0, 0, 0, -30, -30 },
			{ -50, -30, -30, -30, -30, -30, -30, -50 } };

	public static int evaluate(int list, int depth) {
		int counter = 0, material = evaluateMaterial();
		counter += evaluateAttack();
		counter += material;
		counter += evaluateMoveablitly(list, depth, material);
		counter += evaluatePositional(material);
		Algorithm.flipBoard();
		material = evaluateMaterial();
		counter -= evaluateAttack();
		counter -= material;
		counter -= evaluateMoveablitly(list, depth, material);
		counter -= evaluatePositional(material);
		Algorithm.flipBoard();
		return -(counter + depth * 50);
	}

	// Different weights are deducted from each piece depending on the playing modei.e Normal or Aggressive
	public static int evaluateAttack() {
		int counter = 0;
		int tempPositionC = PiecePosition.kingPositionC;
		for (int i = 0; i < 64; i++) {
			if (Gui.Global.playAggressive == 0) {
				switch (PiecePosition.board[i / 8][i % 8]) {
				case "P": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 64;
					}
				}
					break;
				case "R": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 400;
					}
				}
					break;
				case "K": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 250;
					}
				}
					break;
				case "B": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 300;
					}
				}
					break;
				case "Q": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 800;
					}
				}
					break;
				}
			} else {
				switch (PiecePosition.board[i / 8][i % 8]) {
				case "P": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 64;
					}
				}
					break;
				case "R": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 600;
					}
				}
					break;
				case "K": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 400;
					}
				}
					break;
				case "B": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 300;
					}
				}
					break;
				case "Q": {
					PiecePosition.kingPositionC = i;
					if (!PiecePosition.kingSecure()) {
						counter -= 1000;
					}
				}
					break;
				}

			}
		}
		PiecePosition.kingPositionC = tempPositionC;
		if (!PiecePosition.kingSecure()) {
			counter -= 200;
		}
		return counter / 2;
	}
	// Different weights are allocated to each piece depending on the playing modei.e Normal or Aggressive
	public static int evaluateMaterial() {
		int counter = 0, bishopCounter = 0;
		for (int i = 0; i < 64; i++) {
			if (Gui.Global.playAggressive == 0) {

				switch (PiecePosition.board[i / 8][i % 8]) {
				case "P":
					counter += 100;
					break;
				case "R":
					counter += 400;
					break;
				case "K":
					counter += 250;
					break;
				case "B":
					bishopCounter += 1;
					break;
				case "Q":
					counter += 800;
					break;
				}
			} else {
				switch (PiecePosition.board[i / 8][i % 8]) {
				case "P":
					counter += 100;
					break;
				case "R":
					counter += 600;
					break;
				case "K":
					counter += 400;
					break;
				case "B":
					bishopCounter += 1;
					break;
				case "Q":
					counter += 1000;
					break;
				}

			}

		}
		if (bishopCounter >= 1) {
			counter += 300 * bishopCounter;
		} else {
			if (bishopCounter == 1) {
				counter += 250;
			}
		}
		return counter;
	}

	@SuppressWarnings("unused")
	public static int evaluateMoveablitly(int listLength, int depth,
			int material) {
		int counter = 0;
		counter += listLength;// 5 pointer per valid move
		if (listLength == 0) {// current side is in checkmate or stalemate
			if (!PiecePosition.kingSecure()) {// if checkmate
				counter += -200000 * depth;

			} else {// if stalemate
				counter += -150000 * depth;

			}
		}
		return 0;
	}

	public static int evaluatePositional(int material) {
		int counter = 0;
		for (int i = 0; i < 64; i++) {
			switch (PiecePosition.board[i / 8][i % 8]) {
			case "P":
				counter += pawnBoard[i / 8][i % 8];
				break;
			case "R":
				counter += rookBoard[i / 8][i % 8];
				break;
			case "K":
				counter += knightBoard[i / 8][i % 8];
				break;
			case "B":
				counter += bishopBoard[i / 8][i % 8];
				break;
			case "Q":
				counter += queenBoard[i / 8][i % 8];
				break;
			case "A":
				if (material >= 1750) {
					counter += kingMidBoard[i / 8][i % 8];
					counter += PiecePosition.feasibleKingMove(
							PiecePosition.kingPositionC).length() * 10;
				} else {
					counter += kingEndBoard[i / 8][i % 8];
					counter += PiecePosition.feasibleKingMove(
							PiecePosition.kingPositionC).length() * 30;
				}
				break;
			}
		}
		return counter;
	}
}