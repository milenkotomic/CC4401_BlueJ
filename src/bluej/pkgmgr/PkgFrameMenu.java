package bluej.pkgmgr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import bluej.Config;
import bluej.extmgr.MenuManager;
import bluej.extmgr.ToolsExtensionMenu;
import bluej.groupwork.actions.CheckoutAction;
import bluej.pkgmgr.PkgMgrFrame.ProjectOpener;
import bluej.pkgmgr.PkgMgrFrame.URLDisplayer;
import bluej.pkgmgr.actions.AddClassAction;
import bluej.pkgmgr.actions.CancelTestRecordAction;
import bluej.pkgmgr.actions.CheckExtensionsAction;
import bluej.pkgmgr.actions.CheckVersionAction;
import bluej.pkgmgr.actions.CloseProjectAction;
import bluej.pkgmgr.actions.CommitGitHubAction;
import bluej.pkgmgr.actions.CompileAction;
import bluej.pkgmgr.actions.CompileSelectedAction;
import bluej.pkgmgr.actions.DeployMIDletAction;
import bluej.pkgmgr.actions.EndTestRecordAction;
import bluej.pkgmgr.actions.ExportProjectAction;
import bluej.pkgmgr.actions.GenerateDocsAction;
import bluej.pkgmgr.actions.HelpAboutAction;
import bluej.pkgmgr.actions.ImportProjectAction;
import bluej.pkgmgr.actions.NewClassAction;
import bluej.pkgmgr.actions.NewInheritsAction;
import bluej.pkgmgr.actions.NewIssueGitHubAction;
import bluej.pkgmgr.actions.NewMEprojectAction;
import bluej.pkgmgr.actions.NewPackageAction;
import bluej.pkgmgr.actions.NewProjectAction;
import bluej.pkgmgr.actions.NewTabAction;
import bluej.pkgmgr.actions.NewUsesAction;
import bluej.pkgmgr.actions.NewWindowAction;
import bluej.pkgmgr.actions.OpenNonBlueJAction;
import bluej.pkgmgr.actions.OpenProjectAction;
import bluej.pkgmgr.actions.PageSetupAction;
import bluej.pkgmgr.actions.PkgMgrAction;
import bluej.pkgmgr.actions.PreferencesAction;
import bluej.pkgmgr.actions.PrintAction;
import bluej.pkgmgr.actions.PullGitHubAction;
import bluej.pkgmgr.actions.PushGitHubAction;
import bluej.pkgmgr.actions.QuitAction;
import bluej.pkgmgr.actions.RebuildAction;
import bluej.pkgmgr.actions.RemoveAction;
import bluej.pkgmgr.actions.RestartVMAction;
import bluej.pkgmgr.actions.RunTestsAction;
import bluej.pkgmgr.actions.SaveProjectAction;
import bluej.pkgmgr.actions.SaveProjectAsAction;
import bluej.pkgmgr.actions.ShowCopyrightAction;
import bluej.pkgmgr.actions.ShowDebuggerAction;
import bluej.pkgmgr.actions.ShowInheritsAction;
import bluej.pkgmgr.actions.ShowTerminalAction;
import bluej.pkgmgr.actions.ShowTestResultsAction;
import bluej.pkgmgr.actions.ShowTextEvalAction;
import bluej.pkgmgr.actions.ShowUsesAction;
import bluej.pkgmgr.actions.StandardAPIHelpAction;
import bluej.pkgmgr.actions.TutorialAction;
import bluej.pkgmgr.actions.UseLibraryAction;
import bluej.pkgmgr.actions.WebsiteAction;
import bluej.prefmgr.PrefMgr;

public class PkgFrameMenu {
	
	private List<Action> actionsToDisable;
	private List<JComponent> itemsToDisable;
	private JMenu recentProjectsMenu;
	
	public PkgFrameMenu(){
		
	}

