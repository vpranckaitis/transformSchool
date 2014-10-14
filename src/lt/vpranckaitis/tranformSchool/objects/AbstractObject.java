package lt.vpranckaitis.tranformSchool.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.media.opengl.GL2;

import lt.vpranckaitis.tranformSchool.TSGLView;

/**
 * Base class which draws graphical object onto OpenGl canvas
 * 
 * @author Vilius Pranckaitis
 * 
 */
public class AbstractObject {
    private static final int COORDS_PER_VERTEX = 3;
    private final String vertexShaderCode = "uniform mat4 uMVPMatrix;"
	    + "attribute vec4 vPosition;" 
    	+ "attribute vec4 aColor;"
	    + "varying vec4 vColor;" 
    	+ "void main() {"
	    + "  gl_Position = uMVPMatrix * vPosition;"
	    + "  gl_PointSize = 100.0;" 
	    + "  vColor = aColor;" + "}";
    /*
     * private final String fragmentShaderCode = "precision mediump float;" +
     * "varying vec4 vColor;" + "void main() {" + "  gl_FragColor = vColor;" +
     * "}";
     */
    private final String fragmentShaderCode = "varying vec4 vColor;"
	    + "void main() {" + "  gl_FragColor = vColor;" + "}";

    private int mDrawType = GL2.GL_TRIANGLES;

    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ShortBuffer mDrawOrderBuffer;

    public AbstractObject(GL2 gl, float[] vertices, float[] colors,
	    short[] drawOrder, int drawingType) {
	mDrawType = drawingType;

	ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
	bb.order(ByteOrder.nativeOrder());

	mVertexBuffer = bb.asFloatBuffer();
	mVertexBuffer.put(vertices);
	mVertexBuffer.position(0);

	bb = ByteBuffer.allocateDirect(drawOrder.length * 2);
	bb.order(ByteOrder.nativeOrder());

	mDrawOrderBuffer = bb.asShortBuffer();
	mDrawOrderBuffer.put(drawOrder);
	mDrawOrderBuffer.position(0);

	bb = ByteBuffer.allocateDirect(colors.length * 4);
	bb.order(ByteOrder.nativeOrder());

	mColorBuffer = bb.asFloatBuffer();
	mColorBuffer.put(colors);
	mColorBuffer.position(0);

	int vertexShader = TSGLView.loadShader(gl, GL2.GL_VERTEX_SHADER,
		vertexShaderCode);
	int fragmentShader = TSGLView.loadShader(gl, GL2.GL_FRAGMENT_SHADER,
		fragmentShaderCode);

	mProgram = gl.glCreateProgram();
	gl.glAttachShader(mProgram, vertexShader);
	gl.glAttachShader(mProgram, fragmentShader);
	gl.glLinkProgram(mProgram);
	gl.glValidateProgram(mProgram);

	// bb = ByteBuffer.allocateDirect(100);
	// bb.order(ByteOrder.nativeOrder());
	// IntBuffer ib = bb.asIntBuffer();
	// gl.glGetProgramiv(mProgram, GL2.GL_VALIDATE_STATUS, ib);
	// System.out.println(ib.get());
    }

    /**
     * Draws object onto the OpenGL canvas
     * 
     * @param gl
     *            GL object
     * @param mvpMatrix
     *            Model-View-Projection matrix to apply to this object
     */
    public void draw(GL2 gl, float[] mvpMatrix) {
	// System.out.println(gl.glGetError());
	gl.glUseProgram(mProgram);
	// System.out.println(gl.glGetError());
	mPositionHandle = gl.glGetAttribLocation(mProgram, "vPosition");
	gl.glEnableVertexAttribArray(mPositionHandle);
	gl.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
		GL2.GL_FLOAT, false, COORDS_PER_VERTEX * 4, mVertexBuffer);

	int MVPMatrixHandle = gl.glGetUniformLocation(mProgram, "uMVPMatrix");
	gl.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0);

	// GLES20.glDrawArrays(GLES20.GL_, 0, triangleCoords.length /
	// COORDS_PER_VERTEX);

	mColorHandle = gl.glGetAttribLocation(mProgram, "aColor");
	gl.glEnableVertexAttribArray(mColorHandle);
	gl.glVertexAttribPointer(mColorHandle, 4, GL2.GL_FLOAT, false, 4 * 4,
		mColorBuffer);

	gl.glDrawElements(mDrawType, mDrawOrderBuffer.capacity(),
		GL2.GL_UNSIGNED_SHORT, mDrawOrderBuffer);
	gl.glDisableVertexAttribArray(mPositionHandle);
    }

}
