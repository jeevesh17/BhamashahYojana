package com.paprbit.bhamashah.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paprbit.bhamashah.R;
import com.paprbit.bhamashah.net.model.Member;
import com.paprbit.bhamashah.util.LocaleHelper;
import com.paprbit.bhamashah.widget.MemberLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ankush38u on 3/20/2017.
 */

public class FragmentECardBack extends Fragment {
    Gson gson;
    @Bind(R.id.ll_members)
    LinearLayout llMmebers;
    View viewBg;
    List<Member> memberList;
    String lang;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.ss)
    RelativeLayout ss;

    public FragmentECardBack() {
    }

    @SuppressLint("ValidFragment")
    public FragmentECardBack(List<Member> list) {
        memberList = list;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ecard_back, container, false);
        ButterKnife.bind(this, v);
        gson = new Gson();
        return v;
    }


    public void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Bhamashah/" + "Bhamashah_back_" + now + ".jpg";

            // create bitmap screen capture
            View v1 = ss;
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);
            File temp = new File(Environment.getExternalStorageDirectory().toString() + "/Bhamashah");
            if (!temp.exists()) {
                temp.mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lang = LocaleHelper.getLanguage(getActivity());
        viewBg = new View(getActivity());
        viewBg.setBackgroundColor(Color.BLACK);
        llMmebers.addView(viewBg, LinearLayout.LayoutParams.MATCH_PARENT, 2);
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            llMmebers.addView(new MemberLayout(getActivity()).setText(String.valueOf(i + 1),
                    member.getNAME(lang), member.getGENDER(lang), member.getDOB(), member.getAADHAR_ID()));
            viewBg = new View(getActivity());
            viewBg.setBackgroundColor(Color.BLACK);
            llMmebers.addView(viewBg, LinearLayout.LayoutParams.MATCH_PARENT, 2);
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        tvDate.setText(sdf.format(date));
    }
}
