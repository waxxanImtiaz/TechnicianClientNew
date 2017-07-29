package android.com.technicianclient.technician.controller;

import android.com.technicianclient.technician.factory.BeanFactory;
import android.com.technicianclient.technician.services.FieldValueModifierService;

/**
 * Created by Classic on 7/29/2017.
 */

public class MyAccountFieldsChanger  implements DialogInnerIntializer {
    private FieldValueModifierService service;

    public MyAccountFieldsChanger(FieldValueModifierService service){
        this.service = service;
    }

    @Override
    public void execute(String input) {

        service.execute(BeanFactory.getCustomer().getId(),input);
    }


}

