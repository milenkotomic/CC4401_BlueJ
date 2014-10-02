package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.Image;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import bluej.BlueJTheme;
import bluej.Config;
import bluej.pkgmgr.actions.NewMEprojectAction;
import bluej.pkgmgr.actions.NewProjectAction;
import bluej.pkgmgr.actions.NewTabAction;
import bluej.pkgmgr.actions.NewWindowAction;
import bluej.pkgmgr.actions.OpenNonBlueJAction;
import bluej.pkgmgr.actions.OpenProjectAction;
import bluej.pkgmgr.actions.QuitAction;

public class TabbedPkgFrame extends AbstractPkgFrame {
	JTabbedPane jtp;
	PkgFrameMenu menuMgr;
	private static List<JPanel> pkgTabs;
	private TabbedPkgFrame recentFrame = null;
	
	public TabbedPkgFrame(){
		setupWindow();
		
		menuMgr = new PkgFrameMenu();		
		jtp = new JTabbedPane();
		getContentPane().add(jtp); //Incluye las pestañas en el JPanel actual, sin esto, no se ve nada!
		
		//Crear nueva pestaña
		newTab();
        
        setupMenu();
  
	}

	private void newTab(){
		JPanel newTab = new JPanel();
        JLabel lab = new JLabel();
        
        lab.setText("You are in area of Tab");
        pkgTabs.add(newTab);
		        
        newTab.add(lab); 
        jtp.addTab("Tab1", newTab);
		
	}
	
	private void setupWindow(){
		setTitle("BlueJ");
		
		Image icon = BlueJTheme.getIconImage();
	    if (icon != null) {
	    	setIconImage(icon);
	    }
	   
	    setSize(new Dimension(695, 575));
	    setLocation(20,20);
	    
	}
	
	private void setupMenu(){
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		menuMgr.setupMenu(menubar);
	}
	
	 /**
     * Returns an array of all TabbedPkgFrame objects. It can be an empty array if
     * none is found.
     */
    public static TabbedPkgFrame[] getAllFrames(){
    
        TabbedPkgFrame[] openFrames = new TabbedPkgFrame[pkgTabs.size()];
        pkgTabs.toArray(openFrames);

        return openFrames;
    }
	
	
	public TabbedPkgFrame getMostRecent(){
		
        if (recentFrame != null) {
            return recentFrame;
        }
        
        TabbedPkgFrame[] allFrames = getAllFrames();

        // If there are no frames open, yet...
        if (allFrames.length < 1) {
            return null;
        }

        // Assume that the most recent is the first one. Not really the best
        // thing to do...
        TabbedPkgFrame mostRecent = allFrames[0];

        for (int i = 0; i < allFrames.length; i++) {
            if (allFrames[i].getFocusOwner() != null) {
                mostRecent = allFrames[i];
            }
        }
        return mostRecent;
		
	}
	
	
	

}
