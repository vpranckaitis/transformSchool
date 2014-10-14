package lt.vpranckaitis.tranformSchool.matrices;

import java.security.InvalidParameterException;

public class MatrixModifier extends AbstractMatrix {
    public MatrixModifier() {
	this(MatrixBase.MODIFIER_MODEL);
    }

    public MatrixModifier(int type) {
	super();
	setData(type);
    }

    public MatrixModifier(MatrixModifier m) {
	super();
	setData(m);
    }

    public MatrixModifier(TransferableData d) {
	super();
	setData(d);
    }

    public void setData(int type) {
	if ((type & MatrixBase.MODIFIER) == 0) {
	    throw new InvalidParameterException("Expected modifier type");
	}
	mType = type;
    }

    public void setData(MatrixModifier m) {
	mType = m.mType;
    }

    @Override
    public void setData(TransferableData d) {
	if ((d.type & MatrixBase.MODIFIER) == 0) {
	    throw new InvalidParameterException("Expected modifier type");
	}
	this.mType = d.type;
    }

    @Override
    public TransferableData getData() {
	TransferableData r = new TransferableData();
	r.type = mType;
	return r;
    }

    @Override
    public AbstractMatrix createInstance(TransferableData d) {
	return new MatrixModifier(d);
    }

    @Override
    public String toString() {
	String str = "Modifier: ";
	if (mType == MODIFIER_MODEL) {
	    str += "Consider Model";
	} else if (mType == MODIFIER_VIEW) {
	    str += "Consider View";
	} else if (mType == MODIFIER_PROJECTION) {
	    str += "Consider Projection";
	}
	return str;
    }
}
