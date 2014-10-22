package bluej.pkgmgr;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class AbstractPkgFrame extends JFrame implements IPkgFrame{

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
