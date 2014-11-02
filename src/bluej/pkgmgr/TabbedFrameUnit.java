package bluej.pkgmgr;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import bluej.BlueJEventListener;
import bluej.BlueJTheme;
import bluej.Config;
import bluej.collect.DataCollector;
import bluej.debugmgr.ObjectBenchTFU;
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
import bluej.pkgmgr.dependency.Dependency;
import bluej.pkgmgr.target.ClassTarget;
import bluej.pkgmgr.target.Target;
import bluej.pkgmgr.target.role.UnitTestClassRole;
import bluej.prefmgr.PrefMgr;
import bluej.testmgr.record.InvokerRecord;
import bluej.utility.Debug;
import bluej.utility.DialogManager;
import bluej.utility.FileUtility;
import bluej.utility.GradientFillPanel;
import bluej.utility.JavaNames;
import bluej.utility.Utility;

public class TabbedFrameUnit extends JFrame implements BlueJEventListener, MouseListener, PackageEditorListener, FocusListener{
	private Package pkg = null;
	protected JPanel tabWindow;
		
	private TextEvaluatorMgr text = new TextEvaluatorMgr(); 
	private PkgFrameTestingMenu test = new PkgFrameTestingMenu();
	private PkgFrameJavaME javaME = new PkgFrameJavaME();
	private PkgFrameLeftPanel leftPanel = new PkgFrameLeftPanel();
	
	private JScrollPane classScroller = null;
	private NoProjectMessagePanel noProjectMessagePanel = new NoProjectMessagePanel();
	private PackageEditor editor = null;
	private ObjectBenchTFU objbench;
	
	private MenuManager toolsMenuManager;
    private MenuManager viewMenuManager;
	
    /*Conservar para primeras pruebas*/   
    static final int DEFAULT_WIDTH = 560;
    static final int DEFAULT_HEIGHT = 400;
    private JSplitPane splitPane;
    private MachineIcon machineIcon;
    private static Font pkgMgrFont = PrefMgr.getStandardFont();
    private List<JComponent> itemsToDisable;
    private List<Action> actionsToDisable;
    
    
	public TabbedFrameUnit(){
		tabWindow = new JPanel();
		setupActionDisableSet();
		itemsToDisable = new ArrayList<JComponent>();
		objbench = new ObjectBenchTFU(this);
				
		makeFrame();
		//editor = new PackageEditor(pkg, this, this);
		
	}
	
