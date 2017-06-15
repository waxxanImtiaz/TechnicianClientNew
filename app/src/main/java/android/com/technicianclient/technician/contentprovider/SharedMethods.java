package android.com.technicianclient.technician.contentprovider;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.com.technicianclient.technician.mail.GMailSender;

/**
 * Created by Admin on 5/23/2017.
 */

public class SharedMethods {
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean validatePhoneNumber(String phoneNo) {
//        //validate phone numbers of format "1234567890"
//        if (phoneNo.matches("\\d{10}")) return true;
//            //validating phone number with -, . or spaces
//        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
//            //validating phone number with extension length from 3 to 5
//        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
//            //validating phone number where area code is in braces ()
//        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
//            //return false if nothing matches the input
//        else return false;
        if (phoneNo.length() == 11)
            return true;
        return false;

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
