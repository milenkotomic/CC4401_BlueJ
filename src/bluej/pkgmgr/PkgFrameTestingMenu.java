package bluej.pkgmgr;

import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import bluej.pkgmgr.actions.CancelTestRecordAction;
import bluej.pkgmgr.actions.EndTestRecordAction;
import bluej.pkgmgr.actions.RunTestsAction;
import bluej.prefmgr.PrefMgr;

public class PkgFrameTestingMenu extends AbstractPkgMenu{

	private JLabel testStatusMessage;
    private JLabel recordingLabel;
    private AbstractButton endTestButton;
    private AbstractButton cancelTestButton;
    private JMenuItem endTestMenuItem;
    private JMenuItem cancelTestMenuItem;
    
    private JMenu testingMenu;
    private List<JComponent> testItems;
	    
    public PkgFrameTestingMenu(){
    	
    }
        
	public void initTestingMenu(JMenu menu){
	
		createMenuItem(RunTestsAction.getInstance(), testingMenu);
		endTestMenuItem = createMenuItem(EndTestRecordAction.getInstance(), testingMenu);
	    cancelTestMenuItem = createMenuItem(CancelTestRecordAction.getInstance(), testingMenu);
	    endTestMenuItem.setEnabled(false);
	    cancelTestMenuItem.setEnabled(false);

		testItems.add(testingMenu);
		menu.add(testingMenu);
		
	}

    /**
     * Tell whether unit testing tools should be shown.
     */
    public boolean wantToSeeTestingTools()
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
    	
	/**
     * Recording of a test case started - set the interface appropriately.
     */
    public void testRecordingStarted(String message,Project proj)
    {
        recordingLabel.setEnabled(true);
        testStatusMessage.setText(message);
        endTestButton.setEnabled(true);
        endTestMenuItem.setEnabled(true);
        cancelTestButton.setEnabled(true);
        cancelTestMenuItem.setEnabled(true);

        proj.setTestMode(true);
    }
	
	/**
	 * Recording of a test case ended - set the interface appropriately.
	 */
	protected void testRecordingEnded(Project proj)
	{
	    recordingLabel.setEnabled(false);
	    testStatusMessage.setText("");
	    endTestButton.setEnabled(false);
	    endTestMenuItem.setEnabled(false);
	    cancelTestButton.setEnabled(false);
	    cancelTestMenuItem.setEnabled(false);
	
	   if (proj != null) {
		   proj.setTestMode(false);
	   }
	}
	
	
}
