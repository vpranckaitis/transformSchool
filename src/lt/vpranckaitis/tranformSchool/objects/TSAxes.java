package lt.vpranckaitis.tranformSchool.objects;

import javax.media.opengl.GL2;

/**
 * X, Y and Z axes
 * 
 * @author Vilius Pranckaitis
 * @see AbstractObject
 */
public class TSAxes extends AbstractObject {
    static float sCoords[] = { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // x axis

	    0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, // y axis

	    0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f // z axis
    };

    static short[] sDrawOrder = new short[] { 0, 1, 2, 3, 4, 5 };

    static float[] sColorArray = new float[] { 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
	    0.0f, 0.0f, 1.0f,

	    0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f,

	    0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, };

    public TSAxes(GL2 gl) {
	super(gl, sCoords, sColorArray, sDrawOrder, GL2.GL_LINES);
    }
}
