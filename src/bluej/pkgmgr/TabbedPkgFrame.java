package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.Image;

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
	
	public TabbedPkgFrame(){
		setupWindow();
				
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
		
		JMenu menu = new JMenu(Config.getString("menu.package"));
	    int mnemonic = Config.getMnemonicKey("menu.package");
	    menu.setMnemonic(mnemonic);
	    menubar.add(menu);
	        
	    {
	            createMenuItem(NewProjectAction.getInstance(), menu);
	            //javaMEnewProjMenuItem = createMenuItem( NewMEprojectAction.getInstance(), menu );            
	            createMenuItem(OpenProjectAction.getInstance(), menu);
	            //recentProjectsMenu = new JMenu(Config.getString("menu.package.openRecent"));
	            //menu.add(recentProjectsMenu);
	            createMenuItem(OpenNonBlueJAction.getInstance(), menu);
	            //createMenuItem(closeProjectAction, menu);
	            //createMenuItem(saveProjectAction, menu);
	            //createMenuItem(saveProjectAsAction, menu);
	            menu.addSeparator();
	            
//	            createMenuItem(importProjectAction, menu);
//	            createMenuItem(exportProjectAction, menu);
//	            javaMEdeployMenuItem = createMenuItem( deployMIDletAction, menu ); 
//	            javaMEdeployMenuItem.setVisible( false ); //visible only in Java ME packages
//	            menu.addSeparator();
//
//	            createMenuItem(pageSetupAction, menu);
//	            createMenuItem(printAction, menu);

	            if (!Config.usingMacScreenMenubar()) { // no "Quit" here for Mac
	                menu.addSeparator();
	                createMenuItem(QuitAction.getInstance(), menu);
	            }
	        }
		
		
		menu = new JMenu(Config.getString("menu.view"));
	    menu.setMnemonic(Config.getMnemonicKey("menu.view"));
	    menubar.add(menu);
		
	}
	


}
