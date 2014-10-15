package bluej.pkgmgr;

import bluej.groupwork.ui.ActivityIndicator;

public class PkgFrameStatusBar {
	
	private ActivityIndicator progressbar; 
	
	public PkgFrameStatusBar(){
		
	}
	
	/**
	  * Start the activity indicator. Call from any thread.
	  */
	
	public void startProgress()
	 {
	 	/*otra clase*/
	     progressbar.setRunning(true);
	 }
	
	 /**
	  * Stop the activity indicator. Call from any thread.
	  */
	 public void stopProgress()
	 {
	 	/*otra clase*/
	     progressbar.setRunning(false);
	 }
	

}
