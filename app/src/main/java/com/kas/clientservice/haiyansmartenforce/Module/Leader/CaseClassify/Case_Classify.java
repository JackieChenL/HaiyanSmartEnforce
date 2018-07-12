package com.kas.clientservice.haiyansmartenforce.Module.Leader.CaseClassify;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.Case_SouType;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.CustomerValueFormatter;
import com.kas.clientservice.haiyansmartenforce.Utils.MyYValueFormatter;
import com.kas.clientservice.haiyansmartenforce.Wedge.MyMarkerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class Case_Classify extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, OnChartValueSelectedListener {
    TextView tv_title_name;
    ImageView iv_title_back;
    RelativeLayout rl_classify_start_time;
    TextView tv_classify_start_time;
    RelativeLayout rl_classify_end_time;
    TextView tv_classify_end_time;
    ListView lv_classify_list;
    ListView lv_classify_list2;
    Button bt_classify_inquire;
    LinearLayout ll_classify_num;
    TextView classify_allnum;
    TextView classify_endnum;
    LinearLayout ll_classify_num_two;
    TextView classify_allnum_two;
    TextView classify_endnum_two;
    List<Case_SouType> list;
    List<Case_SouType> attList;

    List<Case_SouType> AllList = new ArrayList<Case_SouType>();

    Context mContext = Case_Classify.this;
    String time;
    String mStartTime = "";
    String mEndTime = "";
    String NowStartTime = "";
    String NowEndTime = "";
//    private ProgressDialog progressDialog;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    progressDialog = ProgressDialog.show(Case_Classify.this, "请求网络",
//                            "正在发送...", true, true);
//                    progressDialog.setCanceledOnTouchOutside(false);
                    break;
                case 2:
//                    progressDialog.setMessage("正在建立连接，并验证信息......");
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private BarChart mChart;

    @Override
    protected int getLayoutId() {
        return R.layout.sources_classify;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        initRes();
        tv_title_name.setText("案件分类");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        time = formatter.format(curDate);
        tv_classify_start_time.setText("2000-01-01");
        tv_classify_end_time.setText(time);
//        sendMsg(1, null);
//        sendMsg(2, null);
        mStartTime = tv_classify_start_time.getText().toString();
        mEndTime = time;
        //model=new ToolModel(Case_Classify.this);
        //model.ProcSouTypeCaseStatistics(tv_classify_start_time.getText().toString(), time, new ProcSouTypeCaseStatistics());
        //model.ProcAttributeCaseStatistics(tv_classify_start_time.getText().toString(), time, new ProcAttributeCaseStatistics());
        newWorkRequest(tv_classify_start_time.getText().toString(), time);

        initMpChart();
    }

    public void initMpChart() {
        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawValueAboveBar(true);//将Y数据显示在点的上方
        // mChart.setDrawBorders(true);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(true);//挤压缩放
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setScaleYEnabled(false);
        mChart.setDescription("");
        mChart.setDoubleTapToZoomEnabled(false);//双击缩放
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
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
        mChart.getAxisRight().setEnabled(false);
    }

    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
    ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
    ArrayList<String> xVals = new ArrayList<String>();

    public void setData() {
        if (AllList == null) {
            return;
        }
        xVals.clear();
        yVals1.clear();
        yVals2.clear();
        for (int x = 0; x < AllList.size(); x++) {
            if (!AllList.get(x).getSouTypeName().equals("合计")) {
                xVals.add(AllList.get(x).getSouTypeName());
                yVals1.add(new BarEntry(AllList.get(x).getAllNum(), x));
                yVals2.add(new BarEntry(AllList.get(x).getEndNum(), x));
            }
        }


        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "数量");
        // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
        // ColorTemplate.FRESH_COLORS));
        set1.setColor(Color.rgb(104, 241, 175));
        BarDataSet set2 = new BarDataSet(yVals2, "结案数");
        set2.setColor(Color.rgb(164, 228, 251));
       /* BarDataSet set3 = new BarDataSet(yVals3, "三季度");
        set3.setColor(Color.rgb(242, 247, 158));
*/
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        //dataSets.add(set3);

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
        mChart.animateXY(800, 800);//图表数据显示动画
        mChart.setVisibleXRangeMaximum(15);//设置屏幕显示条数
        mChart.invalidate();
    }

    private void initRes() {
        tv_title_name = (TextView) findViewById(R.id.tv_header_title);
        iv_title_back = (ImageView) findViewById(R.id.iv_heaer_back);
        rl_classify_start_time = (RelativeLayout) findViewById(R.id.rl_classify_start_time);
        tv_classify_start_time = (TextView) findViewById(R.id.tv_classify_start_time);
        rl_classify_end_time = (RelativeLayout) findViewById(R.id.rl_classify_end_time);
        tv_classify_end_time = (TextView) findViewById(R.id.tv_classify_end_time);
        lv_classify_list = (ListView) findViewById(R.id.lv_classify_list);
        lv_classify_list2 = (ListView) findViewById(R.id.lv_classify_list2);
        bt_classify_inquire = (Button) findViewById(R.id.bt_classify_inquire);
        ll_classify_num = (LinearLayout) findViewById(R.id.ll_classify_num);
        classify_allnum = (TextView) findViewById(R.id.classify_allnum);
        classify_endnum = (TextView) findViewById(R.id.classify_endnum);
        ll_classify_num_two = (LinearLayout) findViewById(R.id.ll_classify_num_two);
        classify_allnum_two = (TextView) findViewById(R.id.classify_allnum_two);
        classify_endnum_two = (TextView) findViewById(R.id.classify_endnum_two);
        iv_title_back.setOnClickListener(this);
        rl_classify_start_time.setOnClickListener(this);
        rl_classify_end_time.setOnClickListener(this);
        bt_classify_inquire.setOnClickListener(this);
        //
        lv_classify_list.setOnItemClickListener(this);
        lv_classify_list2.setOnItemClickListener(this);
    }

    public static Handler mHandlerBrck;

    private void newWorkRequest(String startTime, String endTime) {
        mHandlerBrck = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 100) {
                    String response = (String) msg.obj;
                    Date(response);
                }
                if (msg.what == 101) {
                    String response = (String) msg.obj;
                    Date2(response);
                }
            }

            ;
        };
