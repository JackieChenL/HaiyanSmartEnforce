package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.Department;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.kas.clientservice.haiyansmartenforce.R.id.lv_department_list;
import static com.kas.clientservice.haiyansmartenforce.R.id.tv_case_end_time;
import static com.kas.clientservice.haiyansmartenforce.R.id.tv_case_start_time;

public class DepartmentCaseActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(tv_case_start_time)
    TextView tv_startTime;
    @BindView(tv_case_end_time)
    TextView tv_endTime;
    @BindView(R.id.sp_case_type)
    Spinner spinner;
    @BindView(lv_department_list)
    ListView listView;
    @BindView(R.id.bt_department_case_inquire)
    TextView tv_query;

    List<Department> list = new ArrayList<>();
    List<Big> big = new ArrayList<>();
    ArrayAdapter<Big> Typeadapter;
    String startTime = "";
    String endTime = "";
    int bigid;
    String bigName;

    @Override
    protected int getLayoutId() {
        return R.layout.department_case;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("部门案件");
        iv_back.setOnClickListener(this);

        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        timePickerDialog = new TimePickerDialog(mContext);

        endTime = TimeUtils.getFormedTime("yyyy-MM-dd HH:mm:ss");
        startTime = "2000-1-1 00:00:00";
        tv_startTime.setText(startTime);
        tv_endTime.setText(endTime);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                bigid = big.get(position).getId();
                bigName = big.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        getBigType();
        initAdapter();
    }
    Department_CaseAdapter adapter;
    private void initAdapter() {
        adapter = new Department_CaseAdapter(mContext, list,bigid,"",startTime,endTime);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case tv_case_start_time:
                choseStartTime();
                break;
            case tv_case_end_time:
                choseEndTime();
                break;
            case R.id.bt_department_case_inquire:
                query();
                break;

        }
    }

    private void query() {
        list.clear();
        Log.i(TAG, "query: "+bigid);
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/DepartmentCaseStatistics.ashx")
                .addParams("startTime",startTime)
                .addParams("endTime",endTime)
                .addParams("firstLevelID",bigid+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                try {
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray array = object.getJSONArray("KS");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object2=array.getJSONObject(i);
                        String name = object2.getString("depName");
                        int simple = object2.getInt("simple");
                        int generic = object2.getInt("generic");
                        int initiative = object2.getInt("initiative");
                        int passivity = object2.getInt("passivity");
                        int DepartmentID = object2.getInt("DepartmentID");
                        Department department=new Department(name, simple, generic, initiative, passivity,DepartmentID);
                        list.add(department);
                    }
                    adapter.notifyDataSetChanged();

//                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    int flag = 0;

    private void choseEndTime() {
        flag = 2;
        choseTime();
    }

    private void choseStartTime() {
        flag = 1;
        choseTime();
    }

    private void choseTime() {
        timePickerDialog.showPPw(listView);
    }

    TimePickerDialog timePickerDialog;

    @Override
    public void positiveListener() {
        String year = timePickerDialog.getYear() + "";
        String months = timePickerDialog.getMonth() + "";
        String day = timePickerDialog.getDay() + "";
        String hour = timePickerDialog.getHour() + "";
        String minutes = timePickerDialog.getMinute() + "";
        if (flag == 1) {
            tv_startTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
            startTime = tv_startTime.getText().toString();
        }
        if (flag == 2) {
            tv_endTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
            endTime = tv_endTime.getText().toString();
        }
    }

    public void getBigType() {
        OkHttpUtils.get().url(RequestUrl.baseUrl_leader+"Use/Ashx/Getfirstlevel.ashx")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id_) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response.toString());
                    Big mbig = new Big("-----全部-----", -1);
                    big.add(mbig);
                    JSONArray jsonArray = object.getJSONArray("KS");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Big mybig = new Big(name, id);
                        big.add(mybig);
                    }
                    Typeadapter = new ArrayAdapter<Big>(mContext, android.R.layout.simple_spinner_item, big);
                    Typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(Typeadapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void negativeListener() {

    }


    public class Big {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Big(String name, int id) {
            super();
            this.name = name;
            this.id = id;
        }

        public Big(String name) {
            super();
            this.name = name;
        }

        public Big() {
            super();
        }

        @Override
        public String toString() {
            return name;
        }


    }
}
