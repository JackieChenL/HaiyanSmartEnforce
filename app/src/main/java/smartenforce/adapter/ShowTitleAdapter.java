package smartenforce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.bean.AddEnforceBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnforceGoodsBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.bean.FirstLevelBean;
import smartenforce.bean.NameIdValueBean;
import smartenforce.bean.TemplateModelBean;
import smartenforce.bean.ThirdLevelBean;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;


public class ShowTitleAdapter extends RecyclerView.Adapter<ShowTitleAdapter.ViewHolder> {

    private ItemListener listener;
    private Context context;
    private List<Object> list;
    private LayoutInflater inflater;

    public ShowTitleAdapter(List<Object> list, Context ctx) {
        this.list = list;
        this.context = ctx;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ShowTitleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_item_title_blue, parent, false);
        return new ShowTitleAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ShowTitleAdapter.ViewHolder holder, final int position) {
        Object obj = list.get(position);
        if (obj instanceof AddEnforceBean.WithholdGoodsValueBean) {
            AddEnforceBean.WithholdGoodsValueBean bean = (AddEnforceBean.WithholdGoodsValueBean) obj;
            holder.tev_title.setText("物品名称:" + bean.NameWit);
        } else if (obj instanceof EnforceGoodsBean) {
            EnforceGoodsBean bean = (EnforceGoodsBean) obj;
            holder.tev_title.setText("物品名称:" + bean.NameWit);
        }else if(obj instanceof NameIdValueBean){
            NameIdValueBean bean = (NameIdValueBean) obj;
            holder.tev_title.setText(bean.name);
        }else if(obj instanceof TemplateModelBean){
            TemplateModelBean bean = (TemplateModelBean) obj;
            holder.tev_title.setText(bean.NameRef);
        }else if (obj instanceof FirstLevelBean){
            FirstLevelBean firstLevelBean=(FirstLevelBean)obj;
            holder.tev_title.setText(firstLevelBean.NameFiL);
        }else if (obj instanceof ThirdLevelBean){
            ThirdLevelBean thirdLevelBean=(ThirdLevelBean)obj;
            holder.tev_title.setText(thirdLevelBean.NameThL);

        }else if(obj instanceof CitizenBean){
            CitizenBean citizenBean=(CitizenBean)obj;
            holder.tev_title.setText(citizenBean.NameCit);
        }else if (obj instanceof EnterpriseDetailBean){
            EnterpriseDetailBean enterpriseDetailBean=(EnterpriseDetailBean)obj;
            holder.tev_title.setText(enterpriseDetailBean.NameEnt);

        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_title;

        public ViewHolder(View itemView) {
            super(itemView);
            tev_title = (TextView) itemView.findViewById(R.id.tev_title);
            tev_title.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    if (listener != null)
                        listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }


}

