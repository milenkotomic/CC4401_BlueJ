package bluej.pkgmgr;

import javax.swing.JPanel;

public class TabbedFrameUnit {
	private Package pkg = null;
	protected JPanel tabWindow;
	
	public TabbedFrameUnit(){
		
	}
	
	public TabbedFrameUnit(Package p){
		pkg = p;
	}
	
	public boolean isEmptyFrame(){
		return pkg == null;		
	}
	
	public void setState(){
		pkg.setState(Package.S_IDLE);
	}

	public JPanel getTab(){
		return tabWindow;
	}
	
}
