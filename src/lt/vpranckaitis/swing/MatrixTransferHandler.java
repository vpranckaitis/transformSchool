package lt.vpranckaitis.swing;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import javax.swing.event.ListDataListener;

import lt.vpranckaitis.tranformSchool.matrices.AbstractMatrix;
import lt.vpranckaitis.tranformSchool.matrices.MatrixBase;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

/**
 * Enables drag and drop actions in list
 * 
 * @author Vilius Pranckaitis
 * @see TransferHandler
 * @see JList#setTransferHandler(TransferHandler)
 */
public class MatrixTransferHandler extends TransferHandler {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8546698685027134665L;

    int mFrom;
    int mTo;

    @Override
    public boolean canImport(TransferSupport support) {
	return support.isDataFlavorSupported(MatrixBase.matrixFlavor);
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
	JList<AbstractMatrix> list = (JList<AbstractMatrix>) c;
	int i = list.getSelectedIndex();
	DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) list
		.getModel();
	return model.getElementAt(i).getData();
    }

    @Override
    public int getSourceActions(JComponent c) {
	return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferSupport support) {
	if (support.isDrop()) {
	    JList<AbstractMatrix> list = (JList<AbstractMatrix>) support
		    .getComponent();
	    DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) list
		    .getModel();
	    mFrom = list.getSelectedIndex();
	    mTo = ((JList.DropLocation) support.getDropLocation()).getIndex();
	    // System.out.println(list.getSelectedIndex());
	    // System.out.println(model.get(list.getSelectedIndex()));
	    AbstractMatrix matrix;
	    try {
		matrix = model.get(list.getSelectedIndex()).createInstance(
			(TransferableData) support.getTransferable()
				.getTransferData(AbstractMatrix.matrixFlavor));
		if (support.getDropAction() == TransferHandler.MOVE) {
		    ListDataListener[] ls = model.getListDataListeners();
		    for (ListDataListener l : ls) {
			model.removeListDataListener(l);
		    }
		    model.add(mTo, matrix);
		    for (ListDataListener l : ls) {
			model.addListDataListener(l);
		    }
		} else {
		    model.add(mTo, matrix);
		}
		return true;
	    } catch (UnsupportedFlavorException | IOException e) {
		e.printStackTrace();
	    }
	    return false;
	}
	return super.importData(support);
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
	JList<AbstractMatrix> list = (JList<AbstractMatrix>) source;
	if (action == MOVE) {
	    // System.out.println("export Move");
	    // System.out.println(mFrom + " " + mTo);
	    DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) list
		    .getModel();
	    if (mFrom < mTo) {
		model.remove(mFrom);
		list.setSelectedIndex(mTo - 1);
	    } else {
		model.remove(mFrom + 1);
		list.setSelectedIndex(mTo);
	    }
	} else if (action == COPY) {
	    // System.out.println("export Copy");
	    list.setSelectedIndex(mTo);
	}
    }

}
