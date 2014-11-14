package bluej.pkgmgr;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import bluej.Config;
import bluej.groupwork.actions.CheckoutAction;
import bluej.groupwork.actions.TeamActionGroup;

public class PkgFrameTeamMenu extends PkgFrameMenu {
	private JPanel teamPanel;
	private AbstractButton updateButton;
    private AbstractButton commitButton;
    private AbstractButton teamStatusButton;
    private List<JComponent> teamItems;
    
    private JMenuItem shareProjectMenuItem;
    private JMenuItem teamSettingsMenuItem;
    private JMenuItem showLogMenuItem;
    private JMenuItem updateMenuItem;
    private JMenuItem commitMenuItem;
    private JMenuItem statusMenuItem;
    
    private TeamActionGroup teamActions;
	
    public PkgFrameTeamMenu(){
    	teamActions = new TeamActionGroup(false);
        teamActions.setAllDisabled();
        
        teamItems = new ArrayList<JComponent>();
    }
        
	public JPanel createTeamPanel(){
		teamPanel = new JPanel();

		teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));

		teamPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 14, 5));
		updateButton = createButton(teamActions.getUpdateAction(), false, false, 2, 4);                
		updateButton.setAlignmentX(0.15f);
		teamPanel.add(updateButton);
		if(!Config.isMacOSLeopard()) teamPanel.add(Box.createVerticalStrut(3));

		commitButton = createButton(teamActions.getCommitCommentAction(), false, false, 2, 4);
		commitButton.setAlignmentX(0.15f);
		//make the button use a different label than the one from
		// action
		teamPanel.add(commitButton);
		if(!Config.isMacOSLeopard()) teamPanel.add(Box.createVerticalStrut(3));

		teamStatusButton = createButton(teamActions.getStatusAction(), false, false, 2, 4);
		teamStatusButton.setAlignmentX(0.15f);
		teamPanel.add(teamStatusButton);
		if(!Config.isMacOSLeopard()) teamPanel.add(Box.createVerticalStrut(3));
		teamPanel.setAlignmentX(0.5f);

		teamItems.add(teamPanel);
		
		return teamPanel;
	}
	
	public void initTeamMenu(JMenu menu){
		JMenu teamMenu = new JMenu(Config.getString("menu.tools.teamwork"));
		teamMenu.setMnemonic(Config.getMnemonicKey("menu.tools"));

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

		teamItems.add(teamMenu);
		menu.add(teamMenu);
	}
	
	   /**
     * Set the team controls to use the team actions for the project.
     */
    public void resetTeamActions()
    {
        // The reason this is necessary is because team actions are tied to
        // a project, not to a PkgMgrFrame. However, a PkgMgrFrame may be
        // empty and not associated with a project - in that case it has its
        // own TeamActionGroup. When a project is opened, the actions from
        // the project then need to be associated with the appropriate controls.
        
        teamStatusButton.setAction(teamActions.getStatusAction());
        updateButton.setAction(teamActions.getUpdateAction());
        teamSettingsMenuItem.setAction(teamActions.getTeamSettingsAction());
        commitButton.setAction(teamActions.getCommitCommentAction());
        shareProjectMenuItem.setAction(teamActions.getImportAction());
        statusMenuItem.setAction(teamActions.getStatusAction());
        commitMenuItem.setAction(teamActions.getCommitCommentAction());
        commitMenuItem.setText(Config.getString("team.menu.commit"));
        updateMenuItem.setAction(teamActions.getUpdateAction());
        updateMenuItem.setText(Config.getString("team.menu.update"));
        showLogMenuItem.setAction(teamActions.getShowLogAction());
    }
	
	public void setAllDisabled(){
		teamActions.setAllDisabled();
	}
	
	
}
