package com.clique.activity;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.clique.R;
import com.clique.adapter.OrderHistoryAdapter;
import com.clique.decorators.EventDecorator;
import com.clique.decorators.OneDayDecorator;
import com.clique.materialcalendarview.CalendarDay;
import com.clique.materialcalendarview.MaterialCalendarView;
import com.clique.materialcalendarview.OnDateSelectedListener;
import com.clique.utils.CustomTextView;
import com.clique.utils.EasyPermissions;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog alertDialog;
    private ImageView ivBack;
    private CustomTextView tvDone;
    private CustomTextView tvDate;
    private CustomTextView tvTerms, tvAbout;
    private SwitchCompat scAutobuy;
    private CustomTextView tvNotification;
    private SwitchCompat swNotification;
    private FrameLayout llOrders;
    private FrameLayout llContactus;
    private MaterialCalendarView materialCalendarView;
    private EventDecorator eventDecorator;
    private String selecteddate;
    private FrameLayout flNotification;

    private void findViews() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvDone = (CustomTextView) findViewById(R.id.tv_done);
        tvDate = (CustomTextView) findViewById(R.id.tv_date);
        scAutobuy = (SwitchCompat) findViewById(R.id.sc_autobuy);
        tvNotification = (CustomTextView) findViewById(R.id.tv_notification);
        swNotification = (SwitchCompat) findViewById(R.id.sw_notification);
        llOrders = (FrameLayout) findViewById(R.id.ll_orders);
        llContactus = (FrameLayout) findViewById(R.id.ll_contactus);
        tvTerms = (CustomTextView) findViewById(R.id.tv_terms);
        tvAbout = (CustomTextView) findViewById(R.id.about_us);
        flNotification=(FrameLayout)findViewById(R.id.fl_notification);
        flNotification.setOnClickListener(this);
        llContactus.setOnClickListener(this);
        llOrders.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvTerms.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tvNotification.setVisibility(View.VISIBLE);
                } else {
                    tvNotification.setVisibility(View.GONE);
                }
            }
        });
        scAutobuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showAutoBuyAlerDailog();
                    tvDate.setVisibility(View.VISIBLE);
                } else {
                    tvDate.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void showAutoBuyAlerDailog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_select_date, null);
        CustomTextView ctInvite = (CustomTextView) dialogView.findViewById(R.id.tv_invite);
        materialCalendarView = (MaterialCalendarView) dialogView.findViewById(R.id.calender);
        materialCalendarView.addDecorator(new OneDayDecorator(getResources().getDrawable(R.drawable.grayrounded)));
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        // materialCalendarView.setMinDate(materialCalendarView.getCurrentDate());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (selected)
                    selecteddate = date.getDay() + " " + new DateFormatSymbols().getMonths()[date.getMonth()];
            }
        });
        ctInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (!TextUtils.isEmpty(selecteddate))
                    tvDate.setText(selecteddate);
                else {
                    tvDate.setVisibility(View.GONE);
                    scAutobuy.setChecked(false);
                }
            }
        });

        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alertDialog.show();
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    @Override
    public void onClick(View view) {
        if (view == llContactus) {
            Intent i = new Intent(SettingActivity.this, ContactUsActivity.class);
            startActivity(i);
        } else if (view == llOrders) {
            Intent i = new Intent(SettingActivity.this, MyOrderActivity.class);
            startActivity(i);
            //showOrderAlerDailog();
        } else if (view == ivBack) {
            finish();
        } else if (view == tvAbout) {

        } else if (view == tvTerms) {

        }else if(view==flNotification)
        {
            Intent i = new Intent(SettingActivity.this, NotificationActivity.class);
            startActivity(i);
        }
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 28);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            eventDecorator = new EventDecorator(Color.RED, calendarDays);
            materialCalendarView.addDecorator(eventDecorator);
        }
    }
}
