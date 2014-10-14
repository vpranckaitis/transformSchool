package lt.vpranckaitis.swing;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import lt.vpranckaitis.tranformSchool.matrices.TransferableData;
import lt.vpranckaitis.tranformSchool.matrices.TranslateMatrix;
import lt.vpranckaitis.tranformSchool.matrices.TranslateMatrix.Data;

public class TranslateMatrixPanel extends AbstractMatrixPanel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4450518228883259226L;

    private static final double MIN = -10.0d;
    private static final double MAX = 10.0d;
    private static final double VALUE = 0.0d;
    private static final double STEP = 0.1d;

    private static final int PADDING = 5;

    private static final int N = 3;

    private JSpinner mSpin[];

    public TranslateMatrixPanel() {
	super();
	setDataEventsDisabled(true);
	SpringLayout layout = new SpringLayout();
	this.setLayout(layout);
	mSpin = new JSpinner[N];
	String[] str = new String[] { "X:", "Y:", "Z:" };
	JLabel[] labels = new JLabel[N];
	for (int i = 0; i < N; i++) {
	    mSpin[i] = new JSpinner(new SpinnerNumberModel(VALUE, MIN, MAX,
		    STEP));
	    mSpin[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, mSpin[i]
		    .getHeight()));
	    this.add(mSpin[i]);
	    labels[i] = new JLabel(str[i]);
	    this.add(labels[i]);
	    layout.putConstraint(SpringLayout.WEST, labels[i], PADDING,
		    SpringLayout.WEST, this);
	    layout.putConstraint(SpringLayout.WEST, mSpin[i], PADDING,
		    SpringLayout.HORIZONTAL_CENTER, this);
	    layout.putConstraint(SpringLayout.EAST, mSpin[i], -PADDING,
		    SpringLayout.EAST, this);
	    layout.putConstraint(SpringLayout.VERTICAL_CENTER, labels[i], 0,
		    SpringLayout.VERTICAL_CENTER, mSpin[i]);
	    mSpin[i].addChangeListener(this);
	}

	for (int i = 1; i < N; i++) {
	    layout.putConstraint(SpringLayout.NORTH, mSpin[i], PADDING,
		    SpringLayout.SOUTH, mSpin[i - 1]);
	}

	layout.putConstraint(SpringLayout.NORTH, mSpin[0], PADDING,
		SpringLayout.NORTH, this);
	layout.putConstraint(SpringLayout.SOUTH, this, PADDING,
		SpringLayout.SOUTH, mSpin[N - 1]);

	setDataEventsDisabled(false);
    }

    @Override
    protected TransferableData createData() {
	float x = (float) ((double) mSpin[0].getValue());
	float y = (float) ((double) mSpin[1].getValue());
	float z = (float) ((double) mSpin[2].getValue());
	return new TranslateMatrix.Data(x, y, z);
    }

    @Override
    public void setData(TransferableData d) {
	if (d.getClass().equals(TranslateMatrix.Data.class)) {
	    setDataEventsDisabled(true);
	    TranslateMatrix.Data data = (TranslateMatrix.Data) d;
	    mSpin[0].setValue((double) data.x);
	    mSpin[1].setValue((double) data.y);
	    mSpin[2].setValue((double) data.z);
	    setDataEventsDisabled(false);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }
}
