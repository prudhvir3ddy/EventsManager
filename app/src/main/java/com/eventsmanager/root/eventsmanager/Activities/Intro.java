package com.eventsmanager.root.eventsmanager.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class Intro extends IntroActivity{
    SharedPrefs sharedprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullscreen(true);
        super.onCreate(savedInstanceState);
        sharedprefs=new SharedPrefs(this);
        setFinishEnabled(true);
    setButtonBackVisible(false);
    setButtonNextVisible(false);

    setNavigationPolicy(new NavigationPolicy() {
        @Override
        public boolean canGoForward(int position) {
            return position != 5;
        }
        @Override
        public boolean canGoBackward(int position) {
            return position != 0;
        }
    });
    addSlide(new SimpleSlide.Builder()
                .title("Vishnu Events Manager")
                .description("Welcome")
                .image(R.drawable.bvrit)
                .background(R.color.color_canteen)
                .backgroundDark(R.color.color_dark_canteen)
                .build());
    addSlide(new SimpleSlide.Builder()
                .description("How this app works?")
                .image(R.drawable.qstn)
                .background(R.color.color_custom_fragment_2)
                .backgroundDark(R.color.color_dark_custom_fragment_2)
                .build());
    addSlide(new SimpleSlide.Builder()
                .title("Get Events")
                .description("Get All Technical Events In One Place")
                .image(R.drawable.calendar)
                .background(R.color.color_fragment_green)
                .backgroundDark(R.color.color_fragment_green_dark)
                .build());
    addSlide(new SimpleSlide.Builder()
                .title("Real Time Notifications")
                .description("Get real time notification updates whenever there are important events")
                .image(R.drawable.notif)
                .background(R.color.color_custom_fragment_1)
                .backgroundDark(R.color.color_dark_custom_fragment_1)
                .build());
    addSlide(new SimpleSlide.Builder()
                .title("Update Your Status")
                .description("Participated In Technical Events ? Let Us Know We Will Award You")
                .image(R.drawable.icon)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .build());

    addSlide(new SimpleSlide.Builder()
                .title("Need Help ?")
                .description("We Will Make Sure You Get Help For Your Intrested Events")
                .image(R.drawable.help)
                .background(R.color.color_permissions)
                .backgroundDark(R.color.color_dark_permissions)
                .buttonCtaLabel("Get Started")
                .buttonCtaClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sharedprefs.setopened();
            startActivity(new Intent(getApplicationContext(),StudentLogin.class));
            finish();
        }
    })
            .build());

}
}