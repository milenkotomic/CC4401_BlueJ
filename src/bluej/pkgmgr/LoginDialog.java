package bluej.pkgmgr;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import org.eclipse.egit.github.core.client.GitHubClient;

import bluej.github.GitHubConnection;

/**
 * User interface dialog that validates GitHub credentials for logging in   
 * 
 * @author ekauffmann
 * 
 */
public class LoginDialog extends JDialog {
 
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbicon;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
 
    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnLogin = new JButton("Login");
        
        //Do something when clicked to login
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	//if null input in any field, try again
            	if(getUsername().equals("") || getPassword().equals("")){
            		 JOptionPane.showMessageDialog(LoginDialog.this,
                             "Please try again and fill both 'username' and 'password' fields",
                             "Login Error",
                             JOptionPane.INFORMATION_MESSAGE);
            		 // reset username and password
                     tfUsername.setText("");
                     pfPassword.setText("");
                     succeeded = false;            		
            	}
            	
          
            	else{
            		succeeded = true;
            		dispose();
            	}
            }
        });
         
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    /**
     * 
     * @return username entered by the client 
     */
    public String getUsername() {
        return tfUsername.getText().trim();
    }
    
    /**
     * 
     * @return password entered by the client
     */
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    /**
     * 
     * @return True if username and password inputs are not ""
     */
    public boolean isSucceeded() {
        return succeeded;
    }
}