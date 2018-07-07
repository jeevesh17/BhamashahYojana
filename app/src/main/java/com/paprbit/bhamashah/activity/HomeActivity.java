package com.paprbit.bhamashah.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paprbit.bhamashah.R;
import com.paprbit.bhamashah.net.model.Member;
import com.paprbit.bhamashah.net.retrofit.ServiceGenerator;
import com.paprbit.bhamashah.util.LocaleHelper;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Bind(R.id.slider)
    SliderLayout mDemoSlider;
    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.parentLayout)
    LinearLayout parentLayout;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.tv_inputbox_title)
    TextView tvInputBoxTitle;
    @Bind(R.id.progressBar)
    ProgressBar pb;
    Gson gson;
    boolean tempDisable;


    public static final int ACTION_GET_ECARD = 0;
    public static final int ACTION_GET_HOF = 1;
    public static final int ACTION_GET_FAMILY = 2;
    public static final int ACTION_GET_ACCOUNT = 3;

    public static int ACTION = ACTION_GET_ECARD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_home));
        gson = new Gson();
        btn_submit.setOnClickListener(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put(getString(R.string.innogration), R.drawable.bhamashah_00);
        file_maps.put(getString(R.string.launch), R.drawable.bhamashah01);
        file_maps.put(getString(R.string.bhamashah_family), R.drawable.bhamasha02);
        file_maps.put(getString(R.string.rajasthan), R.drawable.bhamashah03);
        file_maps.put(getString(R.string.bhamashah_yojna), R.drawable.bhamashah04);
        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        setEditorFilter();
    }

    private void setEditorFilter() {
        etId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    tempDisable = true;
                } else {
                    tempDisable = false;
                }
                return false;
            }
        });

        etId.addTextChangedListener(new TextWatcher() {
            int lengthBefore = 0;
            int lengthAfter = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lengthBefore = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etId.getText().length() == 0) {
                    return;
                }
                boolean startsWithInt = true;
                try {
                    Integer.parseInt(etId.getText().toString().charAt(0) + "");
                } catch (Exception e) {
                    //means doesn't starts with int
                    startsWithInt = false;
                }

                int maxLength = 0;
                if (startsWithInt) {
                    //ie this is bhamashah ack id not bhamashah family id
                    if ((etId.getText().length() == 4 || etId.getText().length() == 9) && !tempDisable) {
                        etId.append("-");
                    }
                    maxLength = 15;
                } else {
                    //family id's length is WDBFUUK, 7 char
                    maxLength = 7;
                }
                InputFilter[] FilterArray = new InputFilter[2];
                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                FilterArray[1] = new InputFilter.AllCaps();
                etId.setFilters(FilterArray);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                lengthAfter = editable.length();
                if (lengthAfter > lengthBefore) {
                    tempDisable = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_language) {
            showChangeLangDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bhamashah_ecard) {
            ACTION = ACTION_GET_ECARD;
            tvInputBoxTitle.setText(R.string.get_bhamashah_ecard);
        } else if (id == R.id.nav_hof_details) {
            ACTION = ACTION_GET_HOF;
            tvInputBoxTitle.setText(R.string.inputbox_title_hof);
        } else if (id == R.id.nav_family_details) {
            ACTION = ACTION_GET_FAMILY;
            tvInputBoxTitle.setText(R.string.inputbox_title_family);
        } else if (id == R.id.nav_account_details) {
            ACTION = ACTION_GET_ACCOUNT;
            tvInputBoxTitle.setText(R.string.inputbox_title_account);
        } else if (id == R.id.nav_exit) {
            this.finish();
        } else if (id == R.id.nav_bhamashah_yojna) {
            startActivity(new Intent(this,BhamashahInfo.class));
        } else if (id == R.id.nav_nearby_hospitals) {
            startActivity(new Intent(this,HospitalActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ACTION == ACTION_GET_ECARD) {
            tvInputBoxTitle.setText(R.string.get_bhamashah_ecard);
        } else if (ACTION == ACTION_GET_HOF) {
            tvInputBoxTitle.setText(R.string.inputbox_title_hof);
        } else if (ACTION == ACTION_GET_FAMILY) {
            tvInputBoxTitle.setText(R.string.inputbox_title_family);
        } else if (ACTION == ACTION_GET_ACCOUNT) {
            tvInputBoxTitle.setText(R.string.inputbox_title_account);
        }
    }

    //section for account details start
    private void getAccountDetails() {
        if (etId.getText().length() < 7) {
            Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_info), Toast.LENGTH_SHORT).show();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        if (etId.getText().length() > 7) {
            //first fetch family id from the ack id
            Call<ResponseBody> call = ServiceGenerator.getService().getBhamashahFamilyIdFromAckId(etId.getText().toString());
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
                        Log.d("success", res);
                        try {
                            JSONObject jobject = new JSONObject(res);
                            JSONObject hofObject = (JSONObject) jobject.get("hof_Details");
                            String bhamashah_family_id = hofObject.getString("FAMILYIDNO");
                            nowGetAccountDetails(bhamashah_family_id);
                        } catch (JSONException e) {
                            Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG)
                                    .setAction(getString(R.string.ok), null).show();
                            e.printStackTrace();
                        }

                    } else {
                        Log.d("error", response.errorBody().toString());
                        Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.ok), null).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("failure", t.getLocalizedMessage());
                    Snackbar.make(parentLayout, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok), null).show();
                }
            });
        } else {
            //we have family id
            nowGetAccountDetails(etId.getText().toString());
        }

    }

    private void nowGetAccountDetails(String bhamashahFamilyId) {
        Call<ResponseBody> call = ServiceGenerator.getService().getHofAndFamilyEnglishFullDetails(bhamashahFamilyId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                pb.setVisibility(View.GONE);
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
                        String ifsc = hofObject.getString("IFSC_CODE");
                        Snackbar s = Snackbar.make(parentLayout, getString(R.string.your_acc_is) + " : " + accno + "\n" + getString(R.string.your_ifsc_is) + " : " + ifsc, Snackbar.LENGTH_INDEFINITE);
                        s.setAction(getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    } catch (JSONException e) {
                        Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                        e.printStackTrace();
                    }

                } else {
                    try {
                        Log.d("error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(parentLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok), null).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                pb.setVisibility(View.GONE);
                Log.d("failure", t.getLocalizedMessage());
                Snackbar.make(parentLayout, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.ok), null).show();
            }
        });
    }
    //section for account details end


    //hof section start
    private void getHOFDetails() {
        if (etId.getText().length() < 7) {
            Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_info), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, HOFDetails.class);
        intent.putExtra("id", etId.getText().toString());
        startActivity(intent);
    }
    //hof section end

    //ecard section start
    private void getEcard() {
        if (etId.getText().length() < 7) {
            Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_info), Toast.LENGTH_SHORT).show();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ServiceGenerator.getService().getBhamashahHofAndFamilyEngHindiDetailsMedium(etId.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                pb.setVisibility(View.GONE);
                if (response.isSuccess()) {
                    String family = null;
                    Member hof = null;
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        family = json.getString("family_Details");
                        hof = gson.fromJson(json.getString("hof_Details"), Member.class);
                        ArrayList<Member> members = gson.fromJson(family, new TypeToken<List<Member>>() {
                        }.getType());
                        Intent intent = new Intent(HomeActivity.this, ECardActivity.class);
                        intent.putExtra("members", members);
                        intent.putExtra("hof", hof);
                        startActivity(intent);
                    } catch (Exception e) {
                        Snackbar.make(parentLayout,e.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.ok), null).show();
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
    //ecard section end

    //getfamily section start
    private void getFamilyDetails() {
        if (etId.getText().length() < 7) {
            Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_info), Toast.LENGTH_SHORT).show();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = ServiceGenerator.getService().getBhamashahHofAndFamilyEngHindiDetailsMedium(etId.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                pb.setVisibility(View.GONE);
                if (response.isSuccess()) {
                    String family = null;
                    try {
                        family = new JSONObject(response.body().string()).getString("family_Details");
                        ArrayList<Member> members = gson.fromJson(family, new TypeToken<List<Member>>() {
                        }.getType());
                        Intent intent = new Intent(HomeActivity.this, FamilyDetails.class);
                        intent.putExtra("members", members);
                        startActivity(intent);
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
                pb.setVisibility(View.GONE);
                Snackbar.make(parentLayout, getString(R.string.network_error), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.ok), null).show();
            }
        });


    }
    //getfamily section end

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            if (ACTION == ACTION_GET_ACCOUNT) {
                getAccountDetails();
            } else if (ACTION == ACTION_GET_HOF) {
                getHOFDetails();
            } else if (ACTION == ACTION_GET_FAMILY) {
                getFamilyDetails();
            } else if (ACTION == ACTION_GET_ECARD) {
                getEcard();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (pb != null && pb.getVisibility() == View.VISIBLE) {
            pb.setVisibility(View.GONE);
        }
    }


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner_lang);
        //final ListView lv = (ListView) dialogView.findViewById(R.id.list);
        dialogBuilder.setTitle(getResources().getString(R.string.lang_dialog_title));
        dialogBuilder.setMessage(getResources().getString(R.string.lang_dialog_message));
        dialogBuilder.setPositiveButton(getString(R.string.change_lang), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                //int langpos = lv.getSelectedItemPosition();
                switch (langpos) {
                    case 0: //English
                        setLang("en");
                        return;
                    case 1: //hindi
                        setLang("hi");
                        return;
                    default: //By default set to english
                        setLang("en");
                        return;
                }
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel_lang_change, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        String lang = LocaleHelper.getLanguage(HomeActivity.this);
        switch (lang) {
            case "en":
                spinner1.setSelection(0);
                break;
            case "hi":
                spinner1.setSelection(1);
                break;
        }

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void setLang(String langcode) {
        LocaleHelper.setLocale(this, langcode);
        HomeActivity.this.recreate();
        Toast.makeText(getApplicationContext(), langcode, Toast.LENGTH_SHORT).show();

    }
}
