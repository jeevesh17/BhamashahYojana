package com.paprbit.bhamashah.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.paprbit.bhamashah.R;
import com.paprbit.bhamashah.net.model.Member;
import com.paprbit.bhamashah.net.retrofit.ServiceGenerator;
import com.paprbit.bhamashah.util.LocaleHelper;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HOFDetails extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout parentLayout;
    @Bind(R.id.profile_image)
    RoundedImageView profileImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_ack_id)
    TextView tvackId;
    @Bind(R.id.tv_family_id)
    TextView tvFamilyId;
    @Bind(R.id.tv_name_hof)
    TextView tvNameHof;
    @Bind(R.id.tv_dob)
    TextView tvDob;
    @Bind(R.id.tv_aadhar_id)
    TextView tvAadharId;
    @Bind(R.id.tv_fathers_name)
    TextView tvFatherName;
    @Bind(R.id.tv_mothers_name)
    TextView tvMotherName;
    @Bind(R.id.tv_bpl_no)
    TextView tvBplNo;
    @Bind(R.id.tv_ration_card)
    TextView tvRationCard;
    @Bind(R.id.progressBar)
    ProgressBar pb;
    String lang;
    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hofdetails);
        ButterKnife.bind(this);
        gson = new Gson();
        lang = LocaleHelper.getLanguage(getApplicationContext());
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_home));
        getSupportActionBar().setTitle(getString(R.string.title_activity_home));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id");
        if (id != null) {
            pb.setVisibility(View.VISIBLE);
            getDetails(id);
        }
    }

    public void getImage(String ackId) {
        Call<ResponseBody> call = ServiceGenerator.getService().getHofPhoto(ackId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                pb.setVisibility(View.GONE);
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
                    Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok), null).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

                pb.setVisibility(View.GONE);
                Snackbar.make(parentLayout, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.ok), null).show();
            }
        });

    }

    public void getDetails(String id) {
        Call<ResponseBody> call = ServiceGenerator.getService().getBhamashahHofAndFamilyEngHindiDetailsMedium(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    String hof = null;
                    try {
                        hof = new JSONObject(response.body().string()).getString("hof_Details");
                        Member member = gson.fromJson(hof, Member.class);
                        getImage(member.getBHAMASHAH_ID());
                        tvName.setText(member.getNAME(lang));
                        tvAadharId.setText(member.getAADHAR_ID());
                        tvackId.setText(member.getBHAMASHAH_ID());
                        tvFamilyId.setText(member.getFAMILYIDNO());
                        tvNameHof.setText(member.getNAME(lang));
                        tvFatherName.setText(member.getFATHER_NAME(lang));
                        tvMotherName.setText(member.getMOTHER_NAME(lang));
                        tvBplNo.setText(member.getNFSA_BPL_NUMBER());
                        tvDob.setText(member.getDOB());
                        tvRationCard.setText(member.getRATION_CARD_NO());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok), null).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(parentLayout, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.ok), null).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pb != null && pb.getVisibility() == View.VISIBLE) {
            pb.setVisibility(View.GONE);
        }
    }
}
