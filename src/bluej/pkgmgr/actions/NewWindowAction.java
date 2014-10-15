package bluej.pkgmgr.actions;
import bluej.pkgmgr.PkgMgrFrame;

/**Action to perform when the user chooses "new window". When the button is pressed, 
 * an empty window is opened. 
 */
final public class NewWindowAction extends PkgMgrAction {

	
	static private NewWindowAction instance = null;
	    
	 /**Singleton method, since the action is needed once.
	   * 
	   * @return an instance of the class.
	  */
	 static public NewWindowAction getInstance()
	 {
		 if(instance == null)
			 instance = new NewWindowAction();
		 return instance;
	 }
	 /**Private constructor, according to Singleton method.  */
	 private NewWindowAction()
	 {
		 super("Open New Window");
	 }
	 
	 /**Specifies the action to be performed when the button is clicked.
	  * @see bluej.pkgmgr.actions.PkgMgrAction#actionPerformed(bluej.pkgmgr.PkgMgrFrame)
	  */
	 public void actionPerformed(PkgMgrFrame pmf)
	 {
	        pmf.menuCall();
	        pmf.doOpenWindow();
	 }
}

