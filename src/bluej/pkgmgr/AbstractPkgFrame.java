package bluej.pkgmgr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
