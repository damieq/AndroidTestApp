package com.development.damian.testapp;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;

import java.io.File;

public class ApplicationState extends Application {
	private boolean Logged = false;
	private boolean Closing = false;
    private boolean DebugMode = false;  //Set true for display errors on screen (only for debug mode)
	public final String SHARED_PREFERENCES = "damian.testappandroid.SHARED_PREFERENCES";
	public final String KEY_MAP_VISIBLE = "damian.testappandroid.KEY_MAP_VISIBLE";
	private boolean ProcessWidGetPanic = false;
    private boolean isSmallDevice = false;
	private boolean PanicActive = false;
	private boolean OldVersion = false;
	private String PanicMode = "";
	private Boolean isGeoloc = false;
	private CountDownTimer countTimer;
	private String outputFile = Environment.getExternalStorageDirectory() + File.separator + ".taxiguardian" + File.separator;
	private String recordFileName;
	private MediaRecorder myRecorder;
	private Boolean isPopUpOn = false;
	private Boolean lockScreenDialogOpen = false;
	private boolean isRunWidget = false;
	private Handler hndDoActions;
	private Runnable rnaDoActions;
	private boolean NotificatioVisible = true;
	private int currentTripStateID = 0;
	private int stateGPS = 1;
	private int stateSignal = 1;
	private int timeToUpdateGps = 30000;
	private boolean isNewMessage = false;
	private int lastTripReceived = 0;
	
	
	public boolean isNewMessage() {
		return isNewMessage;
	}
	public void setNewMessage(boolean isNewMessage) {
		this.isNewMessage = isNewMessage;
	}
	public int getTimeToUpdateGps() {
		return timeToUpdateGps;
	}
	public boolean isNotificatioVisible() {
		return NotificatioVisible;
	}
	public void setNotificatioVisible(boolean notificatioVisible) {
		NotificatioVisible = notificatioVisible;
	}
    public Boolean getIsGeoloc() {
		return isGeoloc;
	}
	public void setIsGeoloc(Boolean isGeoloc) {
		this.isGeoloc = isGeoloc;
	}
	public Runnable getRnaDoActions() {
		return rnaDoActions;
	}
	public void setRnaDoActions(Runnable rnaDoActions) {
		this.rnaDoActions = rnaDoActions;
	}
	public Handler getHndDoActions() {
		return hndDoActions;
	}
	public void setHndDoActions(Handler hndDoActions) {
		this.hndDoActions = hndDoActions;
	}
	public boolean isRunWidget() {
		return isRunWidget;
	}
	public void setRunWidget(boolean isRunWidget) {
		this.isRunWidget = isRunWidget;
	}
	public Boolean getIsPopUpOn() {
		return isPopUpOn;
	}
	public void setIsPopUpOn(Boolean isPopUpOn) {
		this.isPopUpOn = isPopUpOn;
	}
	public MediaRecorder getMyRecorder() {
		return myRecorder;
	}
	public void setMyRecorder(MediaRecorder myRecorder) {
		this.myRecorder = myRecorder;
	}
	public String getRecordFileName() {
		return recordFileName;
	}
	public void setRecordFileName(String recordFileName) {
		this.recordFileName = recordFileName;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public CountDownTimer getCountTimer() {
		return countTimer;
	}
	public void setCountTimer(CountDownTimer countTimer) {
		this.countTimer = countTimer;
	}
	public boolean isClosingFragment() {
		return ClosingFragment;
	}
	public void setClosingFragment(boolean closingFragment) {
		ClosingFragment = closingFragment;
	}
	private boolean ClosingFragment = false;

	
	public boolean isSmallDevice() {
		return isSmallDevice;
	}
	public void setSmallDevice(boolean isSmallDevice) {
		this.isSmallDevice = isSmallDevice;
	}
	public boolean isLogged() {
		return Logged;
	}
	public void setLogged(boolean logged) {
		Logged = logged;
	}
	public boolean isClosing() {
		return Closing;
	}
	public void setClosing(boolean closing) {
		Closing = closing;
	}
	public boolean isDebugMode() {
		return DebugMode;
	}
	public boolean isGeolocationEnabled() {
		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	public boolean isGPSEnabled() {
		LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public boolean isExternalStorageEnabled() {
    	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
    		return true;
    	else
    		return false;
	}

	public boolean isProcessWidGetPanic() {
		return ProcessWidGetPanic;
	}
	public void setProcessWidGetPanic(boolean processWidGetPanic) {
		ProcessWidGetPanic = processWidGetPanic;
	}
	public boolean isPanicActive() {
		return PanicActive;
	}
	public void setPanicActive(boolean panicActive) {
		PanicActive = panicActive;
	}
	public boolean isOldVersion() {
		return OldVersion;
	}
	public void setOldVersion(boolean oldVersion) {
		OldVersion = oldVersion;
	}
	public String getPanicMode() {
		return PanicMode;
	}
	public void setPanicMode(String panicMode) {
		PanicMode = panicMode;
	}
	public int getCurrentTripStateID() {
		return currentTripStateID;
	}
	public void setCurrentTripStateID(int currentTripStateID) {
		this.currentTripStateID = currentTripStateID;
	}
	public int getStateGPS() {
		return stateGPS;
	}
	public void setStateGPS(int stateGPS) {
		this.stateGPS = stateGPS;
	}
	public int getStateSignal() {
		return stateSignal;
	}
	public void setStateSignal(int stateSignal) {
		this.stateSignal = stateSignal;
	}

	public void setLastTripReceived(int lastTripReceived) {
		this.lastTripReceived = lastTripReceived;
	}

	public int getLastTripReceived() {
		return lastTripReceived;
	}

	public Boolean isLockScreenDialogOpen() {
		return lockScreenDialogOpen;
	}

	public void setLockScreenDialogOpen(Boolean lockScreenDialogOpen) {
		this.lockScreenDialogOpen = lockScreenDialogOpen;
	}
}
