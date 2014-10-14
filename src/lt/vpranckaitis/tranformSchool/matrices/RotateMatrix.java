package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix.Data;

public class RotateMatrix extends AbstractMatrix {
    private static final int NO_AXIS = 0;
    public static final int AXIS_X = 1;
    public static final int AXIS_Y = 2;
    public static final int AXIS_Z = 3;

    private int mAxis = NO_AXIS;
    private float mA = 0.0f;
    private float mX = 1.0f;
    private float mY = 0.0f;
    private float mZ = 0.0f;

    public RotateMatrix(boolean vector) {
	super();
	if (vector) {
	    this.mType = TYPE_ROTATE_VECTOR;
	    setData(0.0f, 1.0f, 0.0f, 0.0f);
	} else {
	    this.mType = TYPE_ROTATE_AXIS;
	    mAxis = AXIS_X;
	    setData(0.0f, 1.0f, 0.0f, 0.0f);
	}
    }

    public RotateMatrix(float angle, float x, float y, float z) {
	super();
	mType = TYPE_ROTATE_VECTOR;
	setData(angle, x, y, z);
    }

    public RotateMatrix(float angle, int axis) {
	super();
	mType = TYPE_ROTATE_AXIS;
	mAxis = axis;
	switch (axis) {
	case AXIS_X:
	    setData(angle, 1.0f, 0.0f, 0.0f);
	    break;
	case AXIS_Y:
	    setData(angle, 0.0f, 1.0f, 0.0f);
	    break;
	case AXIS_Z:
	    setData(angle, 0.0f, 0.0f, 1.0f);
	    break;
	default:
	    throw new InvalidParameterException("Invalid axis constant");
	}

    }

    public RotateMatrix(RotateMatrix m) {
	super();
	setData(m);
    }

    public RotateMatrix(TransferableData d) {
	super();
	setData(d);
    }

    public void setData(float angle, float x, float y, float z) {
	mA = angle;
	mX = x;
	mY = y;
	mZ = z;
	mMatrix = Matrix.getRotateM(angle, x, y, z);
    }

    public void setData(RotateMatrix m) {
	mType = m.mType;
	mAxis = m.mAxis;
	mA = m.mA;
	mX = m.mX;
	mY = m.mY;
	mZ = m.mZ;
	copyMatrix(m.mMatrix);
    }

    public void setData(TransferableData d) {
	if (d.getClass().equals(Data.class)) {
	    Data dt = (Data) d;
	    mType = dt.type;
	    mAxis = dt.axis;
	    mA = dt.angle;
	    mX = dt.x;
	    mY = dt.y;
	    mZ = dt.z;
	    copyMatrix(dt.matrix);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

    @Override
    public String toString() {
	if (mType == TYPE_ROTATE_AXIS) {
	    return "Rotate("
		    + mA
		    + ", "
		    + (mAxis == AXIS_X ? "AXIS_X" : (mAxis == AXIS_Y ? "AXIS_Y"
			    : "AXIS_Z")) + ")";
	} else {
	    return "Rotate(" + mA + ", " + mX + ", " + mY + ", " + mZ + ")";
	}
    }

    public Data getData() {
	return new Data(mA, mX, mY, mZ, mAxis, mType, mMatrix);
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new RotateMatrix(d);
    }

    public static class Data extends TransferableData {
	public int axis = NO_AXIS;
	public float angle;
	public float x;
	public float y;
	public float z;

	private Data(float angle, float x, float y, float z, int axis,
		int type, float[] matrix) {
	    this.axis = axis;
	    this.angle = angle;
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.type = type;
	    this.matrix = Arrays.copyOf(matrix, matrix.length);
	}

	public Data(float angle, int axis) {
	    this.axis = axis;
	    this.angle = angle;
	    this.x = (axis == AXIS_X ? 1.0f : 0.0f);
	    this.y = (axis == AXIS_Y ? 1.0f : 0.0f);
	    this.z = (axis == AXIS_Z ? 1.0f : 0.0f);
	    this.type = TYPE_ROTATE_AXIS;
	    this.matrix = Matrix.getRotateM(angle, x, y, z);
	    ;
	}

	public Data(float angle, float x, float y, float z) {
	    this.axis = NO_AXIS;
	    this.angle = angle;
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.type = TYPE_ROTATE_VECTOR;
	    this.matrix = Matrix.getRotateM(angle, x, y, z);
	    ;
	}
    }
}
