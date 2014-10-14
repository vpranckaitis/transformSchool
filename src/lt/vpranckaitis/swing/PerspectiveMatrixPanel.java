package lt.vpranckaitis.swing;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import lt.vpranckaitis.tranformSchool.matrices.PerspectiveMatrix;
import lt.vpranckaitis.tranformSchool.matrices.PerspectiveMatrix.Data;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

public class PerspectiveMatrixPanel extends AbstractMatrixPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7678778178638833923L;

    private static final double MIN = -100.0d;
    private static final double MAX = 100.0d;
    private static final double VALUE = 1.0d;
    private static final double STEP = 0.1d;

    private static final int PADDING = 5;

    private static final int N = 4;

    private JSpinner mSpin[]; // {fovy, aspect, zNear, zFar}

    public PerspectiveMatrixPanel() {
	super();
	setDataEventsDisabled(true);
	SpringLayout layout = new SpringLayout();
	this.setLayout(layout);
	mSpin = new JSpinner[N];
	mSpin[0] = new JSpinner(
		new SpinnerNumberModel(30.0, -360.0, 360.0, 1.0));
	mSpin[1] = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, STEP));
	mSpin[2] = new JSpinner(new SpinnerNumberModel(1.0, 0.01, MAX, STEP));
	mSpin[3] = new JSpinner(new SpinnerNumberModel(10.0, 0.01, MAX, STEP));

	String[] str = new String[] { "Fov:", "Aspect:", "Z Near:", "Z Far:" };
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
	return new PerspectiveMatrix.Data(v[0], v[1], v[2], v[3]);
    }

    @Override
    public void setData(TransferableData d) {
	if (d.getClass().equals(PerspectiveMatrix.Data.class)) {
	    setDataEventsDisabled(true);
	    PerspectiveMatrix.Data data = (PerspectiveMatrix.Data) d;
	    mSpin[0].setValue((double) data.fovy);
	    mSpin[1].setValue((double) data.aspect);
	    mSpin[2].setValue((double) data.zNear);
	    mSpin[3].setValue((double) data.zFar);
	    setDataEventsDisabled(false);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

}
