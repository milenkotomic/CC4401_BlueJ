package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import bluej.Config;
import bluej.pkgmgr.actions.DeployMIDletAction;
import bluej.pkgmgr.actions.NewMEprojectAction;
import bluej.prefmgr.PrefMgr;

public class PkgFrameJavaME extends AbstractPkgMenu{
    private JMenuItem javaMEdeployMenuItem;
    private JMenuItem javaMEnewProjMenuItem;
    private JPanel javaMEPanel;
		
    public PkgFrameJavaME(){  	
    	javaMEPanel = new JPanel();
    	
    }
    
    public void initJavaMEProj(JMenu menu){
    	javaMEnewProjMenuItem = createMenuItem( NewMEprojectAction.getInstance(), menu);   
    }
    
    public void initJavaMEDeploy(JMenu menu){
    	javaMEdeployMenuItem = createMenuItem( DeployMIDletAction.getInstance(), menu); 
    	javaMEdeployMenuItem.setVisible(false); //visible only in Java ME packages
    }
    
    /**
    * Show or hide the Java ME controls.
    */
    public void showJavaMEcontrols(boolean show)
    {           
        javaMEdeployMenuItem.setVisible(show);
        javaMEPanel.setVisible(show);              
    }
	
    /**
     * Show or hide the Java ME tools, which for now is just the
     * 'New ME Project...' menu item in the Project menu.
     * Java ME tools show or not in all packages--not only in
     * Java ME packages--depending on whether the checkbox in 
     * the Preferences panel is ticked or not.
     */
    public void showJavaMEtools(boolean show)
    {
        javaMEnewProjMenuItem.setVisible(show);
    }
    
    /**
     * Tell whether Java ME tools should be shown.
     */
    protected boolean wantToSeeJavaMEtools()
    {
        return PrefMgr.getFlag( PrefMgr.SHOW_JAVAME_TOOLS );
    }
    
    protected JPanel createJavaMEPanel(){  	
    	if (!Config.isRaspberryPi()) javaMEPanel.setOpaque(false);
   
        javaMEPanel.setLayout(new BoxLayout(javaMEPanel, BoxLayout.Y_AXIS));
        javaMEPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 14, 5));

        JLabel label = new JLabel( "Java ME" );
        label.setFont( new Font ("SansSerif", Font.BOLD, 12 ) );
        label.setHorizontalAlignment( JLabel.CENTER );
        label.setForeground( label.getBackground( ).darker( ).darker( ) ); 
        Dimension pref = label.getMinimumSize();
        pref.width = Integer.MAX_VALUE;
        label.setMaximumSize(pref);
        label.setAlignmentX(0.5f);
        javaMEPanel.add( label );
        javaMEPanel.add( Box.createVerticalStrut( 4 ) );   
        
        AbstractButton button = createButton( DeployMIDletAction.getInstance(), false, false, 4, 4 );
        button.setAlignmentX(0.5f);
        javaMEPanel.add( button );
        javaMEPanel.add( Box.createVerticalStrut( 4 ) );   
        if(!Config.isMacOSLeopard()) javaMEPanel.add(Box.createVerticalStrut(3));
        
        return javaMEPanel;
    }
	
}
