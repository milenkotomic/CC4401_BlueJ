package bluej.pkgmgr;

import java.util.Iterator;

import bluej.prefmgr.PrefMgr;

public class JavaMEManager {

	  /**
     * Check whether the status of the 'Show Java ME tools' preference has
     * changed, and if it has, show or hide them as requested.
     */
    public static void updateJavaMEstatus()
    {
    	/* ?-> esto debiese hacerlo una clase, no el mismo pkgframe*/
        if ( javaMEtoolsShown != wantToSeeJavaMEtools() )  {
            for (Iterator<PkgMgrFrame> i = frames.iterator(); i.hasNext();) {
                i.next().showJavaMEtools( !javaMEtoolsShown );
            }
            javaMEtoolsShown = !javaMEtoolsShown;
        }
    }
    /**
     * Tell whether Java ME tools should be shown.
     */
    private static boolean wantToSeeJavaMEtools()
    {
    	/* ?-> esto debiese hacerlo una clase, no el mismo pkgframe*/
        return PrefMgr.getFlag( PrefMgr.SHOW_JAVAME_TOOLS );
    }
	
    /**
     * Show or hide the Java ME controls.
     */
    private void showJavaMEcontrols(boolean show )
    {           
    	/* ?-> esto debiese hacerlo una clase, no el mismo pkgframe*/
        javaMEdeployMenuItem.setVisible(show);
        javaMEPanel.setVisible(show);              
    }
	
}
