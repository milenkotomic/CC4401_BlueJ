package bluej.pkgmgr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import bluej.Config;
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
    private List<JComponent> testItems = new ArrayList<JComponent>();
	    
    private AbstractButton runButton;
    
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
	
	public JPanel createTestingPanel(boolean visible){
		JPanel testPanel = new JPanel();
		
		if (!Config.isRaspberryPi()) testPanel.setOpaque(false);
	     
		 testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));

		 testPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 14, 5));

		 runButton = createButton(RunTestsAction.getInstance(), false, false, 2, 4);
		 runButton.setText(Config.getString("pkgmgr.test.run"));
		 runButton.setAlignmentX(0.15f);
		 testPanel.add(runButton);
		 testPanel.add(Box.createVerticalStrut(8));

		 JLabel recordingLabel = new JLabel(Config.getString("pkgmgr.test.record"), Config
				 .getFixedImageAsIcon("record.gif"), SwingConstants.LEADING);
		 //recordingLabel.setFont(pkgMgrFont);
		 recordingLabel.setEnabled(visible);
		 recordingLabel.setAlignmentX(0.15f);
		 testPanel.add(recordingLabel);
		 testPanel.add(Box.createVerticalStrut(3));

		 Action action = EndTestRecordAction.getInstance();
		 AbstractButton endTestButton = createButton(action, false, false, 2, 4);
		 //make the button use a different label than the one from
		 // action
		 endTestButton.setText(Config.getString("pkgmgr.test.end"));
		 endTestButton.setEnabled(visible);

		 testPanel.add(endTestButton);
		 if(!Config.isMacOSLeopard()) testPanel.add(Box.createVerticalStrut(3));

		 action = CancelTestRecordAction.getInstance();
		 AbstractButton cancelTestButton = createButton(action, false, false, 2, 4);
		 //make the button use a different label than the one from
		 // action
		 cancelTestButton.setText(Config.getString("cancel"));
		 cancelTestButton.setEnabled(visible);

		 testPanel.add(cancelTestButton);

		 testPanel.setAlignmentX(0.5f);
		 testItems.add(testPanel); 
	
		 return testPanel;
	}
}
