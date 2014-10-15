/*
 This file is part of the BlueJ program. 
 Copyright (C) 1999-2009  Michael Kolling and John Rosenberg 
 
 This program is free software; you can redistribute it and/or 
 modify it under the terms of the GNU General Public License 
 as published by the Free Software Foundation; either version 2 
 of the License, or (at your option) any later version. 
 
 This program is distributed in the hope that it will be useful, 
 but WITHOUT ANY WARRANTY; without even the implied warranty of 
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 GNU General Public License for more details. 
 
 You should have received a copy of the GNU General Public License 
 along with this program; if not, write to the Free Software 
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. 
 
 This file is subject to the Classpath exception as provided in the  
 LICENSE.txt file that accompanied this code.
 */
package bluej.pkgmgr.actions;

import bluej.extmgr.ExtensionsManager;
import bluej.pkgmgr.PkgMgrFrame;

/**
 * Check installed extensions. Pop up a dialog box displaying summary info
 * about each installed extension, allowing user to get a brief description
 * of each one.
 * 
 * @author Davin McCall
 * @version $Id: CheckExtensionsAction.java 6215 2009-03-30 13:28:25Z polle $
 */
public final class CheckExtensionsAction extends PkgMgrAction {
    
    static private CheckExtensionsAction instance = null;
    
    /**
     * Factory method. This is the way to retrieve an instance of the class,
     * as the constructor is private.
     * @return an instance of the class.
     */
    static public CheckExtensionsAction getInstance()
    {
        if(instance == null)
            instance = new CheckExtensionsAction();
        return instance;
    }
    
    private CheckExtensionsAction()
    {
        super("menu.help.extensions");
    }
    
    public void actionPerformed(PkgMgrFrame pmf)
    {
        pmf.menuCall();
        ExtensionsManager.getInstance().showHelp(pmf);
    }
}
