int height(){
	int lh = 0;
	int rh = 0;
	if(left!=null)
		lh = left.height();
	if (right!=null)
		rh = right.height();
	if(lh>rh)
		return 1+lh;
	else
		return 1+rh;
}
