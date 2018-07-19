package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kas.clientservice.haiyansmartenforce.Entity.Big;
import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Totality;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
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

import static com.tianditu.maps.VecMapView.TAG;

public class Vp_Totality_Fragment extends Fragment implements View.OnClickListener {
	RelativeLayout rl_totality_start_time;
	TextView tv_totality_start_time;
	RelativeLayout rl_totality_end_time;
	TextView tv_totality_end_time;
	ListView lv_totality_list;
	TextView totality_num;
	LinearLayout ll_totality_num;
	Button bt_totality_inquire;
	List<Vp_Totality> list;
	String start_time;
	String end_time;
	Spinner sp_totality_type;
	RelativeLayout rl_totality_type;
	List<Big> big;
	ArrayAdapter<Big> Typeadapter;
	int bigid;
	private Context mContext;
	public static Context mContext2;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog = ProgressDialog.show(getActivity(), "",
						"", true, true);
				progressDialog.setCanceledOnTouchOutside(false);
				break;
			case 2:
				progressDialog.setMessage("正在加载");
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.totality_fragment, null);
		mContext = getActivity();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initRes();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		String sdate = "2000-1-1";
		tv_totality_start_time.setText(sdate);
		tv_totality_end_time.setText(time);
		sendMsg(1, null);
		sendMsg(2, null);
		// model=new ToolModel(getActivity());
		// model.Totality(tv_totality_start_time.getText().toString(), time, new
		// Totality());
		// model.getBig(new getBig());
		netWorkRequest(tv_totality_start_time.getText().toString(), time);
		netWorkRequest2();
