package com.edgard.smarthome.iota;

import android.util.Log;

import com.edgard.smarthome.LedValueEventListener;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;

public class UtilRaspberry {
    private static final String TAG = "TAG";
    public static Gpio setConfigurationLed(PeripheralManager peripheralManager, Gpio led, String pin) {

        try {
            // Step 1. Create GPIO connection.
            led = peripheralManager.openGpio(pin);
            // Step 2. Configure as an input.
            led.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        return led;
    }

    public static void setReferenceFirebase(DatabaseReference ref, String on, Gpio led) {
        DatabaseReference redRef = ref.child(on);//"red_on"
        redRef.addValueEventListener(new LedValueEventListener(led));
    }
}
