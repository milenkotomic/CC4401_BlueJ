package bluej.parser;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import bluej.parser.InfoParser;
import bluej.parser.entity.ClassLoaderResolver;
import bluej.parser.entity.EntityResolver;

public class MyParserTestOwnFile {

	BufferedReader reader;
	EntityResolver resolver;
	InfoParser infop;
	
	@Before
	public void setUp() throws FileNotFoundException{
		reader = (new BufferedReader(new InputStreamReader(new FileInputStream(new File("./test/src/bluej/parser/TestFile.java")))));
		resolver = new ClassLoaderResolver(InfoParser.class.getClassLoader());
		infop = new InfoParser(reader, resolver);
		infop.parseCU();
	}
	
	
	@Test
	public void methodsQuantityAnalizer(){
		System.out.println(infop.getMethods().size());
		assertTrue(infop.getMethods().size() == 8);
	}


	
	@Test
	public void parametersQuantityAnalizer(){
		assertTrue(infop.getMethods().get(0).getParamNames().size() == 0);
		assertTrue(infop.getMethods().get(1).getParamNames().size() == 1);
		assertTrue(infop.getMethods().get(2).getParamNames().size() == 0);
		assertTrue(infop.getMethods().get(3).getParamNames().size() == 1);
		assertTrue(infop.getMethods().get(4).getParamNames().size() == 1);
		assertTrue(infop.getMethods().get(infop.getMethods().size()-1).getParamNames().size() == 0);
		assertTrue(infop.getMethods().get(infop.getMethods().size()-2).getParamNames().size() == 3);
		assertTrue(infop.getMethods().get(infop.getMethods().size()-3).getParamNames().size() == 2);
		
		
	}
	
	@Test
	public void methodsNameAnalizer(){
		assertTrue(infop.getMethods().get(0).getName().equals("method1"));
		assertTrue(infop.getMethods().get(1).getName().equals("method2"));
		assertTrue(infop.getMethods().get(2).getName().equals("method1"));
		assertTrue(infop.getMethods().get(3).getName().equals("method2"));
		assertTrue(infop.getMethods().get(4).getName().equals("aParameter"));
		assertTrue(infop.getMethods().get(5).getName().equals("twoParameters"));
		assertTrue(infop.getMethods().get(6).getName().equals("threeParameters"));
		assertTrue(infop.getMethods().get(7).getName().equals("noParams"));
		
		
	}
	

	
}

