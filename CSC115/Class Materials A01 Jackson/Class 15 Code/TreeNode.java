		public class TreeNode<T> {
			//attributes
			private T data;
			private TreeNode left;
			private TreeNode right;

			//constructors
			public TreeNode() {
				this.left = null;
				this.right = null;
			}
			public TreeNode(T info) {
				this.data = info;
				this.left = null;
				this.right = null;
			}

			//assessors & mutators
			public T getData() {
				return this.data;
			}
			public void setData(T info) {
				this.data = info;
			}

			public TreeNode getLeft() {
				return this.left;
			}
			public void setLeft(TreeNode newLeft) {
				this.left = newLeft;
			}
			public TreeNode getRight() {
				return this.right;
			}
			public void setRight(TreeNode newRight) {
				this.right = newRight;
			}
		}



