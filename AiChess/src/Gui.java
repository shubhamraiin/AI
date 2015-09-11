/*
 Chess Programming Assignment 
By: Shubham Rai
scr130130
 */

//This class contains the Whole Gui of the project.

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

import java.util.*;

@SuppressWarnings({ "serial", "unused" })
public class Gui extends JPanel implements MouseListener, MouseMotionListener {

	public static class Global {
		public static int searchDepth;
		static int branchFac;
		static int playAggressive = -1;
		static boolean blockRook = false;
		static boolean blockKnight = false;
		static boolean blockBishop = false;
		static boolean blockQueen = false;

	}

	// 1 if human is white, 0 if human is black
	static int humanWhite = -1;

	public static void main(String[] args) {

		// get king's location
		while (!"A"
				.equals(PiecePosition.board[PiecePosition.kingPositionC / 8][PiecePosition.kingPositionC % 8])) {
			PiecePosition.kingPositionC++;
		}// get King's location
		while (!"a"
				.equals(PiecePosition.board[PiecePosition.kingPositionL / 8][PiecePosition.kingPositionL % 8])) {
			PiecePosition.kingPositionL++;
		}

		JFrame frame = new JFrame("AI Chess Project ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Gui gui = new Gui();
		frame.add(gui);
		frame.setSize(528, 550);
		frame.setVisible(true);
		frame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height
				/ 2 - frame.getSize().height / 2);
		System.out.println(PiecePosition.sortMoves(PiecePosition
				.attainableMoves()));

		// Take in Search Depth value from the user
		final String[] depth = { "2", "3", "4", "5", };
		String opt = (String) JOptionPane.showInputDialog(frame,
				"Choose Search Depth", "Search Depth",
				JOptionPane.QUESTION_MESSAGE, null, depth, depth[2]);
		Global.searchDepth = Integer.parseInt(opt);
		System.out.println(Global.searchDepth);

		// Take in Branching factor value from the user
		final String[] bf = { "3", "4", "5", "6", "7", "8" };
		String ch = (String) JOptionPane.showInputDialog(frame,
				"Choose Branching Factor", "Branching Factor",
				JOptionPane.QUESTION_MESSAGE, null, bf, bf[3]);
		Global.branchFac = Integer.parseInt(ch);
		System.out.println(Global.branchFac);

		// Different Playing Modes for Ai
		Object[] sel = { "Normal", "Aggresive" };
		Global.playAggressive = JOptionPane.showOptionDialog(frame,
				"How Should Ai Play", "Choice", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, sel, sel[0]);

		if (Global.playAggressive == 0) {
			Global.playAggressive = 0;

		} else
			Global.playAggressive = 1;

		// Limit opponent's moves for particular pieces for the initial 10 moves
		final String[] block = { "No", "Block Rook", "Block Knight",
				"Block Bishop", "Block Queen", };
		String blkopt = (String) JOptionPane.showInputDialog(frame,
				"Do you want to Block Opponent's movement of Pieces?",
				"Block Pieces", JOptionPane.QUESTION_MESSAGE, null, block,
				block[0]);

		switch (blkopt) {

		case "No":
			break;
		case "Block Rook":
			Global.blockRook = true;
			break;
		case "Block Knight":
			Global.blockKnight = true;
			break;
		case "Block Bishop":
			Global.blockBishop = true;

			break;
		case "Block Queen":
			Global.blockQueen = true;
			break;
		}

		// choose which side you want to play as
		Object[] choice = { "Black", "White" };
		humanWhite = JOptionPane.showOptionDialog(frame, "Choose a Side:",
				"Choice", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, choice, choice[1]);

		if (humanWhite == 0) {
			long startTime = System.currentTimeMillis();
			Algorithm.makeMove(Algorithm.alphaBeta(Global.searchDepth, 1000000,
					-1000000, "", 0));
			long endTime = System.currentTimeMillis();
			System.out.println("That took " + (endTime - startTime)
					+ " milliseconds");
			Algorithm.flipBoard();
			frame.repaint();

		}

		Algorithm.makeMove("7655 ");
		Algorithm.undoMove("7655 ");
		for (int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(PiecePosition.board[i]));
		}

	}

	static int initialMouseX, initialMouseY, newMouseX, newMouseY;
	static int squareSize = 65;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.white);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for (int i = 0; i < 64; i += 2) {

			g.setColor(new Color(70, 109, 186));
			g.fillRect((i % 8 + (i / 8) % 2) * squareSize,
					(i / 8) * squareSize, squareSize, squareSize);

			g.setColor(new Color(255, 220, 175));
			g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize,
					((i + 1) / 8) * squareSize, squareSize, squareSize);

		}

		/*
		 * Pieces:White/black pawn=P/p knight=K/k bishop=B/b rook (castle)=R/r
		 * Queen=Q/q King=A/a
		 */
		Image chessPiecesImage;
		if (humanWhite == 0)
			chessPiecesImage = new ImageIcon("chess.png").getImage();
		else
			chessPiecesImage = new ImageIcon("chess1.png").getImage();
		for (int i = 0; i < 64; i++) {
			int j = -1, k = -1;
			switch (PiecePosition.board[i / 8][i % 8]) {
			case "P":
				j = 5;
				k = 0;
				break;
			case "p":
				j = 5;
				k = 1;
				break;
			case "R":
				j = 2;
				k = 0;
				break;
			case "r":
				j = 2;
				k = 1;
				break;
			case "K":
				j = 4;
				k = 0;
				break;
			case "k":
				j = 4;
				k = 1;
				break;
			case "B":
				j = 3;
				k = 0;
				break;
			case "b":
				j = 3;
				k = 1;
				break;
			case "Q":
				j = 1;
				k = 0;
				break;
			case "q":
				j = 1;
				k = 1;
				break;
			case "A":
				j = 0;
				k = 0;
				break;
			case "a":
				j = 0;
				k = 1;
				break;
			}
			if (j != -1 && k != -1) {
				g.drawImage(chessPiecesImage, (i % 8) * squareSize, (i / 8)
						* squareSize, (i % 8 + 1) * squareSize, (i / 8 + 1)
						* squareSize, j * 64, k * 64, (j + 1) * 64,
						(k + 1) * 64, this);
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			// if inside the board
			initialMouseX = e.getX();
			initialMouseY = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			// if inside the board
			newMouseX = e.getX();
			newMouseY = e.getY();
			if (e.getButton() == MouseEvent.BUTTON1) {
				String dragMove;
				if (newMouseY / squareSize == 0
						&& initialMouseY / squareSize == 1
						&& "P".equals(PiecePosition.board[initialMouseY
								/ squareSize][initialMouseX / squareSize])) {
					// pawn promotion
					dragMove = ""
							+ initialMouseX
							/ squareSize
							+ newMouseX
							/ squareSize
							+ PiecePosition.board[newMouseY / squareSize][newMouseX
									/ squareSize] + "QP";
				} else {
					// regular move
					dragMove = ""
							+ initialMouseY
							/ squareSize
							+ initialMouseX
							/ squareSize
							+ newMouseY
							/ squareSize
							+ newMouseX
							/ squareSize
							+ PiecePosition.board[newMouseY / squareSize][newMouseX
									/ squareSize];
				}
				String userPosibilities = PiecePosition.attainableMoves();
				if (userPosibilities.replaceAll(dragMove, "").length() < userPosibilities
						.length()) {
					// if valid move
					Algorithm.makeMove(dragMove);
					Algorithm.flipBoard();

					Algorithm.makeMove(Algorithm.alphaBeta(Global.searchDepth,
							1000000, -1000000, "", 0));
					Algorithm.flipBoard();
					repaint();
					int king = -1, stale = -1;
					int checkcounter = 0;
					String kingStalemove;
					String listmate = PiecePosition.attainableMoves();
					if (!PiecePosition.kingSecure()) {
						king = 1;
						checkcounter += 1;
						if (listmate.length() == 0) {
							JOptionPane.showMessageDialog(null,
									"Game Over: Chack Mate!!");
							System.exit(50);

						}
					} else
						king = 0;
					if (king == 1) {
						if (checkcounter != 4)
							JOptionPane.showMessageDialog(null, "Check!!");
					}

					kingStalemove = PiecePosition
							.feasibleKingMove(PiecePosition.kingPositionC);
					if (kingStalemove.isEmpty())
						stale = 1;
					else
						stale = 0;
					if (stale == 1) {
						JOptionPane.showMessageDialog(null, "Stalemate!!");
					}

				}
			}
			/*Algorithm.makeMove("7655 ");
			Algorithm.undoMove("7655 ");
			for (int i = 0; i < 8; i++) {
				System.out.println(Arrays.toString(PiecePosition.board[i]));
			}*/

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	boolean mouseDragged;

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}