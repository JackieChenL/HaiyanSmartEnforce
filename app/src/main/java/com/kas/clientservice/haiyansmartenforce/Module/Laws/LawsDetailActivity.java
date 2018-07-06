package com.kas.clientservice.haiyansmartenforce.Module.Laws;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class LawsDetailActivity extends BaseActivity implements View.OnClickListener{
	TextView title_return_task;
	TextView title_name_task;
	TextView tv_LawsDetail;
	LawsBean bean;

	@BindView(R.id.tv_header_title)
	TextView tv_title;
	@BindView(R.id.iv_heaer_back)
	ImageView iv_back;
	@Override
	protected int getLayoutId() {
		return R.layout.lawsdetail_activity;
	}

	@Override
	protected String getTAG() {
		return this.toString();
	}

	@Override
	protected void initResAndListener() {
		super.initResAndListener();
		bean=(LawsBean)getIntent().getSerializableExtra("bean");
		tv_title.setText("详情");
		iv_back.setOnClickListener(this);
		tv_LawsDetail=(TextView)findViewById(R.id.tv_LawsDetail);
//		tv_title.setText(bean.getTitleReg());
    		/*String tvtext=bean.getMaintextReg().replace("<br />", "\n");
    		tvtext=tvtext.replace("<p>", "");
    		tvtext=tvtext.replace("</p>", "");
    		tvtext=tvtext.replace("<strong>", "");
    		tvtext=tvtext.replace("</strong>", "");
    		tvtext=tvtext.replace("&nbsp;", "");
    		tvtext=tvtext.replace("<hr>", "");
    		tvtext=tvtext.replace("<hr />", "");
    		tvtext=tvtext.replace("\r", "");
    		tvtext=tvtext.replace(" ", "");*/
		String tvtext=bean.getMaintextReg();
		tvtext=tvtext.replace("&nbsp;", "");
		tv_LawsDetail.setText(Html.fromHtml(tvtext));
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_heaer_back:
				finish();
				break;
		}
	}
}