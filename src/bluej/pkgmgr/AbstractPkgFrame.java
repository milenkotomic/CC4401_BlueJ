package bluej.pkgmgr;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AbstractPkgFrame extends JFrame {

	 /**
     * Add a new menu item to a menu.
     */
    protected JMenuItem createMenuItem(Action action, JMenu menu)
    {
        JMenuItem item = menu.add(action);
        item.setIcon(null);
        return item;
    }
		
}
