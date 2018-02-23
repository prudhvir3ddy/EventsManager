package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentLogin extends AppCompatActivity {
EditText e1,e2;
TextView t1;
Button b;
LoadToast loadToast;
SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");
        sharedPrefs=new SharedPrefs(this);
        if(sharedPrefs.getlogin()!=null)
        {
            startActivity(new Intent(StudentLogin.this,HomeActivity.class));
            finish();
        }

        e1=(EditText)findViewById(R.id.rollno);
        e2=(EditText)findViewById(R.id.passwd);
        b=(Button)findViewById(R.id.btn_login);
        t1=(TextView)findViewById(R.id.link_signup);

        loadToast=new LoadToast(this);
        loadToast.setText("loading..");
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentLogin.this,StudentRegisterActivity.class));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roll=e1.getText().toString();
                String pass=e2.getText().toString();
                loadToast.show();
                AndroidNetworking.initialize(getApplicationContext());
                AndroidNetworking.post(Urls.studentlog)
                        .addBodyParameter("roll",roll)
                        .addBodyParameter("pass",pass)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                loadToast.success();
                                Log.d("response",""+response);
                                int j=response.length();
                                for(int i=0;i<j;i++)
                                {
                                    JSONObject jsonObject;
                                    try {
                                        jsonObject=response.getJSONObject(i);
                                        if(!jsonObject.has("failed"))
                                        {
                                            String name=jsonObject.getString("name");
                                            String rollno=jsonObject.getString("rollno");
                                            String branch=jsonObject.getString("branch");
                                            String phone=jsonObject.getString("phone");
                                            String email=jsonObject.getString("email");
                                            sharedPrefs.setRollno(rollno);
                                            sharedPrefs.saveprefs(name,email,phone,branch);
                                            sharedPrefs.setlogin();
                                            startActivity(new Intent(StudentLogin.this,HomeActivity.class));
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Failed man",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                loadToast.error();
                                Log.d("error",""+anError);
                            }
                        });
            }
        });

    }
}