	public TabbedFrameUnit(Package p){
		pkg = p;
		tabWindow = new JPanel();
				
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
	    		
		/*Panel para los botones que se encuentran al lado izquierdo*/
		JPanel buttonPanel = leftPanel.createLeftPanel();      
        tabWindow.add(buttonPanel);
        
        /*Panel para las opciones de testeo, en general, desactivadas por defecto*/
        JPanel testPanel = test.createTestingPanel(false);    
        
        
        /*Panel para actividades asociadas a un paquete java ME*/
        JPanel javaMEPanel = javaME.createJavaMEPanel();    
                
        /*Machine icon: Indicador de actividad tipo "barbero"*/
        machineIcon = new MachineIcon();
        machineIcon.setAlignmentX(0.5f);
        //itemsToDisable.add(machineIcon);
        
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
        toolPanel.add(buttonPanel);
        toolPanel.add(Box.createVerticalGlue());
        //toolPanel.add(teamPanel);
        toolPanel.add(javaMEPanel);
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
        
        //if (!testToolsShown) {
            test.showTestingTools(false);
        //}

        // hide Java ME tools if not wanted
        //if (! javaMEtoolsShown) {
        	//javaME.showJavaMEtools(false);
        //}
            
        javaMEPanel.setVisible(false);          
        
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
    
	public File[] importProjectDir(File dir, boolean showFailureDialog)
	{
	       // recursively copy files from import directory to package directory
	       File[] fails = FileUtility.recursiveCopyFile(dir, getPackage().getPath());

	       // if we have any files which failed the copy, we show them now
	       if (fails != null && showFailureDialog) {
	           JDialog importFailedDlg = new ImportFailedDialog(this, fails);
	           importFailedDlg.setVisible(true);
	       }

	       // add bluej.pkg files through the imported directory structure
	       List<File> dirsToConvert = Import.findInterestingDirectories(getPackage().getPath());
	       Import.convertDirectory(dirsToConvert);

	       // reload all the packages (which discovers classes which may have
	       // been added by the import)
	       getProject().reloadAll();
	       
	       return fails;
	}
	
	public void rebuild(){
		this.getPackage().rebuild();
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
     * Implementation of the "New Class" user function.
     */
    public void doCreateNewClass()
    {
        NewClassDialog dlg = new NewClassDialog(this, isJavaMEpackage());
        boolean okay = dlg.display();

        if (okay) {
            String name = dlg.getClassName();
            String template = dlg.getTemplateName();

            createNewClass(name, template, true);
        }
    }
    
    public boolean createNewClass(String name, String template, boolean showErr)
    {
        // check whether name is already used
        if (pkg.getTarget(name) != null) {
            DialogManager.showError(this, "duplicate-name");
            return false;
        }

        //check if there already exists a class in a library with that name 
        String[] conflict=new String[1];
        Class<?> c = pkg.loadClass(pkg.getQualifiedName(name));
        if (c != null){
            if (! Package.checkClassMatchesFile(c, new File(getPackage().getPath(), name + ".class"))) {
                conflict[0]=Package.getResourcePath(c);
                if (DialogManager.askQuestion(this, "class-library-conflict", conflict) == 0) {
                    return false;
                }
            }
        }

        ClassTarget target = null;
        target = new ClassTarget(pkg, name, template);

        if ( template != null ) { 
            boolean success = target.generateSkeleton(template);
            if (! success)
                return false;
        }

        pkg.findSpaceForVertex(target);
        pkg.addTarget(target);

        if (editor != null) {
            editor.revalidate();
            editor.scrollRectToVisible(target.getBounds());
            editor.repaint();
        }

        if (target.getRole() instanceof UnitTestClassRole) {
            pkg.compileQuiet(target);
        }
        
        DataCollector.addClass(pkg, target.getSourceFile());
        
        return true;
    }
    
    /**
     * Prompts the user with a dialog asking for the name of a package to
     * create. Package name can be fully qualified in which case all
     * intermediate packages will also be created as necessary.
     */
    public void doCreateNewPackage()
    {
        NewPackageDialog dlg = new NewPackageDialog(this);
        boolean okay = dlg.display();
        
        if (!okay)
            return;
        
        String name = dlg.getPackageName();

        if (name.length() == 0)
            return;

        createNewPackage(name, true);
    }
    
    public boolean createNewPackage(String name, boolean showErrDialog)
    {
        String fullName;

        // if the name is fully qualified then we leave it as is but
        // if it is not we assume they want to create a package in the
        // current package
        if (name.indexOf('.') > -1) {
            fullName = name;
        }
        else {
            fullName = getPackage().getQualifiedName(name);
        }

        // check whether name is already used for a class or package
        // in the parent package
        String prefix = JavaNames.getPrefix(fullName);
        String base = JavaNames.getBase(fullName);

        Package basePkg = getProject().getPackage(prefix);
        if (basePkg != null) {
            if (basePkg.getTarget(base) != null) {
                if (showErrDialog)
                    DialogManager.showError(this, "duplicate-name");
                return false;
            }
        }

        getProject().createPackageDirectory(fullName);

        // check that everything has gone well and instruct all affected
        // packages to reload (to make them notice the new sub packages)
        Package newPackage = getProject().getPackage(fullName);

        if (newPackage == null) {
            Debug.reportError("creation of new package failed unexpectedly");
            // TODO propagate a more informative exception
            return false;
        }
        
        newPackage = newPackage.getParent();
        while (newPackage != null) {
            newPackage.reload();
            newPackage = newPackage.getParent();
        }
        
        return true;
    }

    public void doRemove()
    {
        Component permanentFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
        if (permanentFocusOwner == editor || Arrays.asList(editor.getComponents()).contains(permanentFocusOwner)) { // focus in diagram
            if (!(doRemoveTargets() || doRemoveDependency())) {
                DialogManager.showError(this, "no-class-selected");
            }
        }
        else if (permanentFocusOwner == objbench || objbench.getObjects().contains(permanentFocusOwner)) { // focus in object bench
            objbench.removeSelectedObject(pkg.getId());
        }
        else {
            // ignore the command - focus is probably in text eval area
        }
    }
    
    private boolean doRemoveTargets()
    {
        Target[] targets = pkg.getSelectedTargets();
        if (targets.length <= 0) {
            return false;
        }
        if (askRemoveClass()) {
            for (int i = 0; i < targets.length; i++) {
                targets[i].remove();
            }
        }
        return true;
    }

    private boolean doRemoveDependency()
    {
        Dependency dependency = pkg.getSelectedDependency();
        if (dependency == null) {
            return false;
        }
        dependency.remove();
        return true;
    }

    public boolean askRemoveClass()
    {
        int response = DialogManager.askQuestion(this, "really-remove-class");
        return response == 0;
    }
    
    /**
     * The user function to add a uses arrow to the diagram was invoked.
     */
    public void doNewUses()
    {
        pkg.setState(Package.S_CHOOSE_USES_FROM);
        //setStatus(Config.getString("pkgmgr.chooseUsesFrom"));
        pkg.getEditor().clearSelection();
    }

    /**
     * The user function to add an inherits arrow to the dagram was invoked.
     */
    public void doNewInherits()
    {
        pkg.setState(Package.S_CHOOSE_EXT_FROM);
        //setStatus(Config.getString("pkgmgr.chooseInhFrom"));
        editor.clearSelection();
    }
    
    /**
     * Toggle the state of the "show uses arrows" switch.
     */
    public void updateShowUses(boolean valueToUpdate)
    {
    	pkg.setShowUses(valueToUpdate);
    	editor.repaint();
    }
    
    public void updateShowExtends(boolean valueToUpdate)
    {
    	pkg.setShowExtends(valueToUpdate);
    	editor.repaint();
    }
      
    /**
     * Displays the package in the frame for editing
     */
    public void openPackage(Package pkg)
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

        if(! Config.isGreenfoot()) {
            this.editor = new PackageEditor(pkg, this, this);
            editor.getAccessibleContext().setAccessibleName(Config.getString("pkgmgr.graphEditor.title"));
            editor.setFocusable(true);
            //editor.setTransferHandler(new FileTransferHandler(this));
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
            
            //showUsesMenuItem.setSelected(uses_str.equals("true"));
            //showExtendsMenuItem.setSelected(extends_str.equals("true"));
            
            //updateShowUsesInPackage();
            //updateShowExtendsInPackage();
            
            pack();
            editor.revalidate();
            editor.requestFocus();
            
            enableFunctions(true); // changes menu items
            updateWindow();
            setVisible(true);
            
            //updateTextEvalBackground(isEmptyFrame());
                    
            //this.toolsMenuManager.setMenuGenerator(new ToolsExtensionMenu(pkg));
            //this.toolsMenuManager.addExtensionMenu(pkg.getProject());

            //this.viewMenuManager.setMenuGenerator(new ViewExtensionMenu(pkg));
            //this.viewMenuManager.addExtensionMenu(pkg.getProject());
        
            //teamActions = pkg.getProject().getTeamActions();
            //resetTeamActions();             
           
            // In Java-ME packages, we display Java-ME controls in the
            // test panel. We are just using the real estate of the test panel.
            // The rest of the testing tools (menus, etc) are always hidden.
            if (getProject().isJavaMEProject()) {
            	javaME.showJavaMEcontrols(true);
                test.showTestingTools(false);
            }
            else {
                test.showTestingTools(test.wantToSeeTestingTools());
            }                
        }
        
        DataCollector.packageOpened(pkg);

        ExtensionsManager.getInstance().packageOpened(pkg);
    }
    
    /**
     * Adds shortcuts for Ctrl-TAB and Ctrl-Shift-TAB to the given pane, which move to the
     * next/previous pane of the main three (package editor, object bench, code pad) that are visible
     */
    private void addCtrlTabShortcut(final JComponent toPane)
    {
        toPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK), "nextPMFPane");
        toPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "prevPMFPane");
        toPane.getActionMap().put("nextPMFPane", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                movePaneFocus(toPane, +1);
            }
        });
        toPane.getActionMap().put("prevPMFPane", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                movePaneFocus(toPane, -1);
            }
        });
    }
    
    /**
     * Moves focus from given pane to prev (-1)/next (+1) pane.
     */
    private void movePaneFocus(final JComponent fromPane, int direction)
    {
        List<JComponent> visiblePanes = new ArrayList<JComponent>();
        if (editor != null)
        {
            // editor is null if no package is open
            visiblePanes.add(editor);
        }
        // Object bench is always present, even if no package open:
        visiblePanes.add(objbench);
        /*if (showingTextEvaluator)
        {
            visiblePanes.add(textEvaluator.getFocusableComponent());
        }*/
        
        for (int i = 0; i < visiblePanes.size(); i++)
        {
            if (visiblePanes.get(i) == fromPane)
            {
                int destination = i + direction;
                // Wrap around:
                if (destination >= visiblePanes.size()) destination = 0;
                if (destination < 0) destination = visiblePanes.size() - 1;
                
                visiblePanes.get(destination).requestFocusInWindow();
            }
        }
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
     * User function "Generate Documentation...".
     */
    public void generateProjectDocumentation()
    {
        String message = pkg.generateDocumentation();
        if (message.length() != 0) {
            DialogManager.showText(this, message);
        }
    }
    
    /**
     * Restart the debugger VM associated with this project.
     */
    public void restartDebugger()
    {
        if (!isEmptyFrame())
        {
            getProject().restartVM();
            DataCollector.restartVM(getProject());
        }
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
            //toolsMenuManager.setMenuGenerator(new ToolsExtensionMenu(pkg));
            //viewMenuManager.setMenuGenerator(new ViewExtensionMenu(pkg));
            
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
