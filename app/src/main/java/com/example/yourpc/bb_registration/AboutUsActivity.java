package com.example.yourpc.bb_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
       /*
        *   Maria Medhat (13-00432)
            Marina Twfils (13-00526)
            Martina GamaL (13-00527)
            Mariam Gerges (13-00949)
            Maria Bahgat (13-00389)
            Sandy Fawzy (12-00049)
            Abnaoub Samy (12-00027)
        * */

        View aboutPage = new AboutPage(this)
                .isRTL(true)
                .setImage(R.drawable.logo_color)
                .setDescription("put description here")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addGroup("Abnaoub Samy (12-00027)")
                .addGroup("Maria Medhat (13-00432)")
                .addGroup("Marina Twfils (13-00526)")
                .addGroup("Martina GamaL (13-00527)")
                .addGroup("Mariam Gerges (13-00949)")
                .addGroup("Maria Bahgat (13-00389)")
                .addGroup("Sandy Fawzy (12-00049)")
                .addYoutube("5ze_xC-y6lI")
                .addYoutube("rolXvtMg7CI")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);

    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}
