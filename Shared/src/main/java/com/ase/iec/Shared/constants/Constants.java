package com.ase.iec.Shared.constants;

public class Constants {
	
	public static final int TICKER_INTERVALL_STANDARD_SECONDS = 60*10; //sec
	public static final int TICKER_INTERVALL_PREMIUM_SECONDS = 60*5; //sec 
	
	public static final double PREMIUM_STANDARD_FAKTOR = TICKER_INTERVALL_STANDARD_SECONDS / TICKER_INTERVALL_PREMIUM_SECONDS;
	
	
	public static final int SCHEDULE_INTERVALL = 10; //sec 

	public static final int SERVICE_MAN_NOTIFICATION = 10; //tickcount
	
	public static int HOUR_SUNRISE = 7;
	public static int HOUR_SUNSET = 20;
}
