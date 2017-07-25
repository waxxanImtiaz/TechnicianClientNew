package android.com.technicianclient.technician.controller;

import android.app.Activity;
import android.com.technicianclient.technician.services.FieldValueModifierService;

/**
 * Created by Classic on 7/25/2017.
 */

public class CustomDialogInitializer implements DialogInnerIntializer {
    private FieldValueModifierService service;
    private String variable;
    private String flag;

    private Activity activity;
    public CustomDialogInitializer(FieldValueModifierService service,Activity activity){
        this.service = service;
        this.activity = activity;
    }
    @Override
    public void execute(String input) {

        service.execute(variable,input,flag);
    }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
