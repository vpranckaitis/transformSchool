package lt.vpranckaitis.swing;

import java.awt.Dimension;
import java.security.InvalidParameterException;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix;
import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix.Data;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

public class LookAtMatrixPanel extends AbstractMatrixPanel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1452776614131233578L;

    private static final double MIN = -100.0d;
    private static final double MAX = 100.0d;
    private static final double VALUE = 0.0d;
    private static final double STEP = 0.1d;

    private static final int PADDING = 5;

    private static final int N = 9;

    private JSpinner mSpin[]; // {eyeX, eyeY, eyeZ, centerX, centerY, centerZ,
			      // upX, upY, upZ}

    public LookAtMatrixPanel() {
	super();
	setDataEventsDisabled(true);
	SpringLayout layout = new SpringLayout();
	this.setLayout(layout);
	mSpin = new JSpinner[N];
	String[] str = new String[] { "Eye X:", "Eye Y:", "Eye Z:",
		"Center X:", "Center Y:", "Center Z:", "Up X:", "Up Y:",
		"Up Z:" };
	JLabel[] labels = new JLabel[N];
	for (int i = 0; i < N; i++) {
	    mSpin[i] = new JSpinner(new SpinnerNumberModel(VALUE, MIN, MAX,
		    STEP));
	}

	for (int i = 0; i < N; i++) {
	    labels[i] = new JLabel(str[i]);
	    layout.putConstraint(SpringLayout.WEST, labels[i], PADDING,
		    SpringLayout.WEST, this);
	    layout.putConstraint(SpringLayout.VERTICAL_CENTER, labels[i], 0,
		    SpringLayout.VERTICAL_CENTER, mSpin[i]);
	    layout.putConstraint(SpringLayout.WEST, mSpin[i], PADDING,
		    SpringLayout.HORIZONTAL_CENTER, this);
	    layout.putConstraint(SpringLayout.EAST, mSpin[i], -PADDING,
		    SpringLayout.EAST, this);
	    this.add(mSpin[i]);
	    mSpin[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, mSpin[i]
		    .getHeight()));
	    this.add(labels[i]);
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
	return new LookAtMatrix.Data(v[0], v[1], v[2], v[3], v[4], v[5], v[6],
		v[7], v[8]);
    }

    @Override
    public void setData(TransferableData d) {
	if (d.getClass().equals(LookAtMatrix.Data.class)) {
	    setDataEventsDisabled(true);
	    LookAtMatrix.Data data = (LookAtMatrix.Data) d;
	    mSpin[0].setValue((double) data.eyeX);
	    mSpin[1].setValue((double) data.eyeY);
	    mSpin[2].setValue((double) data.eyeZ);
	    mSpin[3].setValue((double) data.centerX);
	    mSpin[4].setValue((double) data.centerY);
	    mSpin[5].setValue((double) data.centerZ);
	    mSpin[6].setValue((double) data.upX);
	    mSpin[7].setValue((double) data.upY);
	    mSpin[8].setValue((double) data.upZ);
	    setDataEventsDisabled(false);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}

    }

}
