package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix.Data;

public class SimpleMatrix extends AbstractMatrix {
    public SimpleMatrix() {
	this(Matrix.getIdentityM());
    }

    public SimpleMatrix(float[] m) {
	super();
	mType = TYPE_SIMPLE;
	setData(m);
    }

    public SimpleMatrix(float[][] m, boolean transpose) {
	super();
	mType = TYPE_SIMPLE;
	setData(m, transpose);
    }

    public SimpleMatrix(SimpleMatrix m) {
	super();
	mType = TYPE_SIMPLE;
	setData(m);
    }

    public SimpleMatrix(TransferableData d) {
	super();
	mType = TYPE_SIMPLE;
	setData(d);
    }

    public void setData(float m[]) {
	copyMatrix(m);
    }

    public void setData(float[][] m, boolean transpose) {
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		if (transpose) {
		    mMatrix[i * 4 + j] = m[i][j];
		} else {
		    mMatrix[j * 4 + i] = m[i][j];
		}
	    }
	}
    }

    public void setData(SimpleMatrix m) {
	copyMatrix(m.mMatrix);
    }

    public void setData(TransferableData d) {
	if (d.getClass().equals(Data.class)) {
	    Data dt = (Data) d;
	    copyMatrix(dt.matrix);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

    public Data getData() {
	return new Data(mType, mMatrix);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Custom(");
	for (int i = 0; i < 15; i++) {
	    sb.append(mMatrix[i]);
	    sb.append(", ");
	}
	sb.append(mMatrix[15]);
	sb.append(")");
	return sb.toString();
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new SimpleMatrix(d);
    }

    public static class Data extends TransferableData {
	private Data(int type, float[] matrix) {
	    this.matrix = Arrays.copyOf(matrix, matrix.length);
	    this.type = type;
	}

	public Data(float[] matrix) {
	    this.matrix = Arrays.copyOf(matrix, matrix.length);
	    this.type = TYPE_SIMPLE;
	}
    }
}
