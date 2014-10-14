package lt.vpranckaitis.tranformSchool.objects;

import javax.media.opengl.GL2;

/**
 * Cube with variously colored sides.
 * 
 * @author Vilius Pranckaitis
 * @see AbstractObject
 */
public class TSTransparentBox extends AbstractObject {
    private static final float[] sVertices = new float[] { 1.0f, 1.0f, 1.0f,
	    -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f,

	    1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f,
	    -1.0f, -1.0f, };

    private static final float[] sColors = new float[] { 0.5f, 0.5f, 0.5f,
	    0.3f, 0.5f, 0.5f, 0.5f, 0.3f, 0.5f, 0.5f, 0.5f, 0.3f, 0.5f, 0.5f,
	    0.5f, 0.3f, 0.5f, 0.5f, 0.5f, 0.3f, 0.5f, 0.5f, 0.5f, 0.3f, 0.5f,
	    0.5f, 0.5f, 0.3f, 0.5f, 0.5f, 0.5f, 0.3f };

    static short[] sDrawOrder = new short[] { 0, 1, 2, 0, 2, 3,

    4, 7, 6, 4, 6, 5,

    0, 3, 7, 0, 7, 4,

    0, 4, 5, 0, 5, 1,

    1, 5, 6, 1, 6, 2,

    2, 6, 7, 2, 7, 3,

    };

    public TSTransparentBox(GL2 gl) {
	super(gl, sVertices, sColors, sDrawOrder, GL2.GL_TRIANGLES);
    }
}
