package com.kas.clientservice.haiyansmartenforce.Module.CauseSearch;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.ActionList;
import com.kas.clientservice.haiyansmartenforce.Entity.Big;
import com.kas.clientservice.haiyansmartenforce.Entity.Small;
import com.kas.clientservice.haiyansmartenforce.Entity.Son;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class Cause_Query extends BaseActivity implements View.OnClickListener {

	ImageView title_return_task;
	TextView title_name_task;
	Spinner sp_causer_query_big;
	Spinner sp_causer_query_Small;
	Spinner sp_causer_query_son;
	EditText et_causer_query_act;
	Button bt_causer_query;
	ListView lv_case_query;
	RelativeLayout rl_cause_query_act;
	List<Big> big;
	ArrayAdapter<Big> Typeadapter;
	int bigid;
	List<Small> small;
	ArrayAdapter<Small> smalladapter;
	int smallid;
	List<Son> son;
	ArrayAdapter<Son> sonadapter;
	int sonid;
	int num = 0;
	String act;
	List<ActionList> list;
	MyApplication appIP;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog = ProgressDialog.show(Cause_Query.this, "请求网络",
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
		return R.layout.cause_query;
	}

	@Override
	protected String getTAG() {
		return this.toString();
	}

	@Override
	protected void initResAndListener() {
		super.initResAndListener();
		appIP=(MyApplication) getApplication();
		initRes();
		title_name_task.setText("案由查询");
		sendMsg(1,null);
		sendMsg(2,null);
		getBig();
		//model.getBig(new getBig());
		lv_case_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				int actionID = list.get(position).getActionID();
//				Intent intent=new Intent(Cause_Query.this, ActList.class);
//				intent.putExtra("actionID", actionID);
//				startActivity(intent);
			}
		});

		sp_causer_query_big.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent,
									   View view, int position, long id) {
				// TODO Auto-generated method stub
				Big item = Typeadapter.getItem(position);
				String name = item.getName();
				bigid = item.getId();
				if (bigid!=-1) {
					getSmall(bigid);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}


	
	public void sendMsg(int flag, String content) {
		Message msg = new Message();
		msg.what = flag;
		msg.obj = content;
		handler.sendMessage(msg); 
	}	

	private void initRes() {
		title_return_task=(ImageView) findViewById(R.id.iv_heaer_back);
		title_name_task=(TextView) findViewById(R.id.tv_header_title);
		sp_causer_query_big=(Spinner) findViewById(R.id.sp_causer_query_big);
		sp_causer_query_Small=(Spinner) findViewById(R.id.sp_causer_query_Small);
		sp_causer_query_son=(Spinner) findViewById(R.id.sp_causer_query_son);
		et_causer_query_act=(EditText) findViewById(R.id.et_causer_query_act);
		bt_causer_query=(Button) findViewById(R.id.bt_causer_query);
		lv_case_query=(ListView) findViewById(R.id.lv_case_query);
		rl_cause_query_act=(RelativeLayout) findViewById(R.id.rl_cause_query_act);
		title_return_task.setOnClickListener(this);
		bt_causer_query.setOnClickListener(this);
	}

	public void getBig(){
		OkHttpUtils.get().url(RequestUrl.baseUrl_leader+"/Use/Ashx/Getfirstlevel.ashx")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
				showNetErrorToast();
			}

			@Override
			public void onResponse(String response, int j) {
				Log.i(TAG, "onResponse: "+response);
				big=new ArrayList<Big>();
				Big mbig=new Big("-----请选择-----",0);
				big.add(mbig);
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
					Typeadapter = new ArrayAdapter<Big>(Cause_Query.this,android.R.layout.simple_spinner_item, big);
					Typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_causer_query_big.setAdapter(Typeadapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});


	}
	
	public void getSmall(int smbigid){
		OkHttpUtils.get().url(RequestUrl.baseUrl_leader+"/Use/Ashx/GetSecondLevel.ashx")
				.addParams("firstlevelid",smbigid+"")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
				showNetErrorToast();
			}

			@Override
			public void onResponse(String response, int j) {
				Log.i(TAG, "onResponse: "+response);
				small=new ArrayList<Small>();
				Small sm=new Small("-----请选择-----",0);
				small.add(sm);
				try {
					JSONObject object = new JSONObject(response.toString());
					JSONArray jsonArray = object.getJSONArray("KS");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int id = jsonObject.getInt("id");
						String name = jsonObject.getString("name");
						Small mysmall=new Small(name,id);
						small.add(mysmall);
					}
					smalladapter = new ArrayAdapter<Small>(Cause_Query.this,android.R.layout.simple_spinner_item, small);
					smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_causer_query_Small.setAdapter(smalladapter);
					sp_causer_query_Small.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
												   View view, int position, long id) {
							Small item = smalladapter.getItem(position);
							String name = item.getName();
							smallid = item.getId();
							//model.Son(smallid, new getSon());
							Son(smallid);
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {

						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
    private void Son(int smallid) {
		OkHttpUtils.get().url(RequestUrl.baseUrl_leader+"/Use/Ashx/GetThirdLevel.ashx")
				.addParams("Secondlevelid",smallid+"")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
			}

			@Override
			public void onResponse(String response, int j) {
				Log.i(TAG, "onResponse: "+response);
				son=new ArrayList<Son>();
				Son so=new Son("-----请选择-----",0);
				son.add(so);
				try {
					JSONObject object = new JSONObject(response.toString());
					JSONArray jsonArray = object.getJSONArray("KS");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int id = jsonObject.getInt("id");
						String name = jsonObject.getString("name");
						Son myso=new Son(name,id);
						son.add(myso);
					}
					sonadapter = new ArrayAdapter<Son>(Cause_Query.this,android.R.layout.simple_spinner_item, son);
					sonadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_causer_query_son.setAdapter(sonadapter);
					progressDialog.dismiss();
					sp_causer_query_son.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
												   View view, int position, long id) {
							// TODO Auto-generated method stub
							Son item = sonadapter.getItem(position);
							String name = item.getName();
							sonid = item.getId();

						}
						@Override
						public void onNothingSelected(AdapterView<?> parent) {

						}
					});

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_heaer_back:
			finish();
			break;
		case R.id.bt_causer_query:
			act = et_causer_query_act.getText().toString();
			if (sonid>=1) {
				String a=String.valueOf(sonid);
				String son="3"+a;
				num=Integer.parseInt(son);
			}else if (smallid>=1) {
				String b=String.valueOf(smallid);
				String small="2"+b;
				num=Integer.parseInt(small);
			}else if (bigid>=1) {
				String c=String.valueOf(bigid);
				String big="1"+c;
				num=Integer.parseInt(big);
			}
			if(num==0&&act.equals("")){
				Toast.makeText(Cause_Query.this, "必须输入一个条件", Toast.LENGTH_SHORT).show();
				return;
			}
			//model.getActionList(num, act, new getActList());
			sendMsg(1, "");
			sendMsg(2, "");
			Log.e("num",num+"");
			getActionList(num+"",act);
			break;
		default:
			break;
		}
	}
	
    public void getActionList(String actNum,String act){
		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"/Mobile/getactionlist.ashx")
				.addParams("QueryID",actNum)
				.addParams("Act",act)
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
			}

			@Override
			public void onResponse(String response, int id) {
				list=new ArrayList<ActionList>();

				try {
					JSONObject object = new JSONObject(response.toString());
					JSONArray jsonArray = object.getJSONArray("KS");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int ActionID = jsonObject.getInt("ActionID");
						String ActCaA = jsonObject.getString("ActCaA");
						//int CodeCaA = jsonObject.getInt("CodeCaA");
						ActionList act=new ActionList(ActionID, ActCaA, 0);
						list.add(act);
					}
					if (list.size()<=0) {
						Toast.makeText(Cause_Query.this, "没有相关信息", Toast.LENGTH_SHORT).show();
					}
					ActListAdapter adapter=new ActListAdapter(Cause_Query.this, list);
					lv_case_query.setAdapter(adapter);
					ListViewFitParent.setListViewHeightBasedOnChildren(lv_case_query);
					progressDialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

    }
    
}
