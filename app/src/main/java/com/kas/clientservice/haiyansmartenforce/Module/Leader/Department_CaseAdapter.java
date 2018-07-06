package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.Department;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;

import java.util.List;

public class Department_CaseAdapter extends BaseAdapter {
	private Context context;
	private List<Department> list;

	private int bigid;
	private String bigName;
	private String SrartTime;
	private String EndTime;
	public Department_CaseAdapter(Context context, List<Department> list , int bigid, String bigName,
								  String SrartTime, String EndTime){
		this.context=context;
		this.list=list;
		
		this.bigid = bigid;
		this.bigName = bigName;
		this.SrartTime = SrartTime;
		this.EndTime = EndTime;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHoder hoder;
		if (view == null) {
			hoder = new ViewHoder();
			view=View.inflate(context, R.layout.department_list_item, null);
			hoder.tv_department_name=(TextView) view.findViewById(R.id.tv_department_name);
			hoder.tv_department_initiative=(TextView) view.findViewById(R.id.tv_department_initiative);
			hoder.tv_department_passivity=(TextView) view.findViewById(R.id.tv_department_passivity);
			hoder.tv_department_generic=(TextView) view.findViewById(R.id.tv_department_generic);
			hoder.tv_department_simple=(TextView) view.findViewById(R.id.tv_department_simple);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		hoder.tv_department_name.setText(list.get(position).getDepName());
		hoder.tv_department_initiative.setText(list.get(position).getInitiative()+"");
		hoder.tv_department_passivity.setText(list.get(position).getPassivity()+"");
		hoder.tv_department_generic.setText(list.get(position).getGeneric()+"");
		hoder.tv_department_simple.setText(list.get(position).getSimple()+"");
		//巡查
		hoder.tv_department_initiative.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int what = Constants.WhatDepartmentDetailsSource;
				String type = "initiative";
				startSimple(position,type,what);
			}
		});
		//线索
		hoder.tv_department_passivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int what = Constants.WhatDepartmentDetailsSource;
				String type = "passivity";
				startSimple(position,type,what);
			}
		});
		//简单
		hoder.tv_department_generic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int what = Constants.WhatDepartmentDetailsCase;
				String type = "generic";
				startSimple(position,type,what);
			}
		});
		//一般
		hoder.tv_department_simple.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int what = Constants.WhatDepartmentDetailsCase;
				String type = "simple";
				startSimple(position,type,what);
			}
		});
		return view;
	}
	private void startSimple(int position,String type,int what){
		Log.i("test","position:" + position);
		Intent intent = new Intent();
		intent.setClass(context, DetailsListActivity.class);
		intent.putExtra("what",what);
		intent.putExtra("type",type);
		intent.putExtra("DeDepartmentID",list.get(position).getDepartmentID()+"");
		intent.putExtra("firstLevelID","");
		intent.putExtra("bigName",bigName);
		intent.putExtra("StartTime",SrartTime.substring(0,SrartTime.indexOf(" ")));
		intent.putExtra("EndTime",EndTime.substring(0,EndTime.indexOf(" ")));
		context.startActivity(intent);
	}
	class ViewHoder {
		TextView tv_department_name;
		TextView tv_department_initiative;
		TextView tv_department_passivity;
		TextView tv_department_generic;
		TextView tv_department_simple;
	}

}