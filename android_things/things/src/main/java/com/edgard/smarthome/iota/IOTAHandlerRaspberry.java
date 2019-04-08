package com.edgard.smarthome.iota;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class IOTAHandlerRaspberry {

    public static DatabaseReference databaseReference_payment;
    public static DatabaseReference databaseReference_tx_init;
    public static DatabaseReference value_tx;
    public static DatabaseReference databaseReference_tx_green, databaseReference_tx_red;
    private Gpio ledRed = null, ledGreen = null, ledBlue = null;

//    private static final String BM4_PIN = "BCM3";

    public void processLed() {
        final String BM26_PIN = "BCM26";
        final String BM4_PIN = "BCM4";
        final String BM2_PIN = "BCM2";

        PeripheralManager peripheralManager = PeripheralManager.getInstance();

        ledRed = UtilRaspberry.setConfigurationLed(peripheralManager, ledRed, BM26_PIN);
        ledGreen = UtilRaspberry.setConfigurationLed(peripheralManager, ledGreen, BM4_PIN);
        ledBlue = UtilRaspberry.setConfigurationLed(peripheralManager, ledBlue, BM2_PIN);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        DatabaseReference ref = database.getReference("iota");
//        UtilRaspberry.setReferenceFirebase(ref, "TX_RED", ledRed);
//        UtilRaspberry.setReferenceFirebase(ref, "TX_GREEN", ledBlue);

    }


    public void processValueIota() {
        processLed();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        final DatabaseReference reference = database.getReference("iota");

        final DatabaseReference url = reference.child("SERVER_NAME_IOTA");

        url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UtilIO.SERVER = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference address = reference.child("DESTINITY_ADDRESS");

        address.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UtilIO.TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_2 = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference seed1 = reference.child("SEED_ORIGIN");
        seed1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UtilIO.TEST_SEED1 = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        value_tx = reference.child("TX_VALUE");
        value_tx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String value = ((String) dataSnapshot.getValue());
                    UtilIO.TX_VALUE = Integer.parseInt(value);

                } catch (Exception e) {
                    UtilIO.TX_VALUE = 0;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference_payment = reference.child("TX_PAYMENT");




        databaseReference_tx_init = reference.child("TX_INIT");
        databaseReference_tx_init.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean value = (Boolean) dataSnapshot.getValue();
                try {
                    ledBlue.setValue(value == null ? false : value);
                } catch (IOException e) {
                    Log.e("TAG", "Error.onDataChange", e);
                }

                if (value) {
                    procesarPago();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference_tx_red = reference.child("TX_RED");
        databaseReference_tx_red.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean value = (Boolean) dataSnapshot.getValue();
                try {
                    ledRed.setValue(value == null ? false : value);
                } catch (IOException e) {
                    Log.e("TAG", "Error.onDataChange", e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference_tx_green = reference.child("TX_GREEN");
        databaseReference_tx_green.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean value = (Boolean) dataSnapshot.getValue();
                try {
                    ledGreen.setValue(value == null ? false : value);
                } catch (IOException e) {
                    Log.e("TAG", "Error.onDataChange", e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void procesarPago() {
        IOTAHandlerRaspberry.databaseReference_payment.setValue("...");
        new Thread(new Runnable() {
            public void run() {
                try {
                   // UtilIO.connectNode();
                    UtilIO.connectIOTA();
//                    UtilIO.shouldSendTransferWithoutInputs();
                    databaseReference_tx_green.setValue(true);
                    databaseReference_tx_red.setValue(false);
                    IOTAHandlerRaspberry.databaseReference_payment.setValue("OK");
                    databaseReference_tx_init.setValue(false);
//                            recOk.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    databaseReference_tx_init.setValue(false);
                    databaseReference_tx_green.setValue(false);
                    databaseReference_tx_red.setValue(true);

                    IOTAHandlerRaspberry.databaseReference_payment.setValue("FAIL");

//                            recFail.setVisibility(View.VISIBLE);
                    Log.e("TAG", "Error", e);
                }
            }
        }).start();
    }


}
