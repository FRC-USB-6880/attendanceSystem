package com.frcusb6880.attendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Calendar calendar;
    private Date date;
    private Date time;
    private long fullInTime;
    private long fullOutTime;
    private int[] formalInTime;
    private int[] formalOutTime;
    private ArrayList<String> prevTimes;
    private boolean signedIn = false;

    public int getDifference() {
        return difference;
    }

    private int difference=0;


    public Member(String fName, String lName){
        this.fName = fName;
        this.lName = lName;
        prevTimes = new ArrayList<>();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("MM-dd-yy");
        calendar = new GregorianCalendar();
    }

    public void signIn() {
        formalInTime = new int[]{calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};
        fullInTime = calendar.getTimeInMillis();
        signedIn = true;
        System.out.println(fullInTime);
    }

    public boolean signOut(){
        if (signedIn) {
            formalOutTime = new int[]{calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)};
            fullOutTime = calendar.getTimeInMillis();
            difference = (int) ((fullOutTime - fullInTime) / 60000);
            System.out.println(fullOutTime);
            signedIn = false;
            return true;
        }
        System.out.println("Error: Not signed in");
        return false;
    }

    public String getMeetingSaveOutput(){
        String line = fName + "," + lName + "," + formalInTime[0]+":"+formalInTime[1]+":"+formalInTime[2] + "," + ((formalOutTime!=null)?formalOutTime[0]:"N/A")+":"+((formalOutTime!=null)?formalOutTime[1]:"N/A")+":"+((formalOutTime!=null)?formalOutTime[2]:"N/A") + "," + difference +"\n";
        return line;
    }

    public String getRosterSaveOutput(){
        String line = fName + "," + lName;
        for (int i = 0; i < prevTimes.size(); i++) {
            line += "," + prevTimes.get(i);
        }
        line += "," + ((difference != 0) ? difference : " ") + "\n";
        return line;
    }

    public void addPrevTime(String timeStr) {
        prevTimes.add(timeStr);
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public static ArrayList<Member> sortMembersLname(ArrayList<Member> members) {
        ArrayList<Member> tmp = new ArrayList<>();

        for (Member member : members) {
            if (tmp.size() == 0) {
                tmp.add(member);
                continue;
            }

            boolean inserted = false;
            for (int i = 0; i < tmp.size(); i++) {
                if (member.getlName().compareTo(tmp.get(i).getlName()) <= 0) {
                    tmp.add(i, member);
                    inserted = true;
                    break;
                }
            }

            if (!inserted) {
                tmp.add(member);
            }
        }
        return tmp;
    }

}
