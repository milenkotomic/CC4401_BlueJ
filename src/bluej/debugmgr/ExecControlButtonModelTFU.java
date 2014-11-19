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
package bluej.debugmgr;

import javax.swing.JToggleButton;

import bluej.pkgmgr.TabbedFrameUnit;
import bluej.pkgmgr.TabbedPkgFrame;

/**
 * ButtonModel for the "Show Debugger" checkBoxItem in the menu.
 * This model takes care that the right things happen when the checkbox
 * is shown or changed.
 *
 * @author Michael Kolling
 */
public class ExecControlButtonModelTFU extends JToggleButton.ToggleButtonModel
{
	private TabbedFrameUnit tfu;
	
    public ExecControlButtonModelTFU(TabbedPkgFrame tpf)
    {
        super();
        this.tfu = tpf.getLastFrame();
    }

    public boolean isSelected()
    {
    	if (tfu.isEmptyFrame()) {
			// if no project is open, we default to off
			return false;
    	}
    	else if (!tfu.getProject().hasExecControls()) {
			// we don't want to create the ExecControls frame unless we
			// have to, so if its not made yet, default to off
			return false;
    	}
    	else {
			// otherwise, ask the ExecControls if they're visible
			return tfu.getProject().getExecControls().isVisible();
    	}
    }

    public void setSelected(boolean b)
    {
		if (!tfu.isEmptyFrame()) {
			super.setSelected(b);
			tfu.getProject().getExecControls().showHide(b);
		}
    }
}