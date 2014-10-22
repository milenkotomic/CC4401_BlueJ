package bluej.pkgmgr;

public interface IPkgFrame{
	/*Interface que deben cumplir todos los tipos de ventanas*/
	
	public void menuCall();
		
	/*Llamadas a metodos cuando se apretan botones*/
	
	/*Project*/
	public boolean doNewProject(boolean isJavaME);
	public void doOpen();
	public void doOpenNonBlueJ();
	public void doClose(boolean keepLastFrame, boolean doSave);
	
	/*Tools*/
	public void showPreferences();
		
	/*Help*/
	public void aboutBlueJ();
	public void showCopyright();
	public void showWebPage(String url);
	
	/*Github*/
	public void doNewIssueGitHub();
	public void doPushGitHub();
	public void doCommitGitHub();
	public void doPullGitHub();
	
	/*Windows*/
	public void doOpenTab();
	public void doOpenWindow();
	
	/*
	public void updateShowUsesInPackage();
	
	public void callLibraryClass();
	public void doTest();
	public void restartDebugger();
	public void doRemove();*/
	public void doAddFromFile();
	/*
	public void doPrint();
	public void doPageSetup();
	
	
	public void doNewUses();
	
	public void doCreateNewPackage();
	
	public void doNewInherits();
	public void doCreateNewClass();
	public void doImport();
	public void generateProjectDocumentation();
	public void doExport();
	public void doEndTest();
	public void doDeployMIDlet();
	public void compileSelected();
	public void doCancelTest();
	*/



	/*Herramientas opcionales*/
	/*public void updateTestingStatus();
	public void updateTeamStatus();
	public void updateJavaMEstatus();*/
	

	
	
}