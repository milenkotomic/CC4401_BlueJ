package bluej.pkgmgr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.Action;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import bluej.Config;

import bluej.extmgr.MenuManager;
import bluej.pkgmgr.PkgMgrFrame.URLDisplayer;
import bluej.pkgmgr.actions.AddClassAction;

import bluej.pkgmgr.actions.CheckExtensionsAction;
import bluej.pkgmgr.actions.CheckVersionAction;
import bluej.pkgmgr.actions.CloseProjectAction;
import bluej.pkgmgr.actions.CommitGitHubAction;
import bluej.pkgmgr.actions.CompileAction;
import bluej.pkgmgr.actions.CompileSelectedAction;

import bluej.pkgmgr.actions.ExportProjectAction;
import bluej.pkgmgr.actions.GenerateDocsAction;
import bluej.pkgmgr.actions.HelpAboutAction;
import bluej.pkgmgr.actions.ImportProjectAction;
import bluej.pkgmgr.actions.NewClassAction;
import bluej.pkgmgr.actions.NewInheritsAction;
import bluej.pkgmgr.actions.NewIssueGitHubAction;

import bluej.pkgmgr.actions.DeployMIDletAction;
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
import bluej.pkgmgr.actions.SaveProjectAction;
import bluej.pkgmgr.actions.SaveProjectAsAction;
import bluej.pkgmgr.actions.ShowCopyrightAction;
import bluej.pkgmgr.actions.ShowDebuggerAction;
import bluej.pkgmgr.actions.ShowInheritsAction;
import bluej.pkgmgr.actions.ShowTerminalAction;

import bluej.pkgmgr.actions.ShowTextEvalAction;
import bluej.pkgmgr.actions.ShowUsesAction;
import bluej.pkgmgr.actions.StandardAPIHelpAction;
import bluej.pkgmgr.actions.TutorialAction;
import bluej.pkgmgr.actions.UseLibraryAction;
import bluej.pkgmgr.actions.WebsiteAction;
import bluej.utility.Utility;


public class PkgFrameMenu extends AbstractPkgMenu {
	private JCheckBoxMenuItem showUsesMenuItem;
    private JCheckBoxMenuItem showExtendsMenuItem;
    
    private JMenuBar menubar;
    private JMenu ToolMenu;
    private JMenu ViewMenu;
          
    
	public PkgFrameMenu(JMenuBar menubar){
		this.menubar = menubar;
		
		ToolMenu = new JMenu(Config.getString("menu.tools"));
		ToolMenu.setMnemonic(Config.getMnemonicKey("menu.tools"));

		ViewMenu = new JMenu(Config.getString("menu.view"));
		ViewMenu.setMnemonic(Config.getMnemonicKey("menu.view"));
	}
	
	public JPopupMenu getToolMenuPopUp(){
		return ToolMenu.getPopupMenu();
	}
	
	public JPopupMenu getViewMenuPopUp(){
		return ViewMenu.getPopupMenu();
	}
		
	public void initShowItems(Properties p){
		String uses_str = p.getProperty("package.showUses", "true");
        String extends_str = p.getProperty("package.showExtends", "true");
        
		showUsesMenuItem.setSelected(uses_str.equals("true"));
        showExtendsMenuItem.setSelected(extends_str.equals("true"));
	}
	
	/**
	 * @param menubar
	 */
	
