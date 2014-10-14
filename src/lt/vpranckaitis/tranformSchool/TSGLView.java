package lt.vpranckaitis.tranformSchool;

import java.awt.GraphicsDevice;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilitiesChooser;
import javax.media.opengl.GLCapabilitiesImmutable;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLCanvas;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.tranformSchool.objects.TSBox;

/**
 * Class for showing OpenGL view
 * 
 * @author Vilius Pranckaitis
 */
public class TSGLView extends GLCanvas implements GLEventListener {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5167836150794246327L;

    protected float[] mScreen;
    protected float[] mMVP;
    protected float[] mM;
    protected float[] mV;
    protected float[] mP;
    protected float[] mVP;

    protected boolean mHasAll;

    public TSGLView() throws GLException {
	super();
	this.addGLEventListener(this);
	init();
    }

    public TSGLView(GLCapabilitiesImmutable capsReqUser,
	    GLCapabilitiesChooser chooser, GLContext shareWith,
	    GraphicsDevice device) throws GLException {
	super(capsReqUser, chooser, shareWith, device);
	this.addGLEventListener(this);
	init();
    }

    public TSGLView(GLCapabilitiesImmutable capsReqUser, GLContext shareWith)
	    throws GLException {
	super(capsReqUser, shareWith);
	this.addGLEventListener(this);
	init();
    }

    public TSGLView(GLCapabilitiesImmutable capsReqUser) throws GLException {
	super(capsReqUser);
	this.addGLEventListener(this);
	init();
    }

    private void init() {
	mMVP = Matrix.getIdentityM();
	mScreen = Matrix.getIdentityM();
	mM = Matrix.getIdentityM();
	mV = Matrix.getIdentityM();
	mP = Matrix.getIdentityM();
	mVP = Matrix.getIdentityM();
	mHasAll = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable
     * )
     */
    @Override
    public void init(GLAutoDrawable drawable) {
	GL2 gl = drawable.getGL().getGL2();
	gl.glEnable(GL2.GL_DEPTH_TEST);
	gl.glEnable(GL2.GL_BLEND);
	gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	gl.glClearColor(0, 0, 0, 1.0f);
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.media.opengl.GLEventListener#dispose(javax.media.opengl.GLAutoDrawable
     * )
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable
     * )
     */
    @Override
    public void display(GLAutoDrawable drawable) {
	GL2 gl = drawable.getGL().getGL2();
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable
     * , int, int, int, int)
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
	    int height) {
	mScreen = Matrix.getScaleM((float) height / (float) width, 1, 1);
    }

    /**
     * Sets Model-View-Projection matrix.
     * 
     * The class displays objects according to the matrix given.
     * 
     * If available, use {@link #setMVPMatrix(float[], float[], float[])}
     * instead of this.
     * 
     * @param m
     *            Model-View-Projection matrix
     */
    public void setMVPMatrix(float[] m) {
	for (int i = 0; i < 16; i++) {
	    mMVP[i] = m[i];
	}
	mHasAll = false;
    }

    /**
     * Sets Model, View and Projection matrices.
     * 
     * The class displays objects according to the matrices given.
     * 
     * @param mM
     *            Model matrix
     * @param mV
     *            View matrix
     * @param mP
     *            Projection matrix
     */
    public void setMVPMatrix(float[] mM, float[] mV, float[] mP) {
	for (int i = 0; i < 16; i++) {
	    this.mM[i] = mM[i];
	    this.mV[i] = mV[i];
	    this.mP[i] = mP[i];
	}
	mVP = Matrix.multiplyMM(mP, mV);
	mMVP = Matrix.multiplyMM(mVP, mM);
	mHasAll = true;
    }

    /**
     * Loads shader from shader code
     * 
     * @param gl
     *            GL object
     * @param type
     *            The type of the shaders, {@link GL2ES2#GL_VERTEX_SHADER} or
     *            {@link GL2ES2#GL_FRAGMENT_SHADER}
     * @param shaderCode
     *            The GLSL code to compile
     * @return The reference value of shader object
     */
    public static int loadShader(GL2 gl, int type, String shaderCode) {
	// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	int shader = gl.glCreateShader(type);
	// add the source code to the shader and compile it
	gl.glShaderSource(shader, 1, new String[] { shaderCode },
		new int[] { shaderCode.length() }, 0);
	gl.glCompileShader(shader);
	return shader;
    }

}
