package game2048;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Grille extends JPanel{
	private ImageIcon icone2 = new ImageIcon("Ico/ico2.jpg");
	private ImageIcon icone4 = new ImageIcon("Ico/ico4.jpg"); 
	private ImageIcon icone8 = new ImageIcon("Ico/ico8.jpg"); 
	private ImageIcon icone16 = new ImageIcon("Ico/ico16.png"); 
	private ImageIcon icone32 = new ImageIcon("Ico/ico32.png"); 
	private ImageIcon icone64 = new ImageIcon("Ico/ico64.png");
	private ImageIcon icone128 = new ImageIcon("Ico/ico128.png");
	private ImageIcon icone256 = new ImageIcon("Ico/ico256.jpg");
	private ImageIcon icone512 = new ImageIcon("Ico/ico512.png");
	private ImageIcon icone1024 = new ImageIcon("Ico/ico1024.png");
	private ImageIcon icone2048 = new ImageIcon("Ico/ico2048.png");
	private ImageIcon iconeVide = new ImageIcon("Ico/icoVide.png");
	
	private int taille;
	private Case[][] cases;

	public Grille(int taille) {
		this.taille = taille;
		cases = new Case[taille][taille];
		setLayout(new GridLayout(taille, taille));
		addKeyListener(new InputKey());
		setFocusable(true);
		initGrid();
		creerIcones();
		
		updateDisplay();

		/*
		 * versLeHaut(); updateDisplay();
		 * 
		 * placerRand2(); updateDisplay();
		 * 
		 * versLeHaut(); updateDisplay();
		 */
	}

	private void initGrid() {
		for (int i = 0; i < taille; ++i) {
			for (int j = 0; j < taille; ++j) {
				cases[i][j] = new Case(0);
				add(cases[i][j]);
			}
		}
		placerRand2();
		placerRand2(); // PB MAJEUR: très souvent générés sur la meme ligne
	}

	private void placerRand2() {
		while (true) {
			Random generateur = new Random(System.currentTimeMillis());
			int i = generateur.nextInt(taille);
			int j = generateur.nextInt(taille);
			// on sort de while(true) dès que generateur a généré des
			// coordonnées d'une case vide
			if (cases[i][j].getEmpty() == true) {
				cases[i][j].setValeurCase(2);
				break;
			}
		}
	}

	private  void versLeHaut() {
		for (int i = 1; i < taille; ++i) {
			for (int j = 0; j < taille; ++j) { // parcours de la grille
				if (cases[i][j].getEmpty() == false) { // dès qu'on tombe sur
														// une case non vide
					int next;
					next = i;
					while (--next >= 0 && cases[next][j].getEmpty() == true) {
						// on la décale au max vers la gauche
						cases[next][j].setValeurCase(cases[i][j]
								.getValeurCase());
						cases[i--][j].setEmpty(true);
					}

					if (next < 0) {
						// du à la pré-décrémentation précédant la sortie du
						// while, il faut tester s'il n'y a pas out of range
						++next;
						++i;
					}

					if (cases[next][j].getValeurCase() == cases[i][j]
							.getValeurCase()) {
						cases[next][j].setValeurCase(cases[next][j]
								.getValeurCase()
								+ cases[next][j].getValeurCase());
						cases[i][j].setEmpty(true);
					}
				}
			}
		}
	}

	private void versLeBas() {
		for (int i = taille - 2; i >= 0; --i) {
			for (int j = 0; j < taille; ++j) { // parcours de la grille
				if (cases[i][j].getEmpty() == false) { // dès qu'on tombe sur
														// une case non vide
					int next;
					next = i;
					while (++next < taille && cases[next][j].getEmpty() == true) {
						// on la décale au max vers la gauche
						cases[next][j].setValeurCase(cases[i][j]
								.getValeurCase());
						cases[i++][j].setEmpty(true);
					}

					if (next >= taille) {
						// du à la pré-décrémentation précédant la sortie du
						// while, il faut tester s'il n'y a pas out of range
						--next;
						--i;
					}

					if (cases[next][j].getValeurCase() == cases[i][j]
							.getValeurCase()) {
						cases[next][j].setValeurCase(cases[next][j]
								.getValeurCase()
								+ cases[next][j].getValeurCase());
						cases[i][j].setEmpty(true);
					}
				}
			}
		}
	}

	private void versLaDroite() {
		for (int i = 0; i < taille; ++i) {
			for (int j = taille - 2; j >= 0; --j) { // parcours de la grille
				if (cases[i][j].getEmpty() == false) { // dès qu'on tombe sur
														// une case non vide
					int next;
					next = j;
					while (++next < taille && cases[i][next].getEmpty() == true) {
						// on la décale au max vers la droite
						cases[i][next].setValeurCase(cases[i][j]
								.getValeurCase());
						cases[i][j++].setEmpty(true);
					}

					if (next >= taille) {
						// du à la pré-incrémentation précédant la sortie du
						// while, il faut tester s'il n'y a pas out of range
						--next;
						--j;
					}

					if (cases[i][next].getValeurCase() == cases[i][j]
							.getValeurCase()) {
						cases[i][next].setValeurCase(cases[i][next]
								.getValeurCase()
								+ cases[i][next].getValeurCase());
						cases[i][j].setEmpty(true);
					}
				}
			}
		}
	}

	private void versLaGauche() {
		for (int i = 0; i < taille; ++i) {
			for (int j = 1; j < taille; ++j) { // parcours de la grille
				if (cases[i][j].getEmpty() == false) { // dès qu'on tombe sur
														// une case non vide
					int next;
					next = j;
					while (--next >= 0 && cases[i][next].getEmpty() == true) {
						// on la décale au max vers la gauche
						cases[i][next].setValeurCase(cases[i][j]
								.getValeurCase());
						cases[i][j--].setEmpty(true);
					}

					if (next < 0) {
						// du à la pré-décrémentation précédant la sortie du
						// while, il faut tester s'il n'y a pas out of range
						++next;
						++j;
					}

					if (cases[i][next].getValeurCase() == cases[i][j]
							.getValeurCase()) {
						cases[i][next].setValeurCase(cases[i][next]
								.getValeurCase()
								+ cases[i][next].getValeurCase());
						cases[i][j].setEmpty(true);
					}
				}
			}
		}
	}

	private  void updateDisplay() {
		/* test d'affichage */
		for (int i = 0; i < taille; ++i) {
			for (int j = 0; j < taille; ++j) {
				System.out.print(cases[i][j].getValeurCase());
			}
			System.out.println();
		}

		System.out.println();
	}
	
	private class InputKey extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keys = e.getKeyCode();
			if(keys == KeyEvent.VK_UP){
				versLeHaut();
				placerRand2();
				//updateDisplay();
				creerIcones();
			}
			else if(keys == KeyEvent.VK_DOWN){
				versLeBas();
				placerRand2();
				//updateDisplay();
				creerIcones();
			}
			else if(keys == KeyEvent.VK_LEFT){
				versLaGauche();
				placerRand2();
				//updateDisplay();
				creerIcones();
			}
			else if(keys == KeyEvent.VK_RIGHT){
				versLaDroite();
				placerRand2();
				//updateDisplay();
				creerIcones();
			}
		}
	}
	
	private void creerIcones() {
		for (int i = 0; i < taille; ++i)
			for (int j = 0; j < taille; ++j) {
				switch(cases[i][j].getValeurCase()){
				case 2 :
					cases[i][j].setIcon(icone2);
					break;
				case 4 :
					cases[i][j].setIcon(icone4);
					break;
				case 8 :
					cases[i][j].setIcon(icone8);
					break;
				case 16 :
					cases[i][j].setIcon(icone16);
					break;
				case 32 :
					cases[i][j].setIcon(icone32);
					break;
				case 64 :
					cases[i][j].setIcon(icone64);
					break;
				case 128 :
					cases[i][j].setIcon(icone128);
					break; 
				case 256 :
					cases[i][j].setIcon(icone256);
					break;
				case 512 :
					cases[i][j].setIcon(icone512);
					break;
				case 1024 :
					cases[i][j].setIcon(icone1024);
					break;
				case 2048 :
					cases[i][j].setIcon(icone2048);
					break;
					
				default :
					cases[i][j].setIcon(iconeVide);
					break;
				}
				cases[i][j].setPreferredSize(new Dimension(128, 128));
				this.add(cases[i][j]);
			}
	}

	public static void main(String[] args) {
		new Grille(4);
	}

}
