package bluej.pkgmgr;

import bluej.Config;

public class PkgFrameWindowTitle {

	 /**
     * Set the window title to show the current package name.
     */
    private void updateWindowTitle(IPkgFrame pmf)
    {
        /*a abstract*/
        if (pmf.isEmptyFrame()) {
            setTitle("BlueJ");
        }
        else {
            String title = Config.getString("pkgmgr.title") + getProject().getProjectName();

            if (!getPackage().isUnnamedPackage())
                title = title + "  [" + getPackage().getQualifiedName() + "]";
            
            if(getProject().isTeamProject())
                title = title + " (" + Config.getString("team.project.marker") + ")";

            setTitle(title);
        }
    }
}
