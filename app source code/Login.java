package de.kai_morich.simple_bluetooth_terminal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Login extends AppCompatActivity {
    public Button b1,b3;
    public String login;
    public EditText e1,e2;
    SQLiteDatabase con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        b3=(Button)findViewById(R.id.loginbutton);
        b1=(Button)findViewById(R.id.button1);
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.password);

        con =  this.openOrCreateDatabase("BluetoothMotor", MODE_PRIVATE, null);
        String CREATE_reg_TABLE = "CREATE TABLE IF NOT EXISTS user('userid' TEXT,'pwd' TEXT,'username' TEXT,'address' TEXT,'phone' TEXT,'email' TEXT,'pwd1' TEXT,'pwd2' TEXT,'pwd3' TEXT,'pwd4' TEXT)";
        con.execSQL(CREATE_reg_TABLE);

        String CREATE_loggedin_TABLE = "CREATE TABLE IF NOT EXISTS loggedin('userid' TEXT,'pwd' TEXT,'username' TEXT,'address' TEXT,'phone' TEXT,'email' TEXT,'pwd1' TEXT,'pwd2' TEXT,'pwd3' TEXT,'pwd4' TEXT)";
        con.execSQL(CREATE_loggedin_TABLE);

        //Getting local database values
        Cursor c=con.rawQuery("select * from loggedin",null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            // Toast.makeText(getApplication(),"Login Success",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Login.this,MainActivity.class);
            i.putExtra("userid",c.getString(0));
            i.putExtra("pwd",c.getString(1));
            i.putExtra("pwd1",c.getString(6));
            i.putExtra("pwd2",c.getString(7));
            i.putExtra("pwd3",c.getString(8));
            i.putExtra("pwd4",c.getString(9));

            startActivity(i);
        }
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg)
            {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            @SuppressLint("NewApi")
            public void onClick(View arg)
            {
                if (android.os.Build.VERSION.SDK_INT >= 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                //Getting local database values
                Cursor c=con.rawQuery("select * from user where userid='"+ e1.getText().toString() +"' and pwd='"+ e2.getText().toString() +"'",null);
                c.moveToFirst();
                if(c.getCount()>0)
                {
                    con.execSQL("INSERT INTO loggedin (userid,pwd,pwd1,pwd2,pwd3,pwd4) Values ('"+ e1.getText().toString() +"','"+ e2.getText().toString() +"','"+ c.getString(6) +"','"+ c.getString(7) +"','"+ c.getString(8) +"','"+ c.getString(9) +"');");

                    // Toast.makeText(getApplication(),"Login Success",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Login.this,MainActivity.class);
                    i.putExtra("userid",c.getString(0));
                    i.putExtra("pwd",c.getString(1));
                    i.putExtra("pwd1",c.getString(6));
                    i.putExtra("pwd2",c.getString(7));
                    i.putExtra("pwd3",c.getString(8));
                    i.putExtra("pwd4",c.getString(9));
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplication(),"Invalid Login!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}