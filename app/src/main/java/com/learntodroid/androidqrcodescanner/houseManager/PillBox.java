package com.learntodroid.androidqrcodescanner.houseManager;

import java.util.Scanner;

public class PillBox {
    private String boxID;
    private boolean boxState = false;
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

    public PillBox() {
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

    public boolean onAlarmSet(){
        isLocked = false;
        do {
            boxLed = true;
            Scanner myObj = new Scanner(System.in);
            boxState = myObj.nextBoolean();
        } while (boxState == false);
        if (boxState == true){
            boxLed = false;
            return true;
        } else return false;

    }


}
