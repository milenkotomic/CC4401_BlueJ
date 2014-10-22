package bluej.github;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.OAuthService;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by milenkotomic on 20-10-14.
 */
public class IssueTest {

    public static void main(String[] args) throws IOException{

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
        /*
        cliente.setCredentials(username, contraseña);
        try {
            OAuthService oauth = new OAuthService(cliente);
            System.out.println(oauth.getAuthorizations());
        }
        catch (RequestException e){
            System.out.println(e.getStatus());
        }
        */



        GitHubConnection ghc = new GitHubConnection();
        ghc.createClient(username, contraseña);
        System.out.println(ghc.getRepositories());
        String title = "Issue Test: Titulo y cuerpo (?)";
        String repo = "TestRepository";
        String desc = "Cuerpo de Issue";
        ghc.submitIssue(title, desc, repo);


    }
}
