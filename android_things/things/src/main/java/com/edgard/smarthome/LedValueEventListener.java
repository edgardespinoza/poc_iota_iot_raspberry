package com.edgard.smarthome;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class LedValueEventListener implements ValueEventListener {

    private final Gpio led;

    public LedValueEventListener(Gpio led) {
        this.led = led;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Boolean on = (Boolean) dataSnapshot.getValue();
        try {
            led.setValue(on == null ? false : on);
        } catch (IOException e) {
            Log.e("TAG", "Error.onDataChange", e);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e("Switch", "Failed to read value.", databaseError.toException());
    }
}
