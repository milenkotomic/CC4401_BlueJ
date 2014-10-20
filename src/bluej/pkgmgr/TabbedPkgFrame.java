package bluej.pkgmgr;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import bluej.BlueJEvent;
import bluej.BlueJTheme;
import bluej.Config;
import bluej.extmgr.MenuManager;
import bluej.pkgmgr.PkgMgrFrame.ProjectOpener;
import bluej.pkgmgr.actions.NewMEprojectAction;
import bluej.pkgmgr.actions.NewProjectAction;
import bluej.pkgmgr.actions.NewTabAction;
import bluej.pkgmgr.actions.NewWindowAction;
import bluej.pkgmgr.actions.OpenNonBlueJAction;
import bluej.pkgmgr.actions.OpenProjectAction;
import bluej.pkgmgr.actions.QuitAction;
import bluej.prefmgr.PrefMgr;
import bluej.utility.DialogManager;
import bluej.utility.FileUtility;

public class TabbedPkgFrame extends AbstractPkgFrame {
	JTabbedPane jtp;
	PkgFrameMenu menuMgr;
	PkgFrameTestingMenu testMenu;
	private JLabel statusbar;
	private JMenu recentProjectsMenu;
	private static boolean testToolsShown;
	
	
	private MenuManager toolsMenuManager;
	private MenuManager viewMenuManager;
	
	private static List<TabbedFrameUnit> pkgTabs = new ArrayList<TabbedFrameUnit>();
	protected TabbedFrameUnit recentFrame = null;
		
