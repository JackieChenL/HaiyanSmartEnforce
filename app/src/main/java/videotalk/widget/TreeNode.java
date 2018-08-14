package videotalk.widget;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;




public class TreeNode<T extends LayoutItemType> implements Cloneable {
    private T content;
    private TreeNode parent;
    private List<TreeNode> childList;
    private boolean isExpand;
    private int height = -1;

    public TreeNode(@NonNull T content) {
        this.content = content;
    }

    public int getHeight() {
        if(this.isRoot()) {
            this.height = 0;
        } else if(this.height == -1) {
            this.height = this.parent.getHeight() + 1;
        }

        return this.height;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return this.childList == null || this.childList.isEmpty();
    }

    public void setContent(T content) {
        this.content = content;
    }

    public T getContent() {
        return this.content;
    }

    public List<TreeNode> getChildList() {
        return this.childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }

    public TreeNode addChild(TreeNode node) {
        if(this.childList == null) {
            this.childList = new ArrayList();
        }

        this.childList.add(node);
        node.parent = this;
        return this;
    }

    public boolean toggle() {
        this.isExpand = !this.isExpand;
        return this.isExpand;
    }

    public void collapse() {
        if(!this.isExpand) {
            this.isExpand = false;
        }

    }

    public void expand() {
        if(this.isExpand) {
            this.isExpand = true;
        }

    }

    public boolean isExpand() {
        return this.isExpand;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public String toString() {
        return "TreeNode{content=" + this.content + ", parent=" + (this.parent == null?"null":this.parent.getContent().toString()) + ", childList=" + (this.childList == null?"null":this.childList.toString()) + ", isExpand=" + this.isExpand + '}';
    }

    protected TreeNode<T> clone() throws CloneNotSupportedException {
        TreeNode clone = new TreeNode(this.content);
        clone.isExpand = this.isExpand;
        return clone;
    }
}
