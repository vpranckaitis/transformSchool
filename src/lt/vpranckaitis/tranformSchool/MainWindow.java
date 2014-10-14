package lt.vpranckaitis.tranformSchool;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lt.vpranckaitis.opengl.Matrix;
import lt.vpranckaitis.swing.FrustumMatrixPanel;
import lt.vpranckaitis.swing.InsertMatrixAction;
import lt.vpranckaitis.swing.LookAtMatrixPanel;
import lt.vpranckaitis.swing.MatrixDisplayPanel;
import lt.vpranckaitis.swing.MatrixTransferHandler;
import lt.vpranckaitis.swing.OrthoMatrixPanel;
import lt.vpranckaitis.swing.PerspectiveMatrixPanel;
import lt.vpranckaitis.swing.RotateMatrixPanel;
import lt.vpranckaitis.swing.ScaleMatrixPanel;
import lt.vpranckaitis.swing.TranslateMatrixPanel;
import lt.vpranckaitis.swing.event.MatrixDataListener;
import lt.vpranckaitis.tranformSchool.matrices.AbstractMatrix;
import lt.vpranckaitis.tranformSchool.matrices.FrustumMatrix;
import lt.vpranckaitis.tranformSchool.matrices.LookAtMatrix;
import lt.vpranckaitis.tranformSchool.matrices.MatrixBase;
import lt.vpranckaitis.tranformSchool.matrices.MatrixModifier;
import lt.vpranckaitis.tranformSchool.matrices.OrthoMatrix;
import lt.vpranckaitis.tranformSchool.matrices.PerspectiveMatrix;
import lt.vpranckaitis.tranformSchool.matrices.RotateMatrix;
import lt.vpranckaitis.tranformSchool.matrices.ScaleMatrix;
import lt.vpranckaitis.tranformSchool.matrices.SimpleMatrix;
import lt.vpranckaitis.tranformSchool.matrices.TransferableData;
import lt.vpranckaitis.tranformSchool.matrices.TranslateMatrix;

/**
 * Main window of the program.
 * 
 * @author Vilius Pranckaitis
 * 
 */
