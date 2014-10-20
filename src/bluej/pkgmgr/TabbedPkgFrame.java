package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import bluej.BlueJEvent;
import bluej.BlueJTheme;
import bluej.Config;
import bluej.extmgr.MenuManager;
import bluej.pkgmgr.PkgMgrFrame.ProjectOpener;
import bluej.prefmgr.PrefMgr;
import bluej.prefmgr.PrefMgrDialog;
import bluej.utility.Debug;
import bluej.utility.DialogManager;
import bluej.utility.FileUtility;
import bluej.utility.Utility;

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
	    
		recentProjectsMenu = new JMenu(Config.getString("menu.package.openRecent"));
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
		
		menuMgr.setupMenu(menubar,recentProjectsMenu);
	}

	public TabbedFrameUnit createFrame(Package pkg){
		TabbedFrameUnit tfu = new TabbedFrameUnit(pkg);
		pkgTabs.add(tfu);
				
		return tfu;
	}
	
	public TabbedFrameUnit createFrame(){
		TabbedFrameUnit tfu = new TabbedFrameUnit();
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
    
    public void doOpenWindow(){
    	TabbedPkgFrame frame = new TabbedPkgFrame();
     	frame.setVisible(true);
 
    }
    
    /**
     * Preferences menu was chosen.
     */
    public void showPreferences()
    {
        PrefMgrDialog.showDialog();
    }

    /**
     * About menu was chosen.
     */
    public void aboutBlueJ()
    {
        AboutBlue about = new AboutBlue(this, bluej.Boot.BLUEJ_VERSION);
        about.setVisible(true);
    }

    /**
     * Copyright menu item was chosen.
     */
    public void showCopyright()
    {
        JOptionPane.showMessageDialog(this, new String[]{
                Config.getString("menu.help.copyright.line0"), " ",
                Config.getString("menu.help.copyright.line1"), Config.getString("menu.help.copyright.line2"),
                Config.getString("menu.help.copyright.line3"), Config.getString("menu.help.copyright.line4"),
                },
                Config.getString("menu.help.copyright.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * showWebPage - show a page in a web browser and display a message in the
     * status bar.
     */
    public void showWebPage(String url)
    {
        if (Utility.openWebBrowser(url))
            setStatus(Config.getString("pkgmgr.webBrowserMsg"));
        else
            setStatus(Config.getString("pkgmgr.webBrowserError"));
    }
    
    /**
     * Allow the user to select a directory into which we create a project.
     * @param isJavaMEproject   Whether this is a Java Micro Edition project or not.
     * @return true if the project was successfully created. False otherwise.
     */
    public boolean doNewProject( boolean isJavaMEproject )
    {
        String title = Config.getString( "pkgmgr.newPkg.title" );
        if ( isJavaMEproject )
            title = Config.getString( "pkgmgr.newMEpkg.title" );
                    
        File newnameFile = FileUtility.getDirName( this, title,
                 Config.getString( "pkgmgr.newPkg.buttonLabel" ), false, true );

        if (newnameFile == null)
            return false;

        if(newnameFile.exists()) {
            Debug.message("Attempt to create project with existing directory: " + newnameFile.getAbsolutePath());
            DialogManager.showErrorWithText(null, "directory-exists", newnameFile.getPath());
            return false;
        }
        else if(!newProject( newnameFile.getAbsolutePath(), isJavaMEproject ) ) {
            DialogManager.showErrorWithText(null, "cannot-create-directory", newnameFile.getPath());
            return false;
        }

        return true;
    }
    
    /**
     * Create a new project and display it in a frame.
     * @param dirName           The directory to create the project in
     * @param isJavaMEproject   Whether to create a Java Micro Edition project
     * @return     true if successful, false otherwise
     */
    public boolean newProject(String dirName, boolean isJavaMEproject )
    {
        if (Project.createNewProject(dirName, isJavaMEproject)) {
            Project proj = Project.openProject(dirName, this);
            
            Package unNamedPkg = proj.getPackage("");
            
            if (recentFrame.isEmptyFrame()) {
                recentFrame.openPackage(unNamedPkg);
            }
            else {
                TabbedFrameUnit pmf = createFrame(unNamedPkg);
                DialogManager.tileWindow(pmf, this);
                pmf.setVisible(true);
            }    
            return true;
        }
        return false;
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

           //pmf.setVisible(true);

           return true;
       }
   }
   
   /**
    * Open a dialog that lets the user choose a project. The project selected
    * is opened in a frame.
    */
   public void doOpen()
   {
       File dirName = FileUtility.getPackageName(this);
       doOpen(dirName, null);
   }
   
   /**
    * Opens either a project from a directory or an archive.
    * 
    * @param pmf Optional parameter. Used for displaying dialogs and reuse
    *            if it is the empty frame.
    */
   public boolean doOpen(File projectPath, TabbedFrameUnit pmf)
   {     
       boolean createdNewFrame = false;
       if(pmf == null && frameCount() > 0) {
           pmf = pkgTabs.get(0);
       }
       else if(pmf == null) {
           pmf = createFrame();
           createdNewFrame = true;
       }

       boolean openedProject = false;
       if (projectPath != null) {
           if (projectPath.isDirectory() || Project.isProject(projectPath.toString())) {
               if(openProject(projectPath.getAbsolutePath())) {
                   openedProject = true;
               }
           }
           else {
               if(openArchive(projectPath)) {
                   openedProject = true;
               }
           }
       }
       if(createdNewFrame && !openedProject) {
           // Close newly created frame if it was never used.
           closeFrame(pmf);
       }
       return openedProject;
   }
   
   /**
    * Open an archive file (jar or same contents with other extensions) as a
    * BlueJ project. The file contents are extracted, the containing directory
    * is then converted into a BlueJ project if necessary, and opened.
    */
   private boolean openArchive(File archive)
   {
       // Determine the output path.
       File oPath = Utility.maybeExtractArchive(archive, this);
       
       if (oPath == null)
           return false;
       
       if (Project.isProject(oPath.getPath())) {
           return openProject(oPath.getPath());
       }
       else {
           // Convert to a BlueJ project
           if (Import.convertNonBlueJ(this, oPath)) {
               return openProject(oPath.getPath());
           }
           else {
               return false;
           }
       }        
   }
   
   /**
    * Open a dialog that lets a user convert existing Java source into a BlueJ
    * project.
    * 
    * The project selected is opened in a frame.
    */
   public void doOpenNonBlueJ()
   {
       File dirName = FileUtility.getNonBlueJDirectoryName(this);

       if (dirName == null)
           return;

       File absDirName = dirName.getAbsoluteFile();
       
       // First confirm the chosen file exists
       if (! absDirName.exists()) {
           // file doesn't exist
           DialogManager.showError(this, "file-does-not-exist");
           return;
       }
       
       if (absDirName.isDirectory()) {
           // Check to make sure it's not already a project
           if (Project.isProject(absDirName.getPath())) {
               DialogManager.showError(this, "open-non-bluej-already-bluej");
               return;
           }
           
           // Try and convert it to a project
           if (! Import.convertNonBlueJ(this, absDirName))
               return;
           
           // then construct it as a project
           openProject(absDirName.getPath());
       }
       else {
           // Presumably it's an archive file
           openArchive(absDirName);
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

    

