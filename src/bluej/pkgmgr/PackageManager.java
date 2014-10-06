package bluej.pkgmgr;

import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JCheckBoxMenuItem;

import bluej.Config;
import bluej.collect.DataCollector;
import bluej.extmgr.ToolsExtensionMenu;
import bluej.extmgr.ViewExtensionMenu;
import bluej.utility.Debug;

public class PackageManager {
	private Package pkg = null;
	private PackageEditor editor = null;
		
	private JCheckBoxMenuItem showUsesMenuItem;
    private JCheckBoxMenuItem showExtendsMenuItem;
		
	public PackageManager(){

		
	}
    
	 /**
     * Return the package shown by this frame.
     * 
     * This call should be bracketed by a call to isEmptyFrame() before use.
     */
    public Package getPackage()
    {
        return pkg;
    }

	
	/**
     * Displays the package in the frame for editing
     */
    public void openPackage(Package pkg, IPkgFrame frame)
    {
        if (pkg == null) {
            throw new NullPointerException();
        }

        // if we are already editing a package, close it and
        // open the new one
        if (this.pkg != null) {
            closePackage();
        }

        this.pkg = pkg;

        editor = new PackageEditor(pkg, this, this);
        editor.getAccessibleContext().setAccessibleName(Config.getString("pkgmgr.graphEditor.title"));
        editor.setFocusable(true);
        editor.setTransferHandler(new FileTransferHandler(this));
        editor.addMouseListener(this); // This mouse listener MUST be before
        editor.addFocusListener(this); //  the editor's listener itself!
        editor.startMouseListening();
        pkg.setEditor(this.editor);
        addCtrlTabShortcut(editor);
            
            classScroller.setViewportView(editor);
            
            // fetch some properties from the package that interest us
            Properties p = pkg.getLastSavedProperties();
            
            try {
                String width_str = p.getProperty("package.editor.width", Integer.toString(DEFAULT_WIDTH));
                String height_str = p.getProperty("package.editor.height", Integer.toString(DEFAULT_HEIGHT));
                
                classScroller.setPreferredSize(new Dimension(Integer.parseInt(width_str), Integer.parseInt(height_str)));
                
                String objectBench_height_str = p.getProperty("objectbench.height");
                String objectBench_width_str = p.getProperty("objectbench.width");
                if (objectBench_height_str != null && objectBench_width_str != null) {
                    objbench.setPreferredSize(new Dimension(Integer.parseInt(objectBench_width_str),
                            Integer.parseInt(objectBench_height_str)));
                }
                
                String x_str = p.getProperty("package.editor.x", "30");
                String y_str = p.getProperty("package.editor.y", "30");
                
                int x = Integer.parseInt(x_str);
                int y = Integer.parseInt(y_str);
                
                if (x > (Config.screenBounds.width - 80))
                    x = Config.screenBounds.width - 80;
                
                if (y > (Config.screenBounds.height - 80))
                    y = Config.screenBounds.height - 80;
                
                setLocation(x, y);
            } catch (NumberFormatException e) {
                Debug.reportError("Could not read preferred project screen position");
            }
            
            String uses_str = p.getProperty("package.showUses", "true");
            String extends_str = p.getProperty("package.showExtends", "true");
            
            showUsesMenuItem.setSelected(uses_str.equals("true"));
            showExtendsMenuItem.setSelected(extends_str.equals("true"));
            
            updateShowUsesInPackage();
            updateShowExtendsInPackage();
            
            pack();
            editor.revalidate();
            editor.requestFocus();
            
            enableFunctions(true); // changes menu items
            updateWindow();
            setVisible(true);
            
            updateTextEvalBackground(isEmptyFrame());
                    
            this.toolsMenuManager.setMenuGenerator(new ToolsExtensionMenu(pkg));
            this.toolsMenuManager.addExtensionMenu(pkg.getProject());

            this.viewMenuManager.setMenuGenerator(new ViewExtensionMenu(pkg));
            this.viewMenuManager.addExtensionMenu(pkg.getProject());
        
            teamActions = pkg.getProject().getTeamActions();
            resetTeamActions();             
           
            // In Java-ME packages, we display Java-ME controls in the
            // test panel. We are just using the real estate of the test panel.
            // The rest of the testing tools (menus, etc) are always hidden.
            if (getProject().isJavaMEProject()) {
                showJavaMEcontrols(true);
                showTestingTools(false);
            }
            else {
                showTestingTools(wantToSeeTestingTools());
            }                
        }
        
        DataCollector.packageOpened(pkg);

        extMgr.packageOpened(pkg);
    }
	
    /**
     * Closes the current package.
     */
    public void closePackage()
    {
        if (isEmptyFrame()) {
            return;
        }
        
        extMgr.packageClosing(pkg);

        if(! Config.isGreenfoot()) {
            classScroller.setViewportView(null);
            classScroller.setBorder(Config.normalBorder);
            editor.removeMouseListener(this);
            editor.removeFocusListener(this);
            this.toolsMenuManager.setMenuGenerator(new ToolsExtensionMenu(pkg));
            this.viewMenuManager.setMenuGenerator(new ViewExtensionMenu(pkg));
            
            getObjectBench().removeAllObjects(getProject().getUniqueId());
            clearTextEval();
            updateTextEvalBackground(true);
            showJavaMEcontrols(false);
            showTestingTools(wantToSeeTestingTools());
        }

        getPackage().closeAllEditors();
        
        DataCollector.packageClosed(pkg);

        Project proj = getProject();

        editor = null;
        pkg = null;
        // if there are no other frames editing this project, we close
        // the project
        if (PkgMgrFrame.getAllProjectFrames(proj) == null) {
            Project.cleanUp(proj);
        }
    }
    
	/**
     * Toggle the state of the "show uses arrows" switch.
     */
    public void updateShowUsesInPackage()
    {
        pkg.setShowUses(isShowUses());
        editor.repaint();
    }

    public void updateShowExtendsInPackage()
    {
        pkg.setShowExtends(isShowExtends());
        editor.repaint();
    }
	
	

}
