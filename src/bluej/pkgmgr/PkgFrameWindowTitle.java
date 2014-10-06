package bluej.pkgmgr;

import bluej.Config;

public class PkgFrameWindowTitle {

	public PkgFrameWindowTitle(){
		
		
	}
		
	 /**
     * Set the window title to show the current package name.
     */
    protected void updateWindowTitle(IPkgFrame pmf)
    {
        if (pmf.isEmptyFrame()) {
            pmf.setWindowTitle("BlueJ");
        }
        else {
            String title = Config.getString("pkgmgr.title") + pmf.getProject().getProjectName();

            if (!pmf.getPackage().isUnnamedPackage())
                title = title + "  [" + pmf.getPackage().getQualifiedName() + "]";
            
            if(pmf.getProject().isTeamProject())
                title = title + " (" + Config.getString("team.project.marker") + ")";

            pmf.setWindowTitle(title);
        }
    }
  
}
