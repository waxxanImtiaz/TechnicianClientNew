package android.com.technicianclient.technician.contentprovider;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.com.technicianclient.technician.mail.GMailSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 5/23/2017.
 */

public class SharedMethods {

    public static boolean validatePhoneNumber(String phoneNo) {
        String regex = "^\\+?[0-9. ()-]{10,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNo);

        if(phoneNo.length() < 11 || phoneNo.length() > 11)
            return false;


        if (!phoneNo.startsWith("03") )
            return false;
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmailAddress(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public static void hideKeyBoard(Activity c) {
        // Check if no view has focus:
        View view = c.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void sendEmail(final String recipent) {
        new Thread() {
            public void run() {

                try {
                    GMailSender sender = new GMailSender(SharedFields.EMAIL_APP, SharedFields.EMAIL_PASS);
                    if (sender.sendMail(SharedFields.EMAIL_SUBJECT,
                            SharedFields.EMAIL_BODY,
                           recipent,
                            recipent)) {
                       Log.d("Email","Email sent successfully to "+recipent);
                    } else {
                        Log.d("email did not sent to", "reciepent:"+recipent);
                    }


                } catch (Exception e) {
                    Log.e("SendMail except:", e.getMessage(), e);
                }
            }
        }.start();
    }

}

