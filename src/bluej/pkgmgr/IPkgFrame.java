package bluej.pkgmgr;

public interface IPkgFrame{
	/*Interface que deben cumplir todos los tipos de ventanas*/
	
	public void menuCall();
		
	/*Llamadas a metodos cuando se apretan botones*/
	
	/*Project*/
	public void doClose(boolean b, boolean c);
	
	/*Tools*/
	public void showPreferences();
	
	
	/*Help*/
	public void aboutBlueJ();
	public void showCopyright();
	public void showWebPage(String url);
	
	/*Windows*/
	abstract void doOpenTab();
	
	/*public void doOpenWindow();
	public void updateShowUsesInPackage();
	
	public void callLibraryClass();
	public void doTest();
	public void restartDebugger();
	public void doRemove();*/
	public void doAddFromFile();
	/*public void doPushGitHub();
	public void doPrint();
	public void doPageSetup();
	public void doOpen();
	public void doOpenNonBlueJ();
	public void doNewUses();
	public boolean doNewProject(boolean b);
	public void doCreateNewPackage();
	public void doNewIssueGitHub();
	public void doNewInherits();
	public void doCreateNewClass();
	public void doImport();
	public void generateProjectDocumentation();
	public void doExport();
	public void doEndTest();
	public void doDeployMIDlet();
	public void compileSelected();
	public void doCancelTest();
	public void doCommitGitHub();*/
	
	
	
	
	//public Package getPackage();
	//public void clearStatus();

	
	
}