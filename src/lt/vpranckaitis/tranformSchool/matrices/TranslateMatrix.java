package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix.Data;

public class TranslateMatrix extends AbstractMatrix {
    private float mX = 0.0f;
    private float mY = 0.0f;
    private float mZ = 0.0f;

    public TranslateMatrix() {
	this(0.0f, 0.0f, 0.0f);
    }

    public TranslateMatrix(float x, float y, float z) {
	super();
	mType = TYPE_TRANSLATE;
	setData(x, y, z);
    }

    public TranslateMatrix(TranslateMatrix m) {
	super();
	mType = TYPE_TRANSLATE;
	setData(m);
    }

    public TranslateMatrix(TransferableData d) {
	super();
	mType = TYPE_TRANSLATE;
	setData(d);
    }

    public void setData(float x, float y, float z) {
	mX = x;
	mY = y;
	mZ = z;
	mMatrix = Matrix.getTranslateM(x, y, z);
    }

    public void setData(TranslateMatrix m) {
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
	return "Translate(" + mX + ", " + mY + ", " + mZ + ")";
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new TranslateMatrix(d);
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
	    this.type = TYPE_TRANSLATE;
	    this.matrix = Matrix.getTranslateM(x, y, z);
	}
    }
}
