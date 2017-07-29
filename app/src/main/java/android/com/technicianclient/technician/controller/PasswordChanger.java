package android.com.technicianclient.technician.controller;

import android.app.Activity;
import android.com.technicianclient.technician.services.FieldValueModifierService;

/**
 * Created by Classic on 7/25/2017.
 */

public class PasswordChanger implements DialogInnerIntializer {
    private FieldValueModifierService service;

    public PasswordChanger(FieldValueModifierService service){
        this.service = service;
    }

    @Override
    public void execute(String input) {

        service.execute(input);
    }


}
