package lt.vpranckaitis.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lt.vpranckaitis.swing.event.MatrixDataListener;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;

public abstract class AbstractMatrixPanel extends JPanel implements
	ChangeListener, ActionListener {
    /**
	 * 
	 */
    private static final long serialVersionUID = -2632473950514226209L;

    private List<MatrixDataListener> mDataListeners = new ArrayList<>();

    private boolean mIsEventsDisabled = false;

    public AbstractMatrixPanel() {
	super();
    }

    public void addDataChangeListener(MatrixDataListener l) {
	mDataListeners.add(l);
    }

    public void fireDataChanged() {
	for (MatrixDataListener l : mDataListeners) {
	    l.dataChanged(createData());
	}
    }

    @Override
    public void stateChanged(ChangeEvent e) {
	if (!mIsEventsDisabled) {
	    fireDataChanged();
	    // System.out.println("changed");
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (!mIsEventsDisabled) {
	    fireDataChanged();
	    // System.out.println("changed");
	}
    }

    protected void setDataEventsDisabled(boolean disable) {
	mIsEventsDisabled = disable;
    }

    protected abstract TransferableData createData();

    public abstract void setData(TransferableData d);
}
