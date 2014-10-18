package bluej.pkgmgr;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class AbstractPkgMenu {

	protected JMenuItem createMenuItem(Action action, JMenu menu){
		JMenuItem item = menu.add(action);
		item.setIcon(null);
		return item;
	}
	
}
