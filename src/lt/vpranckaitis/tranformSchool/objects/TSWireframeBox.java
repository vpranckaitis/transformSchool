package lt.vpranckaitis.tranformSchool.objects;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;

public class TSWireframeBox extends AbstractObject {
    private static final float[] sVertices = new float[] { 1.0f, 1.0f, 1.0f,
	    1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f,
	    -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f,
	    1.0f };

    private static final float[] sColors = new float[] { 0.5f, 0.5f, 0.5f,
	    1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f,
	    0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f,
	    0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f };

    private static final short[] sDrawOrder = new short[] { 0, 1, 1, 2, 2, 3,
	    3, 0,

	    4, 5, 5, 6, 6, 7, 7, 4,

	    0, 7, 1, 6, 2, 5, 3, 4 };

    public TSWireframeBox(GL2 gl) {
	super(gl, sVertices, sColors, sDrawOrder, GL2ES1.GL_LINES);
    }
}
