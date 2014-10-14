package lt.vpranckaitis.opengl;

import java.util.Arrays;

/**
 * Class with methods for creation and manipulation of various transformation
 * matrices.
 * 
 * @author Vilius Pranckaitis
 */
public class Matrix {
    /**
     * Static class
     */
    protected Matrix() {
    }

    /**
     * Returns identity matrix
     * 
     * @return Identity matrix
     * @see <a
     *      href="http://www.opengl.org/sdk/docs/man2/xhtml/glLoadIdentity.xml"
     *      target="_blank">glLoadIdentity</a>
     */
    public static float[] getIdentityM() {
	float[] r = new float[16];
	// diagonal
	r[0] = r[5] = r[10] = r[15] = 1f;
	// first column
	r[1] = r[2] = r[3] = 0;
	// second column
	r[4] = r[6] = r[7] = 0;
	// third column
	r[8] = r[9] = r[11] = 0;
	// fourth column
	r[12] = r[13] = r[14] = 0;

	return r;
    }

    /**
     * Returns inverted matrix
     * 
     * @param m
     *            Matrix to be inverted
     * @return Inverted matrix
     */
    public static float[] getInvertedM(float[] m) {
	float[] r = new float[16];
	final float src0 = m[0];
	final float src4 = m[1];
	final float src8 = m[2];
	final float src12 = m[3];

	final float src1 = m[4];
	final float src5 = m[5];
	final float src9 = m[6];
	final float src13 = m[7];

	final float src2 = m[8];
	final float src6 = m[9];
	final float src10 = m[10];
	final float src14 = m[11];

	final float src3 = m[12];
	final float src7 = m[13];
	final float src11 = m[14];
	final float src15 = m[15];

	// calculate pairs for first 8 elements (cofactors)
	final float atmp0 = src10 * src15;
	final float atmp1 = src11 * src14;
	final float atmp2 = src9 * src15;
	final float atmp3 = src11 * src13;
	final float atmp4 = src9 * src14;
	final float atmp5 = src10 * src13;
	final float atmp6 = src8 * src15;
	final float atmp7 = src11 * src12;
	final float atmp8 = src8 * src14;
	final float atmp9 = src10 * src12;
	final float atmp10 = src8 * src13;
	final float atmp11 = src9 * src12;

	// calculate first 8 elements (cofactors)
	final float dst0 = (atmp0 * src5 + atmp3 * src6 + atmp4 * src7)
		- (atmp1 * src5 + atmp2 * src6 + atmp5 * src7);
	final float dst1 = (atmp1 * src4 + atmp6 * src6 + atmp9 * src7)
		- (atmp0 * src4 + atmp7 * src6 + atmp8 * src7);
	final float dst2 = (atmp2 * src4 + atmp7 * src5 + atmp10 * src7)
		- (atmp3 * src4 + atmp6 * src5 + atmp11 * src7);
	final float dst3 = (atmp5 * src4 + atmp8 * src5 + atmp11 * src6)
		- (atmp4 * src4 + atmp9 * src5 + atmp10 * src6);
	final float dst4 = (atmp1 * src1 + atmp2 * src2 + atmp5 * src3)
		- (atmp0 * src1 + atmp3 * src2 + atmp4 * src3);
	final float dst5 = (atmp0 * src0 + atmp7 * src2 + atmp8 * src3)
		- (atmp1 * src0 + atmp6 * src2 + atmp9 * src3);
	final float dst6 = (atmp3 * src0 + atmp6 * src1 + atmp11 * src3)
		- (atmp2 * src0 + atmp7 * src1 + atmp10 * src3);
	final float dst7 = (atmp4 * src0 + atmp9 * src1 + atmp10 * src2)
		- (atmp5 * src0 + atmp8 * src1 + atmp11 * src2);

	// calculate pairs for second 8 elements (cofactors)
	final float btmp0 = src2 * src7;
	final float btmp1 = src3 * src6;
	final float btmp2 = src1 * src7;
	final float btmp3 = src3 * src5;
	final float btmp4 = src1 * src6;
	final float btmp5 = src2 * src5;
	final float btmp6 = src0 * src7;
	final float btmp7 = src3 * src4;
	final float btmp8 = src0 * src6;
	final float btmp9 = src2 * src4;
	final float btmp10 = src0 * src5;
	final float btmp11 = src1 * src4;

	// calculate second 8 elements (cofactors)
	final float dst8 = (btmp0 * src13 + btmp3 * src14 + btmp4 * src15)
		- (btmp1 * src13 + btmp2 * src14 + btmp5 * src15);
	final float dst9 = (btmp1 * src12 + btmp6 * src14 + btmp9 * src15)
		- (btmp0 * src12 + btmp7 * src14 + btmp8 * src15);
	final float dst10 = (btmp2 * src12 + btmp7 * src13 + btmp10 * src15)
		- (btmp3 * src12 + btmp6 * src13 + btmp11 * src15);
	final float dst11 = (btmp5 * src12 + btmp8 * src13 + btmp11 * src14)
		- (btmp4 * src12 + btmp9 * src13 + btmp10 * src14);
	final float dst12 = (btmp2 * src10 + btmp5 * src11 + btmp1 * src9)
		- (btmp4 * src11 + btmp0 * src9 + btmp3 * src10);
	final float dst13 = (btmp8 * src11 + btmp0 * src8 + btmp7 * src10)
		- (btmp6 * src10 + btmp9 * src11 + btmp1 * src8);
	final float dst14 = (btmp6 * src9 + btmp11 * src11 + btmp3 * src8)
		- (btmp10 * src11 + btmp2 * src8 + btmp7 * src9);
	final float dst15 = (btmp10 * src10 + btmp4 * src8 + btmp9 * src9)
		- (btmp8 * src9 + btmp11 * src10 + btmp5 * src8);

	// calculate determinant
	final float det = src0 * dst0 + src1 * dst1 + src2 * dst2 + src3 * dst3;

	// calculate matrix inverse
	final float invdet = 1.0f / det;
	r[0] = dst0 * invdet;
	r[1] = dst1 * invdet;
	r[2] = dst2 * invdet;
	r[3] = dst3 * invdet;

	r[4] = dst4 * invdet;
	r[5] = dst5 * invdet;
	r[6] = dst6 * invdet;
	r[7] = dst7 * invdet;

	r[8] = dst8 * invdet;
	r[9] = dst9 * invdet;
	r[10] = dst10 * invdet;
	r[11] = dst11 * invdet;

	r[12] = dst12 * invdet;
	r[13] = dst13 * invdet;
	r[14] = dst14 * invdet;
	r[15] = dst15 * invdet;

	return r;
    }

