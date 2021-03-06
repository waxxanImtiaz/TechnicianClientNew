package android.com.technicianclient.technician.setters;

import android.app.Activity;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.PasswordChanger;
import android.com.technicianclient.technician.services.FieldValueModifierService;

import java.util.List;

/**
 * Created by Classic on 7/25/2017.
 */

public class InitializerFieldValueSetter {
    public void init(Activity activity, List<String> values,String message,int inputType){
        FieldValueModifierService service = new FieldValueModifierService(activity);
        service.setFormData(values);
        PasswordChanger initializer = new PasswordChanger(service);
        SharedMethods.showInputDialog(activity, initializer,message,inputType);
    }
}