	public TabbedPkgFrame(){
		setupWindow();
		
		menuMgr = new PkgFrameMenu();
		testMenu = new PkgFrameTestingMenu();
		
		jtp = new JTabbedPane();
		getContentPane().add(jtp); //Incluye las pestañas en el JPanel actual, sin esto, no se ve nada!
		
		recentFrame = new TabbedFrameUnit();
		pkgTabs.add(recentFrame);
        
		jtp.addTab("Tab1", recentFrame.getTab());
	         
        setupMenu();
        
        testToolsShown = testMenu.wantToSeeTestingTools();
  
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

	public TabbedFrameUnit createFrame(Package pkg){
		TabbedFrameUnit tfu = new TabbedFrameUnit(pkg);
		pkgTabs.add(tfu);
				
		return tfu;
		
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
	
    /**
     * @return the number of currently open top level frames
     */
    public static int frameCount()
    {
        return pkgTabs.size();
    }

    /**
     * Find a frame which is editing a particular Package and return it or
     * return null if it is not being edited
     */
    public static TabbedFrameUnit findFrame(Package pkg)
    {
        for (Iterator<TabbedFrameUnit> i = pkgTabs.iterator(); i.hasNext();) {
            TabbedFrameUnit pmf = i.next();

            if (!pmf.isEmptyFrame() && pmf.getPackage() == pkg)
                return pmf;
        }
        return null;
    }
    
    
    
    /**
     * Update the window title and show needed messages
     */
    private void updateWindow()
    {
        recentFrame.updateWindow();
      
    }
    
    
    /**
     * Remove a frame from the set of currently open PkgMgrFrames. The
     * PkgMgrFrame must not be editing a package when this function is called.
     */
    public static void closeFrame(TabbedFrameUnit frame)
    {
        if (!frame.isEmptyFrame())
            throw new IllegalArgumentException();

        pkgTabs.remove(frame);

        BlueJEvent.removeListener(frame);
        PrefMgr.setFlag(PrefMgr.SHOW_TEXT_EVAL, frame.isTextEvalVisible());

        // frame should be garbage collected but we will speed it
        // on its way
        //pkgTabs.dispose();
    }
           
    /*ACTIONS*/
        
    public void doOpenTab(){
		TabbedFrameUnit newTab = new TabbedFrameUnit();
        pkgTabs.add(newTab);
		        
		jtp.addTab("Tab1", newTab.getTab());
	}
    
    /**
     * Perform a user initiated close of this frame/package.
     * 
     * There are two different methods for the user to initiate a close. One is
     * through the "Close" menu item and the other is with the windows close
     * button. We want slightly different behaviour for these two cases.
     */
    public void doClose(boolean keepLastFrame, boolean doSave)
    {
        if (doSave) {
            recentFrame.doSave();
        }

        // If only one frame and this was from the menu
        // "close", close should close existing package rather
        // than remove frame

        if (frameCount() == 1) {
            if (keepLastFrame) {
                recentFrame.testRecordingEnded(); // disable test controls
                recentFrame.closePackage();
                
                updateRecentProjects();
                menuMgr.enableFunctions(false); // changes menu items
                updateWindow();
                toolsMenuManager.addExtensionMenu(null);
                viewMenuManager.addExtensionMenu(null);
            }
            else { // all frames gone, lets quit
                bluej.Main.doQuit();
            }
        }
        else {
           recentFrame.closePackage(); // remove package and frame
           closeFrame(recentFrame);
        }
    }
    
    /**
     * Update the 'Open Recent' menu
     */
    private void updateRecentProjects()
    {
        ProjectOpener opener = new ProjectOpener();
        recentProjectsMenu.removeAll();

        List<String> projects = PrefMgr.getRecentProjects();
        for (Iterator<String> it = projects.iterator(); it.hasNext();) {
            JMenuItem item = recentProjectsMenu.add(it.next());
            item.addActionListener(opener);
        }
    }

    
    /**
     * Implementation of the "Add Class from File" user function
     */
    public void doAddFromFile()
    {
        // multi selection file dialog that shows .java and .class files
        File[] classes = FileUtility.getMultipleFiles(this, Config.getString("pkgmgr.addClass.title"), Config
                .getString("pkgmgr.addClass.buttonLabel"), FileUtility.getJavaSourceFilter());

        if (classes == null)
            return;
        
        recentFrame.importFromFile(classes);
    }
    
	
	 /**
    * Check whether the status of the 'Show unit test tools' preference has
    * changed, and if it has, show or hide them as requested.
    */
   public void updateTestingStatus()
   {
       if (testToolsShown != testMenu.wantToSeeTestingTools()) {
           for (Iterator<TabbedFrameUnit> i = pkgTabs.iterator(); i.hasNext();) {
             
               TabbedFrameUnit pmf = i.next();
               
               //Testing tools are always hidden in Java ME packages.  
               if (pmf.isJavaMEpackage()) {
            	   testMenu.showTestingTools(false);
               }
               else {
                   testMenu.showTestingTools(!testToolsShown);               
               }
           }
           testToolsShown = !testToolsShown;
       }
   }

   /**
    * Open the project specified by 'projectPath'. Return false if not
    * successful. Displays a warning dialog if the opened project resides in
    * a read-only directory.
    */
   private boolean openProject(String projectPath)
   {
       Project openProj = Project.openProject(projectPath, this);
       if (openProj == null)
           return false;
       else {
           Package initialPkg = openProj.getPackage(openProj.getInitialPackageName());

           TabbedFrameUnit pmf = findFrame(initialPkg);

           if (pmf == null) {
               if (recentFrame.isEmptyFrame()) {
                   pmf = recentFrame;
                   //pmf.openPackage(initialPkg);
               }
               else {
                   pmf = createFrame(initialPkg);

                   DialogManager.tileWindow(pmf, this);
               }
           }

           pmf.setVisible(true);

           return true;
       }
   }
   
   /**
    * Display a message in the status bar of the frame
    */
   public final void setStatus(final String status)
   {
        EventQueue.invokeLater(new Runnable() {
           public void run() {
               if (statusbar != null)
                   statusbar.setText(status);
           }
       });
       
   }
   
   
	class ProjectOpener implements ActionListener{
	
		public ProjectOpener()
		{}
	
			@Override
		   public void actionPerformed(ActionEvent evt)
		   {
		       String project = evt.getActionCommand();
		       if (!openProject(project))
		           setStatus(Config.getString("pkgmgr.error.open"));
		   }
	}
    
}

    

