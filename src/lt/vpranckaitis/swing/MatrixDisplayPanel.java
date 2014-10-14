package lt.vpranckaitis.swing;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MatrixDisplayPanel extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7441083240922283822L;

    private static final int N = 16;

    private JLabel[] mLabels;

    public MatrixDisplayPanel() {
	super();
	this.setLayout(new GridLayout(4, 4, 5, 5));
	mLabels = new JLabel[N];
	for (int i = 0; i < N; i++) {
	    mLabels[i] = new JLabel();
	    mLabels[i].setHorizontalAlignment(JLabel.CENTER);
	}
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		this.add(mLabels[i + j * 4]);
	    }
	}
	reset();
    }

    public void setMatrix(float[] m) {
	for (int i = 0; i < N; i++) {
	    mLabels[i].setText(Float.toString(m[i]));
	}
    }

    public void reset() {
	for (int i = 0; i < N; i++) {
	    mLabels[i].setText(Float.toString(0.0f));
	}
    }

}