public class MainWindow extends JFrame implements MatrixDataListener,
	ListSelectionListener, ActionListener, ListDataListener, KeyListener {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7982373494911692615L;

    private static final String EMPTY_PANEL = "empty_panel";
    private static final String EMPTY_PANEL_TEXT = "Please select a matrix above";
    private static final String MODIFIER_MODEL_TEXT = "<html>Modifier: from this point <br>matrices will be considered <br>as model matrices</html>";
    private static final String MODIFIER_VIEW_TEXT = "<html>Modifier: from this point <br>matrices will be considered <br>as view matrices</html>";
    private static final String MODIFIER_PROJECTION_TEXT = "<html>Modifier: from this point <br>matrices will be considered <br>as projection matrices</html>";

    private static final int LEFT_PANEL_WIDTH = 200;

    private JLabel mEmptyP;
    private JLabel mModifierP;
    private FrustumMatrixPanel mFrustumP;
    private LookAtMatrixPanel mLookAtP;
    private OrthoMatrixPanel mOrthoP;
    private PerspectiveMatrixPanel mPerspectiveP;
    private RotateMatrixPanel mRotateSP;
    private RotateMatrixPanel mRotateVP;
    private ScaleMatrixPanel mScaleP;
    private TranslateMatrixPanel mTranslateP;

    private MatrixDisplayPanel mDisplayP;

    private CardLayout mCardLayout;

    private JList<AbstractMatrix> mList;
    private JPanel mPanelData;

    private JLabel mStatusBar;

    private TSCameraView mCanvasNW;
    private TSGLView mCanvasNE;
    private TSGLView mCanvasSW;
    private TSGLView mCanvasSE;

    static {
	GLProfile.initSingleton();
    }

    public MainWindow() throws HeadlessException {
	super();
	init();
    }

    public MainWindow(GraphicsConfiguration gc) {
	super(gc);
	init();
    }

    public MainWindow(String title, GraphicsConfiguration gc) {
	super(title, gc);
	init();
    }

    public MainWindow(String title) throws HeadlessException {
	super(title);
	init();
    }

    private void init() {
	// System.out.println("  " + Arrays.toString(Matrix.multiplyMM(new
	// float[]{-1.0f, 0.0f, -0.0f, 0.0f, 0.0f, 1.0f, -0.0f, 0.0f, 0.0f,
	// -0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}, new float[]{1.0f, 0.0f,
	// 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, -0.0f,
	// -0.0f, 2.0f, 1.0f})));
	this.setSize(new Dimension(800, 600));
	this.setLocation(50, 50);

	GLProfile glp = GLProfile.getDefault();
	GLCapabilities caps = new GLCapabilities(glp);
	mCanvasNW = new TSCameraView(caps);
	mCanvasNE = new TSSideView(caps, -TSSideView.AXIS_Y);
	mCanvasSW = new TSSideView(caps, TSSideView.AXIS_X);
	mCanvasSE = new TSSideView(caps, -TSSideView.AXIS_Z);

	this.getContentPane().setLayout(new BorderLayout());

	JPanel panelTop = new JPanel();
	panelTop.setLayout(new GridLayout(0, 1));
	panelTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
		getBackground().darker()));

	JPanel panelLeft = new JPanel();
	panelLeft.setLayout(new GridLayout(3, 1));
	panelLeft.setMinimumSize(new Dimension(LEFT_PANEL_WIDTH, 0));
	panelLeft.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, -1));
	panelLeft.setMaximumSize(new Dimension(LEFT_PANEL_WIDTH,
		Integer.MAX_VALUE));

	JPanel panelCenter = new JPanel();
	panelCenter.setLayout(new GridLayout(2, 2, 3, 3));

	JPanel panelBottom = new JPanel();
	panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.Y_AXIS));
	panelBottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
		getBackground().darker()));

	JPanel panelRight = new JPanel();

	JToolBar toolbar = new JToolBar();
	toolbar.setFloatable(false);
	toolbar.setVisible(false);

	mStatusBar = new JLabel();
	mStatusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	mStatusBar.setText(" ");

	panelBottom.add(mStatusBar);
	// panelBottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new
	// Color(0x999999)));

	/*
	 * setup list
	 */
	mList = new JList<>();
	mList.setCellRenderer(new ListRenderer());
	mList.setDragEnabled(true);
	mList.setDropMode(DropMode.INSERT);
	mList.setTransferHandler(new MatrixTransferHandler());
	mList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	mList.addListSelectionListener(this);
	DefaultListModel<AbstractMatrix> model = new DefaultListModel<>();
	model.addListDataListener(this);
	mList.setModel(model);
	mList.addKeyListener(this);
	JScrollPane scroll = new JScrollPane(mList);
	panelLeft.add(scroll);
	// panelLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new
	// Color(0x999999)));

	/*
	 * add initial matrices
	 */

	model.addElement(new RotateMatrix(30.0f, RotateMatrix.AXIS_Y));
	model.addElement(new ScaleMatrix(0.2f, 0.2f, 0.2f));
	model.addElement(new TranslateMatrix(0.2f, 0.2f, 0.2f));
	model.addElement(new MatrixModifier(MatrixBase.MODIFIER_VIEW));
	model.addElement(new LookAtMatrix(0.5f, 2.0f, 2.0f, 0.0f, 0.0f, 0.0f,
		0.0f, 1.0f, 0.0f));
	model.addElement(new MatrixModifier(MatrixBase.MODIFIER_PROJECTION));
	model.addElement(new PerspectiveMatrix(30.0f, 1.0f, 1.0f, 4.0f));

	/*
	 * set up matrix panels
	 */
	mEmptyP = new JLabel(EMPTY_PANEL_TEXT);
	mEmptyP.setOpaque(true);
	mEmptyP.setHorizontalAlignment(JLabel.CENTER);
	mModifierP = new JLabel();
	mModifierP.setOpaque(true);
	mModifierP.setHorizontalAlignment(JLabel.CENTER);
	mFrustumP = new FrustumMatrixPanel();
	mFrustumP.addDataChangeListener(this);
	mLookAtP = new LookAtMatrixPanel();
	mLookAtP.addDataChangeListener(this);
	mOrthoP = new OrthoMatrixPanel();
	mOrthoP.addDataChangeListener(this);
	mPerspectiveP = new PerspectiveMatrixPanel();
	mPerspectiveP.addDataChangeListener(this);
	mRotateSP = new RotateMatrixPanel(false);
	mRotateSP.addDataChangeListener(this);
	mRotateVP = new RotateMatrixPanel(true);
	mRotateVP.addDataChangeListener(this);
	mScaleP = new ScaleMatrixPanel();
	mScaleP.addDataChangeListener(this);
	mTranslateP = new TranslateMatrixPanel();
	mTranslateP.addDataChangeListener(this);

	mPanelData = new JPanel();
	mCardLayout = new CardLayout();
	mPanelData.setLayout(mCardLayout);
	int hPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
	int vPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
	mPanelData.add(new JScrollPane(mEmptyP, vPolicy, hPolicy), EMPTY_PANEL);
	mPanelData.add(new JScrollPane(mModifierP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.MODIFIER));
	mPanelData.add(new JScrollPane(mFrustumP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_FRUSTUM));
	mPanelData.add(new JScrollPane(mLookAtP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_LOOK_AT));
	mPanelData.add(new JScrollPane(mOrthoP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_ORTHO));
	mPanelData.add(new JScrollPane(mPerspectiveP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_PERSPECTIVE));
	mPanelData.add(new JScrollPane(mRotateSP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_ROTATE_AXIS));
	mPanelData.add(new JScrollPane(mRotateVP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_ROTATE_VECTOR));
	mPanelData.add(new JScrollPane(mScaleP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_SCALE));
	mPanelData.add(new JScrollPane(mTranslateP, vPolicy, hPolicy),
		Integer.toString(MatrixBase.TYPE_TRANSLATE));
	panelLeft.add(mPanelData);

	mDisplayP = new MatrixDisplayPanel();
	panelLeft.add(mDisplayP);

	/*
	 * menu items
	 */
	JMenuBar menubar = new JMenuBar();

	JMenu fileM = new JMenu("File");
	JMenu insertM = new JMenu("Insert");
	JMenu helpM = new JMenu("Help");

	helpM.add(new AbstractAction("About") {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String msg = "Transform School\n(c) Vilius Pranckaitis\n2013";
		JOptionPane.showMessageDialog(MainWindow.this, msg, "About",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	});

	JMenu insertMatrixM = new JMenu("Matrix");
	insertMatrixM.add(new InsertMatrixAction("Rotate (Axis)",
		MatrixBase.TYPE_ROTATE_AXIS, this));
	insertMatrixM.add(new InsertMatrixAction("Rotate (Vector)",
		MatrixBase.TYPE_ROTATE_VECTOR, this));
	insertMatrixM.add(new InsertMatrixAction("Scale",
		MatrixBase.TYPE_SCALE, this));
	insertMatrixM.add(new InsertMatrixAction("Translate",
		MatrixBase.TYPE_TRANSLATE, this));
	insertMatrixM.addSeparator();
	insertMatrixM.add(new InsertMatrixAction("Look At",
		MatrixBase.TYPE_LOOK_AT, this));
	insertMatrixM.addSeparator();
	insertMatrixM.add(new InsertMatrixAction("Frustum",
		MatrixBase.TYPE_FRUSTUM, this));
	insertMatrixM.add(new InsertMatrixAction("Perspective",
		MatrixBase.TYPE_PERSPECTIVE, this));
	insertMatrixM.add(new InsertMatrixAction("Ortho",
		MatrixBase.TYPE_ORTHO, this));

	JMenu insertModifierM = new JMenu("Modifier");
	insertModifierM.add(new InsertMatrixAction("Consider Model",
		MatrixBase.MODIFIER_MODEL, this));
	insertModifierM.add(new InsertMatrixAction("Consider View",
		MatrixBase.MODIFIER_VIEW, this));
	insertModifierM.add(new InsertMatrixAction("Consider Projection",
		MatrixBase.MODIFIER_PROJECTION, this));

	insertM.add(insertMatrixM);
	insertM.add(insertModifierM);
	menubar.add(insertM);
	menubar.add(helpM);

	/*
	 * setup layout
	 */
	panelTop.add(menubar);
	panelTop.add(toolbar);

	this.add(panelTop, BorderLayout.NORTH);
	this.add(panelLeft, BorderLayout.WEST);
	this.add(panelCenter, BorderLayout.CENTER);
	this.add(panelRight, BorderLayout.EAST);
	this.add(panelBottom, BorderLayout.SOUTH);

	panelCenter.add(mCanvasNW);
	panelCenter.add(mCanvasNE);
	panelCenter.add(mCanvasSW);
	panelCenter.add(mCanvasSE);

	setMatrices();
	// mList.setSelectedIndex(0);

    }

    /*
     * private void setMatrices() { DefaultListModel<AbstractMatrix> model =
     * (DefaultListModel<AbstractMatrix>) mList.getModel(); float[][] m = new
     * float[][]{Matrix.getIdentityM(), Matrix.getIdentityM(),
     * Matrix.getIdentityM(),Matrix.getIdentityM()}; int groupCheck = 0; boolean
     * goodOrder = true; for(int i = 0; i < model.getSize(); i++) { float[]
     * matrix = model.get(i).getMatrix(); m[0] = Matrix.multiplyMM(matrix,
     * m[0]); if(goodOrder) { int group = model.get(i).getType() &
     * MatrixBase.GROUP_MASK; switch(group) { case MatrixBase.TYPE_MODEL: m[1] =
     * Matrix.multiplyMM(matrix, m[1]); break; case MatrixBase.TYPE_VIEW: m[2] =
     * Matrix.multiplyMM(matrix, m[2]); break; case MatrixBase.TYPE_PROJECTION:
     * m[3] = Matrix.multiplyMM(matrix, m[3]); break; default: break; }
     * goodOrder = group >= groupCheck; groupCheck = group; } } if(goodOrder) {
     * mCanvasNW.setMVPMatrix(m[1], m[2], m[3]); mCanvasNE.setMVPMatrix(m[1],
     * m[2], m[3]); mCanvasSW.setMVPMatrix(m[1], m[2], m[3]);
     * mCanvasSE.setMVPMatrix(m[1], m[2], m[3]); setWarning(" "); } else {
     * mCanvasNW.setMVPMatrix(m[0]); mCanvasNE.setMVPMatrix(m[0]);
     * mCanvasSW.setMVPMatrix(m[0]); mCanvasSE.setMVPMatrix(m[0]); setWarning(
     * "Warning: matrix list doesn't convey Model-View-Projection order. Some parts of display are hidden"
     * ); }
     * 
     * mCanvasNW.display(); mCanvasNE.display(); mCanvasSW.display();
     * mCanvasSE.display(); }
     */

    /**
     * Calculates and sets Model, View and Projection matrices to the OpenGL
     * views
     */
    private void setMatrices() {
	DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) mList
		.getModel();
	float[][] m = new float[][] { Matrix.getIdentityM(),
		Matrix.getIdentityM(), Matrix.getIdentityM() };
	int j = 0;
	for (int i = 0; i < model.getSize(); i++) {
	    int type = model.get(i).getType();
	    if ((type & MatrixBase.MODIFIER) > 0) {
		if (type == MatrixBase.MODIFIER_MODEL) {
		    j = 0;
		} else if (type == MatrixBase.MODIFIER_VIEW) {
		    j = 1;
		} else if (type == MatrixBase.MODIFIER_PROJECTION) {
		    j = 2;
		}
	    } else {
		float[] matrix = model.get(i).getMatrix();
		m[j] = Matrix.multiplyMM(matrix, m[j]);
	    }
	}
	mCanvasNW.setMVPMatrix(m[0], m[1], m[2]);
	mCanvasNE.setMVPMatrix(m[0], m[1], m[2]);
	mCanvasSW.setMVPMatrix(m[0], m[1], m[2]);
	mCanvasSE.setMVPMatrix(m[0], m[1], m[2]);

	mCanvasNW.display();
	mCanvasNE.display();
	mCanvasSW.display();
	mCanvasSE.display();
    }

    @Override
    public void dataChanged(TransferableData d) {
	int index = mList.getSelectedIndex();
	DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) mList
		.getModel();
	AbstractMatrix matrix = model.get(index);
	matrix.setData(d);
	mDisplayP.setMatrix(d.matrix);
	mList.repaint();
	setMatrices();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
	int index = mList.getSelectedIndex();
	DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) mList
		.getModel();
	if (index < 0 || index > model.getSize()) {
	    ((CardLayout) mPanelData.getLayout()).show(mPanelData, EMPTY_PANEL);
	    mEmptyP.setText(EMPTY_PANEL_TEXT);
	    return;
	}
	AbstractMatrix matrix = model.get(index);
	if ((matrix.getType() & MatrixBase.MODIFIER) > 0) {
	    mCardLayout.show(mPanelData, Integer.toString(MatrixBase.MODIFIER));
	} else {
	    mCardLayout.show(mPanelData, Integer.toString(matrix.getType()));
	}
	switch (matrix.getType()) {
	case MatrixBase.TYPE_FRUSTUM:
	    mFrustumP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_LOOK_AT:
	    mLookAtP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_ORTHO:
	    mOrthoP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_PERSPECTIVE:
	    mPerspectiveP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_ROTATE_AXIS:
	    mRotateSP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_ROTATE_VECTOR:
	    mRotateVP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_SCALE:
	    mScaleP.setData(matrix.getData());
	    break;
	case MatrixBase.TYPE_TRANSLATE:
	    mTranslateP.setData(matrix.getData());
	    break;
	case MatrixBase.MODIFIER_MODEL:
	    mModifierP.setText(MODIFIER_MODEL_TEXT);
	    break;
	case MatrixBase.MODIFIER_VIEW:
	    mModifierP.setText(MODIFIER_VIEW_TEXT);
	    break;
	case MatrixBase.MODIFIER_PROJECTION:
	    mModifierP.setText(MODIFIER_PROJECTION_TEXT);
	    break;
	default:
	    break;
	}
	mDisplayP.setMatrix(matrix.getMatrix());
    }

    private void setWarning(String warning) {
	mStatusBar.setText(warning);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	Object o = e.getSource();
	if (o.getClass().equals(InsertMatrixAction.class)) {
	    AbstractMatrix m = new SimpleMatrix();

	    InsertMatrixAction a = (InsertMatrixAction) o;
	    switch (a.getType()) {
	    case MatrixBase.TYPE_FRUSTUM:
		m = new FrustumMatrix();
		break;
	    case MatrixBase.TYPE_LOOK_AT:
		m = new LookAtMatrix();
		break;
	    case MatrixBase.TYPE_ORTHO:
		m = new OrthoMatrix();
		break;
	    case MatrixBase.TYPE_PERSPECTIVE:
		m = new PerspectiveMatrix();
		break;
	    case MatrixBase.TYPE_ROTATE_AXIS:
		m = new RotateMatrix(false);
		break;
	    case MatrixBase.TYPE_ROTATE_VECTOR:
		m = new RotateMatrix(true);
		break;
	    case MatrixBase.TYPE_SCALE:
		m = new ScaleMatrix();
		break;
	    case MatrixBase.TYPE_TRANSLATE:
		m = new TranslateMatrix();
		break;
	    case MatrixBase.MODIFIER_MODEL:
		m = new MatrixModifier(MatrixBase.MODIFIER_MODEL);
		break;
	    case MatrixBase.MODIFIER_PROJECTION:
		m = new MatrixModifier(MatrixBase.MODIFIER_PROJECTION);
		break;
	    case MatrixBase.MODIFIER_VIEW:
		m = new MatrixModifier(MatrixBase.MODIFIER_VIEW);
		break;
	    default:
		m = null;
		break;
	    }

	    if (m != null) {
		int index = mList.getSelectedIndex();
		DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) mList
			.getModel();
		if (index >= 0 && index + 1 < model.size()) {
		    model.add(index + 1, m);
		    mList.setSelectedIndex(index + 1);
		} else {
		    model.addElement(m);
		    mList.setSelectedIndex(model.size() - 1);
		}
		setMatrices();
	    }
	}
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
	setMatrices();
    }

    @Override
    public void intervalAdded(ListDataEvent e) {
	setMatrices();
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
	setMatrices();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_DELETE) {
	    int index = mList.getSelectedIndex();
	    DefaultListModel<AbstractMatrix> model = (DefaultListModel<AbstractMatrix>) mList
		    .getModel();
	    if (index >= 0 && index < model.size()) {
		model.remove(index);
	    }
	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Class which styles the {@link JList} elements.
     * 
     * @author Vilius Pranckaitis
     * 
     */
    private static final class ListRenderer extends DefaultListCellRenderer {

	/**
		 * 
		 */
	private static final long serialVersionUID = -5800993660223737244L;

	private static final Color COLOR_MODIFIER = new Color(0x333333);
	private static final Color COLOR_MODIFIER_TEXT = new Color(0xffffff);
	private static final Color COLOR_MODEL = new Color(0xffcccc);
	private static final Color COLOR_VIEW = new Color(0xccffcc);
	private static final Color COLOR_PROJECTION = new Color(0xccccff);

	@Override
	public Component getListCellRendererComponent(JList<?> list,
		Object value, int index, boolean isSelected,
		boolean cellHasFocus) {
	    // TODO Auto-generated method stub
	    Component c = super.getListCellRendererComponent(list, value,
		    index, isSelected, cellHasFocus);
	    if (!isSelected) {
		AbstractMatrix m = (AbstractMatrix) value;
		Color color = new Color(0xffffff);
		if ((m.getType() & MatrixBase.MODIFIER) > 0) {
		    color = COLOR_MODIFIER;
		    c.setForeground(COLOR_MODIFIER_TEXT);
		} else if ((m.getType() & MatrixBase.TYPE_MODEL) > 0) {
		    color = COLOR_MODEL;
		} else if ((m.getType() & MatrixBase.TYPE_VIEW) > 0) {
		    color = COLOR_VIEW;
		} else if ((m.getType() & MatrixBase.TYPE_PROJECTION) > 0) {
		    color = COLOR_PROJECTION;
		}
		c.setBackground(color);
	    }
	    return c;
	}

    }
}
