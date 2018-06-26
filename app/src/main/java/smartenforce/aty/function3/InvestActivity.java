package smartenforce.aty.function3;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.ShowTitleActivity;
import smartenforce.frament.InvestCaseFragment;
import smartenforce.frament.InvestSourceFragment;

public class InvestActivity extends ShowTitleActivity implements RadioGroup.OnCheckedChangeListener {
    private LinearLayout llt_container;
    private RadioGroup rg;
    private RadioButton rbtn_source, rbtn_case;
    private InvestSourceFragment sourceFragment;
    private FragmentManager fragmentManager;
    private InvestCaseFragment caseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_invest);
    }

    @Override
    protected void findViews() {
        llt_container = (LinearLayout) findViewById(R.id.llt_container);
        rg = (RadioGroup) findViewById(R.id.rg);
        rbtn_source = (RadioButton) findViewById(R.id.rbtn_source);
        rbtn_case = (RadioButton) findViewById(R.id.rbtn_case);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("调查取证");
        fragmentManager = getSupportFragmentManager();
        rg.setOnCheckedChangeListener(this);
        rbtn_source.performClick();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rbtn_source) {
            if (sourceFragment == null) {
                sourceFragment = new InvestSourceFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.llt_container, sourceFragment).commit();
        } else if (checkedId == R.id.rbtn_case) {
            if (caseFragment == null) {
                caseFragment = new InvestCaseFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.llt_container, caseFragment).commit();
        }

    }
}




















