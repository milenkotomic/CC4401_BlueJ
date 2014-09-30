package bluej.parser;

/*/Users/Fabian/Developer/Uchile/Ingenieria de Software/Curso/BlueJ/CC4401_BlueJ/test/src/bluej/parser/ParserTest.java*/
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bluej.debugger.gentype.Reflective;
import bluej.parser.entity.EntityResolver;
import bluej.parser.entity.JavaEntity;
import bluej.parser.entity.PackageEntity;
import bluej.parser.entity.PackageOrClass;
import bluej.parser.entity.ParsedReflective;
import bluej.parser.entity.TypeEntity;
import bluej.parser.nodes.ParsedCUNode;
import bluej.parser.nodes.ParsedTypeNode;
import bluej.utility.JavaNames;

public class TestFile {
	private int privateInt;
	public String publicString;
	protected int asignedInt = 2;
	public final int finalInt = 0;
	
	
	interface HelloWorld {
        public void method1();
        public void method2(String someone);
        
    }
  
	
	TestFile(){
		System.out.println("Constructor1");
		 HelloWorld testAnonymous = new HelloWorld() {
	            public void method1() {
	                System.out.println("test");
	            }
	            public void method2(String a) {
	                System.out.println("asd " + a);
	            }
	            
	        };
	}
	
	TestFile (int a){
		int ab = 0;
		System.out.println("Constructor2");
	}
	
	public void aParameter (String param1){
		int xd = 123;
		System.out.println("Test1");
		
	}
	
	public void twoParameters (String param1, int param2){
		System.out.println("Test22");
		System.out.println("Test222");
		
	}

	public int threeParameters (String param1, int param2, interClass param3){
		System.out.println("Test3");
		System.out.println("Test3");
		System.out.println("Test3");
		return 0;
	}
	class interClass{
		public void noParams(){}
		
	}
	
	
	
	
}
