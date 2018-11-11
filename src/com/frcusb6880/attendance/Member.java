package com.frcusb6880.attendance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Member {
    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    private String fName;
    private String lName;
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;
    private Date date;
    private Date time;
    private long fullInTime;
    private long fullOutTime;
    private int[] formalInTime;
    private int[] formalOutTime;

    public int getDifference() {
        return difference;
    }

    private int difference=0;


    public Member(String fName, String lName){
        this.fName = fName;
        this.lName = lName;
        Calendar calendar = new GregorianCalendar();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("MM-dd-yy");

        formalInTime = new int[]{calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};
        fullInTime = calendar.getTimeInMillis();
        System.out.println(fullInTime);
    }

    public void signOut(){
        Calendar calendar = new GregorianCalendar();
        formalOutTime = new int[]{calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};
        fullOutTime = calendar.getTimeInMillis();
        difference = (int)((fullOutTime-fullInTime)/60000);
        System.out.println(fullOutTime);
    }

    public String getSaveOutput(){
        String line = fName + "," + lName + "," + formalInTime[0]+":"+formalInTime[1]+":"+formalInTime[2] + "," + ((formalOutTime!=null)?formalOutTime[0]:"N/A")+":"+((formalOutTime!=null)?formalOutTime[1]:"N/A")+":"+((formalOutTime!=null)?formalOutTime[2]:"N/A") + "," + difference +"\n";
        return line;
    }
}
