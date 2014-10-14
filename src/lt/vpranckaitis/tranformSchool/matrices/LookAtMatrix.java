package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;
import java.util.Arrays;

import lt.vpranckaitis.opengl.Matrix;

public class LookAtMatrix extends AbstractMatrix {
    private float mEyeX;
    private float mEyeY;
    private float mEyeZ;
    private float mCenterX;
    private float mCenterY;
    private float mCenterZ;
    private float mUpX;
    private float mUpY;
    private float mUpZ;

    public LookAtMatrix() {
	this(0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public LookAtMatrix(float eyeX, float eyeY, float eyeZ, float centerX,
	    float centerY, float centerZ, float upX, float upY, float upZ) {
	super();
	mType = TYPE_LOOK_AT;
	setData(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    public LookAtMatrix(LookAtMatrix m) {
	super();
	mType = TYPE_LOOK_AT;
	setData(m);
    }

    public LookAtMatrix(TransferableData d) {
	super();
	mType = TYPE_LOOK_AT;
	setData(d);
    }

    public void setData(float eyeX, float eyeY, float eyeZ, float centerX,
	    float centerY, float centerZ, float upX, float upY, float upZ) {
	mEyeX = eyeX;
	mEyeY = eyeY;
	mEyeZ = eyeZ;
	mCenterX = centerX;
	mCenterY = centerY;
	mCenterZ = centerZ;
	mUpX = upX;
	mUpY = upY;
	mUpZ = upZ;
	mMatrix = Matrix.getLookAtM(eyeX, eyeY, eyeZ, centerX, centerY,
		centerZ, upX, upY, upZ);
    }

    public void setData(LookAtMatrix m) {
	mEyeX = m.mEyeX;
	mEyeY = m.mEyeY;
	mEyeZ = m.mEyeZ;
	mCenterX = m.mCenterX;
	mCenterY = m.mCenterY;
	mCenterZ = m.mCenterZ;
	mUpX = m.mUpX;
	mUpY = m.mUpY;
	mUpZ = m.mUpZ;
	copyMatrix(m.mMatrix);
    }

    public void setData(TransferableData d) {
	if (d.getClass().equals(Data.class)) {
	    Data dt = (Data) d;
	    mEyeX = dt.eyeX;
	    mEyeY = dt.eyeY;
	    mEyeZ = dt.eyeZ;
	    mCenterX = dt.centerX;
	    mCenterY = dt.centerY;
	    mCenterZ = dt.centerZ;
	    mUpX = dt.upX;
	    mUpY = dt.upY;
	    mUpZ = dt.upZ;
	    copyMatrix(dt.matrix);
	} else {
	    throw new InvalidParameterException("Required class: "
		    + Data.class.toString() + "; Given: " + d.getClass() + ".");
	}
    }

    public Data getData() {
	return new Data(mEyeX, mEyeY, mEyeZ, mCenterX, mCenterY, mCenterZ,
		mUpX, mUpY, mUpZ, mType, mMatrix);
    }

    @Override
    public String toString() {
	// TODO Auto-generated method stub
	return "LookAt(" + mEyeX + ", " + mEyeY + ", " + mEyeZ + ", "
		+ mCenterX + ", " + mCenterY + ", " + mCenterZ + ", " + mUpX
		+ ", " + mUpY + ", " + mUpZ + ")";
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new LookAtMatrix(d);
    }

    public static class Data extends TransferableData {
	public float eyeX;
	public float eyeY;
	public float eyeZ;
	public float centerX;
	public float centerY;
	public float centerZ;
	public float upX;
	public float upY;
	public float upZ;

	private Data(float eyeX, float eyeY, float eyeZ, float centerX,
		float centerY, float centerZ, float upX, float upY, float upZ,
		int type, float[] matrix) {
	    this.eyeX = eyeX;
	    this.eyeY = eyeY;
	    this.eyeZ = eyeZ;
	    this.centerX = centerX;
	    this.centerY = centerY;
	    this.centerZ = centerZ;
	    this.upX = upX;
	    this.upY = upY;
	    this.upZ = upZ;
	    this.type = type;
	    this.matrix = Arrays.copyOf(matrix, matrix.length);
	}

	public Data(float eyeX, float eyeY, float eyeZ, float centerX,
		float centerY, float centerZ, float upX, float upY, float upZ) {
	    this.eyeX = eyeX;
	    this.eyeY = eyeY;
	    this.eyeZ = eyeZ;
	    this.centerX = centerX;
	    this.centerY = centerY;
	    this.centerZ = centerZ;
	    this.upX = upX;
	    this.upY = upY;
	    this.upZ = upZ;
	    this.type = TYPE_LOOK_AT;
	    this.matrix = Matrix.getLookAtM(eyeX, eyeY, eyeZ, centerX, centerY,
		    centerZ, upX, upY, upZ);
	}
    }
}
