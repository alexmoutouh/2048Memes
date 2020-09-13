package game2048;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Fenetre2048 extends JFrame {

	public Fenetre2048() {
		super("2048");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"Voulez-vous vraiment quitter la partie en cours?",
						"Quitter?", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setLayout(new BorderLayout());
		Grid grid = new Grid(4);
		add(grid);
		//JButton enterButton = new JButton( "Enter" );
		//add(enterButton, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new Fenetre2048();
	}

}
