package lt.vpranckaitis.tranformSchool;

import java.awt.GraphicsDevice;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilitiesChooser;
import javax.media.opengl.GLCapabilitiesImmutable;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLException;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.tranformSchool.objects.TSAxes;
import lt.vpranckaitis.tranformSchool.objects.TSBox;
import lt.vpranckaitis.tranformSchool.objects.TSMesh;
import lt.vpranckaitis.tranformSchool.objects.TSPlane;
import lt.vpranckaitis.tranformSchool.objects.TSTransparentBox;
import lt.vpranckaitis.tranformSchool.objects.TSWireframeBox;

/**
 * OpenGL view which displays orthogonal projection from one of sides
 * 
 * @author Vilius Pranckaitis
 * 
 */
public class TSSideView extends TSGLView {
    private TSBox mBox;
    private TSAxes mAxes;
    private TSMesh mMesh;
    private TSPlane mPlane;
    private TSWireframeBox mVolume;
    private TSTransparentBox mVolumeBox;

    private float[] mDirM;

    private float[] mAxesPos;
    private float[] mVolM;
    private float[] mBoxM;
    private float[] mDynamicScreenM;

    public static final int AXIS_X = 1;
    public static final int AXIS_Y = 2;
    public static final int AXIS_Z = 3;

    public TSSideView(int direction) throws GLException {
	super();
	setDirection(direction);
    }

    public TSSideView(GLCapabilitiesImmutable capsReqUser,
	    GLCapabilitiesChooser chooser, GLContext shareWith,
	    GraphicsDevice device, int direction) throws GLException {
	super(capsReqUser, chooser, shareWith, device);
	setDirection(direction);
    }

    public TSSideView(GLCapabilitiesImmutable capsReqUser, GLContext shareWith,
	    int direction) throws GLException {
	super(capsReqUser, shareWith);
	setDirection(direction);
    }

    public TSSideView(GLCapabilitiesImmutable capsReqUser, int direction)
	    throws GLException {
	super(capsReqUser);
	setDirection(direction);
    }

    /**
     * Sets the direction of projection
     * 
     * The direction of view is selected so that the camera is directed along
     * the axis. For example, if selected {@value #AXIS_X}, the camera is
     * directed from to the positive values. If the negative value is given, the
     * camera is directed to the negative values of the axis.
     * 
     * @param d
     *            One of {@value #AXIS_X}, {@value #AXIS_Y}, {@value #AXIS_Z} or
     *            the negative value of any of these.
     */
    private void setDirection(int d) {
	mDirM = Matrix.getIdentityM();
	switch (d) {
	case -AXIS_X:
	    mDirM = Matrix.multiplyMM(Matrix.getLookAtM(0.0001f, 0.0f, 0.0f,
		    0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f), mDirM);
	    break;
	case AXIS_X:
	    mDirM = Matrix.multiplyMM(Matrix.getLookAtM(-0.0001f, 0.0f, 0.0f,
		    0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f), mDirM);
	    break;
	case -AXIS_Z:
	    mDirM = Matrix.multiplyMM(Matrix.getLookAtM(0.0f, 0.0f, 0.0001f,
		    0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f), mDirM);
	    break;
	case AXIS_Z:
	    mDirM = Matrix.multiplyMM(Matrix.getLookAtM(0.0f, 0.0f, -0.0001f,
		    0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f), mDirM);
	    break;
	case -AXIS_Y:
	    mDirM = Matrix.multiplyMM(Matrix.getLookAtM(0.0f, 0.0001f, 0.0f,
		    0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f), mDirM);
	    break;
	case AXIS_Y:
	    mDirM = Matrix.multiplyMM(Matrix.getLookAtM(0.0f, -0.0001f, 0.0f,
		    0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f), mDirM);
	    break;
	default:
	    break;
	}
	mDirM = Matrix.multiplyMM(
		Matrix.getOrthoM(-1.0f, 1.0f, -1.0f, 1.0f, -100.0f, 100.0f),
		mDirM);
    }

    /*
     * (non-Javadoc)
     * 
     * @see lt.vpranckaitis.tranformSchool.TSGLView#init(javax.media.opengl.
     * GLAutoDrawable)
     */
    @Override
    public void init(GLAutoDrawable drawable) {
	super.init(drawable);
	GL2 gl = drawable.getGL().getGL2();
	if (mBox == null) {
	    mBox = new TSBox(gl);
	}
	if (mAxes == null) {
	    mAxes = new TSAxes(gl);
	}
	if (mMesh == null) {
	    mMesh = new TSMesh(gl);
	}
	if (mPlane == null) {
	    mPlane = new TSPlane(gl);
	}
	if (mVolume == null) {
	    mVolume = new TSWireframeBox(gl);
	}
	if (mVolumeBox == null) {
	    mVolumeBox = new TSTransparentBox(gl);
	}
	mAxesPos = Matrix.getIdentityM();
	mAxesPos = Matrix.multiplyMM(Matrix.getScaleM(0.1f, 0.1f, 0.5f),
		mAxesPos);
	mAxesPos = Matrix.multiplyMM(Matrix.getTranslateM(0.85f, -0.85f, 0.0f),
		mAxesPos);

	mVolM = Matrix.getIdentityM();

	mDynamicScreenM = Matrix.getIdentityM();
    }

