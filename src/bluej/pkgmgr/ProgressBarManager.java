package bluej.pkgmgr;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import bluej.groupwork.ui.ActivityIndicator;

public class ProgressBarManager {
	
	private ActivityIndicator progressbar; 
	
	public ProgressBarManager(JPanel statusArea){
		progressbar = new ActivityIndicator();
        progressbar.setRunning(false);
        statusArea.add(progressbar, BorderLayout.EAST);
	}
	
	/**
	  * Start the activity indicator. Call from any thread.
	  */
	
	public void startProgress()
	 {
	     progressbar.setRunning(true);
	 }
	
	 /**
	  * Stop the activity indicator. Call from any thread.
	  */
	 public void stopProgress()
	 {
		 progressbar.setRunning(false);
	 }
	
	 
	 
	 
	 
}
