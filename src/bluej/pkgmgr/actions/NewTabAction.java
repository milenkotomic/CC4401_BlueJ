package bluej.pkgmgr.actions;

import bluej.pkgmgr.IPkgFrame;
import bluej.pkgmgr.PkgMgrFrame;

/**Action to perform when the user chooses "new tab". When the button is pressed, 
 * an empty tab is opened. 
 */
public class NewTabAction extends PkgMgrAction {
		
	static private NewTabAction instance = null;
		    
	/**Singleton method, since the action is needed once.
	   * @return an instance of the class.
	  */
	 static public NewTabAction getInstance()
	{
		 if(instance == null)
			 instance = new NewTabAction();
		 return instance;
	}
	 
	 /**Private constructor, according to Singleton method.  */	    
	 private NewTabAction()
	 {
		 super("Open New Tab"); 
	 }
	
	 /**Specifies the action to be performed when the button is clicked.
	  * @see bluej.pkgmgr.actions.PkgMgrAction#actionPerformed(bluej.pkgmgr.PkgMgrFrame)
	  */
	 public void actionPerformed(IPkgFrame pmf)
	 {
	        pmf.menuCall();
	        pmf.doOpenTab();
	 }
}




