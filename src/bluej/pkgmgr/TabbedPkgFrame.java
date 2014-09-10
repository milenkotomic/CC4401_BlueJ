package bluej.pkgmgr;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import bluej.BlueJTheme;

public class TabbedPkgFrame extends JFrame {
	
	public TabbedPkgFrame(){
		setIcon();
		setTabTitle();
		setSize();
		setPosition();
		
		JTabbedPane jtp = new JTabbedPane();
		getContentPane().add(jtp); //Incluye las pestañas en el JPanel actual, sin esto, no se ve nada!
			
		
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JLabel label1 = new JLabel();
        
        label1.setText("You are in area of Tab1");
        
        JLabel label2 = new JLabel();
        label2.setText("You are in area of Tab2");
        
        jp1.add(label1);
        jp2.add(label2);
        
        jtp.addTab("Tab1", jp1);
        jtp.addTab("Tab2", jp2);
	}
	
	private void setTabTitle(){
		setTitle("BlueJ");
      
    }
	
	private void setIcon(){
		Image icon = BlueJTheme.getIconImage();
	    if (icon != null) {
	    	setIconImage(icon);
	    }
	}
	
	private void setSize(){
		setSize(new Dimension(695, 575)); //Tamaño estandar de ventana BlueJ
	}
	
	private void setPosition(){
		setLocation(20,20);
	}

}
