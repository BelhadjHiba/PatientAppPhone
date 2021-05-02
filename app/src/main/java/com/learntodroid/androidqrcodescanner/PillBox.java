package com.learntodroid.androidqrcodescanner;

import java.util.Scanner;

public class PillBox {
    private String boxID;
    private boolean boxState;
    private Slot slot;
    private int nbSlots;
    private boolean boxLed = false;
    private boolean isLocked = true;

    public PillBox(int nbSlots){
        setNbSlots(nbSlots);
    }
    public PillBox(String boxID, boolean boxState, int nbSlots) {
        setBoxID(boxID);
        setBoxState(boxState);
        setNbSlots(nbSlots);
    }

    public String getBoxID() {
        return boxID;
    }

    public void setBoxID(String boxID) {
        this.boxID = boxID;
    }

    public boolean isBoxState() {
        return boxState;
    }

    public void setBoxState(boolean boxState) {
        this.boxState = boxState;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public int getNbSlots() {
        return nbSlots;
    }

    public void setNbSlots(int nbSlots) {
        this.nbSlots = nbSlots;
        for (int i=0; i<nbSlots; i++){
            Slot slot = new Slot();
            slot.getSlotID();
        }
    }

    public boolean getBoxLed() {
        return boxLed;
    }

    public void setBoxLed(boolean boxLed) {
        this.boxLed = boxLed;
    }

    public void onAlarmSet(){
        do {
            isLocked = false;
            setBoxLed(true);
            Scanner myObj = new Scanner(System.in);
            setBoxState(myObj.nextBoolean());
        } while ( getBoxLed()== false);
        if (getBoxLed())
            setBoxLed(false);
    }


}
