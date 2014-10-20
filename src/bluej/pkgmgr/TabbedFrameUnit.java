package bluej.pkgmgr;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import bluej.BlueJEventListener;
import bluej.BlueJTheme;
import bluej.Config;
import bluej.collect.DataCollector;
import bluej.debugmgr.objectbench.ObjectBench;
import bluej.extmgr.ExtensionsManager;
import bluej.extmgr.MenuManager;
import bluej.extmgr.ToolsExtensionMenu;
import bluej.extmgr.ViewExtensionMenu;
import bluej.groupwork.ui.ActivityIndicator;
import bluej.pkgmgr.actions.AddClassAction;
import bluej.pkgmgr.actions.CancelTestRecordAction;
import bluej.pkgmgr.actions.CloseProjectAction;
import bluej.pkgmgr.actions.CompileAction;
import bluej.pkgmgr.actions.CompileSelectedAction;
import bluej.pkgmgr.actions.EndTestRecordAction;
import bluej.pkgmgr.actions.ExportProjectAction;
import bluej.pkgmgr.actions.GenerateDocsAction;
import bluej.pkgmgr.actions.ImportProjectAction;
import bluej.pkgmgr.actions.NewClassAction;
import bluej.pkgmgr.actions.NewInheritsAction;
import bluej.pkgmgr.actions.NewPackageAction;
import bluej.pkgmgr.actions.NewUsesAction;
import bluej.pkgmgr.actions.PageSetupAction;
import bluej.pkgmgr.actions.PrintAction;
import bluej.pkgmgr.actions.RebuildAction;
import bluej.pkgmgr.actions.RemoveAction;
import bluej.pkgmgr.actions.RestartVMAction;
import bluej.pkgmgr.actions.RunTestsAction;
import bluej.pkgmgr.actions.SaveProjectAction;
import bluej.pkgmgr.actions.SaveProjectAsAction;
import bluej.pkgmgr.actions.ShowDebuggerAction;
import bluej.pkgmgr.actions.ShowInheritsAction;
import bluej.pkgmgr.actions.ShowTerminalAction;
import bluej.pkgmgr.actions.ShowTextEvalAction;
import bluej.pkgmgr.actions.ShowUsesAction;
import bluej.pkgmgr.actions.UseLibraryAction;
import bluej.prefmgr.PrefMgr;
import bluej.testmgr.record.InvokerRecord;
import bluej.utility.DialogManager;
import bluej.utility.GradientFillPanel;
import bluej.utility.Utility;

public class TabbedFrameUnit extends JFrame  implements BlueJEventListener, MouseListener, PackageEditorListener, FocusListener{
	private Package pkg = null;
	protected JPanel tabWindow;
		
	private TextEvaluatorMgr text; 
	private PkgFrameTestingMenu test;
	private PkgFrameJavaME javaME;
	
	private JScrollPane classScroller = null;
	private NoProjectMessagePanel noProjectMessagePanel = new NoProjectMessagePanel();
	private PackageEditor editor = null;
	private ObjectBench objbench;
	
	private MenuManager toolsMenuManager;
    private MenuManager viewMenuManager;
	
    /*Conservar para primeras pruebas*/
    private AbstractButton imgExtendsButton;
    private AbstractButton imgDependsButton;
    
    private List<JComponent> testItems;
    static final int DEFAULT_WIDTH = 560;
    static final int DEFAULT_HEIGHT = 400;
    private JSplitPane splitPane;
    private MachineIcon machineIcon;
    private JLabel statusbar;
    private static Font pkgMgrFont = PrefMgr.getStandardFont();
    private List<JComponent> itemsToDisable;
    private List<Action> actionsToDisable;
    
    
	public TabbedFrameUnit(){
		tabWindow = new JPanel();
		text = new TextEvaluatorMgr();
		test = new PkgFrameTestingMenu();
		javaME = new PkgFrameJavaME();
		setupActionDisableSet();
		itemsToDisable = new ArrayList<JComponent>();
		
		makeFrame();
		//editor = new PackageEditor(pkg, this, this);
		
	}
	
