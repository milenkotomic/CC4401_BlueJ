package bluej.github;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;

/**
 * Created by milenkotomic on 07-10-14.
 */
public class GitHubConnection {

    private GitHubClient client;

    public GitHubClient createClient(String username, String password){
        client = new GitHubClient();
        client.setCredentials(username, password);
        return client;

    }

    public boolean credentialsEsblished(){
        return client.getUser() != null;
    }

}


