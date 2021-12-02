package com.example.finalproject;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubpageSettingAcknowledgment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_setting_help_acknowlegment);

        TextView textViewHyperLink = (TextView) findViewById(R.id.setting_menuAcknowledgments_IconSourceHyperLink);
        textViewHyperLink.setText(Html.fromHtml(" <a href=\"https://www.flaticon.com/authors/good-ware\">Flaticon</a> "));
        textViewHyperLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
