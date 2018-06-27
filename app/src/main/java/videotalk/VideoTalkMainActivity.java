package videotalk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.CommonActivity;
import videotalk.ui.activities.VideoTalkActivity;


public class VideoTalkMainActivity extends CommonActivity {

    static final private int OPEN_SETTINGS_CODE = 1;
    static final private int REQUEST_PERMISSION_CODE = 101;

    private EditText mSessionIDEditText;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videotalk_main);

        mStartButton = (Button)findViewById(R.id.start_button);
        mStartButton.setEnabled(false);

        mSessionIDEditText = (EditText)findViewById(R.id.sessionid_edittext);
        mSessionIDEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mStartButton.setEnabled(true);
                } else {
                    mStartButton.setEnabled(false);
                }
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionGranted = checkOrRequestPermission();
                if (permissionGranted) {
                    gotoVideoTalk();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPEN_SETTINGS_CODE:
                // TODO: 刷新配置信息
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                boolean allPermissionGranted = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allPermissionGranted = false;
                        Toast.makeText(this, getString(R.string.vt_toast_permission_denied, permissions[i]), Toast.LENGTH_LONG).show();
                    }
                }
                if (allPermissionGranted) {
                    gotoVideoTalk();
                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
            }
            break;
        }
    }

    private boolean checkOrRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    private void gotoVideoTalk() {

        String sessionID = mSessionIDEditText.getText().toString();
        if(sessionID.contains(" ")){
            Toast.makeText(this, R.string.vt_input_session_id_no_allow_contain_white_space, Toast.LENGTH_LONG).show();
            return;
        }
        if (sessionID.length() > 0) {
            // 将sessionID传给下一个页面
            Intent intent = new Intent(this, VideoTalkActivity.class);
            intent.putExtra("sessionId", sessionID.trim());
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.vt_hint_input_session_id, Toast.LENGTH_LONG).show();
        }
    }
}
