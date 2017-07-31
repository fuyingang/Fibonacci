package leeco.fibonacci;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Fibonacci extends Activity implements View.OnClickListener {


    private static final String TAG = "Fibonacci";
    private EditText input;
    private Button button;
    private TextView output;
    private RadioGroup rG;

    private long number;
    private long result;

    private IFibonacciService service;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate, thread ID is: " + Thread.currentThread().getId());
        setContentView(R.layout.activity_fibonacci);
        input = (EditText) super.findViewById(R.id.fibInput);
        input.addTextChangedListener(inputTextWatcher);
        output = (TextView) super.findViewById(R.id.fibOutput);
        button = (Button) super.findViewById(R.id.btnCompute);
        button.setOnClickListener(this);
        button.setEnabled(false);

        rG = (RadioGroup) findViewById(R.id.rG);

        //rG.setOnClickListener(rGOnClickListener);
    }


    public void onClick(View v) {

            FibonacciRequest.Type type;

            switch (rG.getCheckedRadioButtonId()) {
                case R.id.iJ:
                    type = FibonacciRequest.Type.ITERATE_JAVA;
                    break;
                case R.id.rJ:
                    type = FibonacciRequest.Type.RECURSIVE_JAVA;
                    break;
                case R.id.iN:
                    type = FibonacciRequest.Type.ITERATE_NATIVE;
                    break;
                case R.id.rN:
                    type = FibonacciRequest.Type.RECURSIVE_NATIVE;
                    break;
                default:
                    return;

            }

            final FibonacciRequest request = new FibonacciRequest(number, type);

            final ProgressDialog dialog = ProgressDialog.show(this, "", "Calculating...", true);

            new AsyncTask<Void, Void, String>(){
                @Override
                protected String doInBackground(Void... params) {

                    try {
                        long totalTime = SystemClock.uptimeMillis();

                        FibonacciResponse response = service.fib(request);

                        totalTime = SystemClock.uptimeMillis() - totalTime;

                        return String.format("fibonacci(%d) = %d\nin %d ms\n(+ %d ms)", number,
                                response.getResult(), response.getTimeInMillis(), totalTime - response.getTimeInMillis());

                    } catch (RemoteException ex) {
                        Log.w(TAG, "remote error");
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    dialog.dismiss();
                    if (result == null) {
                        Toast.makeText(Fibonacci.this, "compute error", Toast.LENGTH_SHORT).show();
                    } else {
                        output.setText("Result is: " + result);
                    }
                }

            }.execute();

        }


    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            button.setEnabled(false);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String inputText = input.getText().toString();

            try {
                number = Long.parseLong(inputText);
                button.setEnabled(true);
            } catch (NumberFormatException e) {
                Log.w(TAG, "Invalid input, please input number");
                button.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onResume() {
        Log.d(TAG, "onResumed");
        super.onResume();

        serviceIntent = new Intent().setComponent(new ComponentName("leeco.fibonacci", "leeco.fibonacci.FibonacciService"));

        startService(serviceIntent);

        bindService(serviceIntent, serviceConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        //stopService(serviceIntent);
        unbindService(serviceConn);
    }

    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Log.d(TAG, "fibonacci service connected.");

            service = IFibonacciService.Stub.asInterface(iBinder);
            //button.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            button.setEnabled(false);
            service = null;
        }
    };
}
