package videotalk.tree;

import com.kas.clientservice.haiyansmartenforce.R;

import videotalk.widget.LayoutItemType;


public class ParentNode implements LayoutItemType {
    public TreeBean treeBean;

    public ParentNode(TreeBean treeBean) {
        this.treeBean = treeBean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_user_list_dir;
    }
}
