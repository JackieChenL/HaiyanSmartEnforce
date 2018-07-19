package smartenforce.aty.patrol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import smartenforce.bean.tcsf.NameValueIdBean;
import smartenforce.intf.OnItemClickListener;

import java.util.List;


public class PatrolTitleAdapter extends RecyclerView.Adapter<PatrolTitleAdapter.ViewHolder> {
    List<Object> list;
    Context mContext;
    LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private static final int TYPE_NORMAL = 1;

    public PatrolTitleAdapter(List<Object> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_patrol_title, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Object obj = list.get(position);
        if (obj instanceof NameValueIdBean.RoadBean) {
            holder.tev_title.setText(((NameValueIdBean.RoadBean) obj).Road);
        }
        holder.tev_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, obj);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_title;
        LinearLayout llt_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tev_title = (TextView) itemView.findViewById(R.id.tev_title);
            llt_item = (LinearLayout) itemView.findViewById(R.id.llt_item);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
