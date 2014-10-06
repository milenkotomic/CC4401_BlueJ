package bluej.pkgmgr;

import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bluej.Config;
import bluej.groupwork.actions.TeamActionGroup;
import bluej.prefmgr.PrefMgr;

public class TeamToolsManager {
	private boolean teamToolsShown;
	
	/*Team action menu item*/
	 private JMenu teamMenu;
	 private JMenuItem shareProjectMenuItem;
	 private JMenuItem teamSettingsMenuItem;
	 private JMenuItem showLogMenuItem;
	 private JMenuItem updateMenuItem;
	 private JMenuItem commitMenuItem;
	 private JMenuItem statusMenuItem;
	 private AbstractButton updateButton;
	 private AbstractButton commitButton;
	 private AbstractButton teamStatusButton;
	 private List<JComponent> teamItems;
	 private TeamActionGroup teamActions;
	
	
	public TeamToolsManager(){
		teamToolsShown = wantToSeeTeamTools();
		
		
	}
	
	
	/**
     * Check whether the status of the 'Show teamwork tools' preference has
     * changed, and if it has, show or hide them as requested.
     */
    public void updateTeamStatus(IPkgFrame frame)
    {
    	if (teamToolsShown != wantToSeeTeamTools()){
            
    		frame.showTeamTools(!teamToolsShown);
    		teamToolsShown = !teamToolsShown;
        }
    }

    /**
     * Tell whether teamwork tools should be shown.
     */
    private boolean wantToSeeTeamTools()
    {
    	return PrefMgr.getFlag(PrefMgr.SHOW_TEAM_TOOLS);
    }
  
    /**
     * Set the team controls to use the team actions for the project.
     */
    private void resetTeamActions()
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

	
	
	

}
