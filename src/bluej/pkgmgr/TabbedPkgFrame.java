package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.EventQueue;
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
	private JLabel statusbar;
	
	private static List<TabbedFrameUnit> pkgTabs;
	protected TabbedFrameUnit recentFrame = null;
		
	public TabbedPkgFrame(){
		setupWindow();
		
		menuMgr = new PkgFrameMenu();
		jtp = new JTabbedPane();
		getContentPane().add(jtp); //Incluye las pestañas en el JPanel actual, sin esto, no se ve nada!
			
		//Crear nueva pestaña
		JPanel jp1 = new JPanel();
        JLabel label1 = new JLabel();
        
        label1.setText("You are in area of Tab1");
             
        jp1.add(label1); 
        jtp.addTab("Tab1", jp1);
        
        setupMenu();
  
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

	public void doOpenTab(){
		TabbedFrameUnit newTab = new TabbedFrameUnit();
        pkgTabs.add(newTab);
		        
		jtp.addTab("Tab1", newTab.getTab());
	}
	
	 /**
     * Called on (almost) every menu invocation to clean up.
     */
    public void menuCall()
    {
        if (!recentFrame.isEmptyFrame())
            recentFrame.setState();
        clearStatus();
    }
	
    /**
     * Clear status bar of the frame
     */
    public void clearStatus()
    {
       EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (statusbar != null)
                    statusbar.setText(" ");
            }
        });
    }
	
}
