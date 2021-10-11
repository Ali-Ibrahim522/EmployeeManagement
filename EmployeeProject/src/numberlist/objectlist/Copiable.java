package numberlist.objectlist;

/**
 * this interface is responsible for holding the method copy and allowing
 * classes that implement it to have instances of their class copied.
 * 
 * @author Ali Ibrahim
 * @version 1.0
 */
public interface Copiable {
    /**
     * copies an objects values into a new version of said object
     *
     * @return the new copied version of the object
     */
    public Copiable copy();
}
