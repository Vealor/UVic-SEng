/* VertexHeapTester.java
   CSC 226 - Fall 2014
   
   Template for a heap of vertex entries, ordered
   by an associated weight value.
   
   Note: Code is not well documented. Use at your own risk.

   B. Bird - 10/27/2014
*/


class VertexHeapElement{
	public static final int INVALID_WEIGHT = Integer.MAX_VALUE;
	public int v;
	public int weight;
	public VertexHeapElement(int v){
		this.v = v;
		this.weight = INVALID_WEIGHT;
	}	
}
class VertexHeap{
	//Array based heap using 1-based indexing.
	public VertexHeapElement[] heap;
	
	//indices[v] contains the index i such that heap[i].v == v
	public int[] indices;
	
	//Index of last valid element in heap (also number of elements since indexing is 1-based)
	private int heapEnd; 
	
	//Initialize a vertex heap over n vertices, with
	//each vertex's weight initialized to infinity.
	public VertexHeap(int n){
		heap = new VertexHeapElement[n+1];
		indices = new int[n+1];
		heapEnd = n;
		for (int i = 0; i < n; i++){
			heap[i+1] = new VertexHeapElement(i);
			indices[i] = i+1;
		}
	}
	
	//Return the size of the heap
	public int size(){
		return heapEnd;
	}
	
	//Print the contents of the heap (on one line)
	public void printContents(String title){
		if (!title.equals(""))
			System.out.printf("%s: ",title);
		for (int i = 1; i <= heapEnd; i++){
			if (heap[i].weight != VertexHeapElement.INVALID_WEIGHT)
				System.out.printf("%d (%d), ",heap[i].v, heap[i].weight);
			else
				System.out.printf("%d (inf), ",heap[i].v);
		}
		System.out.printf("\n");
	}
	
	//Remove and return the minimum element.
	public VertexHeapElement removeMin(){
		VertexHeapElement removeElement = heap[1];
		swapElements(1,heapEnd);
		heapEnd--;
		bubbleDown(1);
		
		return removeElement;
	}
	
	public void swapElements(int i, int j){
		VertexHeapElement temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
		indices[heap[i].v] = i;
		indices[heap[j].v] = j;
		return;
	}
	
	//Return the current weight of a vertex.
	public int getWeight(int vertex){
		int heap_index = indices[vertex];
		VertexHeapElement e = heap[heap_index];
		return e.weight;
	}
	
	//Set the weight of vertex adjust_vertex to new_weight.
	public void adjust(int adjust_vertex, int new_weight){

		int adjust_index = indices[adjust_vertex];
		VertexHeapElement adjustElement = heap[adjust_index];
		if (new_weight > adjustElement.weight)
			throw new Error(); //This shouldn't happen in Dijkstra's algorithm
		adjustElement.weight = new_weight;
		//Bubble up adjustElement
		bubbleUp(adjust_index);
		return;
	}
	
	public void bubbleUp(int i){
		if(i/2 != 0){
			if(heap[i/2].weight > heap[i].weight){
				swapElements(i/2,i);
				bubbleUp(i/2);
			}
		}
		return;
	}
	
	public void bubbleDown(int i){
		if(2*i > heapEnd){
			return; //no children
		}
		int leftIndex = 2*i;
		int leftWeight = heap[leftIndex].weight;
		
		int rightIndex = 2*i+1;
		int rightWeight = VertexHeapElement.INVALID_WEIGHT;
		if(rightIndex<= heapEnd)
			rightWeight = heap[rightIndex].weight;
		
		int minChildIndex = leftIndex;
		if(leftWeight > rightWeight){
			minChildIndex = rightIndex;
		}
		
		if(heap[i].weight <= minChildIndex){
			return;
		}
		swapElements(i,minChildIndex);
		bubbleDown(minChildIndex);
	}
}


public class VertexHeapTester{
	public static void main(String[] args){
		//Create a vertex heap of 10 vertices.
		VertexHeap H = new VertexHeap(10);
		H.printContents("New heap");
		
		VertexHeapElement e;
		
		//Set the weight of vertex 0 to 0
		H.adjust(0,0);
		H.printContents("Set weight(0) = 0");
		//Set the weight of vertex 6 to 10
		H.adjust(6,10);
		H.printContents("Set weight(6) = 10");
		
		//Remove minimum (should be 0)
		e = H.removeMin();
		if (e == null){
			System.out.printf("removeMin failed (result is null)\n");
		}else{
			System.out.printf("removeMin returned vertex %d (weight %d)\n",e.v, e.weight);
		}
		H.printContents("After removeMin");
		//Set the weight of vertex 1 to 11
		H.adjust(1,11);
		H.printContents("Set weight(1) = 11");
		//Set the weight of vertex 2 to 16
		H.adjust(2,16);
		H.printContents("Set weight(2) = 16");
		
		//Remove minimum (should be 6)
		e = H.removeMin();
		if (e == null){
			System.out.printf("removeMin failed (result is null)\n");
		}else{
			System.out.printf("removeMin returned vertex %d (weight %d)\n",e.v, e.weight);
		}
		
		//Set the weight of vertex 3 to 3
		H.adjust(3,3);
		H.printContents("Set weight(3) = 3");
		//Set the weight of vertex 2 to 1
		H.adjust(2,1);
		H.printContents("Set weight(2) = 1");
		
		//Print the weights of vertices 1, 2, 3 (should be 11, 1, 3)
		System.out.printf("weight(1) = %d, weight(2) = %d, weight(3) = %d\n",H.getWeight(1), H.getWeight(2), H.getWeight(3));
		
		//Remove minimum (should be 2)
		e = H.removeMin();
		if (e == null){
			System.out.printf("removeMin failed (result is null)\n");
		}else{
			System.out.printf("removeMin returned vertex %d (weight %d)\n",e.v, e.weight);
		}
		
	}
}