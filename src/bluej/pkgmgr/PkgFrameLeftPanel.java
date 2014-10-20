package bluej.pkgmgr;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import bluej.Config;
import bluej.pkgmgr.actions.CompileAction;
import bluej.pkgmgr.actions.NewClassAction;
import bluej.pkgmgr.actions.NewInheritsAction;
import bluej.pkgmgr.actions.NewUsesAction;

public class PkgFrameLeftPanel extends AbstractPkgMenu {

	protected JPanel createLeftPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        AbstractButton button = createButton(NewClassAction.getInstance(), false, false, 4, 4);
        buttonPanel.add(button);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));
        
        AbstractButton imgDependsButton = createButton(NewUsesAction.getInstance(), true, false, 4, 4);
        buttonPanel.add(imgDependsButton);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));

        AbstractButton imgExtendsButton = createButton(NewInheritsAction.getInstance(), true, false, 4, 4);
        buttonPanel.add(imgExtendsButton);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));

        button = createButton(CompileAction.getInstance(), false, false, 4, 4);
        buttonPanel.add(button);
        if(!Config.isMacOSLeopard()) buttonPanel.add(Box.createVerticalStrut(3));

        buttonPanel.setAlignmentX(0.5f);
        
        return buttonPanel;
  	}
	
	
}
