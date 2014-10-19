package bluej.parser;

public interface CodeSmellsDetector {
	public int instanceVariables();
	public int methodQuantity();
	public int cohesionLevel();

}