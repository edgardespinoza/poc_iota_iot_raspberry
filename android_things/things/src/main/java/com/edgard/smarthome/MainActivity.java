package com.edgard.smarthome;

import android.app.Activity;
import android.os.Bundle;

import com.edgard.smarthome.iota.IOTAHandlerRaspberry;
import com.edgard.smarthome.iota.UtilRaspberry;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {
    private static final String TAG = "SmartHomeActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // buildHome();


        IOTAHandlerRaspberry iotaHandler = new IOTAHandlerRaspberry();
        iotaHandler.processValueIota();
    }

    private void buildHome() {
        final String RED_PIN = "BCM4";
        final String BLUE_PIN = "BCM6";
        final String GREEN_PIN = "BCM16";
        PeripheralManager peripheralManager = PeripheralManager.getInstance();
        Gpio ledRed=null, ledBue=null, ledGreen=null;

        ledRed = UtilRaspberry.setConfigurationLed(peripheralManager, ledRed, RED_PIN);
        ledBue = UtilRaspberry.setConfigurationLed(peripheralManager, ledBue, BLUE_PIN);
        ledGreen = UtilRaspberry.setConfigurationLed(peripheralManager, ledGreen, GREEN_PIN);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("lighting_system");
        UtilRaspberry.setReferenceFirebase(ref, "SALA", ledRed);
        UtilRaspberry.setReferenceFirebase(ref, "COCINA", ledGreen);
        UtilRaspberry.setReferenceFirebase(ref, "DORMITORIO", ledBue);

    }


}
