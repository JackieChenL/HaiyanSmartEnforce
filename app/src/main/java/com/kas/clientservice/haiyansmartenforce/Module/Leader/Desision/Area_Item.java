package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Area_Item;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Module.Leader.DetailsListActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class Area_Item extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
	TextView tv_title_name;
	ImageView iv_title_back;
	TextView tv_area_item_time;
	TextView tv_area_item_act;
	ListView lv_area_item_list;
	LinearLayout ll_area_item_num;
	TextView area_item_num;
	TextView area_item_endnum;
	List<Vp_Area_Item> list;
	int actID;
	int depID;
	String start;
	String end;
	String name;
	
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog = ProgressDialog.show(Area_Item.this, "请求网络",
						"正在发送...", true, true);
				progressDialog.setCanceledOnTouchOutside(false);
				break;
			case 2:
				progressDialog.setMessage("正在建立连接，并验证信息......");
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected int getLayoutId() {
		return R.layout.area_item;
	}

	@Override
	protected String getTAG() {
		return this.toString();
	}

	@Override
	protected void initResAndListener() {
		super.initResAndListener();
		initRes();
		tv_title_name.setText("总体分析");
		Intent intent = this.getIntent();
		depID = intent.getIntExtra("depID", 0);
		actID = intent.getIntExtra("actID", 0);
		start = intent.getStringExtra("start");
		end = intent.getStringExtra("end");
		name = intent.getStringExtra("name");
		tv_area_item_time.setText(start+"--"+end);
		tv_area_item_act.setText(name);
		sendMsg(1, null);
		sendMsg(2, null);
		//ToolModel model=new ToolModel(this);
		//model.AreaItem(start, end, depID, actID, new getAreaItem());
		newWorkRequest(start, end, depID, actID);
		//OkHttpUtils.AreaItem(startTime, endTime, depID, actID, handler, what);
	}

	public static Handler mHandlerBrck;
	private void newWorkRequest(String startTime, String endTime,int depID,int actID){
//		mHandlerBrck = new Handler(){
//			public void handleMessage(android.os.Message msg) {
//				if(msg.what == Constant.AreaItem){
//					String response = (String)msg.obj;
//					Date(response);
//				}
//			};
//		};
//		OkHttpUtils.AreaItem(startTime,endTime,depID,actID,mHandlerBrck, Constant.AreaItem);
		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/DepartmentEmployeeCaseStatistics.ashx")
				.addParams("startTime", startTime+"")
				.addParams("endTime", endTime)
				.addParams("depID", depID+"")
				.addParams("actID", actID+"")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
			}

			@Override
			public void onResponse(String response, int id) {
				Date(response);
			}
		});
	}
	private void Date(String response){
		list=new ArrayList<Vp_Area_Item>();
		int num = 0;
		int endnum = 0;
			try {
				JSONObject object = new JSONObject(response.toString());
				JSONArray array = object.getJSONArray("KS");
				for (int i = 0; i < array.length(); i++) {
					JSONObject object2=array.getJSONObject(i);
					String empName = object2.getString("empName");
					int allNum = object2.getInt("allNum");
					int endNum = object2.getInt("endNum");
					int EmployeeID = object2.getInt("EmployeeID");
					num+=allNum;  
					endnum+=endNum;  
					Vp_Area_Item area_Item=new Vp_Area_Item(empName, allNum, endNum,EmployeeID);
					list.add(area_Item);
				}
				if (num==0) {
					ll_area_item_num.setVisibility(View.GONE);
				}else {
					ll_area_item_num.setVisibility(View.VISIBLE);
					area_item_num.setText(num+"");
					area_item_endnum.setText(endnum+"");
				}
				Area_Item_Adapter adapter=new Area_Item_Adapter(Area_Item.this, list);
				lv_area_item_list.setAdapter(adapter);
				ListViewFitParent.setListViewHeightBasedOnChildren(lv_area_item_list);
				progressDialog.dismiss();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	public void sendMsg(int flag, String content) {
		Message msg = new Message();
		msg.what = flag;
		msg.obj = content;
		handler.sendMessage(msg); 
	}

	private void initRes() {
		tv_title_name=(TextView) findViewById(R.id.tv_header_title);
		iv_title_back=(ImageView) findViewById(R.id.iv_heaer_back);
		tv_area_item_time=(TextView) findViewById(R.id.tv_area_item_time);
		tv_area_item_act=(TextView) findViewById(R.id.tv_area_item_act);
		lv_area_item_list=(ListView) findViewById(R.id.lv_area_item_list);
		ll_area_item_num=(LinearLayout) findViewById(R.id.ll_area_item_num);
		area_item_num=(TextView) findViewById(R.id.area_item_num);
		area_item_endnum=(TextView) findViewById(R.id.area_item_endnum);
		iv_title_back.setOnClickListener(this);
		lv_area_item_list.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_title_back:
			finish();
			break;
		default:
			break;
		}
	}
	


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(Area_Item.this,DetailsListActivity.class);
		intent.putExtra("what", Constants.WhatDecisionAreaDetailsPersonage);
		intent.putExtra("EmployeeID",list.get(arg2).getEmployeeID()+"");
		intent.putExtra("actID",actID+"");
		intent.putExtra("StartTime",start);
		intent.putExtra("EndTime",end);
		intent.putExtra("depID", depID);
		intent.putExtra("depName", name);
		startActivity(intent);
	}
	
}