	/**
	 * @param menubar
	 */
	protected void setupMenu(JMenuBar menubar){
				
		itemsToDisable = new ArrayList<JComponent>();

	    JMenu menu = new JMenu(Config.getString("menu.package"));
	    int mnemonic = Config.getMnemonicKey("menu.package");
	    menu.setMnemonic(mnemonic);
	    menubar.add(menu);
	    {
	    	createMenuItem(NewProjectAction.getInstance(), menu);
	       //     javaMEnewProjMenuItem = createMenuItem( NewMEprojectAction.getInstance(), menu );            
	        createMenuItem(OpenProjectAction.getInstance(), menu);
	        //   recentProjectsMenu = new JMenu(Config.getString("menu.package.openRecent"));
	          //  menu.add(recentProjectsMenu);
	        createMenuItem(OpenNonBlueJAction.getInstance(), menu);
	        createMenuItem(CloseProjectAction.getInstance(), menu);
	        createMenuItem(SaveProjectAction.getInstance(), menu);
	        createMenuItem(SaveProjectAsAction.getInstance(), menu);
	        menu.addSeparator();

	        createMenuItem(ImportProjectAction.getInstance(), menu);
	        createMenuItem(ExportProjectAction.getInstance(), menu);
	         //   javaMEdeployMenuItem = createMenuItem( DeployMIDletAction.getInstance(), menu ); 
	         //   javaMEdeployMenuItem.setVisible( false ); //visible only in Java ME packages
	        menu.addSeparator();

	        createMenuItem(PageSetupAction.getInstance(), menu);
	        createMenuItem(PrintAction.getInstance(), menu);

	        if (!Config.usingMacScreenMenubar()) { // no "Quit" here for Mac
	        	menu.addSeparator();
	            createMenuItem(QuitAction.getInstance(), menu);
	        }
	    }

	        menu = new JMenu(Config.getString("menu.edit"));
	        menu.setMnemonic(Config.getMnemonicKey("menu.edit"));
	        menubar.add(menu);
	        {
	            createMenuItem(NewClassAction.getInstance(), menu);
	            createMenuItem(NewPackageAction.getInstance(), menu);
	            createMenuItem(AddClassAction.getInstance(), menu);
	            createMenuItem(RemoveAction.getInstance(), menu);
	            menu.addSeparator();

	            createMenuItem(NewUsesAction.getInstance(), menu);
	            createMenuItem(NewInheritsAction.getInstance(), menu);
	        }

	        menu = new JMenu(Config.getString("menu.tools"));
	        menu.setMnemonic(Config.getMnemonicKey("menu.tools"));
	        menubar.add(menu);
	        {
	            createMenuItem(CompileAction.getInstance(), menu);
	            createMenuItem(CompileSelectedAction.getInstance(), menu);
	            createMenuItem(RebuildAction.getInstance(), menu);
	            createMenuItem(RestartVMAction.getInstance(), menu);
	            menu.addSeparator();

	            createMenuItem(UseLibraryAction.getInstance(), menu);
	            createMenuItem(GenerateDocsAction.getInstance(), menu);

	        //    testingMenu = new JMenu(Config.getString("menu.tools.testing"));
	         //   testingMenu.setMnemonic(Config.getMnemonicKey("menu.tools"));
	            {
	           //  createMenuItem(RunTestsAction.getInstance(), testingMenu);
	            //    endTestMenuItem = createMenuItem(EndTestRecordAction.getInstance(), testingMenu);
	            //    cancelTestMenuItem = createMenuItem(CancelTestRecordAction.getInstance(), testingMenu);
	            //    endTestMenuItem.setEnabled(false);
	            //    cancelTestMenuItem.setEnabled(false);
	            }
	           // testItems.add(testingMenu);
	         /*   menu.add(testingMenu);
	            
	            //team menu setup
	            teamMenu = new JMenu(Config.getString("menu.tools.teamwork"));
	            teamMenu.setMnemonic(Config.getMnemonicKey("menu.tools"));
	            {
	                Action checkoutAction = CheckoutAction.getInstance();
	                createMenuItem(checkoutAction , teamMenu);
	                shareProjectMenuItem = createMenuItem(teamActions.getImportAction(), teamMenu);               
	                
	                teamMenu.addSeparator();
	                
	                updateMenuItem = createMenuItem(teamActions.getUpdateAction(), teamMenu);
	                updateMenuItem.setText(Config.getString("team.menu.update"));
	                commitMenuItem = createMenuItem(teamActions.getCommitCommentAction(), teamMenu);
	                commitMenuItem.setText(Config.getString("team.menu.commit"));
	                statusMenuItem = createMenuItem(teamActions.getStatusAction(), teamMenu);
	                showLogMenuItem = createMenuItem(teamActions.getShowLogAction(), teamMenu);
	                
	                teamMenu.addSeparator();
	                
	                teamSettingsMenuItem = createMenuItem(teamActions.getTeamSettingsAction(), teamMenu);
	            }
	            teamItems.add(teamMenu);
	            menu.add(teamMenu);*/

	            if (!Config.usingMacScreenMenubar()) { // no "Preferences" here for
	                                                   // Mac
	                menu.addSeparator();
	                createMenuItem(PreferencesAction.getInstance(), menu);
	            }

	            // Create the menu manager that looks after extension tools menus
	          //  toolsMenuManager = new MenuManager(menu.getPopupMenu());

	            // If this is the first frame create the extension tools menu now.
	            // (Otherwise, it will be created during project open.)
	            //if (frames.size() <= 1) {
	              //  toolsMenuManager.setMenuGenerator(new ToolsExtensionMenu(null));
	              //  toolsMenuManager.addExtensionMenu(null);
	            }
	        

	     
	        
	        menu = new JMenu(Config.getString("menu.view"));
	        menu.setMnemonic(Config.getMnemonicKey("menu.view"));
	        menubar.add(menu);
	        {
	        	//showUsesMenuItem = createCheckboxMenuItem(ShowUsesAction.getInstance(), menu, true);
	           // showExtendsMenuItem = createCheckboxMenuItem(ShowInheritsAction.getInstance(), menu, true);
	            menu.addSeparator();

	         createCheckboxMenuItem(ShowDebuggerAction.getInstance(), menu, false);
	         createCheckboxMenuItem(ShowTerminalAction.getInstance(), menu, false);
	         createCheckboxMenuItem(ShowTextEvalAction.getInstance(), menu, false);
	            JSeparator testSeparator = new JSeparator();
	         //   testItems.add(testSeparator);
	            menu.add(testSeparator);

	          //  showTestResultsItem = createCheckboxMenuItem(ShowTestResultsAction.getInstance(), menu, false);
	         //   testItems.add(showTestResultsItem);

	            // Create the menu manager that looks after extension view menus
	          //  viewMenuManager = new MenuManager(menu.getPopupMenu());

	            // If this is the first frame create the extension view menu now.
	            // (Otherwise, it will be created during project open.)
	           // if (frames.size() <= 1) {
	             //   viewMenuManager.addExtensionMenu(null);
	            }
	        //}

	              
	        menu = new JMenu(Config.getString("menu.help"));
	        menu.setMnemonic(Config.getMnemonicKey("menu.help"));
	        menubar.add(menu);
	        {
	            if (!Config.usingMacScreenMenubar()) { // no "About" here for Mac
	                createMenuItem(HelpAboutAction.getInstance(), menu);
	            }
	            createMenuItem(CheckVersionAction.getInstance(), menu);
	            createMenuItem(CheckExtensionsAction.getInstance(), menu);
	            createMenuItem(ShowCopyrightAction.getInstance(), menu);
	            menu.addSeparator();

	            createMenuItem(WebsiteAction.getInstance(), menu);
	            createMenuItem(TutorialAction.getInstance(), menu);
	            createMenuItem(StandardAPIHelpAction.getInstance(), menu);
	        }
	        
	                
	        menu = new JMenu("GitHub");
	        menubar.add(menu);
	        {
	            createMenuItem(PushGitHubAction.getInstance(), menu);
	            createMenuItem(PullGitHubAction.getInstance(), menu);
	            createMenuItem(CommitGitHubAction.getInstance(), menu);
	            createMenuItem(NewIssueGitHubAction.getInstance(), menu);
	        }
	        
	        menu = new JMenu("Windows");
	        menubar.add(menu);
	        {
	        	 createMenuItem(NewWindowAction.getInstance(),menu);
	             createMenuItem(NewTabAction.getInstance(),menu);
	             //menu.addSeparator();
	        	
	        }
	        
	        
	        //addUserHelpItems(menu);
	        //updateRecentProjects();

	       // setJMenuBar(menubar);       
     
    }
	