//		lv_totality_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//									int position, long id) {
//				String actName = list.get(position).getActName();
//				int actID = list.get(position).getActID();
//				Intent intent = new Intent(getActivity(), Totality_Item.class);
//				intent.putExtra("actName", actName);
//				intent.putExtra("actID", actID);
//				intent.putExtra("start_time", tv_totality_start_time.getText()
//						.toString());
//				intent.putExtra("end_time", tv_totality_end_time.getText()
//						.toString());
//				startActivity(intent);
//			}
//		});
		initMpChart();
	}

	protected String[] mParties = new String[] { "Party A", "Party B",
			"Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
			"Party I", "Party J", "Party K", "Party L", "Party M", "Party N",
			"Party O", "Party P", "Party Q", "Party R", "Party S", "Party T",
			"Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z" };

	private PieChart mChart;
	private Typeface tf;
	public void initMpChart() {
		mChart = (PieChart) getActivity().findViewById(R.id.Piechart);
		mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        //tf = Typeface.createFromAsset(getResources().getAssets(), "OpenSans-Regular.ttf");

       // mChart.setCenterTextTypeface(Typeface.createFromAsset(getResources().getAssets(), "OpenSans-Regular.ttf"));
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //mChart.setOnChartValueSelectedListener(this);

       

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
	}

	private void setData(int num) {
		if(list==null||list.size()==0){
			return;
		}
        int mult = num;
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < list.size() ; i++) {
        	 float bb=(float) (list.get(i).getCaseNum()/mult);
           yVals1.add(new Entry((float) ((list.get(i).getCaseNum()/mult)+mult/list.size()), i));
        }
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i <list.size(); i++)
            xVals.add(list.get(i).getActName());
        
        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

	
	private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
	
	 
	public static Handler mHandlerBrck;

	private void netWorkRequest(String startTime, String endTime) {
//		OkHttpUtils.Totality(startTime, endTime, mHandlerBrck,
//				Constant.Totality);
		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/ActionCaseStatistics.ashx")
				.addParams("startTime", startTime)
				.addParams("endTime", endTime)
				.addParams("UserID", UserSingleton.USERINFO.getName().UserID)
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				progressDialog.dismiss();
				Log.i(TAG, "onError: 总体"+e.toString());
			}

			@Override
			public void onResponse(String response, int id) {
				Log.i(TAG, "onResponse: 总体"+response);
				list = new ArrayList<Vp_Totality>();
				int num = 0;
				try {
					JSONObject object = new JSONObject(response.toString());
					JSONArray array = object.getJSONArray("KS");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object2 = array.getJSONObject(i);
						String name = object2.getString("actName");
						int caseNum = object2.getInt("caseNum");
						int actId = object2.getInt("actID");
						num += caseNum;
						Vp_Totality totality = new Vp_Totality(name, caseNum, actId);
						list.add(totality);
					}
					if (list.size() == 0) {
						Toast.makeText(getActivity(), "没有相关信息", Toast.LENGTH_SHORT)
								.show();
						progressDialog.dismiss();
						return;
					}
					if (num == 0) {
						ll_totality_num.setVisibility(View.GONE);
					} else {
						//ll_totality_num.setVisibility(View.VISIBLE);
						totality_num.setText(num + "");
					}
					setData(num);
					Totality_Adapter adapter = new Totality_Adapter(getActivity(), list);
					lv_totality_list.setAdapter(adapter);
					progressDialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void Date(String response) {

		list = new ArrayList<Vp_Totality>();
		int num = 0;
		try {
			JSONObject object = new JSONObject(response.toString());
			JSONArray array = object.getJSONArray("KS");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object2 = array.getJSONObject(i);
				String name = object2.getString("actName");
				int caseNum = object2.getInt("caseNum");
				int actId = object2.getInt("actID");
				num += caseNum;
				Vp_Totality totality = new Vp_Totality(name, caseNum, actId);
				list.add(totality);
			}
			if (list.size() == 0) {
				Toast.makeText(getActivity(), "没有相关信息", Toast.LENGTH_SHORT)
						.show();
				progressDialog.dismiss();
				return;
			}
			if (num == 0) {
				ll_totality_num.setVisibility(View.GONE);
			} else {
				//ll_totality_num.setVisibility(View.VISIBLE);
				totality_num.setText(num + "");
			}
			setData(num);
			Totality_Adapter adapter = new Totality_Adapter(getActivity(), list);
			lv_totality_list.setAdapter(adapter);
			progressDialog.dismiss();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void netWorkRequest2() {
		Date2();
	}

	private void Date2() {
		big = new ArrayList<Big>();
		// Big mbig=new Big("-----请选择-----",0);
		// big.add(mbig);
		OkHttpUtils.get().url(RequestUrl.baseUrl_leader+"Use/Ashx/Getfirstlevel.ashx")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				progressDialog.dismiss();
				Log.i(TAG, "onError: 总体大类" + e.toString());
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
					Typeadapter = new ArrayAdapter<Big>(mContext,
							android.R.layout.simple_spinner_item, big);
					Typeadapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp_totality_type.setAdapter(Typeadapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
//		try {
//			JSONObject object = new JSONObject(response.toString());
//			JSONArray jsonArray = object.getJSONArray("KS");
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				int id = jsonObject.getInt("id");
//				String name = jsonObject.getString("name");
//				Big mybig = new Big(name, id);
//				big.add(mybig);
//			}
//			try {
//				Typeadapter = new ArrayAdapter<Big>(mContext,
//						android.R.layout.simple_spinner_item, big);
//				Typeadapter
//						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				sp_totality_type.setAdapter(Typeadapter);
//			} catch (Exception e) {
//				// TODO: handle exception
//				Log.e("Exception", e.toString());
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}


	private void initRes() {
		rl_totality_start_time = (RelativeLayout) getActivity().findViewById(
				R.id.rl_totality_start_time);
		tv_totality_start_time = (TextView) getActivity().findViewById(
				R.id.tv_totality_start_time);
		rl_totality_end_time = (RelativeLayout) getActivity().findViewById(
				R.id.rl_totality_end_time);
		tv_totality_end_time = (TextView) getActivity().findViewById(
				R.id.tv_totality_end_time);
		lv_totality_list = (ListView) getActivity().findViewById(
				R.id.lv_totality_list);
		totality_num = (TextView) getActivity().findViewById(R.id.totality_num);
		bt_totality_inquire = (Button) getActivity().findViewById(
				R.id.bt_totality_inquire);
		ll_totality_num = (LinearLayout) getActivity().findViewById(
				R.id.ll_totality_num);
		sp_totality_type = (Spinner) getActivity().findViewById(
				R.id.sp_totality_type);
		rl_totality_type = (RelativeLayout) getActivity().findViewById(
				R.id.rl_totality_type);
		rl_totality_start_time.setOnClickListener(this);
		rl_totality_end_time.setOnClickListener(this);
		bt_totality_inquire.setOnClickListener(this);
		rl_totality_type.setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_totality_start_time:
			setTimeDialog(tv_totality_start_time.getText().toString(),
					tv_totality_start_time);
			break;
		case R.id.rl_totality_end_time:
			setTimeDialog(tv_totality_end_time.getText().toString(),
					tv_totality_end_time);
			break;
		case R.id.bt_totality_inquire:
			start_time = tv_totality_start_time.getText().toString();
			end_time = tv_totality_end_time.getText().toString();
			sendMsg(1, null);
			sendMsg(2, null);
			if(list!=null&&list.size()>0){
				list.clear();
				mChart.invalidate();
			}
			netWorkRequest(start_time, end_time);
			// model.Totality(start_time, end_time, new Totality());
			break;
		default:
			break;
		}
	}

	public void sendMsg(int flag, String content) {
		Message msg = new Message();
		msg.what = flag;
		msg.obj = content;
		handler.sendMessage(msg);
	}

	private void setTimeDialog(String str, final TextView text) {
		View view = View.inflate(getActivity(), R.layout.time, null);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.dp_date);
		try {
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			outputFormat.parse(str);// 非常重要
			Calendar c = outputFormat.getCalendar();
			datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH), null);

		} catch (Exception e) {

		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle("日期选择");
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						text.setText(datePicker.getYear() + "-"
								+ (datePicker.getMonth() + 1) + "-"
								+ datePicker.getDayOfMonth() + "");
					}
				});
		builder.show();
	}

}