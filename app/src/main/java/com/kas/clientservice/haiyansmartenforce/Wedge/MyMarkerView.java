package com.kas.clientservice.haiyansmartenforce.Wedge;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.kas.clientservice.haiyansmartenforce.R;

public class MyMarkerView extends MarkerView {
    private TextView tvContent;
	public MyMarkerView(Context context, int layoutResource) {
		super(context, layoutResource);
		// TODO Auto-generated constructor stub
		tvContent = (TextView) findViewById(R.id.tvContent);
	}

	@Override
	public void refreshContent(Entry e, Highlight highlight) {
		// TODO Auto-generated method stub
		if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
        }

	}

	public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }	 
}