package android.com.technicianclient.technician;

import android.app.ProgressDialog;
import android.com.technicianclient.technician.setters.InitializerFieldValueSetter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.com.technicianclient.technician.beans.Customer;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.factory.BeanFactory;
import android.com.technicianclient.technician.serverconnetors.UserLoginTask;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    CallbackManager callbackManager;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private TextView tvDetails;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private Profile profile;
    private TextView tvForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        tvForgetPassword = (TextView)findViewById(R.id.tvForgetPassword);

        SharedFields.isExited = false;

        tvForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> values = new ArrayList<>();
                values.add("Password sent to your email address");
                values.add("Email sent failed");
                values.add("forget_password");
                values.add("email");


                InitializerFieldValueSetter setter = new InitializerFieldValueSetter();
                setter.init(LoginActivity.this,values,getResources().getString(R.string.email_message), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);



            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        LoginManager.getInstance().logOut();

        tvDetails = (TextView) findViewById(R.id.tvDetails);
      ;

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                //tvDetails.setText(String.valueOf("Welcome onCurrentAccessTokenChanged"));
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //nextActivity(newProfile);

                if (oldProfile != null) {
                    profile = oldProfile;

                    //tvDetails.setText(String.valueOf("Welcome ," + oldProfile.getFirstName()));

                }
                if (newProfile != null) {
                    profile = newProfile;

                    //tvDetails.setText(String.valueOf("Welcome ," + newProfile.getFirstName()));
                }

                if (profile != null)
                    initProfile();
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("login", "Login success");
                final Profile profile = Profile.getCurrentProfile();

                // intiRequest();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {

                                    initProfile();


                                } catch (Exception e) {
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

                // id = loginResult.getAccessToken().getUserId();
                //tvDetails.setText(String.valueOf("Welcome ").concat(profile.getName()));

                //loginButton.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {
                // App code
                //  tvLoginStatus.setText("Login cancelled");
                tvDetails.setText(String.valueOf("Login cancelled"));
            }

            @Override
            public void onError(FacebookException exception) {
                tvDetails.setText(String.valueOf("Network Error"));

                Log.d("hashkey", exception.toString());
                // App code
            }
        });

    }

    public void initProfile() {
        SharedMethods.hideKeyBoard(LoginActivity.this);
        Customer customer =BeanFactory.getCustomer();

        //customer.setEmail(object.getString("email"));
        if (profile != null)
            customer.setFbId(profile.getId());

        BeanFactory.setCustomer(customer);
        //loginButton.setVisibility(View.INVISIBLE);
        mAuthTask = new UserLoginTask(this);
        mAuthTask.execute((Void) null);


    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//       // getLoaderManager().initLoader(0, null, this);
//    }

//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        //super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
        //intiRequest();
        //Toast.makeText(this, "You are logged in", Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) ) {
            mPasswordView.setError(getString(R.string.error_field_required));

            return;
        }else
        {

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            return;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            return;
        }

        Customer c = BeanFactory.getCustomer();

        c.setEmail(mEmailView.getText().toString());
        c.setPassword(mPasswordView.getText().toString());
        BeanFactory.setCustomer(c);
        initProfile();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}