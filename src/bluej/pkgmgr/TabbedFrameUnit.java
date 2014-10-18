package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bluej.BlueJEventListener;
import bluej.Config;
import bluej.collect.DataCollector;
import bluej.extmgr.ToolsExtensionMenu;
import bluej.extmgr.ViewExtensionMenu;
import bluej.utility.DialogManager;

public class TabbedFrameUnit extends JComponent  implements BlueJEventListener, MouseListener, PackageEditorListener, FocusListener{
	private Package pkg = null;
	protected JPanel tabWindow;
	private TextEvaluatorMgr text; 
	private PkgFrameTestingMenu test;
	private JScrollPane classScroller = null;
	private NoProjectMessagePanel noProjectMessagePanel = new NoProjectMessagePanel();
	private PackageEditor editor = null;
	
	
	public TabbedFrameUnit(){
		tabWindow = new JPanel();
		text = new TextEvaluatorMgr();
		test = new PkgFrameTestingMenu();
		editor = new PackageEditor(pkg, this, this);
	}
	
	public TabbedFrameUnit(Package p){
		pkg = p;
		tabWindow = new JPanel();
		text = new TextEvaluatorMgr();
		test = new PkgFrameTestingMenu();
	}
	
	public boolean isEmptyFrame(){
		return pkg == null;		
	}
	
	public void setState(){
		pkg.setState(Package.S_IDLE);
	}

	public JPanel getTab(){
		return tabWindow;
	}
	
	public boolean isTextEvalVisible(){
		return text.isTextEvalVisible();
	}
	
	 /**
     * Return the project of the package shown by this frame.
     */
    public Project getProject()
    {
        return pkg == null ? null : pkg.getProject();
    }
	
    protected void testRecordingEnded(){
    	test.testRecordingEnded(getProject());
    }
    
    /**
     * Return true if this frame is editing a Java Micro Edition package.
     */
    public boolean isJavaMEpackage( )
    {
        if (pkg == null) return false;
        return pkg.getProject().isJavaMEProject();
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
     * Save this package. Don't ask questions - just do it.
     */
    public void doSave()
    {
        if (isEmptyFrame()) {
            return;
        }
        
        // store the current editor size in the bluej.pkg file
        Properties p;
        if (pkg.isUnnamedPackage()) {
            // The unnamed package also contains project properties
            p = getProject().getProjectProperties();
        }
        else {
            p = new Properties();
        }
        
        Dimension d = classScroller.getSize();

        p.put("package.editor.width", Integer.toString(d.width));
        p.put("package.editor.height", Integer.toString(d.height));
        
        Point point = getLocation();

        p.put("package.editor.x", Integer.toString(point.x));
        p.put("package.editor.y", Integer.toString(point.y));
        
        d = objbench.getSize();
        p.put("objectbench.width", Integer.toString(d.width));
        p.put("objectbench.height", Integer.toString(d.height));

        p.put("package.showUses", Boolean.toString(isShowUses()));
        p.put("package.showExtends", Boolean.toString(isShowExtends()));
       
        pkg.save(p);
    }
    
    protected void updateWindow(){
    	if (isEmptyFrame()) {
    		classScroller.setViewportView(noProjectMessagePanel);
    		repaint();
    	}	
    	
    	//updateWindowTitle();
   	
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
     * Add the given set of Java source files as classes to this package.
     */
    protected void importFromFile(File[] classes)
    {
        // if there are errors this will potentially bring up multiple error
        // dialogs
        // these could be aggregated however the error messages may be different
        // for each error
        for (int i = 0; i < classes.length; i++) {
            int result = pkg.importFile(classes[i]);

            switch(result) {
                case Package.NO_ERROR :
                    // Have commented out repaint as it does not seem to be
                    // needed
                    //editor.repaint();
                    break;
                case Package.FILE_NOT_FOUND :
                    DialogManager.showErrorWithText(this, "file-does-not-exist", classes[i].getName());
                    break;
                case Package.ILLEGAL_FORMAT :
                    DialogManager.showErrorWithText(this, "cannot-import", classes[i].getName());
                    break;
                case Package.CLASS_EXISTS :
                    DialogManager.showErrorWithText(this, "duplicate-name", classes[i].getName());
                    break;
                case Package.COPY_ERROR :
                    DialogManager.showErrorWithText(this, "error-in-import", classes[i].getName());
                    break;
            }

        }
    }
}
