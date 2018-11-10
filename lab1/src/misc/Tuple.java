package misc;

/**
 * tuple class.
 * @author Robin
 *
 * @param <X> - First element.
 * @param <Y> - second element.
 */
public class Tuple<X, Y> { 
	  public final X x; 
	  public final Y y; 
	  
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	}
