package videotalk.widget;

import android.support.annotation.IdRes;
import android.view.View;



public abstract class TreeViewBinder<VH extends android.support.v7.widget.RecyclerView.ViewHolder> implements LayoutItemType {
    public TreeViewBinder() {
    }

    public abstract VH provideViewHolder(View var1);

    public abstract void bindView(VH var1, int var2, TreeNode var3);

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ViewHolder(View rootView) {
            super(rootView);
        }

        protected <T extends View> T findViewById(@IdRes int id) {
            return this.itemView.findViewById(id);
        }
    }
}
