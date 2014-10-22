package bluej.pkgmgr.actions;

import bluej.Config;
import bluej.pkgmgr.IPkgFrame;
import bluej.pkgmgr.PkgMgrFrame;

public class PullGitHubAction extends PkgMgrAction {
	
	static private PullGitHubAction instance = null;
    
    /**
     * Factory method. This is the way to retrieve an instance of the class,
     * as the constructor is private.
     * @return an instance of the class.
     */
    static public PullGitHubAction getInstance()
    {
        if(instance == null)
            instance = new PullGitHubAction();
        return instance;
    }
    
    private PullGitHubAction()
    {
        super("Pull");
    }
    
    public void actionPerformed(IPkgFrame pmf)
    {
        pmf.doPullGitHub();        
    }

}