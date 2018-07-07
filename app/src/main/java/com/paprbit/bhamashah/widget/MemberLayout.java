package com.paprbit.bhamashah.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paprbit.bhamashah.R;

/**
 * Created by ankush38u on 3/20/2017.
 */

public class MemberLayout extends LinearLayout {
    Context context;
    TextView tvNo;
    TextView tvName;
    TextView tvGender;
    TextView tvdob;
    TextView tvaadhar;

    public MemberLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MemberLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MemberLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MemberLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        LayoutParams lp= new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,10,0,10);
        setLayoutParams(lp);
        tvNo = new TextView(context);
        addView(tvNo, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.08f));
        tvName = new TextView(context);
        addView(tvName, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.25f));
        tvName.setGravity(Gravity.CENTER_HORIZONTAL);
        tvGender = new TextView(context);
        addView(tvGender, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.15f));
        tvGender.setGravity(Gravity.CENTER_HORIZONTAL);
        tvdob = new TextView(context);
        addView(tvdob, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.2f));
        tvdob.setGravity(Gravity.CENTER_HORIZONTAL);
        tvaadhar = new TextView(context);
        tvaadhar.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(tvaadhar, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
        setText(context.getString(R.string.serial_no), context.getString(R.string.name), context.getString(R.string.gender),
                context.getString(R.string.birthdate), context.getString(R.string.aadhar_id));
    }


    public MemberLayout setText(String sn, String name, String gender, String dob, String aadhar) {
        if (tvName != null) {
            tvNo.setText(sn);
            tvName.setText(name);
            tvGender.setText(gender);
            tvdob.setText(dob);
            tvaadhar.setText(aadhar);
        }
        return this;
    }

}
