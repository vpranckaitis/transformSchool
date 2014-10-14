package lt.vpranckaitis.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JList;

/**
 * Action object for insertion of matrices to the list
 * 
 * @author Vilius Pranckaitis
 * 
 */
public class InsertMatrixAction extends AbstractAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3995058399044300820L;

    private int mType;
    private ActionListener mListener;

    public InsertMatrixAction(int type, ActionListener l) {
	super();
	mType = type;
	mListener = l;
    }

    public InsertMatrixAction(String name, Icon icon, int type, ActionListener l) {
	super(name, icon);
	mType = type;
	mListener = l;
    }

    public InsertMatrixAction(String name, int type, ActionListener l) {
	super(name);
	mType = type;
	mListener = l;
    }

    public int getType() {
	return mType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	e.setSource(this);
	mListener.actionPerformed(e);
    }
}
