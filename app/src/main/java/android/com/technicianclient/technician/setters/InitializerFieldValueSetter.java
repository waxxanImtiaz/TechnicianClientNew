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
        service.setSuccessMessage(values.get(0));
        service.setFailureMessage(values.get(1));
        CustomDialogInitializer initializer = new CustomDialogInitializer(service,activity);
        initializer.setFlag(values.get(2));
        initializer.setVariable(values.get(3));
        SharedMethods.showInputDialog(activity, initializer);

    }
}
