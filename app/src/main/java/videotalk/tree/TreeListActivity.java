package videotalk.tree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.rong.callkit.RongCallKit;
import io.rong.imlib.model.Conversation;
import smartenforce.base.ShowTitleActivity;
import videotalk.normal.GroupUserBean;
import videotalk.widget.TreeNode;
import videotalk.widget.TreeViewAdapter;


public class TreeListActivity extends ShowTitleActivity {

    private RecyclerView rcv_list;
    private TreeViewAdapter adapter;
    private Map<String,TreeBean> map=new LinkedHashMap<>();
    ArrayList<String> idList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist_tree_blue);

    }

    public TreeNode findChildren(TreeBean TreeBean) {
         TreeNode treeNode=new TreeNode(new ParentNode(TreeBean));
        if (TreeBean.childList!=null){
            for (int i=0;i<TreeBean.childList.size();i++){
                treeNode.addChild(findChildren(TreeBean.childList.get(i)));
            }
        }

        return treeNode;
    }



    @Override
    protected void findViews() {
        String userName = UserSingleton.getInstance().getUserAccount(this);
        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);
        TreeUtils.getInstance().getNetData(userName,new TreeUtils.onSuccess() {
            @Override
            public void data(List<TreeBean> list) {
                List<TreeBean> beanList= TreeUtils.getInstance().bulid(list) ;
                List<TreeNode> nodes = new ArrayList<>();
                for (int i=0;i<beanList.size();i++){
                    nodes.add(findChildren(beanList.get(i)));
                }


                rcv_list.setLayoutManager(new LinearLayoutManager(aty));
                rcv_list.addItemDecoration(new DividerItemDecoration(aty,DividerItemDecoration.VERTICAL));

                adapter = new TreeViewAdapter(nodes, Arrays.asList(new ParentNodeBinder()));
                adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
                    @Override
                    public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                        if (!node.isLeaf()) {
//                            Log.e("node_tag","isParent");
//                            onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
                        }else{
//                            Log.e("node_tag","isLeaf");
                            TreeBean treeBean =((ParentNode)node.getContent()).treeBean;
                            ParentNodeBinder.ViewHolder ParentCodeNodeBinder=(ParentNodeBinder.ViewHolder)holder;
                            CheckBox cbx=ParentCodeNodeBinder.getCheckBox();

                            cbx.setChecked(!cbx.isChecked());
                            if (cbx.isChecked()){
                                map.put(treeBean.Token,treeBean);
                            }else{
                                map.remove(treeBean.Token);
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {

                    }
                });
                rcv_list.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("选择视频用户");
        tev_title_right.setText("确定");
        tev_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.size()==0){
                  show("你还没有选择视屏用户");
                }else{
                    idList.clear();
                    for(Map.Entry<String, TreeBean> entry: map.entrySet())
                    {
                        idList.add(entry.getValue().id+"");
                    }

                    RongCallKit.setGroupMemberProvider(new RongCallKit.GroupMembersProvider() {
                        @Override

                        public ArrayList<String> getMemberList(String groupId, final RongCallKit.OnGroupMembersResult result) {
                            return idList;
                        }
                    });


                    RongCallKit.startMultiCall(aty, Conversation.ConversationType.GROUP, "1", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO, idList);
                }
            }
        });
    }



}
















