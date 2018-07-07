package com.paprbit.bhamashah.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paprbit.bhamashah.R;

import butterknife.ButterKnife;

/**
 * Created by ankush38u on 3/20/2017.
 */

public class FragmentBhamashahInfo extends Fragment {
    String html;

    public FragmentBhamashahInfo() {
    }

    @SuppressLint("ValidFragment")
    public FragmentBhamashahInfo(String html) {
        this.html = html;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bhamashah_info, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = (TextView) view.findViewById(R.id.section_label);
        tv.setText(Html.fromHtml(html));

    }
}
