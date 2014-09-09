package bluej.pkgmgr.actions;

import bluej.Config;
import bluej.pkgmgr.PkgMgrFrame;

public class NewIssueGitHubAction extends PkgMgrAction {
	
	static private NewIssueGitHubAction instance = null;
    
    /**
     * Factory method. This is the way to retrieve an instance of the class,
     * as the constructor is private.
     * @return an instance of the class.
     */
    static public NewIssueGitHubAction getInstance()
    {
        if(instance == null)
            instance = new NewIssueGitHubAction();
        return instance;
    }
    
    private NewIssueGitHubAction()
    {
        super("NewIssue");
    }
    
    public void actionPerformed(PkgMgrFrame pmf)
    {
        pmf.doNewIssueGitHub();        
    }

}