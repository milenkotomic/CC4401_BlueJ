package bluej.pkgmgr;

public interface IPkgFrame{
	/*Interface que deben cumplir todos los tipos de ventanas*/
	
	Object getLastFrame = null;
	public void menuCall();
	public void clearStatus();	
	
	/*Llamadas a metodos cuando se apretan botones*/
	
	/*Project*/
	public boolean doNewProject(boolean isJavaME);
	public void doOpen();
	public void doOpenNonBlueJ();
	public void doClose(boolean keepLastFrame, boolean doSave);
	public void doSaveProject();
	public void doSaveAs(IPkgFrame target);
	public void doImport();
	public void doExport();
	public void doPageSetup();
	public void doPrint();
	
	/*Edit*/
	public void doCreateNewClass();
	public void doCreateNewPackage();
	public void doAddFromFile();
	public void doRemove();
	public void doNewInherits();
	public void doNewUses();
	
	/*Tools*/
	public void doCompile();
	public void compileSelected();
	public void rebuild(); 
	public void restartDebugger(); //Mismo problema anterior!
	public void callLibraryClass();
	public void generateProjectDocumentation();
	public void showPreferences();
	
	/*View*/
	public void updateShowUsesInPackage();
	public void updateShowExtendsInPackage();
	
	/*Help*/
	public void aboutBlueJ();
	public void showCopyright();
	public void showWebPage(String url);
	
	/*Windows*/
	public void doOpenTab();
	public void doOpenWindow();
	
	
	/*Text Evaluator Functions*/
	public boolean isTextEvalVisible();
	public void showHideTextEval(boolean b);
	
	
	
	
	
	
	
	/*
	
	
	
	public void doTest();
	
	*/
	
	/*public void doPushGitHub();
	public void doNewIssueGitHub();
	public void doCommitGitHub();	
	
	
	
	public void doEndTest();
	public void doDeployMIDlet();
	
	public void doCancelTest();
	*/

	
	
}