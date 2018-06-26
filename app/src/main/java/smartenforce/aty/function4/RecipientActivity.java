package smartenforce.aty.function4;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.ShowTitleActivity;
import smartenforce.frament.CitizenFragment;
import smartenforce.frament.EnterPriseFragment;


//服务对象
public class RecipientActivity extends ShowTitleActivity implements RadioGroup.OnCheckedChangeListener {
    private LinearLayout llt_container;
    private RadioGroup rg;
    private RadioButton rbtn_entertrise, rbtn_citizen;
    private EnterPriseFragment enterPriseFragment;
    private FragmentManager fragmentManager;
    private CitizenFragment citizenFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recipient);
    }

    @Override
    protected void findViews() {
        llt_container = (LinearLayout) findViewById(R.id.llt_container);
        rg = (RadioGroup) findViewById(R.id.rg);
        rbtn_entertrise = (RadioButton) findViewById(R.id.rbtn_entertrise);
        rbtn_citizen = (RadioButton) findViewById(R.id.rbtn_citizen);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("服务对象");
        fragmentManager = getSupportFragmentManager();
        rg.setOnCheckedChangeListener(this);
        rbtn_entertrise.performClick();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rbtn_entertrise) {
            if (enterPriseFragment == null) {
                enterPriseFragment = new EnterPriseFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.llt_container, enterPriseFragment).commit();
        } else if (checkedId == R.id.rbtn_citizen) {
            if (citizenFragment == null) {
                citizenFragment = new CitizenFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.llt_container, citizenFragment).commit();
        }

    }
}




















