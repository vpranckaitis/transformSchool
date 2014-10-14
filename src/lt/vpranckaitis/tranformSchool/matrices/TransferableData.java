package lt.vpranckaitis.tranformSchool.matrices;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.TransferHandler;

/**
 * Base class for storage and transportation of matrix data. Can be transfered
 * through {@link TransferHandler}'s mechanism.
 * 
 * @author Vilius Pranckaitis
 * 
 */
public class TransferableData implements Transferable {
    public int type;
    public float[] matrix;

    private static final DataFlavor[] sFlavors = new DataFlavor[] { MatrixBase.matrixFlavor };

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer
     * .DataFlavor)
     */
    @Override
    public Object getTransferData(DataFlavor flavor)
	    throws UnsupportedFlavorException, IOException {
	if (isDataFlavorSupported(flavor)) {
	    return this;
	} else {
	    throw new UnsupportedFlavorException(flavor);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
     */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
	return sFlavors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.
     * datatransfer.DataFlavor)
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
	return flavor.equals(MatrixBase.matrixFlavor);
    }

    @Override
    public String toString() {
	// TODO Auto-generated method stub
	return type + " " + Arrays.toString(matrix);
    }

}