	public TabbedFrameUnit(Package p){
		pkg = p;
		tabWindow = new JPanel();
		text = new TextEvaluatorMgr();
		test = new PkgFrameTestingMenu();
		javaME = new PkgFrameJavaME();
		
		editor = new PackageEditor(pkg, this, this);
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
	
	protected void makeFrame(){
		setFont(pkgMgrFont);
		setContentPane(new GradientFillPanel(getContentPane().getLayout()));
		JPanel toolPanel = new JPanel();
		toolPanel.setOpaque(false);
		tabWindow.add(toolPanel);
	
		Container contentPane = getContentPane();
	    ((JPanel) contentPane).setBorder(BlueJTheme.generalBorderWithStatusBar);
		
	    JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
	    if (!Config.isRaspberryPi()) mainPanel.setOpaque(false);
	    
		testItems = new ArrayList<JComponent>();
		
		/*Panel para los botones que se encuentran al lado izquierdo*/
		JPanel buttonPanel = createLeftPanel();      
        tabWindow.add(buttonPanel);
        
        /*Panel para las opciones de testeo, en general, desactivadas por defecto*/
        JPanel testPanel = test.createTestingPanel(false);    
        testItems.add(testPanel); 
        
        /*Machine icon: Indicador de actividad tipo "barbero"*/
        machineIcon = new MachineIcon();
        machineIcon.setAlignmentX(0.5f);
        //itemsToDisable.add(machineIcon);
        
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
        toolPanel.add(buttonPanel);
        toolPanel.add(Box.createVerticalGlue());
        //toolPanel.add(teamPanel);
        //toolPanel.add(javaMEPanel);
        toolPanel.add(testPanel);
        toolPanel.add(machineIcon);
        mainPanel.add(toolPanel, BorderLayout.WEST);
               
        classScroller = new JScrollPane();
        configureClassScroller();
        mainPanel.add(classScroller, BorderLayout.CENTER);
                     
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, objbench);
        splitPane.setBorder(null);
        splitPane.setResizeWeight(1.0);
        splitPane.setDividerSize(5);
        if (!Config.isRaspberryPi()) splitPane.setOpaque(false);
        contentPane.add(splitPane, BorderLayout.CENTER);
        
        /*Zona de abajo*/
        JPanel statusArea = new JPanel(new BorderLayout());
        if (!Config.isRaspberryPi()) statusArea.setOpaque(false);

        statusArea.setBorder(BorderFactory.createEmptyBorder(2, 0, 4, 6));
        JLabel statusbar = new JLabel(" ");
        statusbar.setFont(pkgMgrFont);
        statusArea.add(statusbar, BorderLayout.CENTER);

        JLabel testStatusMessage = new JLabel(" ");
        testStatusMessage.setFont(pkgMgrFont);
        statusArea.add(testStatusMessage, BorderLayout.WEST);

        ActivityIndicator progressbar = new ActivityIndicator();
        progressbar.setRunning(false);
        statusArea.add(progressbar, BorderLayout.EAST);
        
        contentPane.add(statusArea, BorderLayout.SOUTH);
        
        tabWindow.add(mainPanel);
        tabWindow.add(contentPane);
        
        if (isEmptyFrame()) {
            enableFunctions(false);
        }
        
	}
	
