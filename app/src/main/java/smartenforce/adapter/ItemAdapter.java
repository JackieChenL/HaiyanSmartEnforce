package smartenforce.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

import smartenforce.bean.CaseBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnforceListBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.bean.GoodListBean;
import smartenforce.bean.InvestigateBean;
import smartenforce.bean.SourseBean;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ItemListener listener;
    private Context context;
    private List<Object> list;
    private LayoutInflater inflater;

    public ItemAdapter(List<Object> list, Context ctx) {
        this.list = list;
        this.context = ctx;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_a2, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Object obj = list.get(position);
        if (obj instanceof EnterpriseDetailBean) {
            EnterpriseDetailBean bean = (EnterpriseDetailBean) obj;
            holder.imv_body.setBackgroundResource(R.drawable.littleicon_user);
            holder.imv_left.setBackgroundResource(R.drawable.littleicon_add);
            holder.tev_top.setText(bean.NameEnt);
            holder.tev_body.setText(bean.LegalrepresentativeEnt);
            holder.tev_left.setText(bean.AddressEnt);
        } else if (obj instanceof CitizenBean) {
            CitizenBean bean = (CitizenBean) obj;
            holder.imv_body.setBackgroundResource(R.drawable.littleicon_user);
            holder.imv_left.setBackgroundResource(R.drawable.littleicon_add);
            holder.tev_top.setText("身份证：" + bean.IdentityCardCit);
            holder.tev_left.setText(bean.AddressCit);
            holder.tev_body.setText(bean.NameCit);

        } else if (obj instanceof InvestigateBean) {
            InvestigateBean bean = (InvestigateBean) obj;
            holder.llt_NameAct.setVisibility(View.VISIBLE);
            holder.imv_body.setBackgroundResource(R.drawable.littleicon_user);
            holder.imv_left.setBackgroundResource(R.drawable.littleicon_add);
            holder.tev_top.setText("文案号：" + bean.ReferenceNumber);
            holder.tev_body.setText(bean.NameECC);
            holder.tev_left.setText(bean.CSAddress);
            holder.tev_NameAct.setText(bean.NameAct);
//            ImageView imv_NameAct;
//            TODO:11111111111111
        } else if (obj instanceof SourseBean) {
            SourseBean bean = (SourseBean) obj;
            holder.imv_body.setBackgroundResource(R.drawable.littleicon_user);
            holder.imv_left.setBackgroundResource(R.drawable.littleicon_add);
            holder.tev_top.setText("文案号：" + bean.ReferenceNumber);
            holder.tev_body.setText(bean.NameECSou);
            holder.tev_left.setText(bean.AddressSou);
        } else if (obj instanceof CaseBean) {
            CaseBean bean = (CaseBean) obj;
            if (bean.type.equals("现场勘查")) {
                holder.tev_add_pic.setVisibility(View.VISIBLE);
                holder.tev_add_graph.setVisibility(View.VISIBLE);
            } else {
                holder.tev_add_pic.setVisibility(View.GONE);
                holder.tev_add_graph.setVisibility(View.GONE);
            }
            holder.imv_left.setBackgroundResource(R.drawable.littleicon_time);
            holder.imv_body.setBackgroundResource(R.drawable.littleicon_user);
            holder.tev_top.setText(bean.type);
            holder.tev_body.setText(bean.NameEmp);
            holder.tev_left.setText(bean.time);
        } else if (obj instanceof EnforceListBean) {
            EnforceListBean bean = (EnforceListBean) obj;
            holder.llt_NameAct.setVisibility(View.VISIBLE);
            holder.imv_body.setBackgroundResource(R.drawable.littleicon_user);
            holder.imv_left.setBackgroundResource(R.drawable.littleicon_time);
            holder.tev_top.setText("文案号：" + bean.ReferenceNumberInA);
            holder.tev_body.setText(bean.NameEmp);
            holder.tev_left.setText(bean.ExecuteTime);
            holder.tev_NameAct.setText(bean.NameIAT);
        }else if (obj instanceof GoodListBean) {
            GoodListBean bean = (GoodListBean) obj;
            holder.llt_NameAct.setVisibility(View.VISIBLE);
            holder.imv_NameAct.setVisibility(View.GONE);
            holder.imv_body.setVisibility(View.GONE);
            holder.imv_left.setVisibility(View.GONE);
            holder.tev_top.setText("物品编号：" + bean.NumberWit);
            holder.tev_body.setText("物品名称："+bean.NameWit);
            holder.tev_NameAct.setText("数量："+bean.CountWit);
            holder.tev_left.setText(bean.ExecuteTime);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tev_top;

        ImageView imv_left;
        TextView tev_left;

        LinearLayout llt_second;
        ImageView imv_body;
        TextView tev_body;
        TextView tev_add_pic, tev_add_graph;

        LinearLayout llt_NameAct;
        ImageView imv_NameAct;
        TextView tev_NameAct;

        public ViewHolder(View itemView) {
            super(itemView);
            tev_top = (TextView) itemView.findViewById(R.id.tev_top);
            tev_add_pic = (TextView) itemView.findViewById(R.id.tev_add_pic);
            tev_add_graph = (TextView) itemView.findViewById(R.id.tev_add_graph);

            llt_second = (LinearLayout) itemView.findViewById(R.id.llt_second);
            imv_body = (ImageView) itemView.findViewById(R.id.imv_body);
            tev_body = (TextView) itemView.findViewById(R.id.tev_body);


            llt_NameAct = (LinearLayout) itemView.findViewById(R.id.llt_NameAct);
            imv_NameAct = (ImageView) itemView.findViewById(R.id.imv_NameAct);
            tev_NameAct = (TextView) itemView.findViewById(R.id.tev_NameAct);

            tev_left = (TextView) itemView.findViewById(R.id.tev_left);
            imv_left = (ImageView) itemView.findViewById(R.id.imv_left);
            itemView.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    if (listener != null)
                        listener.onItemClick(getAdapterPosition());
                }
            });
            tev_add_pic.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    if (buttonListner != null)
                        buttonListner.onAddPictureClick(getAdapterPosition());
                }
            });
            tev_add_graph.setOnClickListener(new NoFastClickLisener() {
                @Override
                public void onNofastClickListener(View v) {
                    if (buttonListner != null)
                        buttonListner.onAddGraphClick(getAdapterPosition());
                }
            });
        }
    }

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    public interface ButtonListener {
        void onAddPictureClick(int p);

        void onAddGraphClick(int p);
    }

    public void setButtonListner(ButtonListener buttonListner) {
        this.buttonListner = buttonListner;
    }

    private ButtonListener buttonListner;
}
