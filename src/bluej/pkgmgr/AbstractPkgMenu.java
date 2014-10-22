package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;

import bluej.Config;
import bluej.utility.Utility;

public abstract class AbstractPkgMenu {

	protected JMenuItem createMenuItem(Action action, JMenu menu){
		JMenuItem item = menu.add(action);
		item.setIcon(null);
		return item;
	}

	 /**
     * Create a button for the interface.
     * 
     * @param action
     *            the Action abstraction dictating text, icon, tooltip, action.
     * @param notext
     *            set true if the action text should not appear (icon only).
     * @param toggle
     *            true if this is a toggle button, false otherwise
     * @param hSpacing
     *            horizontal margin (left, right)
     * @param vSpacing
     *            vertical margin (top, bottom)
     * @return the new button
     */
    protected AbstractButton createButton(Action action, boolean notext, boolean toggle, int hSpacing, int vSpacing)
    {
        AbstractButton button;
        if (toggle) {
            button = new JToggleButton(action);
        }
        else {
            button = new JButton(action);
        }
        //button.setFont(pkgMgrFont);
        Utility.changeToMacButton(button);
        button.setFocusable(false); // buttons shouldn't get focus

        if (notext)
            button.setText(null);

        Dimension pref = button.getMinimumSize();
        pref.width = Integer.MAX_VALUE;
        button.setMaximumSize(pref);
        if(!Config.isMacOSLeopard()) {
            button.setMargin(new Insets(vSpacing, hSpacing, vSpacing, hSpacing));
        }

        return button;
    }
	
}
