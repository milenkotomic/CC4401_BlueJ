package bluej.debugmgr.objectbench;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import bluej.debugger.*;
import bluej.pkgmgr.PkgMgrFrame;
import bluej.pkgmgr.TabbedFrameUnit;
import bluej.prefmgr.PrefMgr;

/**
 * A wrapper around array objects.
 * 
 * The array wrapper is represented by a few red ovals that are visible on the
 * object bench.
 * 
 * @author Andrew Patterson
 * @author Bruce Quig
 * @version $Id: ArrayWrapper.java 6215 2009-03-30 13:28:25Z polle $
 */
public class ArrayWrapperTFU extends ObjectWrapperTFU
{
    public static int WORD_GAP = 8;
    public static int SHADOW_SIZE = 3;
    public static int ARRAY_GAP = 3;
    
    

    public ArrayWrapperTFU(TabbedFrameUnit pmf, AbstractObjectBench ob, DebuggerObject obj, String instanceName)
    {
        super(pmf, ob, obj, obj.getGenType(), instanceName);
    }

    /**
     * Creates the popup menu structure by parsing the object's class
     * inheritance hierarchy.
     * 
     * @param className
     *            class name of the object for which the menu is to be built
     */
    protected void createMenu(String className)
    {
        menu = new JPopupMenu(getName());
        JMenuItem item;

        //        item.addActionListener(
        //            new ActionListener() {
        //                public void actionPerformed(ActionEvent e) {
        // /*invokeMethod(e.getSource());*/ }
        //           });

        // add inspect and remove options
        menu.add(item = new JMenuItem(inspect));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                inspectObject();
            }
        });
        item.setFont(PrefMgr.getStandoutMenuFont());
        item.setForeground(envOpColour);

        menu.add(item = new JMenuItem(remove));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                removeObject();
            }
        });
        item.setFont(PrefMgr.getStandoutMenuFont());
        item.setForeground(envOpColour);

        add(menu);
    }

    /**
     * draw a UML style object (array) instance
     */
    protected void drawUMLStyle(Graphics2D g)
    {
        g.setFont(PrefMgr.getStandardFont());

        drawUMLObjectShape(g, HGAP + ARRAY_GAP*2, (VGAP / 2) + ARRAY_GAP*2, WIDTH - 10, HEIGHT - 10,  SHADOW_SIZE, 8);
        drawUMLObjectShape(g, HGAP + ARRAY_GAP, (VGAP / 2) + ARRAY_GAP, WIDTH - 10, HEIGHT - 10,  SHADOW_SIZE, 8);
        drawUMLObjectShape(g, HGAP, (VGAP / 2), WIDTH - 10, HEIGHT - 10,  SHADOW_SIZE, 8);

        drawUMLObjectText(g, HGAP, (VGAP / 2), WIDTH - 10, 3, getName() + ":", displayClassName);

    }
}
