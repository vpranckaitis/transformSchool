package lt.vpranckaitis.swing.event;

import lt.vpranckaitis.swing.AbstractMatrixPanel;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

/**
 * Event listener for matrix data change event.
 * 
 * This object must be registered in order to receive notifications from matrix
 * panel.
 * 
 * @author Vilius Pranckaitis
 * @see AbstractMatrixPanel
 */
public interface MatrixDataListener {
    public void dataChanged(TransferableData d);
}
