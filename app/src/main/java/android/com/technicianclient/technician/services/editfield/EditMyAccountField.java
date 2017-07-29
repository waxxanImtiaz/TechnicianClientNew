package android.com.technicianclient.technician.services.editfield;

import android.app.Activity;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.MyAccountFieldsChanger;
import android.com.technicianclient.technician.controller.PasswordChanger;
import android.com.technicianclient.technician.services.FieldValueModifierService;

import java.util.List;

/**
 * Created by Classic on 7/29/2017.
 */

public class EditMyAccountField {
    public void init(Activity activity, List<String> values,String message,int inputType){
        FieldValueModifierService service = new FieldValueModifierService(activity);
        service.setFormData(values);
        MyAccountFieldsChanger initializer = new MyAccountFieldsChanger(service);
        SharedMethods.showInputDialog(activity, initializer,message,inputType);
    }
}