	private JPanel createLeftPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        AbstractButton button = createButton(NewClassAction.getInstance(), false, false, 4, 4);
        buttonPanel.add(button);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));
        
        imgDependsButton = createButton(NewUsesAction.getInstance(), true, false, 4, 4);
        buttonPanel.add(imgDependsButton);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));

        imgExtendsButton = createButton(NewInheritsAction.getInstance(), true, false, 4, 4);
        buttonPanel.add(imgExtendsButton);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));

        button = createButton(CompileAction.getInstance(), false, false, 4, 4);
        buttonPanel.add(button);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));

        buttonPanel.setAlignmentX(0.5f);
        
        return buttonPanel;
  	}
	
	
	
	private void configureClassScroller(){
		
		classScroller.setBorder(Config.normalBorder);
        classScroller.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        classScroller.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        classScroller.setFocusable(false);
        classScroller.getVerticalScrollBar().setUnitIncrement(10);
        classScroller.getHorizontalScrollBar().setUnitIncrement(20);
        if (!Config.isRaspberryPi()) classScroller.setOpaque(false);
        
	}
	
	/**
     * Enable/disable functionality. Enable or disable all the interface
     * elements that should change when a project is or is not open.
     */
    protected void enableFunctions(boolean enable)
    {
        /*if (! enable) {
            teamActions.setAllDisabled();
        }*/
        
        for (Iterator<JComponent> it = itemsToDisable.iterator(); it.hasNext();) {
            JComponent component = it.next();
            component.setEnabled(enable);
        }
        for (Iterator<Action> it = actionsToDisable.iterator(); it.hasNext();) {
            Action action = it.next();
            action.setEnabled(enable);
        }
    }
	
    /**
	 * Define which actions are to be disabled when no project is open
	 */
	private void setupActionDisableSet()
	{
		actionsToDisable = new ArrayList<Action>();

		actionsToDisable.add(CloseProjectAction.getInstance());
		actionsToDisable.add(SaveProjectAction.getInstance());
		actionsToDisable.add(SaveProjectAsAction.getInstance());
		actionsToDisable.add(ImportProjectAction.getInstance());
		actionsToDisable.add(ExportProjectAction.getInstance());
		actionsToDisable.add(PageSetupAction.getInstance());
		actionsToDisable.add(PrintAction.getInstance());
		actionsToDisable.add(NewClassAction.getInstance());
		actionsToDisable.add(NewPackageAction.getInstance());
		actionsToDisable.add(AddClassAction.getInstance());
		actionsToDisable.add(RemoveAction.getInstance());
		actionsToDisable.add(NewUsesAction.getInstance());
		actionsToDisable.add(NewInheritsAction.getInstance());
		actionsToDisable.add(CompileAction.getInstance());
		actionsToDisable.add(CompileSelectedAction.getInstance());
		actionsToDisable.add(RebuildAction.getInstance());
		actionsToDisable.add(RestartVMAction.getInstance());
		actionsToDisable.add(UseLibraryAction.getInstance());
		actionsToDisable.add(GenerateDocsAction.getInstance());
		actionsToDisable.add(ShowUsesAction.getInstance());
		actionsToDisable.add(ShowInheritsAction.getInstance());
		actionsToDisable.add(ShowDebuggerAction.getInstance());
		actionsToDisable.add(ShowTerminalAction.getInstance());
		actionsToDisable.add(ShowTextEvalAction.getInstance());
		actionsToDisable.add(RunTestsAction.getInstance());
	}

    
    
	 /**
     * Create a button for the interface.
     * 
     * @param action
     *            the Action abstraction dictating text, icon, tooltip, action.
     * @param notext
     *            set true if the action text should not appear (icon only).
     * @param toggle
     *            true if this is a toggle button, false otherwise
     * @param hSpacing
     *            horizontal margin (left, right)
     * @param vSpacing
     *            vertical margin (top, bottom)
     * @return the new button
     */
    private AbstractButton createButton(Action action, boolean notext, boolean toggle, int hSpacing, int vSpacing)
    {
        AbstractButton button;
        if (toggle) {
            button = new JToggleButton(action);
        }
        else {
            button = new JButton(action);
        }
        //button.setFont(pkgMgrFont);
        Utility.changeToMacButton(button);
        button.setFocusable(false); // buttons shouldn't get focus

        if (notext)
            button.setText(null);

        Dimension pref = button.getMinimumSize();
        pref.width = Integer.MAX_VALUE;
        button.setMaximumSize(pref);
        if(!Config.isMacOSLeopard()) {
            button.setMargin(new Insets(vSpacing, hSpacing, vSpacing, hSpacing));
        }

        return button;
    }
	
		
	 /**
     * Toggle the state of the "show uses arrows" switch.
     */
   /* public void updateShowUsesInPackage()
    {
        pkg.setShowUses(isShowUses());
        editor.repaint();
    }

    public void updateShowExtendsInPackage()
    {
        pkg.setShowExtends(isShowExtends());
        editor.repaint();
    }*/
	
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

       /* p.put("package.showUses", Boolean.toString(isShowUses()));
        p.put("package.showExtends", Boolean.toString(isShowExtends()));*/
       
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
        
        ExtensionsManager.getInstance().packageClosing(pkg);

        if(! Config.isGreenfoot()) {
            classScroller.setViewportView(null);
            classScroller.setBorder(Config.normalBorder);
            editor.removeMouseListener(this);
            editor.removeFocusListener(this);
            toolsMenuManager.setMenuGenerator(new ToolsExtensionMenu(pkg));
            viewMenuManager.setMenuGenerator(new ViewExtensionMenu(pkg));
            
            objbench.removeAllObjects(getProject().getUniqueId());
            text.clearTextEval();
            text.updateTextEvalBackground(true);
            javaME.showJavaMEcontrols(false);
            test.showTestingTools(test.wantToSeeTestingTools());
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

	public void focusGained(FocusEvent e) {
		 classScroller.setBorder(Config.focusBorder);
	     editor.setHasFocus(true);
	}

	public void focusLost(FocusEvent e) {
	    if (editor == null) {
            return;
        }
        
        if (!e.isTemporary() && e.getOppositeComponent() != editor && !editor.isGraphComponent(e.getOppositeComponent())) {
            classScroller.setBorder(Config.normalBorder);
            editor.setHasFocus(false);
        }
		
	}

	@Override
	public void targetEvent(PackageEditorEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void recordInteraction(InvokerRecord ir) {
		objbench.addInteraction(ir);
		
	}


	public void mousePressed(MouseEvent e) {
		//clearStatus();
	}


	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}


	@Override
	public void blueJEvent(int eventId, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
