package bluej.parser;

import java.io.File;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import bluej.parser.entity.ClassLoaderResolver;
import bluej.parser.symtab.ClassInfo;
public class JavadocDetector {
	
    /**
     * Get a data or result file from our hidden stash..
     * NOTE: the stash of data files is in the ast/data directory.
     */
    private File getFile(String name)
    {
        URL url = getClass().getResource("/bluej/parser/ast/data/" + name);
        
        if (url == null || url.getFile().equals(""))
            return null;
        else
            return new File(url.getFile());
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }

    /**
     * Find a target method/class in the comments and return its index (or -1 if not found).
     */
    private int findTarget(Properties comments, String target)
    {
        for (int commentNum = 0; ; commentNum++) {
            String comment = comments.getProperty("comment" + commentNum + ".target");
            if (comment == null) {
                return -1;
            }
            if (comment.equals(target)) {
                return commentNum;
            }
        }
    }
    
    
    public void testCC4401() throws Exception{
    	System.out.println(hasJavadoc(getFile("GameBuilder.dat")));
    	//System.out.println(javadocReport(getFile("A.dat")));
    	//System.out.println(javadocReport(getFile("B.dat")));
    }
    public String javadocReport(File file) throws Exception{
    	ClassInfo info = InfoParser.parse(file);
    	return javadocReport(info);
    }
    public String javadocReport(String text) throws Exception{
    	ClassInfo info = InfoParser.parse(new StringReader(text), new ClassLoaderResolver(getClass().getClassLoader()), null);
    	return javadocReport(info);
    }
    public ArrayList<Boolean> hasJavadoc(File file) throws Exception{
    	ClassInfo info = InfoParser.parse(file);
    	return hasJavadoc(info);
    }
    public ArrayList<Boolean> hasJavadoc(String text) throws Exception{
    	ClassInfo info = InfoParser.parse(new StringReader(text), new ClassLoaderResolver(getClass().getClassLoader()), null);
    	return hasJavadoc(info);
    }
    
    public String javadocReport(ClassInfo info) throws Exception{
    	StringBuilder sb=new StringBuilder();
        Properties comments = info.getComments();
        for (int i=0;comments.get("comment"+i+".target")!=null;i++){
        	sb.append("**************\n");
        	if (comments.get("comment"+i+".text")!=null){
        		sb.append(comments.get("comment"+i+".target"));
        		sb.append(" has javadoc comment: \n");
        		sb.append(comments.get("comment"+i+".text")+"\n");
        	}
        	else{
        		sb.append(comments.get("comment"+i+".target"));
        		sb.append(" hasn't javadoc comment. \n");
        	}
        	sb.append("**************\n");
        }
        return sb.toString();
    }
    public ArrayList<Boolean> hasJavadoc(ClassInfo info) throws Exception{
    	ArrayList<Boolean> listofBoolean = new ArrayList<Boolean>();
    	
        Properties comments = info.getComments();
        for (int i=0;comments.get("comment"+i+".target")!=null;i++){
        	if (comments.get("comment"+i+".text")!=null){
        		listofBoolean.add(true);
        	}
        	else
        	listofBoolean.add(false);
        		
        	
        }
        return listofBoolean;
    } 
    

	
	
}
