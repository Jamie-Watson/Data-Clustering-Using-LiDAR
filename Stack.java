/**
 * 
 * @author Jamie Watson
 * 
 * 		This class implements the Stack data type
 *
 */
public class Stack <E>{
	
	/**
	 * Instance variables
	 * -size: stores the size of the stack
	 * -top: stores the top of the stack 
	 */
	int size;
	Node <E>top;
	
	/**
	 * 
	 * Node class implemented by Stack
	 *
	 * @param <E> the type of node element stored
	 */
	@SuppressWarnings("hiding")
	private class Node <E>{
		
		/**
		 * Instance variables
		 * -elem: stores the data in the node
		 * -next: stores the next node
		 */
		private E elem;
		private Node<E> next;
		
		/**
		 * Constructor for the Node class
		 * @param elem is the data stored in the node
		 */
		public Node(E elem) {
			this.elem=elem;
			this.next=null;
		}
	}
	
	/**
	 * Constructor for the Stack class
	 */
	public Stack (){
		size=0;
		top=null;
	}
	
	/**
	 * 
	 * Method that returns whether the stack is empty or not
	 * @return boolean empty value
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	/**
	 * Method that returns the top of the stack
	 * @return The top element
	 */
	E top() {
		return top.elem;
	}
	
	/**
	 * Method that adds another node onto the stack 
	 * @param elem is the data value of the new node to be added
	 */
	void push(E elem) {
		Node <E> newNode= new Node<E>(elem);
		newNode.next=this.top;
		top=newNode;
		size++;
		
	}
	
	/**
	 * Method that removes one element from the stack
	 * @return The element that was removed
	 */
	E pop() {
		Node<E> poppedNode=top;
		top=top.next;
		size--;
		return poppedNode.elem;
	}
	
}
