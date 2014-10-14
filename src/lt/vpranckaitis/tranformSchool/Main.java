package lt.vpranckaitis.tranformSchool;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
    private static MainWindow mWindow;

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	mWindow = new MainWindow("3D Transformation");
	mWindow.setVisible(true);
	mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
