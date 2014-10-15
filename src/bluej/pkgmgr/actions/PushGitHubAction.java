package bluej.pkgmgr.actions;

import bluej.Config;
import bluej.pkgmgr.PkgMgrFrame;

public class PushGitHubAction extends PkgMgrAction {
	
	static private PushGitHubAction instance = null;
    
    /**
     * Factory method. This is the way to retrieve an instance of the class,
     * as the constructor is private.
     * @return an instance of the class.
     */
    static public PushGitHubAction getInstance()
    {
        if(instance == null)
            instance = new PushGitHubAction();
        return instance;
    }
    
    private PushGitHubAction()
    {
        super("Push");
    }
    
    public void actionPerformed(PkgMgrFrame pmf)
    {
        pmf.doPushGitHub();        
    }

}
