package bluej.github;


import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.OAuthService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List <String> getRepositories() throws IOException{
        RepositoryService service = new RepositoryService(client);
        ArrayList <String> repositories = new ArrayList<String>();
        for (Repository repo : service.getRepositories())
            repositories.add(repo.getName());
        return repositories;
    }


    public void createCommit(){
        Commit commit = new Commit();
        CommitService commitService = new CommitService(client);

    }

    public void submittIssue(String title, String repository) throws IOException{
        Issue is = new Issue();
        is.setTitle(title);
        IssueService i = new IssueService(client);
        Issue createdIssue = i.createIssue(client.getUser(), repository, is);
        System.out.println(createdIssue);
    }

    public void submittIssue(String title, String description, String repository) throws IOException{
        Issue is = new Issue();
        is.setTitle(title);
        is.setBody(description);
        IssueService i = new IssueService(client);
        Issue createdIssue = i.createIssue(client.getUser(), repository, is);
        System.out.println(createdIssue);
    }

}


