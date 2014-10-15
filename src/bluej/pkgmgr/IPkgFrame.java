package bluej.pkgmgr;

public interface IPkgFrame {
	/*Interface que deben cumplir todos los tipos de ventanas*/
	
	public void menuCall();
		
	/*Llamadas a metodos cuando se apretan botones*/
	/*HACER ALGO CON ESTO AHHHH*/
	
	public void showPreferences();
	public void aboutBlueJ();
	public void doOpenWindow();
	public void updateShowUsesInPackage();
	public void showWebPage(String propString);
	public void callLibraryClass();
	public void doTest();
	public void restartDebugger();
	public void doRemove();
	public Package getPackage();
	public void doAddFromFile();
	public void doPushGitHub();
	public void doPrint();
	public void doPageSetup();
	public void doOpen();
	public void doOpenNonBlueJ();
	public void clearStatus();
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
	public void doCommitGitHub();
	public void doClose(boolean b, boolean c);

	public boolean isEmptyFrame();

	public Project getProject();

	public boolean isTextEvalVisible();

	public void showHideTextEval(boolean b);

	public void openPackage(Package pkg);

	public IPkgFrame getMostRecent();
	
}
