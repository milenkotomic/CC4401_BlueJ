package bluej.pkgmgr.actions;

import bluej.Config;
import bluej.pkgmgr.IPkgFrame;
import bluej.pkgmgr.PkgMgrFrame;

public class CommitGitHubAction extends PkgMgrAction {
	
	static private CommitGitHubAction instance = null;
    
    /**
     * Factory method. This is the way to retrieve an instance of the class,
     * as the constructor is private.
     * @return an instance of the class.
     */
    static public CommitGitHubAction getInstance()
    {
        if(instance == null)
            instance = new CommitGitHubAction();
        return instance;
    }
    
    private CommitGitHubAction()
    {
        super("Commit");
    }
    
    public void actionPerformed(IPkgFrame pmf)
    {
        pmf.doCommitGitHub();        
    }

}