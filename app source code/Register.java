package de.kai_morich.simple_bluetooth_terminal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private Button b1,b2;
    public String cal,s,res;
    public EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    SQLiteDatabase con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        e1=(EditText) findViewById(R.id.editText11);
        e2=(EditText) findViewById(R.id.editText1);
        e3=(EditText) findViewById(R.id.editText2);
        e4=(EditText) findViewById(R.id.editText3);
        e5=(EditText) findViewById(R.id.editText4);
        e6=(EditText) findViewById(R.id.editText5);

        e7=(EditText) findViewById(R.id.editText6);
        e8=(EditText) findViewById(R.id.editText7);
        e9=(EditText) findViewById(R.id.editText8);
        e10=(EditText) findViewById(R.id.editText9);

        con =  this.openOrCreateDatabase("BluetoothMotor", MODE_PRIVATE, null);

        b1=(Button)findViewById(R.id.imageButton1);
        b2=(Button)findViewById(R.id.imageButton2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("NewApi")
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                con.execSQL("INSERT INTO user (userid,pwd,username,address,phone,email,pwd1,pwd2,pwd3,pwd4) Values ('"+
                        e1.getText().toString() +"','"+ e2.getText().toString() +"','"+ e3.getText().toString() +"','"+ e4.getText().toString() +"','"+ e5.getText().toString() +"','"+ e6.getText().toString() +"','"+ e7.getText().toString() +"','"+ e8.getText().toString() +"','"+ e9.getText().toString() +"','"+ e10.getText().toString() +"');");
                Toast.makeText(getApplication(),"Sucessfully Registered!!", Toast.LENGTH_SHORT).show();
            }

        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("NewApi")
            public void onClick(View v) {
                finish();
            }

        });

    }


}
