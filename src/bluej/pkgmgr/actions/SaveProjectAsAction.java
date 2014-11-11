/*
 This file is part of the BlueJ program. 
 Copyright (C) 1999-2009,2012,2014  Michael Kolling and John Rosenberg 
 
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

import java.io.File;

import bluej.Config;
import bluej.pkgmgr.IPkgFrame;
import bluej.pkgmgr.Package;
import bluej.pkgmgr.PkgMgrFrame;
import bluej.pkgmgr.Project;
import bluej.utility.Debug;
import bluej.utility.DialogManager;
import bluej.utility.FileUtility;

/**
 * User chooses "save project as". This allows saving the project under a
 * different name, to make a backup etc.
 * 
 * @author Davin McCall
 */
final public class SaveProjectAsAction extends PkgMgrAction
{
	static private SaveProjectAsAction instance = null;
	
	 static public SaveProjectAsAction getInstance()
	{
			 if(instance == null)
				 instance = new SaveProjectAsAction();
			 return instance;
	}
	
    private SaveProjectAsAction()
    {
        super("menu.package.saveAs");
    }
    
    public void actionPerformed(IPkgFrame pmf)
    {
        pmf.menuCall();
        pmf.doSaveAs(pmf);
    }
}
