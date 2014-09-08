package bluej.github;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.sun.codemodel.internal.JLabel;

public class autentificationTest {

	public static void main(String[] args) throws IOException {
		GitHubClient cliente = new GitHubClient();
		//char[] username = null;
		char[] password = null;
		
		String username = JOptionPane.showInputDialog("Ingrese usuario");
		
		JPanel panel = new JPanel();		
		JPasswordField pass = new JPasswordField(10);		
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Ingrese password",
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[0]);
		if(option == 0) // pressing OK button
		{
		    password = pass.getPassword();
		    
		}
		String contraseña = String.valueOf(password);
		cliente.setCredentials("milenkotomic", contraseña);
		System.out.println(cliente.getUser());
		
		RepositoryService service = new RepositoryService(cliente);
		for (Repository repo : service.getRepositories())
		  System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());		
		

	}

}
