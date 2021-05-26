package com.hawksjamesf.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/27/2019  Fri
 */
@Route(path = "/account/login")
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String mEmail = "hawks.jamesf@gmail.com";
    private String mPassword = "hawksjamesf123456A";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);

//        signUp("","");
        mAuth.signInWithEmailAndPassword(mEmail,mPassword);
//        signIn("","");
    }

    AuthCredential credential;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken forceResendingToken;

    public void signIn(String email, String pwd) {
        mAuth.signInWithCustomToken("asdff");
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://spacecraft-22dc1.firebaseapp.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
//                            .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.hawksjamesf.spacecraft",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();
        mAuth.sendSignInLinkToEmail("hawksjameaf@gmail.com", actionCodeSettings);

        mAuth.signInWithEmailAndPassword("email@gmail.com", "123456");
        boolean isEmail = false;
        boolean isPhone = false;
        boolean isGithub = false;
        if (isEmail) {
            String emailLink2 = "";
            credential = EmailAuthProvider.getCredential("hawks.jameaf@gmail.com", emailLink2);
        } else if (isPhone) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber("+16505554567", 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                /**
                 * - 即时验证：在某些情况下可以即时验证电话号码，而无需发送或输入验证码。
                 * - 自动检索：在某些设备上，Google Play 服务可以自动检测收到的验证短信并进行验证，而无需用户执行任何操作。（某些运营商可能不支持这项功能。）
                 */
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    credential = phoneAuthCredential;

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                }

                @Override
                public void onCodeSent(String id, PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(id, token);
                    verificationId = id;
                    forceResendingToken = token;
                    credential = PhoneAuthProvider.getCredential(id, "123456");

                }

                @Override
                public void onCodeAutoRetrievalTimeOut(String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                }
            });
        } else if (isGithub) {

        }
        mAuth.signInWithCredential(credential);


    }

    public void signUp(String email, String pwd) {
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                })
//                .addOnCanceledListener(this, new OnCanceledListener() {
//                    @Override
//                    public void onCanceled() {
//
//                    }
//                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hawks","onComplete");
                        if (task.isSuccessful()) {
                            AuthResult result = task.getResult();
                            if (result != null) {
                                FirebaseUser user = result.getUser();
                                Log.d("hawks","user"+user.getUid());
                            }
                        }

                    }
                });
        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("hawks", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hawks", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }


    private void updateUI(FirebaseUser currentUser) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        String emailLink = intent.getData().toString();
        mAuth.signInWithEmailLink(mEmail, emailLink);
    }
}
