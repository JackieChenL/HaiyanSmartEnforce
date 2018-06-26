package smartenforce.aty.function5;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.ShowTitleActivity;
import smartenforce.frament.EnforceListFragment;
import smartenforce.frament.GoodsListFragment;

public class EnforceActivity extends ShowTitleActivity implements RadioGroup.OnCheckedChangeListener {
    private LinearLayout llt_container;
    private RadioGroup rg;
    private RadioButton rbtn_enforce_list, rbtn_goods_list;
    private FragmentManager fragmentManager;
    private EnforceListFragment enforceListFragment;
    private GoodsListFragment goodsListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_enforce);
    }

    @Override
    protected void findViews() {
        llt_container = (LinearLayout) findViewById(R.id.llt_container);
        rg = (RadioGroup) findViewById(R.id.rg);
        rbtn_enforce_list = (RadioButton) findViewById(R.id.rbtn_enforce_list);
        rbtn_goods_list = (RadioButton) findViewById(R.id.rbtn_goods_list);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("涉案财物");
        fragmentManager = getSupportFragmentManager();
        rg.setOnCheckedChangeListener(this);
        rbtn_enforce_list.performClick();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rbtn_enforce_list) {
            if (enforceListFragment == null) {
                enforceListFragment = new EnforceListFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.llt_container, enforceListFragment).commit();
        } else if (checkedId == R.id.rbtn_goods_list) {
            if (goodsListFragment == null) {
                goodsListFragment = new GoodsListFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.llt_container, goodsListFragment).commit();
        }

    }




}




















