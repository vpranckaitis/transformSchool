package lt.vpranckaitis.tranformSchool.objects;

import javax.media.opengl.GL2;

/**
 * Cube with variously colored sides.
 * 
 * @author Vilius Pranckaitis
 * @see AbstractObject
 */
public class TSBox extends AbstractObject {
    static float sCoords[] = { // in counterclockwise order:
    -1f, 1f, 1f, // front
	    -1f, -1f, 1f, 1f, -1f, 1f, 1f, 1f, 1f,

	    -1f, 1f, 1f, // left
	    -1f, 1f, -1f, -1f, -1f, -1f, -1f, -1f, 1f,

	    -1f, 1f, 1f, // top
	    1f, 1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f,

	    1f, 1f, 1f, // right
	    1f, -1f, 1f, 1f, -1f, -1f, 1f, 1f, -1f,

	    1f, -1f, 1f, // bottom
	    -1f, -1f, 1f, -1f, -1f, -1f, 1f, -1f, -1f,

	    1f, 1f, -1f, // back
	    1f, -1f, -1f, -1f, -1f, -1f, -1f, 1f, -1f, };

    static short[] sDrawOrder = new short[] { 0, 1, 2, 0, 2, 3,

    4, 5, 6, 4, 6, 7,

    8, 9, 10, 8, 10, 11,

    12, 13, 14, 12, 14, 15,

    16, 17, 18, 16, 18, 19,

    20, 21, 22, 20, 22, 23 };

    static float[] c0 = new float[] { 0f, 1f, 1f, 1.0f };
    static float[] c1 = new float[] { 1f, 0f, 1f, 1.0f };
    static float[] c2 = new float[] { 1f, 1f, 0f, 1.0f };
    static float[] c3 = new float[] { 0f, 0f, 1f, 1.0f };
    static float[] c4 = new float[] { 1f, 0f, 0f, 1.0f };
    static float[] c5 = new float[] { 0f, 1f, 0f, 1.0f };

    static float[] sColorArray = new float[] { c0[0], c0[1], c0[2], c0[3],
	    c0[0], c0[1], c0[2], c0[3], c0[0], c0[1], c0[2], c0[3], c0[0],
	    c0[1], c0[2], c0[3],

	    c1[0], c1[1], c1[2], c1[3], c1[0], c1[1], c1[2], c1[3], c1[0],
	    c1[1], c1[2], c1[3], c1[0], c1[1], c1[2], c1[3],

	    c2[0], c2[1], c2[2], c2[3], c2[0], c2[1], c2[2], c2[3], c2[0],
	    c2[1], c2[2], c2[3], c2[0], c2[1], c2[2], c2[3],

	    c3[0], c3[1], c3[2], c3[3], c3[0], c3[1], c3[2], c3[3], c3[0],
	    c3[1], c3[2], c3[3], c3[0], c3[1], c3[2], c3[3],

	    c4[0], c4[1], c4[2], c4[3], c4[0], c4[1], c4[2], c4[3], c4[0],
	    c4[1], c4[2], c4[3], c4[0], c4[1], c4[2], c4[3],

	    c5[0], c5[1], c5[2], c5[3], c5[0], c5[1], c5[2], c5[3], c5[0],
	    c5[1], c5[2], c5[3], c5[0], c5[1], c5[2], c5[3]

    };

    public TSBox(GL2 gl) {
	super(gl, sCoords, sColorArray, sDrawOrder, GL2.GL_TRIANGLES);
    }
}
