package lt.vpranckaitis.tranformSchool.matrices;

import java.awt.datatransfer.DataFlavor;

/**
 * Constants and methods for matrix object
 * 
 * @author Vilius Pranckaitis
 * 
 */
public interface MatrixBase {
    public static final int TYPE_SIMPLE = 0x00;

    public static final int TYPE_MODEL = 0x10;
    public static final int TYPE_ROTATE_AXIS = 0x11;
    public static final int TYPE_ROTATE_VECTOR = 0x12;
    public static final int TYPE_SCALE = 0x13;
    public static final int TYPE_TRANSLATE = 0x14;

    public static final int TYPE_VIEW = 0x20;
    public static final int TYPE_LOOK_AT = 0x21;

    public static final int TYPE_PROJECTION = 0x40;
    public static final int TYPE_FRUSTUM = 0x41;
    public static final int TYPE_PERSPECTIVE = 0x42;
    public static final int TYPE_ORTHO = 0x43;

    public static final int GROUP_MASK = 0xF0;

    public static final int MODIFIER = 0x100;
    public static final int MODIFIER_MODEL = 0x101;
    public static final int MODIFIER_VIEW = 0x102;
    public static final int MODIFIER_PROJECTION = 0x103;

    public static final DataFlavor matrixFlavor = new DataFlavor(
	    MatrixBase.class, "matrix flavor");

    public float[] getMatrix();

    public int getType();
}
