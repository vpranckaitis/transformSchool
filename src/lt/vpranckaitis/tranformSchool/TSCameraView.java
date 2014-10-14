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

/**
 * OpenGL view to display scene according to camera position.
 * 
 * @author Vilius Pranckaitis
 * 
 */
public class TSCameraView extends TSGLView {
    private TSBox mBox;
    private TSAxes mAxes;
    private TSMesh mMesh;
    private TSPlane mPlane;

    private float[] mAxesOrient;
    private float[] mAxesPos;

    public TSCameraView() throws GLException {
	super();
    }

    public TSCameraView(GLCapabilitiesImmutable capsReqUser,
	    GLCapabilitiesChooser chooser, GLContext shareWith,
	    GraphicsDevice device) throws GLException {
	super(capsReqUser, chooser, shareWith, device);
    }

    public TSCameraView(GLCapabilitiesImmutable capsReqUser, GLContext shareWith)
	    throws GLException {
	super(capsReqUser, shareWith);
    }

    public TSCameraView(GLCapabilitiesImmutable capsReqUser) throws GLException {
	super(capsReqUser);
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
	mAxesOrient = Matrix.getIdentityM();
	// mAxesPos = Matrix.multiplyMM(Matrix.getTranslateM(0.85f, -0.85f,
	// 0.0f), mAxesPos);
	float[] invertAxis = Matrix.getIdentityM();
	for (int i = 0; i < 11; i += 5) {
	    if (mP[i] < 0) {
		invertAxis[i] = -1;
	    }
	}
	mAxesOrient = Matrix.multiplyMM(invertAxis, mAxesOrient);
	mAxesPos = Matrix.getScaleM(0.1f, 0.1f, 0.1f);
	mAxesPos = Matrix.multiplyMM(Matrix.getTranslateM(0.85f, -0.85f, 0.0f),
		mAxesPos);

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
	if (mHasAll) {
	    mPlane.draw(gl, Matrix.multiplyMM(mScreen, mVP));
	    gl.glDisable(GL2.GL_DEPTH_TEST);
	    mMesh.draw(gl, Matrix.multiplyMM(mScreen, mVP));
	    gl.glEnable(GL2.GL_DEPTH_TEST);
	    mBox.draw(gl, Matrix.multiplyMM(mScreen, mMVP));
	    // gl.glDisable(GL2.GL_DEPTH_TEST);
	    gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);

	    // axes computation
	    float[] ma = Matrix.getIdentityM();
	    float[][] v = new float[][] {
		    new float[] { 0.0f, 0.0f, 0.0f, 1.0f },
		    new float[] { 1.0f, 0.0f, 0.0f, 1.0f },
		    new float[] { 0.0f, 1.0f, 0.0f, 1.0f },
		    new float[] { 0.0f, 0.0f, 1.0f, 1.0f } };
	    for (int i = 0; i < v.length; i++) {
		v[i] = Matrix.multiplyMV(mV, v[i]);
	    }
	    for (int i = v.length - 1; i >= 0; i--) {
		v[i][0] -= v[0][0];
		v[i][1] -= v[0][1];
		v[i][2] -= v[0][2];
	    }
	    for (int i = 1; i < v.length; i++) {
		float r_length = 1 / Matrix.length(v[i][0], v[i][1], v[i][2]);
		v[i][0] *= r_length;
		v[i][1] *= r_length;
		v[i][2] *= r_length;
		// System.out.println(Matrix.length(v[i][0], v[i][1], v[i][2]));
	    }
	    /*
	     * System.out.println(Arrays.toString(mV));
	     * System.out.println(Arrays.toString(mP));
	     * System.out.println(Arrays.toString(mMVP));
	     * System.out.println(Arrays.toString(Matrix.multiplyMV(mV, new
	     * float[]{1.0f, 1.0f, 1.0f, 1.0f})));
	     * System.out.println(Arrays.toString(Matrix.multiplyMV(mV, new
	     * float[]{-1.0f, -1.0f, -1.0f, -1.0f})));
	     * System.out.println(Arrays.toString(Matrix.multiplyMV(mMVP, new
	     * float[]{1.0f, 1.0f, 1.0f, 1.0f})));
	     * System.out.println(Arrays.toString(Matrix.multiplyMV(mMVP, new
	     * float[]{-1.0f, -1.0f, -1.0f, -1.0f})));
	     */
	    // System.out.println(Arrays.toString(v));
	    // System.out.println(Arrays.toString(mV));
	    for (int i = 0; i < 3; i++) {
		ma[i] = v[1][i];
		ma[i + 4] = v[2][i];
		ma[i + 8] = v[3][i];
	    }
	    ma = Matrix.multiplyMM(mAxesOrient, ma);
	    ma = Matrix.multiplyMM(mScreen, ma);
	    ma = Matrix.multiplyMM(mAxesPos, ma);

	    // draw axes
	    mAxes.draw(gl, ma);
	    // gl.glEnable(GL2.GL_DEPTH_TEST);
	} else {
	    mBox.draw(gl, Matrix.multiplyMM(mScreen, mMVP));
	}
    }

}
