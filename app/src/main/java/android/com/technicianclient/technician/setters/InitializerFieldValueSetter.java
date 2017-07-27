package android.com.technicianclient.technician.setters;

import android.app.Activity;
import android.com.technicianclient.technician.contentprovider.SharedMethods;
import android.com.technicianclient.technician.controller.CustomDialogInitializer;
import android.com.technicianclient.technician.services.FieldValueModifierService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Classic on 7/25/2017.
 */

public class InitializerFieldValueSetter {
    public void init(Activity activity, List<String> values){
        FieldValueModifierService service = new FieldValueModifierService(activity);
        service.setFormData(values);
        CustomDialogInitializer initializer = new CustomDialogInitializer(service);
        SharedMethods.showInputDialog(activity, initializer);
    }
}
