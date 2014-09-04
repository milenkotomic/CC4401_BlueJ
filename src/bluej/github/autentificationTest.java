package bluej.github;

import java.io.IOException;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

public class autentificationTest {

	public static void main(String[] args) throws IOException {
		GitHubClient cliente = new GitHubClient();
		cliente.setCredentials("milenkotomic", "Franteamo5393");
		System.out.println(cliente.getUser());
		
		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories(cliente.getUser()))
		  System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
		
		Issue is = new Issue();
		is.setTitle("Issue prueba API");
		IssueService i = new IssueService(cliente);
		is = i.createIssue(cliente.getUser(), "CC4401_BlueJ", is);
		
		

	}

}
