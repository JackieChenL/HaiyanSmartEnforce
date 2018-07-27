package videotalk;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kas.clientservice.haiyansmartenforce.R;


import java.util.ArrayList;
import java.util.List;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import smartenforce.base.ShowTitleActivity;
import smartenforce.intf.ItemListener;


public class UserListActivity extends ShowTitleActivity {

    private RecyclerView rcv_list;
     private List<GroupUserBean> list;
    private ArrayList<String> userIdList=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist_test);

    }




    @Override
    protected void findViews() {
        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);
        rcv_list.setLayoutManager(new LinearLayoutManager(aty));
        list=VideoTalkUtils.getInstance().getList();
        final UserListAdapter adapter=new UserListAdapter(list,aty);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int P) {
                list.get(P).isSelect=!list.get(P).isSelect;
                adapter.notifyDataSetChanged();
            }
        });
        rcv_list.setAdapter(adapter);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("选择视频用户");
        tev_title_right.setText("确定");
        tev_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userIdList.clear();
                for (int i=0;i<list.size();i++){
                  GroupUserBean groupUserBean=list.get(i);
                  if (groupUserBean.isSelect){
                      userIdList.add(groupUserBean.id);
                  }
                }
                if (userIdList.size()==0){
                    warningShow("请先选择用户");
                }else{
                    RongCallKit.setGroupMemberProvider(new RongCallKit.GroupMembersProvider() {
                        @Override

                        public ArrayList<String> getMemberList(String groupId, final RongCallKit.OnGroupMembersResult result) {
                            return userIdList;
                        }
                    });


                    RongCallKit.startMultiCall(aty, Conversation.ConversationType.GROUP, "1", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO, userIdList);

                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoTalkUtils.getInstance().resetList();
    }
}
















