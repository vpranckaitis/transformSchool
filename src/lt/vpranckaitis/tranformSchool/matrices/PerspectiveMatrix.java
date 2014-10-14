package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;

public class PerspectiveMatrix extends AbstractMatrix {
    private float mFovy;
    private float mAspect;
    private float mZNear;
    private float mZFar;

    public PerspectiveMatrix() {
	this(30.0f, 1.0f, 1.0f, 10.0f);
    }

    public PerspectiveMatrix(float fovy, float aspect, float zNear, float zFar) {
	super();
	mType = TYPE_PERSPECTIVE;
	setData(fovy, aspect, zNear, zFar);
    }

    public PerspectiveMatrix(PerspectiveMatrix m) {
	super();
	mType = TYPE_PERSPECTIVE;
	setData(m);
    }

    public PerspectiveMatrix(TransferableData d) {
	super();
	mType = TYPE_PERSPECTIVE;
	setData(d);
    }

    public void setData(float fovy, float aspect, float zNear, float zFar) {
	mFovy = fovy;
	mAspect = aspect;
	mZNear = zNear;
	mZFar = zFar;
	mMatrix = Matrix.getPerspectiveM(fovy, aspect, zNear, zFar);
    }

    public void setData(PerspectiveMatrix m) {
	mFovy = m.mFovy;
	mAspect = m.mAspect;
	mZNear = m.mZNear;
	mZFar = m.mZFar;
	copyMatrix(m.mMatrix);
    }

    @Override
    public void setData(TransferableData d) {
	if (d.getClass().equals(Data.class)) {
	    Data dt = (Data) d;
	    mFovy = dt.fovy;
	    mAspect = dt.aspect;
	    mZNear = dt.zNear;
	    mZFar = dt.zFar;
	    copyMatrix(dt.matrix);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}

    }

    @Override
    public TransferableData getData() {
	return new Data(mFovy, mAspect, mZNear, mZFar, mType, mMatrix);
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new PerspectiveMatrix(d);
    }

    @Override
    public String toString() {
	String str = "Perspective(" + mFovy + ", " + mAspect + ", " + mZNear
		+ ", " + mZFar + ")";
	return str;
    }

    public static class Data extends TransferableData {
	public float fovy;
	public float aspect;
	public float zNear;
	public float zFar;

	private Data(float fovy, float aspect, float zNear, float zFar,
		int type, float[] m) {
	    this.fovy = fovy;
	    this.aspect = aspect;
	    this.zNear = zNear;
	    this.zFar = zFar;
	    this.type = type;
	    this.matrix = Arrays.copyOf(m, m.length);
	}

	public Data(float fovy, float aspect, float zNear, float zFar) {
	    this.fovy = fovy;
	    this.aspect = aspect;
	    this.zNear = zNear;
	    this.zFar = zFar;
	    this.type = TYPE_PERSPECTIVE;
	    this.matrix = Matrix.getPerspectiveM(fovy, aspect, zNear, zFar);
	}
    }

}