    private void checkMinMax(float[] v, float[] min, float[] max) {
	for (int i = 0; i < 3; i++) {
	    if (max[i] < v[i]) {
		max[i] = v[i];
	    } else if (min[i] > v[i]) {
		min[i] = v[i];
	    }
	}

    }

    private void calculateMatrices() {
	mVolM = Matrix.getIdentityM();
	mVolM = Matrix.multiplyMM(Matrix.getInvertedM(mP), mVolM);
	mVolM = Matrix.multiplyMM(Matrix.getInvertedM(mV), mVolM);
	mVolM = Matrix.multiplyMM(mDirM, mVolM);

	mBoxM = Matrix.multiplyMM(mDirM, mM);

	mDynamicScreenM = Matrix.getIdentityM();

	float[] max = new float[] { Float.NEGATIVE_INFINITY,
		Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY };
	float[] min = new float[] { Float.POSITIVE_INFINITY,
		Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY };

	float[][] vtest = new float[][] {
		new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
		new float[] { -1.0f, 1.0f, 1.0f, 1.0f },
		new float[] { -1.0f, -1.0f, 1.0f, 1.0f },
		new float[] { 1.0f, -1.0f, 1.0f, 1.0f },
		new float[] { 1.0f, -1.0f, -1.0f, 1.0f },
		new float[] { -1.0f, -1.0f, -1.0f, 1.0f },
		new float[] { -1.0f, 1.0f, -1.0f, 1.0f },
		new float[] { 1.0f, 1.0f, -1.0f, 1.0f }, };

	float[] v;
	for (int i = 0; i < vtest.length; i++) {
	    v = Matrix.normalizeV(Matrix.multiplyMV(mBoxM, vtest[i]));
	    checkMinMax(v, min, max);
	    v = Matrix.normalizeV(Matrix.multiplyMV(mVolM, vtest[i]));
	    checkMinMax(v, min, max);
	    v = Matrix.normalizeV(Matrix.multiplyMV(mDirM, vtest[i]));
	    checkMinMax(v, min, max);
	}

	float diffX = max[0] - min[0];
	float diffY = max[1] - min[1];
	float maxDiff = (diffX > diffY) ? diffX : diffY;
	float left = min[0] - (maxDiff - diffX) / 2;
	float right = max[0] + (maxDiff - diffX) / 2;
	float bottom = min[1] - (maxDiff - diffY) / 2;
	float top = max[1] + (maxDiff - diffY) / 2;
	mDynamicScreenM = Matrix.multiplyMM(mScreen,
		Matrix.getOrthoM(left, right, bottom, top, 1, -1));
	mDynamicScreenM = Matrix.multiplyMM(
		Matrix.getScaleM(0.95f, 0.95f, 0.95f), mDynamicScreenM);
    }

    /*
     * (non-Javadoc)
     * 
     * @see lt.vpranckaitis.tranformSchool.TSGLView#display(javax.media.opengl.
     * GLAutoDrawable)
     */
    @Override
    public void display(GLAutoDrawable drawable) {
	super.display(drawable);
	GL2 gl = drawable.getGL().getGL2();
	calculateMatrices();
	if (mHasAll) {
	    mPlane.draw(gl, Matrix.multiplyMM(mDynamicScreenM, mDirM));
	    gl.glDisable(GL2.GL_DEPTH_TEST);
	    mMesh.draw(gl, Matrix.multiplyMM(mDynamicScreenM, mDirM));
	    gl.glEnable(GL2.GL_DEPTH_TEST);
	    mBox.draw(gl, Matrix.multiplyMM(mDynamicScreenM, mBoxM));
	    // gl.glDisable(GL2.GL_DEPTH_TEST);
	    // gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);

	    // draw camera volume
	    gl.glEnable(GL2.GL_CULL_FACE);
	    gl.glCullFace(GL2.GL_BACK);
	    mVolumeBox.draw(gl, Matrix.multiplyMM(mDynamicScreenM, mVolM));
	    gl.glCullFace(GL2.GL_FRONT);
	    mVolumeBox.draw(gl, Matrix.multiplyMM(mDynamicScreenM, mVolM));
	    gl.glDisable(GL2.GL_CULL_FACE);
	    mVolume.draw(gl, Matrix.multiplyMM(mDynamicScreenM, mVolM));

	    mAxes.draw(
		    gl,
		    Matrix.multiplyMM(mAxesPos,
			    Matrix.multiplyMM(mScreen, mDirM)));
	} else {
	    mBox.draw(
		    gl,
		    Matrix.multiplyMM(mDynamicScreenM,
			    Matrix.multiplyMM(mDirM, mM)));
	}
    }

}
