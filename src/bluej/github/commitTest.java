package bluej.github;

import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    }


}
