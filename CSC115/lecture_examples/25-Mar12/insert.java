//
	public boolean insert (int element){
		Treenode n - new TreeNode(element);
		if (root==null){
			root = n;
			return true;
		}
		TreeNode p = root;
		for(;;){
			if(element == p.element)
				return false;
			if(element < p.pelement){
				if (p.left == null){
					p.left  n;
					return true;
				}else{
					p=p.left;
				}
			}else{
				if(p.right ==null){
					p.right = n;
					return true;
				}else{
					p=p.right;
				}
			}
		}
	}
