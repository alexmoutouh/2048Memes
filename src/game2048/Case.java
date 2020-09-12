package game2048;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Case extends JButton {
	private boolean isEmpty;
	private int valeurCase;

	public Case(int val) {
		setPreferredSize(new Dimension(50, 50));
		valeurCase = val;
		if(valeurCase != 0)
			setEmpty(false);
		else
			setEmpty(true);
	}
	
	public int getValeurCase(){
		return valeurCase;
	}
	
	public void setValeurCase(int val){
		valeurCase = val;
		if(valeurCase != 0)
			setEmpty(false);
	}
	
	public boolean getEmpty(){
		return isEmpty;
	}
	
	public void setEmpty(boolean empty){
		isEmpty = empty;
		if(isEmpty)
			setValeurCase(0);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
