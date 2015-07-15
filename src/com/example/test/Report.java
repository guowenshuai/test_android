package com.example.test;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by jky on 15-7-15.
 */
public class Report extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        findView();
        showResult();
        setListensers();
    }

    private static final String TAG = "Report";
    private Button button_back;
    private TextView view_result;
    private TextView view_suggest;

    private void findView() {
        button_back = (Button) findViewById(R.id.report_back);
        view_result = (TextView) findViewById(R.id.result);
        view_suggest = (TextView) findViewById(R.id.suggest);
    }

    /*  计算结果  */
    private void showResult() {
        try {
            DecimalFormat nf = new DecimalFormat("0.00");
            Bundle bundle = this.getIntent().getExtras();

            double height = Double.parseDouble(bundle.getString("KEY_HEIGHT")) / 100;
            Log.d(TAG, "get height");
            double weight = Double.parseDouble(bundle.getString("KEY_WEIGHT"));
            Log.d(TAG, "get weight");
            double BMI = weight / (height * height);
            view_result.setText(getString(R.string.bmi_result) + " " + nf.format(BMI));

            if (BMI > 25) {
                showNotification(BMI);
                view_suggest.setText(R.string.advice_heavy);
            } else if (BMI < 20) {
                view_suggest.setText(R.string.advice_light);
            } else
                view_suggest.setText(R.string.advice_average);
        } catch (NumberFormatException e) {
            Log.e(TAG, "error:   " + e.toString());
            Toast.makeText(Report.this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
        }
    }
/*设置监听*/
    private Button.OnClickListener backMain = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Report.this.finish();
        }
    };


    private void setListensers() {
        button_back.setOnClickListener(backMain);
    }

    /*信息提示*/
    protected void showNotification(double BMI) {
        NotificationManager barManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);/*申请一个notice服务*/
        /*Notification 是在通知栏一闪而过的那条信息*/
        @SuppressWarnings("deprecation") Notification barMsg = new Notification(R.drawable.second, "哦哦 你太重了", System.currentTimeMillis());
        /*pendingIntent是点击通知时候要打开的Activity*/
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //noinspection deprecation
        barMsg.setLatestEventInfo(Report.this, "您的 BMI 过高", "通知监督人", contentIntent);/*Activity, 通知栏通知,
                                                                                         通知栏附加消息, PendingIntent*/
        barManager.notify(0, barMsg);/*用notify方法发送通知*/
    }
}