//        OkHttpUtils.ProcSouTypeCaseStatistics(startTime, endTime, mHandlerBrck, Constant.ProcSouTypeCaseStatistics);
//        RequestBody formBody = new FormBody.Builder()
//                .add("startTime", startTime)
//                .add("endTime", endTime)
//                .build();
        showLoadingDialog();
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/SouTypeCaseStatistics.ashx")
                .addParams("startTime",startTime)
                .addParams("endTime",endTime)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                showNetErrorToast();
                dismissLoadingDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                Message message = new Message();
                message.what = 100;
                message.obj = response;
                mHandlerBrck.sendMessage(message);
            }
        });
    }

    private void Date(String response) {
        Log.i(TAG, "Date: ");
        NowStartTime = mStartTime;
        NowEndTime = mEndTime;
        int aNum = 0;
        int eNum = 0;
        list = new ArrayList<Case_SouType>();
        try {
            JSONObject object = new JSONObject(response.toString());
            JSONArray array = object.getJSONArray("KS");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object2 = array.getJSONObject(i);
                String name = object2.getString("souTypeName");
                int allNum = object2.getInt("allNum");
                int endNum = object2.getInt("endNum");
                aNum += allNum;
                eNum += endNum;
                Case_SouType souType = new Case_SouType(name, allNum, endNum);
                list.add(souType);

            }

            if (aNum == 0 && eNum == 0) {
                ll_classify_num.setVisibility(View.GONE);
            } else {
                //ll_classify_num.setVisibility(View.VISIBLE);
                classify_allnum.setText(aNum + "");
                classify_endnum.setText(eNum + "");
            }
            AllList.addAll(list);
            newWorkRequest2(tv_classify_start_time.getText().toString(), time);
//            SouType_Adapter adapter = new SouType_Adapter(Case_Classify.this, list);
//            lv_classify_list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "Date: "+e.toString());
        }
    }

    private void newWorkRequest2(String startTime, String endTime) {
        Log.i(TAG, "newWorkRequest2: ");
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/AttributeCaseStatistics.ashx")
                .addParams("startTime",startTime)
                .addParams("endTime",endTime)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                dismissLoadingDialog();
                showLoadingDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: "+response);
                Message message = new Message();
                message.what = 101;
                message.obj = response;
                mHandlerBrck.sendMessage(message);
            }
        });
    }

    private void Date2(String response) {
        Log.i("result", "ProcSouTypeCaseStatistics:" + response.toString());
        attList = new ArrayList<Case_SouType>();
        int aNum = 0;
        int eNum = 0;
        try {
            JSONObject object = new JSONObject(response.toString());
            JSONArray array = object.getJSONArray("KS");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object2 = array.getJSONObject(i);
                String name = object2.getString("AttrName");
                int allNum = object2.getInt("allNum");
                int endNum = object2.getInt("endNum");
                aNum += allNum;
                eNum += endNum;
                Case_SouType attribute = new Case_SouType(name, allNum, endNum);
                attList.add(attribute);
            }

            if (aNum == 0 && eNum == 0) {
                ll_classify_num_two.setVisibility(View.GONE);
            } else {
                //ll_classify_num_two.setVisibility(View.VISIBLE);
                classify_allnum_two.setText(aNum + "");
                classify_endnum_two.setText(eNum + "");
            }
            AllList.addAll(attList);
            setData();
//				Attribute_Adapter adapter=new Attribute_Adapter(Case_Classify.this, attList);
//				lv_classify_list2.setAdapter(adapter);
//            progressDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.rl_classify_start_time:
                setTimeDialog(tv_classify_start_time.getText().toString(), tv_classify_start_time);
                break;
            case R.id.rl_classify_end_time:
                setTimeDialog(tv_classify_end_time.getText().toString(), tv_classify_end_time);
                break;
            case R.id.bt_classify_inquire:
                String start_time = tv_classify_start_time.getText().toString();
                String end_time = tv_classify_end_time.getText().toString();

                mStartTime = tv_classify_start_time.getText().toString();
                mEndTime = tv_classify_end_time.getText().toString();
                sendMsg(1, null);
                sendMsg(2, null);
                if (AllList != null && AllList.size() > 0) {
                    AllList.clear();
                    xVals.clear();
                    yVals1.clear();
                    yVals2.clear();
                }
                if (mChart != null) {
                    mChart.invalidate();
                }
                //model.ProcSouTypeCaseStatistics(start_time, end_time, new ProcSouTypeCaseStatistics());
                //model.ProcAttributeCaseStatistics(start_time, end_time, new ProcAttributeCaseStatistics());
                newWorkRequest(start_time, end_time);
                //newWorkRequest2(start_time, end_time);
                //newWorkRequest(tv_classify_start_time.getText().toString(),time);
                //newWorkRequest2(tv_classify_start_time.getText().toString(),time);
                break;
            default:
                break;
        }
    }

    private void setTimeDialog(String str, final TextView text) {
        View view = View.inflate(Case_Classify.this, R.layout.time, null);
        final DatePicker datePicker = (DatePicker) view
                .findViewById(R.id.dp_date);
        try {
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            outputFormat.parse(str);//非常重要
            Calendar c = outputFormat.getCalendar();
            datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH), null);
        } catch (Exception e) {

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Case_Classify.this);
        builder.setView(view);
        builder.setTitle("日期选择");
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text.setText(datePicker.getYear() + "-"
                                + (datePicker.getMonth() + 1) + "-"
                                + datePicker.getDayOfMonth() + ""
                        );
                    }
                });
        builder.show();
    }


    public void sendMsg(int flag, String content) {
        Message msg = new Message();
        msg.what = flag;
        msg.obj = content;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
//        if (arg0.getId() == R.id.lv_classify_list) {
//            Intent intent = new Intent();
//            intent.putExtra("what", httpUrl.WhatDecisionCaseDetailsSource);
//            intent.putExtra("souTypeName", list.get(arg2).getSouTypeName());
//            intent.putExtra("StartTime", NowStartTime);
//            intent.putExtra("EndTime", NowEndTime);
//            intent.setClass(mContext, DetailsListActivity.class);
//            startActivity(intent);
//        }
//        if (arg0.getId() == R.id.lv_classify_list2) {
//            Intent intent = new Intent();
//            intent.putExtra("what", httpUrl.WhatDecisionCaseDetailsCase);
//            intent.putExtra("souTypeName", attList.get(arg2).getSouTypeName());
//            intent.putExtra("StartTime", NowStartTime);
//            intent.putExtra("EndTime", NowEndTime);
//            intent.setClass(mContext, DetailsListActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }
}