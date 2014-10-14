package lt.vpranckaitis.tranformSchool.matrices;

/**
 * Base class for storage and manipulation of data of specific transformation
 * matrix
 * 
 * @author Vilius Pranckaitis
 */
public abstract class AbstractMatrix implements MatrixBase {
    protected int mType;
    protected float[] mMatrix;

    public AbstractMatrix() {
	mMatrix = new float[16];
	mType = TYPE_SIMPLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lt.vpranckaitis.tranformSchool.matrices.MatrixBase#getMatrix()
     */
    @Override
    public float[] getMatrix() {
	return mMatrix;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lt.vpranckaitis.tranformSchool.matrices.MatrixBase#getType()
     */
    @Override
    public int getType() {
	return mType;
    }

    /**
     * Copies matrix to this object
     * 
     * @param m
     *            Matrix to copy
     */
    protected void copyMatrix(float[] m) {
	for (int i = 0; i < 16; i++) {
	    mMatrix[i] = m[i];
	}
    }

    /**
     * Sets fields according to data object given
     * 
     * @param d
     *            Matrix data object
     */
    public abstract void setData(TransferableData d);

    /**
     * Returns the data object of this matrix
     * 
     * @return Matrix data
     */
    public abstract TransferableData getData();

    /**
     * Creates matrix object according to data object given
     * 
     * @param d
     *            Matrix data object
     * @return Matrix object
     */
    public abstract AbstractMatrix createInstance(TransferableData d);
}