	protected JMenuItem createMenuItem(Action action, JMenu menu){
	        JMenuItem item = menu.add(action);
	        item.setIcon(null);
	        return item;
	}
	
	/**
     * Add a new menu item to a menu.
     */
    private JCheckBoxMenuItem createCheckboxMenuItem(PkgMgrAction action, JMenu menu, boolean selected)
    {
        //ButtonModel bmodel = action.getToggleModel(this);

        JCheckBoxMenuItem item = new JCheckBoxMenuItem(action);
        //if (bmodel != null)
           // item.setModel(action.getToggleModel(this));
        //else
            item.setState(selected);
        menu.add(item);
        return item;
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
     * Enable/disable functionality. Enable or disable all the interface
     * elements that should change when a project is or is not open.
     */
    protected void enableFunctions(boolean enable)
    {
       /* if (! enable) {
            teamActions.setAllDisabled();
        }
        
        for (Iterator<JComponent> it = itemsToDisable.iterator(); it.hasNext();) {
            JComponent component = it.next();
            component.setEnabled(enable);
        }*/
        for (Iterator<Action> it = actionsToDisable.iterator(); it.hasNext();) {
            Action action = it.next();
            action.setEnabled(enable);
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
     * Add user defined help menus. Users can add help menus via the
     * bluej.help.items property. See comment in bluej.defs.
     */
    private void addUserHelpItems(JMenu menu)
    {
        String helpItems = Config.getPropString("bluej.help.items", "");

        if (helpItems != null && helpItems.length() > 0) {
            menu.addSeparator();
            URLDisplayer urlDisplayer = new URLDisplayer();

            StringTokenizer t = new StringTokenizer(helpItems);

            while (t.hasMoreTokens()) {
                String itemID = t.nextToken();
                String itemName = Config.getPropString("bluej.help." + itemID + ".label");
                String itemURL = Config.getPropString("bluej.help." + itemID + ".url");
                JMenuItem item = new JMenuItem(itemName);
                item.setActionCommand(itemURL);
                item.addActionListener(urlDisplayer);
                menu.add(item);
            }
        }
    }
    
    
}
