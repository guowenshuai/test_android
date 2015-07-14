package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.net.URISyntaxException;
import java.text.DecimalFormat;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setTitle("d");
        findViews();
        setListeners();
        /*Listen for button clicks*/
    }

    private Button button_calc;
    private EditText field_height;
    private EditText field_weight;
    private TextView view_result;
    private TextView view_suggest;

    private void findViews() {
        button_calc = (Button) findViewById(R.id.submit);
        field_height = (EditText) findViewById(R.id.height);
        field_weight = (EditText) findViewById(R.id.weight);
        view_result = (TextView) findViewById(R.id.result);
        view_suggest = (TextView) findViewById(R.id.suggest);
    }

    private void setListeners() {
        button_calc.setOnClickListener(calc);
    }


    private Button.OnClickListener calc = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            show_Toast message = new show_Toast();
            try {
                DecimalFormat nf = new DecimalFormat("0.00");
                field_height = (EditText) findViewById(R.id.height);
                field_weight = (EditText) findViewById(R.id.weight);
                double height = Double.parseDouble(field_height.getText().toString()) / 100;
                double weight = Double.parseDouble(field_weight.getText().toString());
                double BMI = weight / (height * height);

                view_result = (TextView) findViewById(R.id.result);
                view_result.setText(getText(R.string.bmi_result) + " " + nf.format(BMI));

                view_suggest = (TextView) findViewById(R.id.suggest);
                if (BMI > 25) {
                    view_suggest.setText(R.string.advice_heavy);
                } else if (BMI < 20) {
                    view_suggest.setText(R.string.advice_light);
                } else view_suggest.setText(R.string.advice_average);
            } catch (Exception obj) {
                message.msg_error();
            }
        }
    };

    private class show_Toast {
        void msg_error() {
            Toast.makeText(MyActivity.this, getText(R.string.message), Toast.LENGTH_SHORT).show();
        }
        void msg_about_toast() {
            Toast.makeText(MyActivity.this, getText(R.string.about_title), Toast.LENGTH_SHORT).show();
        }
    }

   /*   menu    */
    protected static final int MENU_ABOUT = Menu.FIRST;
    protected static final int MENU_QUIT = Menu.FIRST+1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ABOUT, 0, "关于").setIcon(R.drawable.ic_close_black_64dp);
        menu.add(0, MENU_QUIT, 0, "结束").setIcon(R.drawable.ic_call_made_black_24dp);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ABOUT:
                openOptionsDialog();
                break;
            case MENU_QUIT:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
/*    *************      */
    private void openOptionsDialog() {
        new AlertDialog.Builder(MyActivity.this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_msg)
                .setPositiveButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton(R.string.homepage_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Uri uri = Uri.parse("http://sites.google.com/site/gasodroid/");
                        try {
                            Uri uri = Uri.parse(getString(R.string.homepage_url));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (Exception e) {

                        }
                    }
                })
                .show();

    }
}
