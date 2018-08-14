package videotalk.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




public class TreeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String KEY_IS_EXPAND = "IS_EXPAND";
    private final List<? extends TreeViewBinder> viewBinders;
    private List<TreeNode> displayNodes;
    private int padding;
    private TreeViewAdapter.OnTreeNodeListener onTreeNodeListener;
    private boolean toCollapseChild;

    public TreeViewAdapter(List<? extends TreeViewBinder> viewBinders) {
        this((List)null, viewBinders);
    }

    public TreeViewAdapter(List<TreeNode> nodes, List<? extends TreeViewBinder> viewBinders) {
        this.padding = 30;
        this.displayNodes = new ArrayList();
        if(nodes != null) {
            this.findDisplayNodes(nodes);
        }

        this.viewBinders = viewBinders;
    }

    private void findDisplayNodes(List<TreeNode> nodes) {
        Iterator var2 = nodes.iterator();

        while(var2.hasNext()) {
            TreeNode node = (TreeNode)var2.next();
            this.displayNodes.add(node);
            if(!node.isLeaf() && node.isExpand()) {
                this.findDisplayNodes(node.getChildList());
            }
        }

    }

    public int getItemViewType(int position) {
        return ((TreeNode)this.displayNodes.get(position)).getContent().getLayoutId();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if(this.viewBinders.size() == 1) {
            return ((TreeViewBinder)this.viewBinders.get(0)).provideViewHolder(v);
        } else {
            Iterator var4 = this.viewBinders.iterator();

            TreeViewBinder viewBinder;
            do {
                if(!var4.hasNext()) {
                    return ((TreeViewBinder)this.viewBinders.get(0)).provideViewHolder(v);
                }

                viewBinder = (TreeViewBinder)var4.next();
            } while(viewBinder.getLayoutId() != viewType);

            return viewBinder.provideViewHolder(v);
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if(payloads != null && !payloads.isEmpty()) {
            Bundle b = (Bundle)payloads.get(0);
            Iterator var5 = b.keySet().iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                byte var8 = -1;
                switch(key.hashCode()) {
                    case 296813391:
                        if(key.equals("IS_EXPAND")) {
                            var8 = 0;
                        }
                    default:
                        switch(var8) {
                            case 0:
                                if(this.onTreeNodeListener != null) {
                                    this.onTreeNodeListener.onToggle(b.getBoolean(key), holder);
                                }
                        }
                }
            }
        }

        super.onBindViewHolder(holder, position, payloads);
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setPadding(((TreeNode)this.displayNodes.get(position)).getHeight() * this.padding, 3, 3, 3);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TreeNode selectedNode = (TreeNode) TreeViewAdapter.this.displayNodes.get(holder.getLayoutPosition());

                try {
                    long isExpand = ((Long)holder.itemView.getTag()).longValue();
                    if(System.currentTimeMillis() - isExpand < 500L) {
                        return;
                    }
                } catch (Exception var5) {
                    holder.itemView.setTag(Long.valueOf(System.currentTimeMillis()));
                }

                holder.itemView.setTag(Long.valueOf(System.currentTimeMillis()));
                if(TreeViewAdapter.this.onTreeNodeListener == null || !TreeViewAdapter.this.onTreeNodeListener.onClick(selectedNode, holder)) {
                    if(!selectedNode.isLeaf()) {
                        boolean isExpand1 = selectedNode.isExpand();
                        int positionStart = TreeViewAdapter.this.displayNodes.indexOf(selectedNode) + 1;
                        if(!isExpand1) {
                            TreeViewAdapter.this.notifyItemRangeInserted(positionStart, TreeViewAdapter.this.addChildNodes(selectedNode, positionStart));
                        } else {
                            TreeViewAdapter.this.notifyItemRangeRemoved(positionStart, TreeViewAdapter.this.removeChildNodes(selectedNode, true));
                        }

                    }
                }
            }
        });
        Iterator var3 = this.viewBinders.iterator();

        while(var3.hasNext()) {
            TreeViewBinder viewBinder = (TreeViewBinder)var3.next();
            if(viewBinder.getLayoutId() == ((TreeNode)this.displayNodes.get(position)).getContent().getLayoutId()) {
                viewBinder.bindView(holder, position, (TreeNode)this.displayNodes.get(position));
            }
        }

    }

    private int addChildNodes(TreeNode pNode, int startIndex) {
        List childList = pNode.getChildList();
        int addChildCount = 0;
        Iterator var5 = childList.iterator();

        while(var5.hasNext()) {
            TreeNode treeNode = (TreeNode)var5.next();
            this.displayNodes.add(startIndex + addChildCount++, treeNode);
            if(treeNode.isExpand()) {
                addChildCount += this.addChildNodes(treeNode, startIndex + addChildCount);
            }
        }

        if(!pNode.isExpand()) {
            pNode.toggle();
        }

        return addChildCount;
    }

    private int removeChildNodes(TreeNode pNode) {
        return this.removeChildNodes(pNode, true);
    }

    private int removeChildNodes(TreeNode pNode, boolean shouldToggle) {
        if(pNode.isLeaf()) {
            return 0;
        } else {
            List childList = pNode.getChildList();
            int removeChildCount = childList.size();
            this.displayNodes.removeAll(childList);
            Iterator var5 = childList.iterator();

            while(var5.hasNext()) {
                TreeNode child = (TreeNode)var5.next();
                if(child.isExpand()) {
                    if(this.toCollapseChild) {
                        child.toggle();
                    }

                    removeChildCount += this.removeChildNodes(child, false);
                }
            }

            if(shouldToggle) {
                pNode.toggle();
            }

            return removeChildCount;
        }
    }

    public int getItemCount() {
        return this.displayNodes == null?0:this.displayNodes.size();
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void ifCollapseChildWhileCollapseParent(boolean toCollapseChild) {
        this.toCollapseChild = toCollapseChild;
    }

    public void setOnTreeNodeListener(TreeViewAdapter.OnTreeNodeListener onTreeNodeListener) {
        this.onTreeNodeListener = onTreeNodeListener;
    }

    public void refresh(List<TreeNode> treeNodes) {
        this.displayNodes.clear();
        this.findDisplayNodes(treeNodes);
        this.notifyDataSetChanged();
    }

    public Iterator<TreeNode> getDisplayNodesIterator() {
        return this.displayNodes.iterator();
    }

    private void notifyDiff(final List<TreeNode> temp) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            public int getOldListSize() {
                return temp.size();
            }

            public int getNewListSize() {
                return TreeViewAdapter.this.displayNodes.size();
            }

            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter.this.areItemsTheSame((TreeNode)temp.get(oldItemPosition), (TreeNode) TreeViewAdapter.this.displayNodes.get(newItemPosition));
            }

            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter.this.areContentsTheSame((TreeNode)temp.get(oldItemPosition), (TreeNode) TreeViewAdapter.this.displayNodes.get(newItemPosition));
            }

            @Nullable
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter.this.getChangePayload((TreeNode)temp.get(oldItemPosition), (TreeNode) TreeViewAdapter.this.displayNodes.get(newItemPosition));
            }
        });
        diffResult.dispatchUpdatesTo(this);
    }

    private Object getChangePayload(TreeNode oldNode, TreeNode newNode) {
        Bundle diffBundle = new Bundle();
        if(newNode.isExpand() != oldNode.isExpand()) {
            diffBundle.putBoolean("IS_EXPAND", newNode.isExpand());
        }

        return diffBundle.size() == 0?null:diffBundle;
    }

    private boolean areContentsTheSame(TreeNode oldNode, TreeNode newNode) {
        return oldNode.getContent() != null && oldNode.getContent().equals(newNode.getContent()) && oldNode.isExpand() == newNode.isExpand();
    }

    private boolean areItemsTheSame(TreeNode oldNode, TreeNode newNode) {
        return oldNode.getContent() != null && oldNode.getContent().equals(newNode.getContent());
    }

    public void collapseAll() {
        List temp = this.backupDisplayNodes();
        ArrayList roots = new ArrayList();
        Iterator var3 = this.displayNodes.iterator();

        TreeNode root;
        while(var3.hasNext()) {
            root = (TreeNode)var3.next();
            if(root.isRoot()) {
                roots.add(root);
            }
        }

        var3 = roots.iterator();

        while(var3.hasNext()) {
            root = (TreeNode)var3.next();
            if(root.isExpand()) {
                this.removeChildNodes(root);
            }
        }

        this.notifyDiff(temp);
    }

    @NonNull
    private List<TreeNode> backupDisplayNodes() {
        ArrayList temp = new ArrayList();
        Iterator var2 = this.displayNodes.iterator();

        while(var2.hasNext()) {
            TreeNode displayNode = (TreeNode)var2.next();

            try {
                temp.add(displayNode.clone());
            } catch (CloneNotSupportedException var5) {
                temp.add(displayNode);
            }
        }

        return temp;
    }

    public void collapseNode(TreeNode pNode) {
        List temp = this.backupDisplayNodes();
        this.removeChildNodes(pNode);
        this.notifyDiff(temp);
    }

    public void collapseBrotherNode(TreeNode pNode) {
        List temp = this.backupDisplayNodes();
        if(pNode.isRoot()) {
            ArrayList parent = new ArrayList();
            Iterator childList = this.displayNodes.iterator();

            TreeNode root;
            while(childList.hasNext()) {
                root = (TreeNode)childList.next();
                if(root.isRoot()) {
                    parent.add(root);
                }
            }

            childList = parent.iterator();

            while(childList.hasNext()) {
                root = (TreeNode)childList.next();
                if(root.isExpand() && !root.equals(pNode)) {
                    this.removeChildNodes(root);
                }
            }
        } else {
            TreeNode parent1 = pNode.getParent();
            if(parent1 == null) {
                return;
            }

            List childList1 = parent1.getChildList();
            Iterator root1 = childList1.iterator();

            while(root1.hasNext()) {
                TreeNode node = (TreeNode)root1.next();
                if(!node.equals(pNode) && node.isExpand()) {
                    this.removeChildNodes(node);
                }
            }
        }

        this.notifyDiff(temp);
    }

    public interface OnTreeNodeListener {
        boolean onClick(TreeNode var1, RecyclerView.ViewHolder var2);

        void onToggle(boolean var1, RecyclerView.ViewHolder var2);
    }
}
