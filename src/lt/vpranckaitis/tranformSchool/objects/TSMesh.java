package lt.vpranckaitis.tranformSchool.objects;

import java.util.Arrays;

import javax.media.opengl.GL2;

/**
 * Mesh
 * 
 * @author Vilius Pranckaitis
 * @see AbstractObject
 */
public class TSMesh extends AbstractObject {
    private static final int N = 10;
    private static final int PARTITION = 100;

    static float sCoords[];

    static short[] sDrawOrder;

    static float[] sColorArray;

    static {
	sCoords = new float[12 * (N + 1)];
	sDrawOrder = new short[4 * (N + 1)];
	sColorArray = new float[16 * (N + 1)];
	for (int i = 0; i <= N; i++) {
	    sCoords[i * 12] = 1.0f - 2.0f * (float) i / N;
	    sCoords[i * 12 + 1] = 0.0f;
	    sCoords[i * 12 + 2] = 1.0f;
	    sCoords[i * 12 + 3] = 1.0f - 2.0f * (float) i / N;
	    sCoords[i * 12 + 4] = 0.0f;
	    sCoords[i * 12 + 5] = -1.0f;

	    sCoords[i * 12 + 6] = 1.0f;
	    sCoords[i * 12 + 7] = 0.0f;
	    sCoords[i * 12 + 8] = 1.0f - 2.0f * (float) i / N;
	    sCoords[i * 12 + 9] = -1.0f;
	    sCoords[i * 12 + 10] = 0.0f;
	    sCoords[i * 12 + 11] = 1.0f - 2.0f * (float) i / N;
	}
	// System.out.println(Arrays.toString(sCoords));
	for (short i = 0; i < sDrawOrder.length; i++) {
	    sDrawOrder[i] = i;
	}

	for (int i = 0; i < sColorArray.length; i++) {
	    sColorArray[i] = 1.0f;
	}
	// System.out.println(Arrays.toString(sCoords));
	// System.out.println(Arrays.toString(sDrawOrder));
    }

    public TSMesh(GL2 gl) {
	super(gl, sCoords, sColorArray, sDrawOrder, GL2.GL_LINES);
    }
}
