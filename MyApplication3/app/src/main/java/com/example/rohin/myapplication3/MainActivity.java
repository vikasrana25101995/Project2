package com.example.rohin.myapplication3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button show, show2, getStarted, Continue;
    EditText edit_password, edit_name, edit_email, edit_password2;
    TextView toast, name_display, forget;
    private final String Default = "N/A";
    String[] Gender = {"Male", "Female"};
    String gender;
    final static String TARGET_BASE_PATH = "/sdcard/appname/voices/";
    Spinner spinner;
    ImageView icon_user;
    private ProgressDialog progressBar;

    //Create a circular progressBar Dialog
    //private android.content.Context ActivityContext;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file

        //Creating a shared preference file  to save the name ,mail address,password and also for setting the correct xml file
        String name_file = sharedPreferences.getString("name", Default);
        String pass_file = sharedPreferences.getString("password", Default);
        String email_file = sharedPreferences.getString("email", Default);
        String gender_file = sharedPreferences.getString("gender", Default);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);


        if (name_file.equals(Default) || pass_file.equals(Default) || email_file.equals(Default) || gender_file.equals(Default))
        {

            setContentView(R.layout.activity_main);

            show = (Button) findViewById(R.id.show);  //Show button in password
            edit_password = (EditText) findViewById(R.id.password);   //Password EditText
            edit_email = (EditText) findViewById(R.id.email);   //email EditText
            edit_name = (EditText) findViewById(R.id.name);   //name EditText
            show.setOnClickListener(new showOrHidePassword());//invoking the showOrHidePassword class to show the password
            toast = (TextView) findViewById(R.id.toast_help);//toast_help object


            //Spinner for choosing the gender
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, Gender);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new spinner());
            //

            getStarted = (Button) findViewById(R.id.getStarted);
            getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String save_name = edit_name.getText().toString();
                    //System.out.println(""+save_name);
                    String save_email = edit_email.getText().toString();
                    //System.out.println(""+save_email);
                    String save_password = edit_password.getText().toString();
                    //System.out.println(""+save_password);

                    //If and else are used to check if all the three text field are empty or not
                    if (save_name.equals("") || save_email.equals("") || save_password.equals("")) {
                        try{
                            Toast.makeText(MainActivity.this, "Please Enter the Details", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e)
                        {}
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", save_name);
                        editor.putString("password", save_password);
                        editor.putString("email", save_email);
                        editor.putString("gender", gender);
                        editor.commit();

                        progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                        progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any where on screen
                        progressBar.setMessage("Please Wait...");//Title shown in the progress bar
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                        progressBar.setProgress(0);//attributes
                        progressBar.setMax(100);//attributes
                        progressBar.show();//show the progress bar
                        //This handler will add a delay of 3 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Intent start to open the navigation drawer activity
                                progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                                Intent intent = new Intent(MainActivity.this, Navigation_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3500);

                    }

                }
            });
        }

        else
        {

            setContentView(R.layout.page2_welcome);
            icon_user = (ImageView) findViewById(R.id.image_icon);

            if (gender_file.equals("Male"))
            {
                icon_user.setImageResource(R.drawable.man);
            }
            else
            {
                icon_user.setImageResource(R.drawable.female);
            }


            name_display = (TextView) findViewById(R.id.name_display);
            name_display.setText(name_file);
            edit_password2 = (EditText) findViewById(R.id.password2);
            show2 = (Button) findViewById(R.id.show2);
            show2.setOnClickListener(new showOrHidePassword2());
            forget = (TextView) findViewById(R.id.forget);
            Continue = (Button) findViewById(R.id.Continue);

            try {
                   Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String local_pass2 = edit_password2.getText().toString();
                        if (sharedPreferences.getString("password", Default).equals(local_pass2)) {

                            progressBar = new ProgressDialog(v.getContext());//Create new object of progress bar type
                            progressBar.setCancelable(false);//Progress bar cannot be cancelled by pressing any wher on screen
                            progressBar.setMessage("Please Wait...");//Tiitle shown in the progress bar
                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//Style of the progress bar
                            progressBar.setProgress(0);//attributes
                            progressBar.setMax(100);//attributes
                            progressBar.show();//show the progress bar
                            //This handler will add a delay of 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Intent start to open the navigation drawer activity
                                    progressBar.cancel();//Progress bar will be cancelled (hide from screen) when this run function will execute after 3.5seconds
                                    Intent intent = new Intent(MainActivity.this, Navigation_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2500);

                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter correct password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Warning", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //class to show or hide password on button click in main activity
    class showOrHidePassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (show.getText().toString() == "SHOW") {
                edit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                show.setText("HIDE");

            } else {

                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show.setText("SHOW");
            }
        }
    }

    //class to show or hide password on button click in main activity second
    class showOrHidePassword2 implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (show2.getText().toString() == "SHOW") {
                edit_password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                show2.setText("HIDE");

            } else {

                edit_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show2.setText("SHOW");
            }
        }
    }

    //Spinner class to select spinner and also add gender
    class spinner implements AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            gender = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //When nothing is selected
            Toast.makeText(getApplicationContext(), "Please Enter the gender", Toast.LENGTH_SHORT).show();
        }
    }
}



