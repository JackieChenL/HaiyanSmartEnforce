package smartenforce.aty.function3;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

import smartenforce.base.ShowTitleActivity;
import smartenforce.intf.ItemListener;
import smartenforce.intf.PermissonCallBack;
import smartenforce.projectutil.FileDownLoadUtil;

public abstract class ShowPdfActivity extends ShowTitleActivity {
    AlertView alterView;

    protected void showPdfChoose(final int id, final int Type) {

        requestPermissionGroup(Pid.FILE, new PermissonCallBack() {
            @Override
            public void onPerMissionSuccess() {
                alterView = new AlertView("提醒", "现场文书已生成，是否现在下载打印?", null, null, new String[]{"稍后", "下载"}, aty, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        alterView.dismissImmediately();
                        if (position == 1) {
                            FileDownLoadUtil.doGetPdfUrl(aty, id, Type,app.userID, new FileDownLoadUtil.onDownLoadCallback() {
                                @Override
                                public void onErroMsg(String msg) {
                                    warningShowAndAction(msg, 0, new ItemListener() {
                                        @Override
                                        public void onItemClick(int P) {
                                            finish();
                                        }
                                    });
                                }

                                @Override
                                public void onSuccess() {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });
                        } else {
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }

                );
                alterView.show();
            }
        });

    }


}
