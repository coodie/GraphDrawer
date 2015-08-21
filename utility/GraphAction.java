package utility;

//TODO
//this class is responsible for performing actions on graph
//like removing selected set of edges

//this will be used in future when i'll implement
//undo option
public interface GraphAction
{	
	public void perform();
	public void performReversed();
}
