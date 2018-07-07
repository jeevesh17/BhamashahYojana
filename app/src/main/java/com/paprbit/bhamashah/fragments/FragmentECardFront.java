package com.paprbit.bhamashah.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.paprbit.bhamashah.R;
import com.paprbit.bhamashah.net.model.Member;
import com.paprbit.bhamashah.net.retrofit.ServiceGenerator;
import com.paprbit.bhamashah.util.LocaleHelper;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ankush38u on 3/20/2017.
 */

public class FragmentECardFront extends Fragment {
    Gson gson;
    Member hof;
    String lang;
    @Bind(R.id.tv_family_id)
    TextView tvFamilyId;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_gender)
    TextView tvGender;
    @Bind(R.id.tv_dob)
    TextView tvdob;
    @Bind(R.id.tv_aadhar_id)
    TextView tvAadharId;
    @Bind(R.id.tv_account_no)
    TextView tvAccountNo;
    @Bind(R.id.tv_ration_card)
    TextView tvRationCard;
    @Bind(R.id.iv_hof)
    RoundedImageView profileImage;
    @Bind(R.id.parentLayout)
    LinearLayout parentLayout;
    @Bind(R.id.ss_front)
    RelativeLayout ss_front;


    public FragmentECardFront() {
    }

    @SuppressLint("ValidFragment")
    public FragmentECardFront(Member hof) {
        this.hof = hof;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ecard_front, container, false);
        ButterKnife.bind(this, v);
        gson = new Gson();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lang = LocaleHelper.getLanguage(getActivity());
        tvFamilyId.setText(hof.getFAMILYIDNO());
        tvAadharId.setText(hof.getAADHAR_ID());
        tvName.setText(hof.getNAME(lang));
        tvGender.setText(hof.getGENDER(lang));
        tvdob.setText(hof.getDOB());
        tvAccountNo.setText(hof.getACC_NO());
        tvRationCard.setText(hof.getRATION_CARD_NO());
        getImage(hof.getBHAMASHAH_ID());
        nowGetAccountDetails(hof.getFAMILYIDNO());
    }

    public void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Bhamashah/" + "Bhamashah_back_" + now + ".jpg";

            // create bitmap screen capture
            View v1 = ss_front;
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


    public void getImage(String ackId) {
        Call<ResponseBody> call = ServiceGenerator.getService().getHofPhoto(ackId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    String encodedImage = null;
                    try {
                        encodedImage = new JSONObject(new JSONObject(response.body().string()).getString("hof_Photo")).getString("PHOTO");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (encodedImage != null) {
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profileImage.setImageBitmap(decodedByte);
                    }
                } else {
                    Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.ok), null).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Snackbar.make(parentLayout, getString(R.string.network_error), Snackbar.LENGTH_LONG).setAction(getString(R.string.ok), null).show();
            }
        });

    }


    private void nowGetAccountDetails(String bhamashahFamilyId) {
        Call<ResponseBody> call = ServiceGenerator.getService().getHofAndFamilyEnglishFullDetails(bhamashahFamilyId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    String res = null;
                    try {
                        res = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jobject = new JSONObject(res);
                        JSONObject hofObject = (JSONObject) jobject.get("hof_Details");
                        String accno = hofObject.getString("ACC_NO");
                        tvAccountNo.setText(accno);

                    } catch (JSONException e) {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}
