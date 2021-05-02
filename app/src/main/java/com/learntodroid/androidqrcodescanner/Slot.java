package com.learntodroid.androidqrcodescanner;

import java.util.Scanner;

public class Slot {
    private int slotID = 0;
    private boolean slotLed = false;
    private boolean slotState = false;
    private boolean isLocked = true;

    public Slot(){}
    public Slot( boolean slotLed, boolean slotState) {
        setSlotLed(slotLed);
        setSlotState(slotState);
    }


    public int getSlotID() {
        return slotID;
    }

    public void setSlotID() {
        this.slotID++;
    }

    public boolean isSlotLed() {
        return slotLed;
    }

    public void setSlotLed(boolean isLed) {
        this.slotLed = isLed;
    }

    public boolean getSlotState() {
        return slotState;
    }

    public void setSlotState(boolean isSelected) {
        if (isSelected && isLocked == false)
            this.slotState = true;
    }
    public void onAlarmStarted(int selectedID){
        do {
            isLocked = false;
            setSlotLed(true);
            Scanner myObj = new Scanner(System.in);
            setSlotState(myObj.nextBoolean());
        } while (getSlotState() == false);
        if (getSlotState())
            setSlotLed(false);
    }
}
