package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix.Data;

public class OrthoMatrix extends AbstractMatrix {
    private float mLeft;
    private float mRight;
    private float mTop;
    private float mBottom;
    private float mNear;
    private float mFar;

    public OrthoMatrix() {
	this(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
    }

    public OrthoMatrix(float left, float right, float bottom, float top,
	    float near, float far) {
	super();
	mType = TYPE_ORTHO;
	setData(left, right, bottom, top, near, far);
    }

    public OrthoMatrix(OrthoMatrix m) {
	super();
	mType = TYPE_ORTHO;
	setData(m);
    }

    public OrthoMatrix(TransferableData d) {
	super();
	mType = TYPE_ORTHO;
	setData(d);
    }

    public void setData(float left, float right, float bottom, float top,
	    float near, float far) {
	mLeft = left;
	mRight = right;
	mTop = top;
	mBottom = bottom;
	mNear = near;
	mFar = far;
	mMatrix = Matrix.getOrthoM(left, right, bottom, top, near, far);
    }

    public void setData(OrthoMatrix m) {
	mLeft = m.mLeft;
	mRight = m.mRight;
	mTop = m.mTop;
	mBottom = m.mBottom;
	mNear = m.mNear;
	mFar = m.mFar;
	copyMatrix(m.mMatrix);
    }

    public void setData(TransferableData d) {
	if (d.getClass().equals(Data.class)) {
	    Data dt = (Data) d;
	    mLeft = dt.left;
	    mRight = dt.right;
	    mTop = dt.top;
	    mBottom = dt.bottom;
	    mNear = dt.near;
	    mFar = dt.far;
	    copyMatrix(dt.matrix);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

    public Data getData() {
	return new Data(mLeft, mRight, mBottom, mTop, mNear, mFar, mType,
		mMatrix);
    }

    @Override
    public String toString() {
	return "Ortho(" + mLeft + ", " + mRight + ", " + mBottom + ", " + mTop
		+ ", " + mNear + ", " + mFar + ")";
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new OrthoMatrix(d);
    }

    public static class Data extends TransferableData {
	public float left;
	public float right;
	public float top;
	public float bottom;
	public float near;
	public float far;

	private Data(float left, float right, float bottom, float top,
		float near, float far, int type, float[] matrix) {
	    this.left = left;
	    this.right = right;
	    this.top = top;
	    this.bottom = bottom;
	    this.near = near;
	    this.far = far;
	    this.type = type;
	    this.matrix = Arrays.copyOf(matrix, matrix.length);
	}

	public Data(float left, float right, float bottom, float top,
		float near, float far) {
	    this.left = left;
	    this.right = right;
	    this.top = top;
	    this.bottom = bottom;
	    this.near = near;
	    this.far = far;
	    this.type = TYPE_ORTHO;
	    this.matrix = Matrix.getOrthoM(left, right, bottom, top, near, far);
	}
    }
}