    /**
     * Creates translation matrix
     * 
     * @param x
     *            Translation on X axis
     * @param y
     *            Translation on Y axis
     * @param z
     *            Translation on Z axis
     * @return Translation matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glTranslate.xml"
     *      target="_blank">glTranslate</a>
     */
    public static float[] getTranslateM(float x, float y, float z) {
	float[] r = getIdentityM();
	r[12] = x;
	r[13] = y;
	r[14] = z;
	return r;
    }

    /**
     * Creates scale matrix
     * 
     * @param sx
     *            Scale on X axis
     * @param sy
     *            Scale on Y axis
     * @param sz
     *            Scale on Z axis
     * @return Scale matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glScale.xml"
     *      target="_blank">glScale</a>
     */
    public static float[] getScaleM(float sx, float sy, float sz) {
	float[] r = getIdentityM();
	r[0] = sx;
	r[5] = sy;
	r[10] = sz;
	return r;
    }

    /**
     * Calculates length of a vector
     * 
     * @param x
     *            Vector X coordinate
     * @param y
     *            Vector Y coordinate
     * @param z
     *            Vector Z coordinate
     * @return Length of the vector
     */
    public static float length(float x, float y, float z) {
	return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Creates rotation matrix, which rotates object around specified vector
     * 
     * @param angle
     *            Angle in degrees
     * @param x
     *            Rotation vector X coordinate
     * @param y
     *            Rotation vector Y coordinate
     * @param z
     *            Rotation vector Z coordinate
     * @return Rotation matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glRotate.xml"
     *      target="_blank">glRotate</a>
     */
    public static float[] getRotateM(float angle, float x, float y, float z) {
	float[] r = getIdentityM();
	double a = angle / 180.0f * Math.PI;
	float sin = (float) Math.sin(a);
	float cos = (float) Math.cos(a);
	if (x == 1.0f && y == 0.0f && z == 0.0f) {
	    r[5] = r[10] = cos;
	    r[6] = sin;
	    r[9] = -sin;
	} else if (x == 0.0f && y == 1.0f && z == 0.0f) {
	    r[0] = r[10] = cos;
	    r[8] = sin;
	    r[2] = -sin;
	} else if (x == 0.0f && y == 0.0f && z == 1.0f) {
	    r[0] = r[5] = cos;
	    r[1] = sin;
	    r[4] = -sin;
	} else {
	    float len = length(x, y, z);
	    if (len != 1.0f) {
		float divLen = 1.0f / len;
		x *= divLen;
		y *= divLen;
		z *= divLen;
	    }
	    float ncos = 1.0f - cos;
	    float xy = x * y;
	    float xz = x * z;
	    float yz = y * z;
	    float xsin = x * sin;
	    float ysin = y * sin;
	    float zsin = z * sin;
	    // 1 column
	    r[0] = x * x * ncos + cos;
	    r[1] = xy * ncos + zsin;
	    r[2] = xz * ncos - ysin;
	    // 2 column
	    r[4] = xy * ncos - zsin;
	    r[5] = y * y * ncos + cos;
	    r[6] = yz * ncos + xsin;
	    // 3 column
	    r[8] = xz * ncos + ysin;
	    r[9] = yz * ncos - xsin;
	    r[10] = z * z * ncos + cos;
	}
	return r;
    }

    /**
     * Creates orthogonal projection matrix
     * 
     * The orthogonal projection matrix makes parallel projection, that means
     * parallel lines remain parallel after applying transformation matrix.
     * 
     * @param left
     *            Left clipping plane
     * @param right
     *            Right clipping plane
     * @param bottom
     *            Bottom clipping plane
     * @param top
     *            Top clipping plane
     * @param near
     *            Near clipping plane
     * @param far
     *            Far clipping plane
     * @return Orthogonal projection matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glOrtho.xml"
     *      target="_blank">glOrtho</a>
     */
    public static float[] getOrthoM(float left, float right, float bottom,
	    float top, float near, float far) {
	float r_width = 1.0f / (right - left);
	float r_height = 1.0f / (top - bottom);
	float r_depth = 1.0f / (far - near);

	float[] r = getIdentityM();
	r[0] = 2.0f * r_width;
	r[5] = 2.0f * r_height;
	r[10] = -2.0f * r_depth;
	r[12] = -(right + left) * r_width;
	r[13] = -(top + bottom) * r_height;
	r[14] = -(far + near) * r_depth;
	return r;
    }

    /**
     * Creates frustum projection matrix
     * 
     * The frustum projection matrix creates projection, which displays object
     * according to perspective.
     * 
     * @param left
     *            Left clipping plane
     * @param right
     *            Right clipping plane
     * @param bottom
     *            Bottom clipping plane
     * @param top
     *            Top clipping plane
     * @param near
     *            Near clipping plane
     * @param far
     *            Far clipping plane
     * @return Frustum matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glFrustum.xml"
     *      target="_blank">glFrustum</a>
     */
    public static float[] getFrustumM(float left, float right, float bottom,
	    float top, float near, float far) {
	float r_width = 1.0f / (right - left);
	float r_height = 1.0f / (top - bottom);
	float r_depth = 1.0f / (far - near);

	float[] r = getIdentityM();
	r[0] = 2.0f * near * r_width;
	r[5] = 2.0f * near * r_height;
	r[8] = (right + left) * r_width;
	r[9] = (top + bottom) * r_height;
	r[10] = -(far + near) * r_depth;
	r[11] = -1;
	r[14] = -2.0f * far * near * r_depth;
	r[15] = 0;
	return r;
    }

    /**
     * Creates perspective projection matrix
     * 
     * This creates the same effect as frustum projection matrix, except that
     * the left, right, bottom and top planes are calculated using field of view
     * and aspect values
     * 
     * @param fovy
     *            Field of view
     * @param aspect
     *            Aspect ratio
     * @param zNear
     *            Near clipping plane
     * @param zFar
     *            Far clipping plane
     * @return Perspective matrix
     * @see <a
     *      href="http://www.opengl.org/sdk/docs/man2/xhtml/gluPerspective.xml"
     *      target="_blank">gluPerspective</a>
     */
    public static float[] getPerspectiveM(float fovy, float aspect,
	    float zNear, float zFar) {
	float[] r = getIdentityM();
	float r_depth = 1 / (zNear - zFar);
	float f = 1.0f / ((float) Math.tan(fovy * (Math.PI / 360.0)));
	r[0] = f / aspect;
	r[5] = f;
	r[10] = (zFar + zNear) * r_depth;
	r[11] = -1;
	r[14] = 2.0f * zFar * zNear * r_depth;
	r[15] = 0;
	return r;
    }

    /**
     * Multiplies two matrices
     * 
     * Return = A * B
     * 
     * @param a
     *            Left matrix
     * @param b
     *            Right matrix
     * @return Resulting matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glMultMatrix.xml"
     *      target="_blank">glMultMatrix</a>
     */
    public static float[] multiplyMM(float[] a, float[] b) {
	float[] r = new float[16];

	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		r[j * 4 + i] = a[0 + i] * b[j * 4 + 0] + a[4 + i]
			* b[j * 4 + 1] + a[8 + i] * b[j * 4 + 2] + a[12 + i]
			* b[j * 4 + 3];
	    }
	}
	return r;
    }

    /**
     * Multiplies matrix with vector
     * 
     * Return = A * B
     * 
     * @param a
     *            Matrix to multiply
     * @param b
     *            Vector to multiply
     * @return Resulting vector
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/glMultMatrix.xml"
     *      target="_blank">glMultMatrix</a>
     */
    public static float[] multiplyMV(float[] a, float[] b) {
	float[] r = new float[4];

	for (int i = 0; i < 4; i++) {
	    r[i] = a[0 + i] * b[0] + a[4 + i] * b[1] + a[8 + i] * b[2]
		    + a[12 + i] * b[3];
	}
	return r;
    }

    /**
     * Normalizes vector
     * 
     * @param v
     *            Vector to normalize
     * @return Normalized vector
     */
    public static float[] normalizeV(float[] v) {
	float[] r = new float[4];
	r[0] = v[0] / v[3];
	r[1] = v[1] / v[3];
	r[2] = v[2] / v[3];
	r[3] = 1.0f;
	return r;
    }

    /**
     * Creates camera transformation matrix
     * 
     * @param eyeX
     *            Eye point X coordinate
     * @param eyeY
     *            Eye point Y coordinate
     * @param eyeZ
     *            Eye point Z coordinate
     * @param centerX
     *            Target point X coordinate
     * @param centerY
     *            Target point Y coordinate
     * @param centerZ
     *            Target point Z coordinate
     * @param upX
     *            Up vector X coordinate
     * @param upY
     *            Up vector Y coordinate
     * @param upZ
     *            Up vector Z coordinate
     * @return Look At matrix
     * @see <a href="http://www.opengl.org/sdk/docs/man2/xhtml/gluLookAt.xml"
     *      target="_blank">gluLookAt</a>
     */
    public static float[] getLookAtM(float eyeX, float eyeY, float eyeZ,
	    float centerX, float centerY, float centerZ, float upX, float upY,
	    float upZ) {
	// glLookAt

	// compute f
	float[] r = getIdentityM();
	float fx = centerX - eyeX;
	float fy = centerY - eyeY;
	float fz = centerZ - eyeZ;

	// normalize f
	float rlf = 1.0f / length(fx, fy, fz);
	fx *= rlf;
	fy *= rlf;
	fz *= rlf;

	// compute s = f x up
	float sx = fy * upZ - fz * upY;
	float sy = fz * upX - fx * upZ;
	float sz = fx * upY - fy * upX;

	// normalize up
	float rlup = 1.0f / length(upX, upY, upZ);
	sx *= rlup;
	sy *= rlup;
	sz *= rlup;

	// compute u = s x f
	float ux = sy * fz - sz * fy;
	float uy = sz * fx - sx * fz;
	float uz = sx * fy - sy * fx;

	// 1 row
	r[0] = sx;
	r[4] = sy;
	r[8] = sz;
	// 2 row
	r[1] = ux;
	r[5] = uy;
	r[9] = uz;
	// 3 row
	r[2] = -fx;
	r[6] = -fy;
	r[10] = -fz;

	r = multiplyMM(r, getTranslateM(-eyeX, -eyeY, -eyeZ));
	return r;
    }
}
