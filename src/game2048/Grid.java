package game2048;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grid extends JPanel {

	private enum Directions {
		UP, DOWN, LEFT, RIGHT;
	}

	private ImageIcon icon2 = new ImageIcon("Ico/ico2.jpg");
	private ImageIcon icon4 = new ImageIcon("Ico/ico4.jpg");
	private ImageIcon icon8 = new ImageIcon("Ico/ico8.jpg");
	private ImageIcon icon16 = new ImageIcon("Ico/ico16.png");
	private ImageIcon icon32 = new ImageIcon("Ico/ico32.png");
	private ImageIcon icon64 = new ImageIcon("Ico/ico64.png");
	private ImageIcon icon128 = new ImageIcon("Ico/ico128.png");
	private ImageIcon icon256 = new ImageIcon("Ico/ico256.jpg");
	private ImageIcon icon512 = new ImageIcon("Ico/ico512.png");
	private ImageIcon icon1024 = new ImageIcon("Ico/ico1024.png");
	private ImageIcon icon2048 = new ImageIcon("Ico/ico2048.png");
	private ImageIcon iconVide = new ImageIcon("Ico/icoVide.png");

	private boolean hasChanged;
	private int size;
	private Cell[][] cases;

	public Grid(int taille) {
		this.hasChanged = false;
		this.size = taille;
		cases = new Cell[taille][taille];
		setLayout(new GridLayout(taille, taille));
		addKeyListener(new InputKey());
		setFocusable(true);
		initGrid();
		refreshIcons();

		displayConsole();
	}

	private void initGrid() {
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				cases[i][j] = new Cell(i, j, 0);
				cases[i][j].setPreferredSize(new Dimension(128, 128));
				cases[i][j].setEnabled(false);
				;
				add(cases[i][j]);
			}
		}
		placeRandom();
		placeRandom(); // generations tres souvent sur la meme ligne !
	}

	private void placeRandom() {
		Cell sq = null;
		do {
			Random generateur = new Random(System.currentTimeMillis());
			int i = generateur.nextInt(size);
			int j = generateur.nextInt(size);

			if (cases[i][j].isEmpty()) {
				sq = cases[i][j];
			}
		} while (sq == null);

		sq.setValue(2);
	}

	private Cell getNextCell(Cell cell, Directions direction) {
		int nextLi = cell.getLi();
		int nextCo = cell.getCo();
		switch (direction) {
		case UP:
			--nextLi;
			break;
		case DOWN:
			++nextLi;
			break;
		case LEFT:
			--nextCo;
			break;
		case RIGHT:
			++nextCo;
			break;
		}
		Cell nextCell;
		try {
			nextCell = cases[nextLi][nextCo];
		} catch (IndexOutOfBoundsException e) {
			nextCell = null;
		}

		return nextCell;
	}

	private void moveAndMergeInto(Cell src, Cell dst) {
		if (dst.isEmpty()) {
			dst.setValue(src.getValue());
			src.setEmpty(true);
			hasChanged = true;
		} else if (dst.getValue() == src.getValue()) {
			dst.setValue(dst.getValue() * 2);
			src.setEmpty(true);
			hasChanged = true;
		}
	}

	/**
	 * Fournit la logique de parcours de la grille en fonction du mouvement
	 * effectue. Le parcours ligne est effectue dans la direction opposee a celle
	 * des mouvements vers le haut et le bas. Le parcours colonne est effectue dans
	 * la direction opposee a celle des mouvements vers la gauche et la droite.
	 * 
	 * @param it        L'indice de parcours.
	 * @param direction La direction dans laquelle le mouvement est effectue.
	 * @param isLine    Indique si it est un indice de ligne ou pas.
	 * 
	 * @return True si it a ete modifie et qu'il reste donc des Cell a deplacer.
	 */
	private boolean nextIteration(GridIterator it, Directions direction, boolean isLine) {
		try {
			if ((it.isLineIterator() && direction.equals(Directions.DOWN))
					|| (!it.isLineIterator() && direction.equals(Directions.RIGHT))) {
				it.prev();
			} else {
				it.next();
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}

	private void move(Directions direction) {
		/*
		 * Initialise les indices de parcours. Les indices sont initialises de sorte que
		 * les Cell en bordure de la grille qui ne bougeront pas, soient ignorees.
		 */
		GridIterator i, j;
		switch (direction) {
		case UP:
			i = new GridIterator(true, 1);
			break;
		case DOWN:
			i = new GridIterator(true, size - 2);
		default:
			i = new GridIterator(true, 0);
			break;
		}

		do {
			switch (direction) {
			case LEFT:
				j = new GridIterator(false, 1);
				break;
			case RIGHT:
				j = new GridIterator(false, size - 2);
				break;
			default:
				j = new GridIterator(false, 0);
				break;
			}

			do {
				Cell nextCell = getNextCell(cases[i.pos()][j.pos()], direction);
				if (nextCell != null) {
					moveAndMergeInto(cases[i.pos()][j.pos()], nextCell);
				}
			} while (nextIteration(j, direction, false));
		} while (nextIteration(i, direction, true));
	}

	private void displayConsole() {
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				System.out.print(cases[i][j].getValue());
			}
			System.out.println();
		}

		System.out.println();
	}

	private void refreshIcons() {
		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j) {
				switch (cases[i][j].getValue()) {
				case 2:
					cases[i][j].setIcon(icon2);
					break;
				case 4:
					cases[i][j].setIcon(icon4);
					break;
				case 8:
					cases[i][j].setIcon(icon8);
					break;
				case 16:
					cases[i][j].setIcon(icon16);
					break;
				case 32:
					cases[i][j].setIcon(icon32);
					break;
				case 64:
					cases[i][j].setIcon(icon64);
					break;
				case 128:
					cases[i][j].setIcon(icon128);
					break;
				case 256:
					cases[i][j].setIcon(icon256);
					break;
				case 512:
					cases[i][j].setIcon(icon512);
					break;
				case 1024:
					cases[i][j].setIcon(icon1024);
					break;
				case 2048:
					cases[i][j].setIcon(icon2048);
					break;

				default:
					cases[i][j].setIcon(iconVide);
					break;
				}
			}
	}

	private class GridIterator {
		private boolean isLineIterator;
		private int position;

		public GridIterator(boolean isLine, int pos) {
			isLineIterator = isLine;
			position = pos;
		}

		public boolean isLineIterator() {
			return isLineIterator;
		}

		public void next() {
			++position;
			if (position <= 0 || size <= position) {
				--position;
				throw new IndexOutOfBoundsException("Iterator outside of the grid.");
			}
		}

		public void prev() {
			--position;
			if (position <= 0 || size <= position) {
				++position;
				throw new IndexOutOfBoundsException("Iterator outside of the grid.");
			}
		}

		public int pos() {
			return position;
		}
	}

	private class InputKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keys = e.getKeyCode();
			if (keys == KeyEvent.VK_UP) {
				move(Directions.UP);
			} else if (keys == KeyEvent.VK_DOWN) {
				move(Directions.DOWN);
			} else if (keys == KeyEvent.VK_LEFT) {
				move(Directions.LEFT);
			} else if (keys == KeyEvent.VK_RIGHT) {
				move(Directions.RIGHT);
			}

			if (hasChanged) {
				placeRandom();
				refreshIcons();
				displayConsole();
			}
			hasChanged = false;
		}
	}
}
