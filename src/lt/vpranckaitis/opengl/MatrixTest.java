package lt.vpranckaitis.opengl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test class to check matrix calculations.
 * 
 * The expected values are calculated by hand using formulas given in OpenGL
 * reference pages. See {@link Matrix} for more information.
 * 
 * @author Vilius Pranckaitis
 * @see Matrix
 */
public class MatrixTest {

    /**
     * @see Matrix#getInvertedM(float[])
     */
    @org.junit.Test
    public void testGetInvertedM() {
	float[] data = new float[] { 1, 2, 0, 4, -1, 3, -1, 2, -4, -1, 0, 1, 4,
		2, -2, -2 };
	float[] expected = new float[] { 10, -12, -4, 6, -18, 34, -30, -17,
		-20, 24, -54, -43, 22, -14, 16, 7 };
	for (int i = 0; i < expected.length; i++) {
	    expected[i] /= 62.0f;
	}
	assertArrayEquals(expected, Matrix.getInvertedM(data), 0.0001f);
    }

    /**
     * @see Matrix#length(float, float, float)
     */
    @Test
    public void testLength() {
	assertEquals(7.874007874f, Matrix.length(2, -3, 7), 0.0001f);
    }

    /**
     * @see Matrix#getOrthoM(float, float, float, float, float, float)
     */
    @Test
    public void testGetOrthoM() {
	float[] expected = new float[] { 0.6667f, 0, 0, 0, 0, 0.2857f, 0, 0, 0,
		0, -0.1818f, 0, -0.3333f, -0.1429f, -0.0909f, 1f };
	assertArrayEquals(expected, Matrix.getOrthoM(-1, 2, -3, 4, -5, 6),
		0.0001f);
    }

    /**
     * @see Matrix#getFrustumM(float, float, float, float, float, float)
     */
    @Test
    public void testGetFrustumM() {
	float[] expected = new float[] { 0.6667f, 0, 0, 0, 0, 0.2857f, 0, 0,
		0.3333f, 0.1429f, -1.4f, -1f, 0, 0, -2.4f, 0 };
	assertArrayEquals(expected, Matrix.getFrustumM(-1, 2, -3, 4, 1, 6),
		0.0001f);
    }

    /**
     * @see Matrix#multiplyMM(float[], float[])
     */
    @Test
    public void testMultiplyMM() {
	float[] data1 = new float[] { 1, 2, 0, 4, -1, 3, -1, 2, -4, -1, 0, 1,
		4, 2, -2, -2 };
	float[] data2 = new float[] { -3, 2, -4, 2, -2, 5, -8, 3, 3, 2, -2, -1,
		5, -1, 3, -1 };
	float[] expected = new float[] { 19, 8, -6, -16, 37, 25, -11, -12, 5,
		12, 0, 16, -10, 2, 3, 23 };
	assertArrayEquals(expected, Matrix.multiplyMM(data1, data2), 0.01f);
    }

    /**
     * @see Matrix#multiplyMV(float[], float[])
     */
    @Test
    public void testMultiplyMV() {
	float[] data1 = new float[] { 1, 2, 0, 4, -1, 3, -1, 2, -4, -1, 0, 1,
		4, 2, -2, -2 };
	float[] data2 = new float[] { -1, 2, -3, 1 };
	float[] expected = new float[] { 13, 9, -4, -5 };
	assertArrayEquals(expected, Matrix.multiplyMV(data1, data2), 0.0001f);
    }

    /**
     * @see Matrix#getLookAtM(float, float, float, float, float, float, float,
     *      float, float)
     */
    @Test
    public void testGetLookAtM() {
	// float[] expected = new float[]{0.8671f, -0.0868f, 0.1881f, 0,
	// 0.0369f, 0.8504f, 0.2822f, 0, -0.1845f, -0.2378f, 0.9407f, 0, 0, 0,
	// 0, 1f};
	float[] expected = new float[] { 0.8671f, -0.0868f, 0.1881f, 0,
		0.0369f, 0.8504f, 0.2822f, 0, -0.1845f, -0.2378f, 0.9407f, 0,
		-0.904f, -0.7636f, -0.4703f, 1f };
	assertArrayEquals(expected,
		Matrix.getLookAtM(1f, 1f, 0, -1f, -2f, -10f, 0, 5f, 1f),
		0.0001f);
    }

    /**
     * @see Matrix#getPerspectiveM(float, float, float, float)
     */
    @Test
    public void testGetPerspectiveM() {
	float[] expected = new float[] { 3.7321f, 0, 0, 0, 0, 3.7321f, 0, 0, 0,
		0, -1.2222f, -1f, 0, 0, -2.2222f, 0 };
	assertArrayEquals(expected, Matrix.getPerspectiveM(30, 1, 1, 10),
		0.0001f);
    }

}
