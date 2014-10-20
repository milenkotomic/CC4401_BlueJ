package bluej.github;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.OAuthService;

/**
 * Created by milenkotomic on 07-10-14.
 */
public class GitHubConnection {

    private GitHubClient client;

    public int createClient(String username, String password){
        client = new GitHubClient();
        client.setCredentials(username, password);
        try {
            OAuthService oauth = new OAuthService(client);
            oauth.getAuthorizations();
            return 200;
        }
        catch (Exception e){
            RequestException ex = (RequestException) e;
            return ex.getStatus();
        }


    }

    public void createCommit(){
        Commit commit = new Commit();
        CommitService commitService = new CommitService(client);

    }

}


