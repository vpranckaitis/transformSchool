package lt.vpranckaitis.swing;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import lt.vpranckaitis.tranformSchool.matrices.RotateMatrix;
import lt.vpranckaitis.tranformSchool.matrices.RotateMatrix.Data;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

public class RotateMatrixPanel extends AbstractMatrixPanel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6093474054442068168L;

    private static final double MIN = -10.0d;
    private static final double MAX = 10.0d;
    private static final double VALUE = 0.0d;
    private static final double STEP = 0.1d;

    private static final String AXIS_X = "X";
    private static final String AXIS_Y = "Y";
    private static final String AXIS_Z = "Z";

    private static final int PADDING = 5;

    private static final int N = 4;

    private JSpinner mSpin[]; // {angle, x, y, z}
    private JComboBox<String> mCombo;

    private boolean mVector;

    public RotateMatrixPanel(boolean vector) {
	super();
	setDataEventsDisabled(true);
	SpringLayout layout = new SpringLayout();
	this.setLayout(layout);
	mVector = vector;
	mSpin = new JSpinner[N];

	mSpin[0] = new JSpinner(new SpinnerNumberModel(VALUE, -360.0d, 360.0d,
		1.0d));
	mSpin[1] = new JSpinner(new SpinnerNumberModel(VALUE, MIN, MAX, STEP));
	mSpin[2] = new JSpinner(new SpinnerNumberModel(VALUE, MIN, MAX, STEP));
	mSpin[3] = new JSpinner(new SpinnerNumberModel(1.0d, MIN, MAX, STEP));
	String[] str = new String[] { "Angle:", "X:", "Y:", "Z:", "Axis:" };

	mCombo = new JComboBox<>(new String[] { AXIS_X, AXIS_Y, AXIS_Z });

	if (mVector) {
	    JLabel[] labels = new JLabel[N];
	    for (int i = 0; i < N; i++) {
		this.add(mSpin[i]);
		mSpin[i].setMaximumSize(new Dimension(Integer.MAX_VALUE,
			mSpin[i].getHeight()));
		labels[i] = new JLabel(str[i]);
		this.add(labels[i]);
		layout.putConstraint(SpringLayout.WEST, labels[i], PADDING,
			SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, mSpin[i], PADDING,
			SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, mSpin[i], -PADDING,
			SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, labels[i],
			0, SpringLayout.VERTICAL_CENTER, mSpin[i]);
		mSpin[i].addChangeListener(this);

		layout.putConstraint(SpringLayout.NORTH, mSpin[0], PADDING,
			SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, this, PADDING,
			SpringLayout.SOUTH, mSpin[N - 1]);
	    }

	    for (int i = 1; i < N; i++) {
		layout.putConstraint(SpringLayout.NORTH, mSpin[i], PADDING,
			SpringLayout.SOUTH, mSpin[i - 1]);
	    }
	} else {
	    JLabel[] labels = new JLabel[2];
	    labels[0] = new JLabel(str[0]);
	    labels[1] = new JLabel(str[4]);
	    this.add(labels[0]);
	    this.add(labels[1]);
	    this.add(mSpin[0]);
	    mSpin[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, mSpin[0]
		    .getHeight()));
	    mSpin[0].addChangeListener(this);
	    layout.putConstraint(SpringLayout.WEST, labels[0], PADDING,
		    SpringLayout.WEST, this);
	    layout.putConstraint(SpringLayout.WEST, mSpin[0], PADDING,
		    SpringLayout.HORIZONTAL_CENTER, this);
	    layout.putConstraint(SpringLayout.EAST, mSpin[0], -PADDING,
		    SpringLayout.EAST, this);
	    layout.putConstraint(SpringLayout.VERTICAL_CENTER, labels[0], 0,
		    SpringLayout.VERTICAL_CENTER, mSpin[0]);
	    layout.putConstraint(SpringLayout.WEST, labels[1], PADDING,
		    SpringLayout.WEST, this);
	    layout.putConstraint(SpringLayout.WEST, mCombo, PADDING,
		    SpringLayout.HORIZONTAL_CENTER, this);
	    layout.putConstraint(SpringLayout.EAST, mCombo, -PADDING,
		    SpringLayout.EAST, this);
	    layout.putConstraint(SpringLayout.VERTICAL_CENTER, labels[1], 0,
		    SpringLayout.VERTICAL_CENTER, mCombo);
	    layout.putConstraint(SpringLayout.NORTH, mCombo, PADDING,
		    SpringLayout.SOUTH, mSpin[0]);
	    this.add(mCombo);
	    mCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, mCombo
		    .getHeight()));
	    mCombo.addActionListener(this);

	    layout.putConstraint(SpringLayout.NORTH, mSpin[0], PADDING,
		    SpringLayout.NORTH, this);
	    layout.putConstraint(SpringLayout.SOUTH, this, PADDING,
		    SpringLayout.SOUTH, mCombo);
	}
	setDataEventsDisabled(false);
    }

    @Override
    protected TransferableData createData() {
	if (mVector) {
	    float[] v = new float[N];
	    for (int i = 0; i < N; i++) {
		v[i] = (float) ((double) mSpin[i].getValue());
	    }
	    return new RotateMatrix.Data(v[0], v[1], v[2], v[3]);
	} else {
	    float v = (float) ((double) mSpin[0].getValue());
	    int selected = mCombo.getSelectedIndex();
	    return new RotateMatrix.Data(v,
		    (selected == 0 ? RotateMatrix.AXIS_X
			    : (selected == 1 ? RotateMatrix.AXIS_Y
				    : RotateMatrix.AXIS_Z)));
	}

    }

    @Override
    public void setData(TransferableData d) {
	if (d.getClass().equals(RotateMatrix.Data.class)) {
	    setDataEventsDisabled(true);
	    RotateMatrix.Data data = (RotateMatrix.Data) d;
	    mSpin[0].setValue((double) data.angle);
	    mSpin[1].setValue((double) data.x);
	    mSpin[2].setValue((double) data.y);
	    mSpin[3].setValue((double) data.z);
	    mCombo.setSelectedIndex(data.axis - 1);
	    setDataEventsDisabled(false);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }
}
