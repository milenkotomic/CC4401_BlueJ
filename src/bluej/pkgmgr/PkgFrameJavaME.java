package bluej.pkgmgr;

import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PkgFrameJavaME {
    private JMenuItem javaMEdeployMenuItem;
    private JPanel javaMEPanel;
	
	
	/**
    * Show or hide the Java ME controls.
    */
    public void showJavaMEcontrols(boolean show)
    {           
        javaMEdeployMenuItem.setVisible(show);
        javaMEPanel.setVisible(show);              
    }
	
	
	
}
