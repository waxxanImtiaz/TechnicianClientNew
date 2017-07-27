package android.com.technicianclient.technician.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.com.technicianclient.technician.contentprovider.SharedFields;
import android.com.technicianclient.technician.serverconnetors.SingleJsonResponseHandler;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 7/14/2017.
 */

public class FieldValueModifierService extends AsyncTask<String , Void, String> {

    private Activity mContext;
    private ProgressDialog progressDialog2;

    private String successMessage;
    private String failureMessage;
    private List<String> form;
    public FieldValueModifierService(final Activity context) {
        this.mContext = context;


    }
    public void setFormData(List<String> form){
        this.form = form;
        setSuccessMessage(form.get(0));
        setFailureMessage(form.get(1));
    }



    @Override
    protected String doInBackground(String... params) {
        // TODO: attempt authentication against a network service.

        try {

            SingleJsonResponseHandler handler = new SingleJsonResponseHandler();
            handler.setUrl(SharedFields.userLink);
            handler.setRequestMethod("POST");

            Map<String, String> arguments = new HashMap<>();
            arguments.put( form.get(2), "true");
            for (int i=3; i<params.length; i++){
                arguments.put( form.get(i), params[i]);
            }



            return handler.setFormParametersAndConnect(arguments).getString("req_status");
        } catch (Exception e) {
            return "";
        }


    }


    @Override
    protected void onPreExecute() {
        if (!((Activity) mContext).isFinishing()) {
            //show dialog
            progressDialog2 = ProgressDialog.show(mContext, "", "Loading");
        }


    }



    @Override
    protected void onPostExecute(final String success) {

        if (progressDialog2 != null)
            progressDialog2.dismiss();

        if (success == null) {
            Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (success.equalsIgnoreCase("success")){
                Toast.makeText(mContext, getSuccessMessage(), Toast.LENGTH_SHORT).show();
            }else Toast.makeText(mContext, getFailureMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("exception", "msg:" + e.toString());
        }
    }


    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}


