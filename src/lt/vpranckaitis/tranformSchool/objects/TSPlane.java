package lt.vpranckaitis.tranformSchool.objects;

import javax.media.opengl.GL2;

/**
 * Mesh
 * 
 * @author Vilius Pranckaitis
 * @see AbstractObject
 */
public class TSPlane extends AbstractObject {
    private static final float[] sVertices = new float[] { 1.0f, 0.0f, 1.0f,
	    -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, };

    private static final float[] sColors = new float[] { 0.0f, 0.0f, 0.0f,
	    1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
	    0.0f, 1.0f, };

    static short[] sDrawOrder = new short[] { 0, 1, 2, 0, 2, 3, };

    public TSPlane(GL2 gl) {
	super(gl, sVertices, sColors, sDrawOrder, GL2.GL_TRIANGLES);
    }
}
