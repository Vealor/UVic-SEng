/* iterative find */

public boolean find (int element){
	TreeNode p = root;
	for(;;){
		if(p==null)
			return false;
		if(element < p.element)
			p=p.element;
		else if(element >p.element)
			p=
