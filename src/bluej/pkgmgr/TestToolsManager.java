package bluej.pkgmgr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import bluej.prefmgr.PrefMgr;

public class TestToolsManager {
	private boolean testToolsShown;
	private List<JComponent> testItems;
	
	public TestToolsManager(){
		testToolsShown = wantToSeeTestingTools();
		testItems = new ArrayList<JComponent>();
	}
		
	protected boolean wantToSeeTestingTools()
    {
		return PrefMgr.getFlag(PrefMgr.SHOW_TEST_TOOLS);
    }
	
	 /**
     * Show or hide the testing tools.
     */
    public void showTestingTools(boolean show)
    {
        for (Iterator<JComponent> it = testItems.iterator(); it.hasNext();) {
            JComponent component = it.next();
            component.setVisible(show);
        }
    }
    
    /*Testing*/
    /**
     * Check whether the status of the 'Show unit test tools' preference has
     * changed, and if it has, show or hide them as requested.
     */
    public void updateTestingStatus(IPkgFrame pmf)
    {
    	if (testToolsShown != wantToSeeTestingTools()) {
      
    		if ( pmf.isJavaMEpackage() ) {
    			pmf.showTestingTools(false);
            }
            else {
            	pmf.showTestingTools(!testToolsShown);               
            }
    	}
            testToolsShown = !testToolsShown;
        
    }
}
