package bluej.github;

import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.DataService;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_COMMITS;
/**
 * Created by milenkotomic on 19-10-14.
 */
public class commitTest {

    public static void main(String[] args) throws IOException {

        GitHubClient client = new GitHubClient();

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
        client.setCredentials(username, contraseña);

        /* TO-DO: utilizar DataService, metodo create commit */

        Commit commit = new Commit();
        DataService service = new DataService(client);
        RepositoryId repo = new RepositoryId("milenkotomic", "CC4401_BlueJ");
        CommitUser user = new CommitUser();


        user.setEmail("miletomc@gmail.com");
        user.setName("milenkotomic");
        Calendar now = Calendar.getInstance();
        user.setDate(now.getTime());
        //user.setDate(new Date());


        commit.setParents(Collections.singletonList(new Commit().setSha("abcd")));
        commit.setTree(new Tree().setSha("abcd"));
        commit.setAuthor(user);
        commit.setCommitter(user);
        commit.setMessage("Pruba de commit");


        System.out.println(commit.getTree());
        try {
            service.createCommit(repo, commit);
        }
        catch (Exception e){
            RequestException ex = (RequestException) e;
            System.out.println(ex.getMessage());
        }
    }
}
