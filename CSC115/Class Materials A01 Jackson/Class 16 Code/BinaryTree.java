		public class BinaryTree<T> implements BinaryTreeADT<T>{
			private BinaryNode<T> root;

			public BinaryTree() {
				this.root = null;
			}
			public BinaryTree(T rootItem, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
				BinaryNode<T> rootNode =
						new BinaryNode(rootItem, getRootNode(leftTree), getRootNode(rightTree));
				this.root = rootNode;
			}

			public BinaryNode<T> getRootNode(BinaryTree<T> tree) {
				return tree.root;
			}

			// Determine the output and the Big-O Running time
			public String traverse() {
				return traverse(this.root);
			}

			public String traverse(BinaryNode<T> top) {
				String result = "";
				return result += top.getItem() +
							traverse(top.getLeftChild()) +
							traverse(top.getRightChild());

			}

			public void setRootItem(T newItem){}

			public void attachLeft(BinaryNode<T> newItem) throws TreeException{
				//we are attaching this new tree to where?
				// lets assume the first left place with no left child, okay?

				BinaryNode<T> temp = this.root;

				while (temp.getLeftChild() != null) {
					temp = temp.getLeftChild();
				}
				// now temp points at the leftmost leftchild-less node


				temp.setLeftChild(newItem);

			}

			public void attachRight(BinaryNode<T> newItem) throws TreeException{}

			public void attachLeftSubtree(BinaryTree<T> leftTree) throws TreeException{}

			public void attachRightSubtree(BinaryTree<T> rightTree) throws TreeException{}

			public void detachLeftSubtree(BinaryNode<T> fromHere) throws TreeException{}

			public void detachRightSubtree(BinaryNode<T> fromHere) throws TreeException{}
			}