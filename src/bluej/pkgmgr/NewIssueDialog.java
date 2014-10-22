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
public class NewIssueDialog extends JDialog {
 
    private JTextField tfIssueTitle;
    private JTextField tfRepository;
    private JTextField tfDescription;
    private JLabel lbIssueTitle;
    private JLabel lbRepository;
    private JLabel lbDescription;
    private JButton btnSubmit;
    private JButton btnCancel;
    private boolean succeeded;
    private boolean cancelled;
 
    public NewIssueDialog(Frame parent) {
        super(parent, "New Issue", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbRepository = new JLabel("Repository name: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbRepository, cs);
 
        tfRepository = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfRepository, cs);
 
        lbIssueTitle = new JLabel("Title: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbIssueTitle, cs);
 
        tfIssueTitle = new JTextField(50);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfIssueTitle, cs);
        
        lbDescription = new JLabel("Description (optional): ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbDescription, cs);
 
        tfDescription = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfDescription, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnSubmit = new JButton("Submit");
        
        //Do something when clicked 'Submit' button
        btnSubmit.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	//if null input in repo or title fields, try again
            	if(getRepo().equals("") || getTitle().equals("")){
            		 JOptionPane.showMessageDialog(NewIssueDialog.this,
                             "Please try again and fill both 'Repository name' and 'Title' fields",
                             "Submit Issue Error",
                             JOptionPane.INFORMATION_MESSAGE);
            		 // reset repo's name and issue's title
                     tfRepository.setText("");
                     tfIssueTitle.setText("");
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
            	cancelled = true;
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnSubmit);
        bp.add(btnCancel);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    /**
     * 
     * @return The user's repository's name entered by the client 
     */
    public String getRepo() {
        return tfRepository.getText().trim();
    }
    
    /**
     * 
     * @return The new issue's title entered by the client
     */
    public String getTitle() {
        return new String(tfIssueTitle.getText().trim());
    }
    
    /**
     * 
     * @return The new issue's description entered by the client
     */
    public String getDescription() {
        return new String(tfDescription.getText().trim());
    }
 
    /**
     * 
     * @return True if repository and title inputs are not ""
     */
    public boolean isSucceeded() {
        return succeeded;
    }
    
    /**
     * 
     * @return True if user clicked the 'Cancel' button
     */
    public boolean isCancelled() {
        return cancelled;
    }
}