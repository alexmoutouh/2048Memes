package game2048;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Cell extends JButton {
	private boolean isEmpty;
	private int li;
	private int co;
	private int value;

	public Cell(int l, int c, int val) {
		setPreferredSize(new Dimension(50, 50));
		li = l;
		co = c;
		value = val;
		if (value != 0)
			setEmpty(false);
		else
			setEmpty(true);
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean empty) {
		isEmpty = empty;
		if (isEmpty)
			setValue(0);
	}

	public int getLi() {
		return li;
	}

	public int getCo() {
		return co;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int val) {
		value = val;
		if (value != 0)
			setEmpty(false);
	}

	@Override
	public String toString() {
		return "Cell [li=" + li + ", co=" + co + ", value=" + value + "]";
	}
}
