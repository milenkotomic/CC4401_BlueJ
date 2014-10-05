package bluej.pkgmgr;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bluej.BlueJEvent;
import bluej.prefmgr.PrefMgr;

public class PkgFrameManager {
	
	protected List<IPkgFrame> frames;
	
	public PkgFrameManager(){
		//frames = new ArrayList
	}
	
	/**
     * Open a PkgMgrFrame with no package. Packages can be installed into this
     * frame using the methods openPackage/closePackage.
     */
    public IPkgFrame createFrame()
    {
    	/*SI*/
        IPkgFrame frame = new PkgMgrFrame();
        frames.add(frame);
        BlueJEvent.addListener(frame);
        
        frame.addWindowFocusListener(new WindowFocusListener() {
            
            @Override
            public void windowLostFocus(WindowEvent e)
            {
                // Nothing to do...
            }
            
            @Override
            public void windowGainedFocus(WindowEvent e)
            {
                Window w = e.getWindow();
                if (w instanceof PkgMgrFrame) {
                    // This *should* always be the case
                    recentFrame = (PkgMgrFrame) w;
                }
            }
        });
        
        return frame;
    }

    /**
     * Open a PkgMgrFrame with a package. This may create a new frame or return
     * an existing frame if this package is already being edited by a frame. If
     * an empty frame exists, that frame will be used to show the package.
     */
    public IPkgFrame createFrame(Package pkg)
    {
    	/*SI*/
        IPkgFrame pmf = findFrame(pkg);

        if (pmf == null) {
            // check whether we've got an empty frame

            if (frames.size() == 1)
                pmf = frames.get(0);

            if ((pmf == null) || !pmf.isEmptyFrame())
                pmf = createFrame();

            pmf.openPackage(pkg);
        }

        return pmf;
    }

    /**
     * Remove a frame from the set of currently open PkgMgrFrames. The
     * PkgMgrFrame must not be editing a package when this function is called.
     */
    public void closeFrame(PkgMgrFrame frame)
    {
    	/*SI*/
        if (!frame.isEmptyFrame())
            throw new IllegalArgumentException();

        frames.remove(frame);

        BlueJEvent.removeListener(frame);
        PrefMgr.setFlag(PrefMgr.SHOW_TEXT_EVAL, frame.showingTextEvaluator);

        // frame should be garbage collected but we will speed it
        // on its way
        frame.dispose();
    }

    /**
     * Find a frame which is editing a particular Package and return it or
     * return null if it is not being edited
     */
    public IPkgFrame findFrame(Package pkg)
    {
    	/*SI*/
        for (Iterator<IPkgFrame> i = frames.iterator(); i.hasNext();) {
            IPkgFrame pmf = i.next();

            if (!pmf.isEmptyFrame() && pmf.getPackage() == pkg)
                return pmf;
        }
        return null;
    }

    /**
     * @return the number of currently open top level frames
     */
    public int frameCount()
    {
    	return frames.size();
    }

    /**
     * Returns an array of all PkgMgrFrame objects. It can be an empty array if
     * none is found.
     */
    public PkgMgrFrame[] getAllFrames()
    {
    	/*SI*/
        PkgMgrFrame[] openFrames = new PkgMgrFrame[frames.size()];
        frames.toArray(openFrames);

        return openFrames;
    }

    /**
     * Find all PkgMgrFrames which are currently editing a particular project
     * 
     * @param proj
     *            the project whose packages to look for
     * 
     * @return an array of open PkgMgrFrame objects which are currently editing
     *         a package from this project, or null if none exist
     */
    public IPkgFrame[] getAllProjectFrames(Project proj)
    {
    	/*SI*/
        return getAllProjectFrames(proj, "");
    }

    /**
     * Find all PkgMgrFrames which are currently editing a particular project,
     * and which are below a certain point in the package heirarchy.
     * 
     * @param proj
     *            the project whose packages to look for
     * @param pkgPrefix
     *            the package name of a package to look for it and all its
     *            children ie if passed java.lang we would return frames for
     *            java.lang, and java.lang.reflect if they exist
     * 
     * @return an array of open PkgMgrFrame objects which are currently editing
     *         a package from this project and which have the package prefix
     *         specified, or null if none exist
     */
    public IPkgFrame[] getAllProjectFrames(Project proj, String pkgPrefix)
    {
    	/*SI:Adaptable a entregar todas las PESTAÑAS que esten en el mismo proyecto*/
        List<IPkgFrame> list = new ArrayList<IPkgFrame>();
        String pkgPrefixWithDot = pkgPrefix + ".";

        for (Iterator<IPkgFrame> i = frames.iterator(); i.hasNext();) {
            IPkgFrame pmf = i.next();

            if (!pmf.isEmptyFrame() && pmf.getProject() == proj) {

                String fullName = pmf.getPackage().getQualifiedName();

                // we either match against the package prefix with a
                // dot added (this stops false matches against similarly
                // named package ie java.lang and java.language) or we
                // match the full name against the package prefix
                if (fullName.startsWith(pkgPrefixWithDot))
                    list.add(pmf);
                else if (fullName.equals(pkgPrefix) || (pkgPrefix.length() == 0))
                    list.add(pmf);
            }
        }

        if (list.isEmpty())
            return null;

        return list.toArray(new PkgMgrFrame[list.size()]);
    }
}
