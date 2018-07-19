package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.Entity.Area_Item;
import com.kas.clientservice.haiyansmartenforce.Entity.Big;
import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Area;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
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

public class Vp_Area_Fragment extends Fragment implements View.OnClickListener{
	RelativeLayout rl_area_start_time;
	TextView tv_area_start_time;
	RelativeLayout rl_area_end_time;
	TextView tv_area_end_time;
	Button bt_area_inquire;
	ListView lv_area_list;
	List<Vp_Area> list;
	List<Area_Item> list2;
	RelativeLayout rl_area_type;
	Spinner sp_area_type;
	List<Big> big;
	ArrayAdapter<Big> Typeadapter;
	int bigid;
	String TAG = "area_fragment";
	private ProgressDialog progressDialog;
	private Context mContext;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog = ProgressDialog.show(getActivity(), "请求网络",
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.area_fragment, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initRes();
		mContext = getActivity();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String time = formatter.format(curDate);
		String sdate = "2000-1-1";
		tv_area_start_time.setText(sdate);
		tv_area_end_time.setText(time);
		sendMsg(1, null);
		sendMsg(2, null);
		//model=new ToolModel(getActivity());
		//model.Area(tv_area_start_time.getText().toString(), time, new Area());
		//model.getBig(new getBig());
		netWorkRequest(tv_area_start_time.getText().toString(), time);
		netWorkRequest2();
	}

	public static Handler mHandlerBrck;
	private void netWorkRequest(String startTime, String endTime){
		mHandlerBrck = new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what == 200){
					String response = (String)msg.obj;
					Date(response);
				}
				if(msg.what == 300){
					String response = (String)msg.obj;
					Date2(response);
				}
			};
		};
//		OkHttpUtils.Area(startTime,endTime,mHandlerBrck, Constant.Area);
		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/DepartmentTopActionCaseStatistic.ashx")
				.addParams("startTime", startTime)
				.addParams("endTime", endTime)
				.addParams("UserID", UserSingleton.USERINFO.getName().UserID)
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: 区域"+e.toString());
				progressDialog.dismiss();
			}

			@Override
			public void onResponse(String response, int id) {
				Log.i(TAG, "onResponse: 区域"+response);
				Message message = new Message();
				message.what = 200;
				message.obj = response;
				mHandlerBrck.sendMessage(message);
			}
		});
	}
	private void Date(String response){
		list=new ArrayList<Vp_Area>();
			try {
				JSONObject object = new JSONObject(response.toString());
				JSONArray array = object.getJSONArray("KS");
				for (int i = 0; i < array.length(); i++) {
					JSONObject object2=array.getJSONObject(i);
					String depName = object2.getString("depName");
					JSONArray child = object2.getJSONArray("details");
					list2 = new ArrayList<Area_Item>();
					for (int j = 0; j < child.length(); j++) {
						JSONObject mJson2 = child.getJSONObject(j);
						String actName = mJson2.getString("actName");
						int id = mJson2.getInt("id");
						int caseNum = mJson2.getInt("caseNum");
						int depID = mJson2.getInt("depID");
						int actID = mJson2.getInt("actID");
						Area_Item area_Item=new Area_Item(actName, caseNum, depID, actID, id);
						list2.add(area_Item);
					}
					Vp_Area area=new Vp_Area(depName, list2);
					list.add(area);
				}
				if (list.size()==0) {
					Toast.makeText(getActivity(), "没有相关信息", Toast.LENGTH_SHORT).show();
					progressDialog.dismiss();
					return ;
				}
				String start = tv_area_start_time.getText().toString();
				String end = tv_area_end_time.getText().toString();
				Area_Adapter adapter=new Area_Adapter(getActivity(), list,start,end);
				lv_area_list.setAdapter(adapter);
				ListViewFitParent.setListViewHeightBasedOnChildren(lv_area_list);
				progressDialog.dismiss();
			} catch (JSONException e) {
				Log.e("Exception", e.toString());
				e.printStackTrace();
			} 
	}
	private void netWorkRequest2(){
//		OkHttpUtils.getBig(mHandlerBrck, Constant.AreaGetBig);
		OkHttpUtils.get().url(RequestUrl.baseUrl_leader+"Use/Ashx/Getfirstlevel.ashx")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				progressDialog.dismiss();
				Log.i(TAG, "onError: "+e.toString());
			}

			@Override
			public void onResponse(String response, int id_) {
//				Log.i(TAG, "onResponse: "+response);
				Date2(response);
			}
		});
	}
	private void Date2(String response){
		big=new ArrayList<Big>();
		//Big mbig=new Big("-----请选择-----",0);
		//big.add(mbig);
		try {
			JSONObject object = new JSONObject(response.toString());
			JSONArray jsonArray = object.getJSONArray("KS");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int id = jsonObject.getInt("id");
				String name = jsonObject.getString("name");
				Big mybig=new Big(name,id);
				big.add(mybig);
			}
			//因为当前获取有问题  所以抛出异常然后重新请求 我可能忘记写请求了 恩 忘了
			try {
				Typeadapter = new ArrayAdapter<Big>(getActivity(),android.R.layout.simple_spinner_item, big);    
				Typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);    
				sp_area_type.setAdapter(Typeadapter);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("Exception", e.toString());
				e.printStackTrace();
			}
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
		rl_area_start_time=(RelativeLayout) getActivity().findViewById(R.id.rl_area_start_time);
		tv_area_start_time=(TextView) getActivity().findViewById(R.id.tv_area_start_time);
		rl_area_end_time=(RelativeLayout) getActivity().findViewById(R.id.rl_area_end_time);
		tv_area_end_time=(TextView) getActivity().findViewById(R.id.tv_area_end_time);
		lv_area_list=(ListView) getActivity().findViewById(R.id.lv_area_list);
		bt_area_inquire=(Button) getActivity().findViewById(R.id.bt_area_inquire);
		sp_area_type=(Spinner) getActivity().findViewById(R.id.sp_area_type);
		rl_area_type=(RelativeLayout) getActivity().findViewById(R.id.rl_area_type);
		
		rl_area_start_time.setOnClickListener(this);
		rl_area_end_time.setOnClickListener(this);
		bt_area_inquire.setOnClickListener(this);
		rl_area_type.setVisibility(View.GONE);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_area_start_time:
			setTimeDialog(tv_area_start_time.getText().toString(),tv_area_start_time);
			break;
		case R.id.rl_area_end_time:
			setTimeDialog(tv_area_end_time.getText().toString(),tv_area_end_time);
			break;
		case R.id.bt_area_inquire:
			String start_time = tv_area_start_time.getText().toString();
			String end_time = tv_area_end_time.getText().toString();
			sendMsg(1,null);
			sendMsg(2,null);
			//model.Area(start_time, end_time, new Area());
			netWorkRequest(start_time, end_time);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 
	  * @方法名： setTimeDialog
	  * @功能描述:日期选择
	  * @param str
	  * @param text void
	 */
	private void setTimeDialog(String str,final TextView text) {
		View view = View.inflate(getActivity(), R.layout.time, null);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.dp_date);

			try {
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				outputFormat.parse(str);//非常重要
				Calendar c = outputFormat.getCalendar();
				datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH), null);
				
			} catch (Exception e) {
				
			}

		// Build DateTimeDialog  
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

	


}