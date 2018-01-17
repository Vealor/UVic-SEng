/*
 * Node.java
 * Jakob Roberts
 * v00484900
 */
 
 public class Node<T>{
	public T item;
	public Node<T> next;
	public Node<T> prev;

	public Node(){
		next=null;
		prev=null;
	}

	public Node(T n){
		item=n;
		next=null;
		prev=null;
	}

	public Node(T n,Node<T> nextNode, Node<T> prevNode ){
		item=n;
		next=nextNode;
		prev=prevNode;
	}

	public T getItem(){
		return item;
	}

	public void setItem(T newItem){
		item=newItem;
	}

	public Node<T> getNext(){
		return next;
	}
	
	public Node<T> getPrev(){
		return prev;
	}

	public void setNext(Node<T> nextNode){
		next=nextNode;
	}
	
	public void setPrev(Node<T> prevNode){
		prev=prevNode;
	}
}