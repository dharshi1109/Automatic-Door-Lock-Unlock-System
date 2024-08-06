package de.kai_morich.simple_bluetooth_terminal;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayDeque;

public class TerminalFragment extends Fragment implements ServiceConnection, SerialListener {
    SQLiteDatabase con;
    private enum Connected { False, Pending, True }
    TextView tv1,tv2, tv3,tv4, tv5;
    Boolean firstClicked = false;
    Boolean secondClicked = false;
    Boolean thirdClicked = false;
    Boolean fourthClicked = false;
    private String deviceAddress;
    private SerialService service;

    private Button btn1,btn2,btn3,btn4,btn5;

    private TextUtil.HexWatcher hexWatcher;

    private Connected connected = Connected.False;
    private boolean initialStart = true;
    private boolean hexEnabled = false;
    private boolean pendingNewline = false;
    private String newline = TextUtil.newline_crlf;

    /*
     * Lifecycle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        deviceAddress = getArguments().getString("device");
        con =  getActivity().openOrCreateDatabase("BluetoothMotor", MODE_PRIVATE, null);
    }

    @Override
    public void onDestroy() {
        if (connected != Connected.False)
            disconnect();
        getActivity().stopService(new Intent(getActivity(), SerialService.class));
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(service != null)
            service.attach(this);
        else
            getActivity().startService(new Intent(getActivity(), SerialService.class)); // prevents service destroy on unbind from recreated activity caused by orientation change
    }

    @Override
    public void onStop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(service != null && !getActivity().isChangingConfigurations())
                service.detach();
        }
        super.onStop();
    }

    @SuppressWarnings("deprecation") // onAttach(context) was added with API 23. onAttach(activity) works for all API versions
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        getActivity().bindService(new Intent(getActivity(), SerialService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDetach() {
        try { getActivity().unbindService(this); } catch(Exception ignored) {}
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(initialStart && service != null) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        service.attach(this);
        if(initialStart && isResumed()) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    /*
     * UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal, container, false);



            // TextView performance decreases with number of spans

        btn1 = view.findViewById(R.id.button1);
        btn2 = view.findViewById(R.id.button2);
        btn3= view.findViewById(R.id.button3);
        btn4 = view.findViewById(R.id.button4);
        btn5 = view.findViewById(R.id.button5);
        tv1 = view.findViewById(R.id.textView6);
        tv2 = view.findViewById(R.id.textView7);
        tv3 = view.findViewById(R.id.textView8);
        tv4 = view.findViewById(R.id.textView9);
        tv5 = view.findViewById(R.id.textView10);


        Intent ij = getActivity().getIntent();
        tv1.setText(ij.getStringExtra("userid"));
        tv2.setText(ij.getStringExtra("pwd1"));
        tv3.setText(ij.getStringExtra("pwd2"));
        tv4.setText(ij.getStringExtra("pwd3"));
        tv5.setText(ij.getStringExtra("pwd4"));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Authenticate User...");

                // set the custom dialog components - text, image and button
                final TextView text = (TextView) dialog.findViewById(R.id.editText1);
                //text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
              //  image.setImageResource(R.drawable.logo);

                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (text.getText().toString().equals(tv2.getText().toString()))
                        {
                            if (firstClicked == false) {
                                send("1");
                                firstClicked = true;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot11).toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                send("2");
                                firstClicked = false;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot12).toString(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Invalid Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                }

        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Authenticate User...");

                // set the custom dialog components - text, image and button
                final TextView text = (TextView) dialog.findViewById(R.id.editText1);
                //text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
             //   image.setImageResource(R.drawable.logo);

                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (text.getText().toString().equals(tv3.getText().toString()))
                        {
                            if (secondClicked == false) {
                                send("3");
                                secondClicked = true;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot21).toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                send("4");
                                secondClicked = false;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot22).toString(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Invalid Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }

        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Authenticate User...");

                // set the custom dialog components - text, image and button
                final TextView text = (TextView) dialog.findViewById(R.id.editText1);
                //text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
               // image.setImageResource(R.drawable.logo);

                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (text.getText().toString().equals(tv4.getText().toString()))
                        {
                            if (thirdClicked == false) {
                                send("5");
                                thirdClicked = true;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot31).toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                send("6");
                                thirdClicked = false;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot32).toString(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Invalid Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }

        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Authenticate User...");

                // set the custom dialog components - text, image and button
                final TextView text = (TextView) dialog.findViewById(R.id.editText1);
                //text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
               // image.setImageResource(R.drawable.logo);

                Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (text.getText().toString().equals(tv5.getText().toString()))
                        {
                            if (fourthClicked == false) {
                                send("7");
                                fourthClicked = true;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot41).toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                send("8");
                                fourthClicked = false;
                                Toast.makeText(getContext(), getResources().getString(R.string.slot42).toString(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Invalid Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }

        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con.execSQL("delete from loggedin;");
                Toast.makeText(getContext(),"Logout Successful !!!",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), Login.class);
                startActivity(i);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_terminal, menu);
        menu.findItem(R.id.hex).setChecked(hexEnabled);
    }


    private void send(String str) {
        if(connected != Connected.True) {
            Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String msg;
            byte[] data;
            if(hexEnabled) {
                StringBuilder sb = new StringBuilder();
                TextUtil.toHexString(sb, TextUtil.fromHexString(str));
                TextUtil.toHexString(sb, newline.getBytes());
                msg = sb.toString();
                data = TextUtil.fromHexString(msg);
            } else {
                msg = str;
                data = (str + newline).getBytes();
            }
            SpannableStringBuilder spn = new SpannableStringBuilder(msg + '\n');
            spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSendText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            service.write(data);
        } catch (Exception e) {
            onSerialIoError(e);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear) {
            return true;
        } else if (id == R.id.newline) {
            String[] newlineNames = getResources().getStringArray(R.array.newline_names);
            String[] newlineValues = getResources().getStringArray(R.array.newline_values);
            int pos = java.util.Arrays.asList(newlineValues).indexOf(newline);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Newline");
            builder.setSingleChoiceItems(newlineNames, pos, (dialog, item1) -> {
                newline = newlineValues[item1];
                dialog.dismiss();
            });
            builder.create().show();
            return true;
        } else if (id == R.id.hex) {
            hexEnabled = !hexEnabled;
            hexWatcher.enable(hexEnabled);
            item.setChecked(hexEnabled);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Serial + UI
     */
    private void connect() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
            status("connecting...");
            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(getActivity().getApplicationContext(), device);
            service.connect(socket);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    private void disconnect() {
        connected = Connected.False;
        service.disconnect();
    }


    private void status(String str) {
        SpannableStringBuilder spn = new SpannableStringBuilder(str + '\n');
        spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorStatusText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    /*
     * SerialListener
     */
    @Override
    public void onSerialConnect() {
        status("connected");
        connected = Connected.True;
    }

    @Override
    public void onSerialConnectError(Exception e) {
        status("connection failed: " + e.getMessage());
        disconnect();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onSerialRead(byte[] data) {
        ArrayDeque<byte[]> datas = new ArrayDeque<>();
        datas.add(data);

    }

    @Override
    public void onSerialRead(ArrayDeque<byte[]> datas) {

    }


    @Override
    public void onSerialIoError(Exception e) {
        status("connection lost: " + e.getMessage());
        disconnect();
    }

}
