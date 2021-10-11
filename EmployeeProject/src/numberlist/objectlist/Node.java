package numberlist.objectlist;

import java.io.Serializable;

/**
 * This class is responsible for holding the value of a node and the location of
 * the next node, including getters and setters.
 *
 * @author Ali Ibrahim
 * @version 1.0
 */
class Node implements Serializable{

    private Node nextNode;
    private Copiable value;

    /**
     * initializes the two fields of Node. initializes value to the given Object
     * in the parameter and initializes nextNode to null.
     *
     * @param obj the value to be stored in the Node
     */
    public Node(Copiable obj) {
	value = obj;
	nextNode = null;
    }

    /**
     * provides the memory address of the next node
     *
     * @return the address of the next node
     */
    public Node getNextNode() {
	return nextNode;
    }

    /**
     * allows the memory address of the next node to be set
     *
     * @param node the address of the new next Node to which nextNode will be
     * set to
     */
    public void setNextNode(Node node) {
	this.nextNode = node;
    }

    /**
     * provides the value stored in the Node
     *
     * @return the value of the Node
     */
    public Copiable getValue() {
	return value;
    }

    /**
     * allows the value of the node to be set.
     *
     * @param value the value of the node to which value will be set to
     */
    public void setValue(Copiable value) {
	this.value = value;
    }

}
