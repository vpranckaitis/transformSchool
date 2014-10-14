package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;

public class ScaleMatrix extends AbstractMatrix {
    private float mX = 1.0f;
    private float mY = 1.0f;
    private float mZ = 1.0f;

    public ScaleMatrix() {
	this(1.0f, 1.0f, 1.0f);
    }

    public ScaleMatrix(float x, float y, float z) {
	super();
	mType = TYPE_SCALE;
	setData(x, y, z);
    }

    public ScaleMatrix(ScaleMatrix m) {
	super();
	mType = TYPE_SCALE;
	setData(m);
    }

    public ScaleMatrix(TransferableData d) {
	super();
	mType = TYPE_SCALE;
	setData(d);
    }

    public void setData(float x, float y, float z) {
	mX = x;
	mY = y;
	mZ = z;
	mMatrix = Matrix.getScaleM(x, y, z);
    }

    public void setData(ScaleMatrix m) {
	mX = m.mX;
	mY = m.mY;
	mZ = m.mZ;
	copyMatrix(m.mMatrix);
    }

    public void setData(TransferableData d) {
	if (d.getClass().equals(Data.class)) {
	    Data dt = (Data) d;
	    mX = dt.x;
	    mY = dt.y;
	    mZ = dt.z;
	    copyMatrix(dt.matrix);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

    public Data getData() {
	return new Data(mX, mY, mZ, mType, mMatrix);
    }

    @Override
    public String toString() {
	return "Scale(" + mX + ", " + mY + ", " + mZ + ")";
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	// TODO Auto-generated method stub
	return new ScaleMatrix(d);
    }

    public static class Data extends TransferableData {
	public float x;
	public float y;
	public float z;

	private Data(float x, float y, float z, int type, float[] matrix) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.type = type;
	    this.matrix = Arrays.copyOf(matrix, matrix.length);
	}

	public Data(float x, float y, float z) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.type = TYPE_SCALE;
	    this.matrix = Matrix.getScaleM(x, y, z);
	}
    }
}
