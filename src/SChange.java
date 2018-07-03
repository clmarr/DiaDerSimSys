import java.util.List; 

/**
 * @author Clayton Marr
 * Abstract parent class for all Shift classes, 
 * 		whose basic role is to intake phonological sequences and output what they will be 
 * 		after the diachronic rule ("Shift") has applied.
 * child classes are differentiated by how their targets are specified
 * it is unnecessary to differentiate them by how their destinations are specified because
 * 	(a) for shifts with phone-specified targets, it is most computationally efficient to make 
 * 			those that are constructed with feature-specified targets internally stored with phone
 * 			specified targets, as this is much more computationally efficient. 
 * 	(b) it is very unadvisable to construct instances with feature-specified targets
 * 			but phone-specified destinations, because this would require very frequent searches
 * 			of all phones in use-- very inefficient. 
 * 
 */

public abstract class SChange {

	protected ShiftContext priorContext, postContext; 
	protected boolean boundsMatter, priorSpecd, postSpecd; 
	protected int minPriorSize, minPostSize, minTargSize; 
	
	public SChange()
	{
		boundsMatter = false; minPriorSize = 0; minPostSize = 0;
		priorSpecd = false; postSpecd = false; 
	}
	public SChange(boolean bm)
	{
		boundsMatter = bm; minPriorSize = 0; minPostSize = 0; 
		priorSpecd = false; postSpecd = false; 

	}
	public SChange(ShiftContext prior, ShiftContext post)
	{
		priorContext = prior; postContext = post; boundsMatter = false; 
		minPriorSize = priorContext.getMinSize(); minPostSize = postContext.getMinSize(); 
		priorSpecd = true; postSpecd = true; 
	}
	public SChange(ShiftContext prior, ShiftContext post, boolean bm)
	{
		priorContext = prior; postContext = post; boundsMatter = bm;
		minPriorSize = priorContext.getMinSize(); minPostSize = postContext.getMinSize(); 
		priorSpecd = true; postSpecd = true; 
	}
	
	public void setPriorContext(ShiftContext p)
	{	priorContext = p; minPriorSize = priorContext.getMinSize(); priorSpecd = true; }
	
	public void setPostContext(ShiftContext p)
	{	postContext = p; minPostSize = postContext.getMinSize(); postSpecd = true; }
	
	public abstract List<SequentialPhonic> realize(List<SequentialPhonic> phonologicalSeq);
	
	public String toString()
	{
		if (!priorSpecd && !postSpecd)	return "";
		
		String output = "/ "; 
		if(priorSpecd)	output += priorContext.toString() + " "; 
		output = output.trim() + " __ "; 
		if(postSpecd)	output += postContext.toString(); 
		return output; 
	}

	protected boolean priorMatch(List<SequentialPhonic> input, int frstTargInd)
	{
		if(minPriorSize == 0)	return true; 
		else 	return priorContext.isPriorMatch(input, frstTargInd); 
	}
	protected boolean posteriorMatch(List<SequentialPhonic> input, int lastTargInd)
	{
		if(minPostSize == 0)	return true;
		else	return postContext.isPosteriorMatch(input, lastTargInd); 
	}
	
}
