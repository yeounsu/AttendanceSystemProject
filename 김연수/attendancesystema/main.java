package attendancesystema;

import javax.swing.SwingUtilities;

public class main extends ImageViewer{

	  public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new ImageViewer();
	        });
	    }
}