	protected void setupPackageMenu(PkgFrameJavaME javaME,JMenu recentProjectsMenu){
		JMenu PackageMenu = new JMenu(Config.getString("menu.package"));
		int mnemonic = Config.getMnemonicKey("menu.package");
		PackageMenu.setMnemonic(mnemonic);
		menubar.add(PackageMenu);
		createMenuItem(NewProjectAction.getInstance(), PackageMenu);
		javaME.initJavaMEProj(PackageMenu);
			
		createMenuItem(OpenProjectAction.getInstance(), PackageMenu);
		PackageMenu.add(recentProjectsMenu);
		createMenuItem(OpenNonBlueJAction.getInstance(), PackageMenu);
		createMenuItem(CloseProjectAction.getInstance(), PackageMenu);
		createMenuItem(SaveProjectAction.getInstance(), PackageMenu);
		createMenuItem(SaveProjectAsAction.getInstance(), PackageMenu);
		PackageMenu.addSeparator();

		createMenuItem(ImportProjectAction.getInstance(), PackageMenu);
		createMenuItem(ExportProjectAction.getInstance(), PackageMenu);
		javaME.initJavaMEDeploy(PackageMenu); 
		PackageMenu.addSeparator();

		createMenuItem(PageSetupAction.getInstance(), PackageMenu);
		createMenuItem(PrintAction.getInstance(), PackageMenu);

		if (!Config.usingMacScreenMenubar()) { // no "Quit" here for Mac
			PackageMenu.addSeparator();
			createMenuItem(QuitAction.getInstance(), PackageMenu);
		}
	}
	
	protected void setupEditMenu(){
		JMenu menu = new JMenu(Config.getString("menu.edit"));
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
	}
	
	protected void setupToolMenu(PkgFrameTestingMenu test,PkgFrameTeamMenu team){
		menubar.add(ToolMenu);
		createMenuItem(CompileAction.getInstance(), ToolMenu);
		createMenuItem(CompileSelectedAction.getInstance(), ToolMenu);
		createMenuItem(RebuildAction.getInstance(), ToolMenu);
		createMenuItem(RestartVMAction.getInstance(), ToolMenu);
		ToolMenu.addSeparator();

		createMenuItem(UseLibraryAction.getInstance(), ToolMenu);
		createMenuItem(GenerateDocsAction.getInstance(), ToolMenu);

		test.initTestingMenu(ToolMenu);	
		team.initTeamMenu(ToolMenu);
		if (!Config.usingMacScreenMenubar()) { // no "Preferences" here for
			// Mac
			ToolMenu.addSeparator();
			createMenuItem(PreferencesAction.getInstance(), ToolMenu);
		}
		
	}
	
	protected void setupViewMenu(PkgFrameTestingMenu test){
		JMenu menu = new JMenu(Config.getString("menu.view"));
		menu.setMnemonic(Config.getMnemonicKey("menu.view"));
		menubar.add(menu);
		showUsesMenuItem = createCheckboxMenuItem(ShowUsesAction.getInstance(), menu, true);
		showExtendsMenuItem = createCheckboxMenuItem(ShowInheritsAction.getInstance(), menu, true);
		menu.addSeparator();

		createCheckboxMenuItem(ShowDebuggerAction.getInstance(), menu, false);
		createCheckboxMenuItem(ShowTerminalAction.getInstance(), menu, false);
		createCheckboxMenuItem(ShowTextEvalAction.getInstance(), menu, false);
		test.initViewTestingMenu(menu);
	}
	
	protected JMenu setupHelpMenu(){
		JMenu menu = new JMenu(Config.getString("menu.help"));
		menu.setMnemonic(Config.getMnemonicKey("menu.help"));
		menubar.add(menu);

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
		
		return menu;
		
	}	

	protected void setupGitHubMenu(){
		JMenu menu = new JMenu("GitHub");
		menubar.add(menu);

		createMenuItem(PushGitHubAction.getInstance(), menu);
		createMenuItem(PullGitHubAction.getInstance(), menu);
		createMenuItem(CommitGitHubAction.getInstance(), menu);
		createMenuItem(NewIssueGitHubAction.getInstance(), menu);
	}	
	
	protected void setupWindowsMenu(){
		JMenu menu = new JMenu("Windows");
		menubar.add(menu);

		JMenuItem newWindow = createMenuItem(NewWindowAction.getInstance(),menu);
		newWindow.setAccelerator(KeyStroke.getKeyStroke("control shift N"));

		JMenuItem newTab = createMenuItem(NewTabAction.getInstance(),menu);
		newTab.setAccelerator(KeyStroke.getKeyStroke("control shift T"));

	}	
	
	public boolean isShowUses()
	{
	        return showUsesMenuItem.isSelected();
	}

	public boolean isShowExtends()
	{
	        return showExtendsMenuItem.isSelected();
	}
	
	
}

