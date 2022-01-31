package com.example.ahmadsidani20190148aliyassine20190234hadiibrahim20170297;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        CheckBox showpassword = (CheckBox) findViewById(R.id.showpass);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showpassword.isChecked()) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                password.setSelection(password.length());
            }
        });

        // Sign in Process
        Button signin = (Button) findViewById(R.id.signin);
        TextView w1 = (TextView) findViewById(R.id.warning1);
        TextView w2 = (TextView) findViewById(R.id.warning2);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals("")) {
                    w1.setText("Empty Field!!!");
                } else {
                    if (!isEmailValid(email.getText().toString())) {
                        w1.setText("Invalid email address");
                    } else {
                        w1.setText("");
                    }

                }
                if (password.getText().toString().equals("")) {
                    w2.setText("Empty Field!!!");
                } else {
                    w2.setText("");
                }


                if(w1.getText().toString().equals("")&&w2.getText().toString().equals("")&&!password.getText().toString().equals("")) {
                    signin.setEnabled(false);
                    // Good To Go now we need to authenticate
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                                        signin.setVisibility(View.INVISIBLE);
                                        pb.setVisibility(View.VISIBLE);
                                        Intent intent =new Intent(getApplication(),MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        HandlerThread handlerThread = new HandlerThread("hideTextHandlerThread");
                                        handlerThread.start();
                                        Handler handler = new Handler(handlerThread.getLooper());
                                        Handler mainHandler = new Handler(Login.this.getMainLooper());
                                        Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                mainHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        startActivity(intent);
                                                        Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                                        invalidateOptionsMenu();
                                                        finish();
                                                        signin.setEnabled(true);

                                                    }
                                                });
                                                handler.getLooper().quit();
                                            }
                                        };
                                        handler.post(runnable);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        signin.setEnabled(true);
                                        Snackbar.make(v, "Incorrect Email or Password!!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                        updateUI(null);
                                    }
                                }
                            });
                }

            }
        });
    }



    public void forgotPassword(View v){
            Intent intent = new Intent(this,Forgotpassword.class);
            EditText email= (EditText)findViewById(R.id.email);
            intent.putExtra("email",email.getText().toString());
            startActivity(intent);
            finish();
        }
        public boolean isEmailValid(CharSequence email) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    private void updateUI(FirebaseUser user) {

    }
}