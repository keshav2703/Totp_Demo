package com.keshav.TOTP_Demo.Constraints;


public class TotpConstraints {
    public static final String LOCK = "Lock";
    public static final String UNLOCKED = "User Unlocked Successfully";
    public static final String NOT_UNLOCKED = "User Unlock Failed";
    public static final String DEFAULTOTP = "000000";
    public static String ACTIVATE="Active";
    public static String SUCCESS="Success";
    public static String FAILED="Failed";
    public static String USER_ADDED="User Added to Central DB";
    public static String USER_NOT_ADDED="User Not Able to Add Central DB";
    public static String InvalidAttempt="First Invalid attempt";
    public static String InvalidAttempt2="Second Invalid attempt";
    public static String InvalidAttempt3="Third Invalid attempt, Your account is Locked";
    public static String AccLock="Your account is locked, kindly reset your password";
    public static String ACTIVATED = "User Successfully Activated.";
    public static String REACTIVATED = "User Successfully Reactivated.";
    public static String NOTFOUND = "Incorrect UserID Password";
    public static final long TIME_STEP = 30;
    public static final int OTP_DIGITS = 6;

    public static String Authenticated = "Authenticated";
    public static String Reused = "OTP Already used. Kindly use new OTP";
}
