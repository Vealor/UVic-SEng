/*
 * LLStack.java
 * Jakob Roberts
 * v00484900
 */


public class LLStack<T> implements Stack<T>{
	
	private Node<T> stack;
	private Node<T> top;
	private int count;
	
	public LLStack(){
		stack = null;
		top = null;
		count = 0;
	}
	
	
	public int size(){
		return count;
	}
	
	public boolean empty(){
		return count == 0;
	}

	public void push(T element){
		Node<T> p = top;
		Node<T> n = new Node<T>(element);
		if(p==null){
			stack = n;
		}else{
			p.next = n;
			n.prev = p;
		}
		top = n;
		count++;
	}
	
	public T pop() throws StackEmptyException{
		if(empty()){
			throw new StackEmptyException("Sorry the stack is empty!");
		}else{
			Node<T> p = stack;
			Node<T> q = top;
			Node<T> temp = new Node<T>();
			toString();
			if (p.next == null){
				temp.item = p.item;
				stack = null;
				top=null;
				count--;
				return temp.item;
			}else{
				temp.item = q.item;
				q = q.prev;
				q.next = null;
				top=q;
				count--;
				return temp.item;
			}
		}
	}
	
	public T peek() throws StackEmptyException{
		if(empty()){
			throw new StackEmptyException("Sorry the stack is empty!");
		}else{
			return top.item;
		}
	}
	
	public void makeEmpty(){
		stack = null;
		top=null;
		count = 0;
	}

	public String toString(){
		String list = "";
		Node<T> p = stack;
		for(int i=0;i<size();i++){
			list += p.item;
			if(p.next!=null){
				list += ",";
			}
			p=p.next;
		}
		list += "";
		return list;
		
	}
}