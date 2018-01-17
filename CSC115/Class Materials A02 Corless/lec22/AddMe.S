Compiled from "AddMe.java"
public class AddMe {
  public AddMe();
    Code:
       0: aload_0       
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return        

  public static void main(java.lang.String[]);
    Code:
       0: iconst_4			// pushes 4 on the stack
       1: istore_1      		// pops and stores in local 1
       2: bipush        10		// pushes 10 on the stack
       4: istore_2      		// pops and stores in local 2
       5: iload_1       		// pushes value of local 1 on stack
       6: iload_2       		// pushes value of local 2 on stack
       7: iadd          		// adds them together
       8: istore_3      		// pops and stores the value in local 3
       9: getstatic     #2              
      12: iload_3       
      13: invokevirtual #3              
      16: return        
}
