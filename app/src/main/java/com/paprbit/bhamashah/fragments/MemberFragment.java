package com.paprbit.bhamashah.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paprbit.bhamashah.R;
import com.paprbit.bhamashah.net.model.Member;
import com.paprbit.bhamashah.util.LocaleHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ankush38u on 3/19/2017.
 */

public class MemberFragment extends Fragment {
    Gson gson;
    @Bind(R.id.parentLayout)
    LinearLayout parentLayout;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_dob)
    TextView tvDob;
    @Bind(R.id.tv_aadhar_id)
    TextView tvAadharId;
    @Bind(R.id.tv_fathers_name)
    TextView tvFatherName;
    @Bind(R.id.tv_mothers_name)
    TextView tvMotherName;
    @Bind(R.id.tv_gender)
    TextView tvGender;
    @Bind(R.id.tv_spouse)
    TextView tvSpouse;
    @Bind(R.id.progressBar)
    ProgressBar pb;
    @Bind(R.id.ll_spouse)
    LinearLayout llspouse;
    String lang;
    Member member;

    public MemberFragment() {
    }

    @SuppressLint("ValidFragment")
    public MemberFragment(Member member) {
        this.member = member;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member, container, false);
        ButterKnife.bind(this, v);
        gson = new Gson();
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lang = LocaleHelper.getLanguage(getActivity());
        if (member != null) {
            tvName.setText(member.getNAME(lang));
            tvDob.setText(member.getDOB());
            tvAadharId.setText(member.getAADHAR_ID());
            tvGender.setText(member.getGENDER(lang));
            tvFatherName.setText(member.getFATHER_NAME(lang));
            tvMotherName.setText(member.getMOTHER_NAME(lang));
            if (member.getSPOUCE_NAME(lang) == null || member.getSPOUCE_NAME(lang).trim().equals("")) {
                llspouse.setVisibility(View.GONE);
            } else {
                tvSpouse.setText(member.getSPOUCE_NAME(lang));
            }
        }
    }
}

