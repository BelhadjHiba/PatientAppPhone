package com.learntodroid.androidqrcodescanner;

import java.util.Scanner;

public class Drawer {
    private String drawerID = "MedDrawer";
    private boolean drawerLed = false;
    private boolean drawerState = false;
    private boolean isLocked = true;

    public Drawer(){}

    public boolean getDrawerLed() {
        return drawerLed;
    }

    public void setDrawerLed(boolean drawerLed) {
        this.drawerLed = drawerLed;
    }

    public boolean getDrawerState() {
        return drawerState;
    }

    public void setDrawerState(boolean drawerState) {
        this.drawerState = drawerState;
    }

    public void onAlarmSet(){
        do {
            isLocked = false;
            setDrawerLed(true);
            Scanner myObj = new Scanner(System.in);
            setDrawerState(myObj.nextBoolean());
        } while ( getDrawerLed()== false);
        if (getDrawerLed())
            setDrawerLed(false);
    }
}
