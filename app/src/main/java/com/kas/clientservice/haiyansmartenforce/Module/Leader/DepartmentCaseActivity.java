package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.Department;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.CustomerValueFormatter;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;
import com.kas.clientservice.haiyansmartenforce.Wedge.MyMarkerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.kas.clientservice.haiyansmartenforce.R.id.lv_department_list;
import static com.kas.clientservice.haiyansmartenforce.R.id.tv_case_end_time;
import static com.kas.clientservice.haiyansmartenforce.R.id.tv_case_start_time;

public class DepartmentCaseActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface, OnChartValueSelectedListener {
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
    @BindView(R.id.barChart_department)
    BarChart mChart;

    List<Department> list = new ArrayList<>();
    List<Big> big = new ArrayList<>();
    ArrayAdapter<Big> Typeadapter;
    String startTime = "";
    String endTime = "";
    int bigid;
    String bigName;
    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
    ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
    ArrayList<BarEntry> yVals4 = new ArrayList<BarEntry>();

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
                Log.i(TAG, "onItemSelected: "+bigid);
                bigName = big.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        initMpChart();
        initAdapter();
        getBigType();
    }
    Department_CaseAdapter adapter;
    private void initAdapter() {
        adapter = new Department_CaseAdapter(mContext, list,bigid,"",startTime,endTime);
        listView.setAdapter(adapter);
    }

    public void initMpChart(){
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawValueAboveBar(true);//将Y数据显示在点的上方
        // mChart.setDrawBorders(true);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(true);//挤压缩放
        mChart.setDrawBarShadow(false);
//        mChart.setScaleYEnabled(false);
//        mChart.setClickable(true);
        mChart.setDoubleTapToZoomEnabled(false);//双击缩放
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
//        mChart.highlightValue(null,true);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        // define an offset to change the original position of the marker
        // (optional)
        // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
        // set the marker to the chart
        mChart.setMarkerView(mv);
        Legend l = mChart.getLegend();//图例
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setTextSize(10f);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        XAxis xl = mChart.getXAxis();
        xl.setLabelRotationAngle(-20);//设置x轴字体显示角度
        //xl.setPosition(XAxisPosition.BOTTOM);


        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setValueFormatter(new LargeValueFormatter());//
        leftAxis.setValueFormatter(new MyYValueFormatter());//自定义y数据格式化方式
        leftAxis.setDrawGridLines(false);//是否画线
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(true);


    }
    public void setData() {
        if(list==null){
            return;
        }

        yVals1.clear();
        yVals2.clear();
        yVals3.clear();
        xVals.clear();
        for (int s=0;s<list.size();s++){
            yVals1.add(new BarEntry(list.get(s).getInitiative(), s));
            yVals2.add(new BarEntry(list.get(s).getPassivity(), s));
            yVals3.add(new BarEntry(list.get(s).getGeneric(), s));
            yVals4.add(new BarEntry(list.get(s).getSimple(), s));
            xVals.add(list.get(s).getDepName());
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "巡查任务");
        // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
        // ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(104, 241, 175));
        BarDataSet set2 = new BarDataSet(yVals2, "线索任务");
        set2.setColor(Color.rgb(164, 228, 251));
        BarDataSet set3 = new BarDataSet(yVals3, "一般程序");
        set3.setColor(Color.rgb(240, 177, 51));
        BarDataSet set4 = new BarDataSet(yVals3, "简易程序");
        set4.setColor(Color.rgb(241, 246, 89));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        BarData data = new BarData(xVals, dataSets);
        // data.setValueFormatter(new LargeValueFormatter());

        // add space between the dataset groups in percent of bar-width
        data.setValueFormatter(new CustomerValueFormatter());
        data.setDrawValues(true);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(13);
        data.setGroupSpace(80f);//设置组数据间距
        //data.setValueTypeface(tf);

        mChart.setData(data);
        mChart.animateXY(800,800);//图表数据显示动画
        mChart.setVisibleXRangeMaximum(15);//设置屏幕显示条数
        mChart.invalidate();
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
                    setData();
//                    adapter.notifyDataSetChanged();

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

                    query();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void negativeListener() {

    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        int x = entry.getXIndex();
        Log.i(TAG, "onValueSelected: x="+x+" i="+i);
        int what;
        String type;
        switch (i) {
            case 0:
                what = Constants.WhatDepartmentDetailsSource;
                type = "initiative";
                startSimple(x,type,what);
                break;
            case 1:
                what = Constants.WhatDepartmentDetailsSource;
                type = "passivity";
                startSimple(x,type,what);
                break;
            case 2:
                what = Constants.WhatDepartmentDetailsCase;
                type = "generic";
                startSimple(x,type,what);
                break;
            case 3:
                what = Constants.WhatDepartmentDetailsCase;
                type = "simple";
                startSimple(x,type,what);
                break;
        }
    }
    private void startSimple(int position,String type,int what){
        Log.i("test","position:" + position);
        Intent intent = new Intent();
        intent.setClass(mContext, DetailsListActivity.class);
        intent.putExtra("what",what);
        intent.putExtra("type",type);
        intent.putExtra("DeDepartmentID",list.get(position).getDepartmentID()+"");
        intent.putExtra("firstLevelID","");
        intent.putExtra("bigName",bigName);
        intent.putExtra("StartTime",startTime.substring(0,startTime.indexOf(" ")));
        intent.putExtra("EndTime",endTime.substring(0,endTime.indexOf(" ")));
        startActivity(intent);
    }

    @Override
    public void onNothingSelected() {
        Log.i(TAG, "onNothingSelected: ");
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
    public class MyYValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;

        public MyYValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(value);
        }




    }
}
