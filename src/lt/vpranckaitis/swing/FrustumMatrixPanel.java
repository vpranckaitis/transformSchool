package lt.vpranckaitis.swing;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import lt.vpranckaitis.tranformSchool.matrices.FrustumMatrix;
import lt.vpranckaitis.tranformSchool.matrices.FrustumMatrix.Data;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

public class FrustumMatrixPanel extends AbstractMatrixPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7678778178638833923L;

    private static final double MIN = -100.0d;
    private static final double MAX = 100.0d;
    private static final double VALUE = 1.0d;
    private static final double STEP = 0.1d;

    private static final int PADDING = 5;

    private static final int N = 6;

    private JSpinner mSpin[]; // {Left, Right, Bottom, Top, Near, Far}

    public FrustumMatrixPanel() {
	super();
	setDataEventsDisabled(true);
	SpringLayout layout = new SpringLayout();
	this.setLayout(layout);
	mSpin = new JSpinner[N];
	mSpin[0] = new JSpinner(new SpinnerNumberModel(-VALUE, MIN, MAX, STEP));
	mSpin[1] = new JSpinner(new SpinnerNumberModel(VALUE, MIN, MAX, STEP));
	mSpin[2] = new JSpinner(new SpinnerNumberModel(-VALUE, MIN, MAX, STEP));
	mSpin[3] = new JSpinner(new SpinnerNumberModel(VALUE, MIN, MAX, STEP));
	mSpin[4] = new JSpinner(new SpinnerNumberModel(0.1d, 0.01d, MAX, STEP));
	mSpin[5] = new JSpinner(new SpinnerNumberModel(VALUE, 0.01d, MAX, STEP));

	String[] str = new String[] { "Left:", "Right:", "Bottom:", "Top:",
		"Near:", "Far:" };
	JLabel[] labels = new JLabel[N];
	// JLabel widest
	for (int i = 0; i < N; i++) {
	    labels[i] = new JLabel(str[i]);
	    this.add(labels[i]);
	    this.add(mSpin[i]);
	    mSpin[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, mSpin[i]
		    .getHeight()));
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
	float[] v = new float[N];
	for (int i = 0; i < N; i++) {
	    v[i] = (float) ((double) mSpin[i].getValue());
	}
	return new FrustumMatrix.Data(v[0], v[1], v[2], v[3], v[4], v[5]);
    }

    @Override
    public void setData(TransferableData d) {
	if (d.getClass().equals(FrustumMatrix.Data.class)) {
	    setDataEventsDisabled(true);
	    FrustumMatrix.Data data = (FrustumMatrix.Data) d;
	    mSpin[0].setValue((double) data.left);
	    mSpin[1].setValue((double) data.right);
	    mSpin[2].setValue((double) data.bottom);
	    mSpin[3].setValue((double) data.top);
	    mSpin[4].setValue((double) data.near);
	    mSpin[5].setValue((double) data.far);
	    setDataEventsDisabled(false);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

}
