package bluej.parser;

import java.util.ArrayList;

import bluej.parser.nodes.MethodNode;

public interface CodeSmellsDetector {
	public int instanceVariables(); //clases largas con muchas variables de instancia
	public int methodQuantity();	//clases largas con muchos metodos
	public int cohesionLevel();		//clases largas con mucha cohesion.
	public boolean hasDeadCode();	//dead code
	public ArrayList<MethodNode>manyParameters(int threshold);
	public ArrayList<MethodNode>complexMethods(int threshold);
	public int switchStmts();

}