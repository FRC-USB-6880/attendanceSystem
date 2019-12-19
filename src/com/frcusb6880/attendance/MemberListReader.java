package com.frcusb6880.attendance;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MemberListReader {
    private String filePath;
    private Calendar calendar;
    private String dir;

    public ArrayList<String> getNames() {
        return names;
    }

    private ArrayList<String> names;

    public MemberListReader(String dir, String filename, JLabel label, ArrayList<Member> members){
        calendar = new GregorianCalendar();
        this.dir = dir;
        filePath = dir+filename;
        File rosterFile = new File(filePath);
        names = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rosterFile));
            String line = reader.readLine();
            while(line != null){
                names.add(line);
                line = reader.readLine();

                String[] cols = line.split(",");
                Member member = new Member(cols[0], cols[1]);
                if (cols.length > 2) {
                    for (int i = 2; i < cols.length; i++){
                        member.addPrevTime(cols[i]);
                    }
                }
                members.add(member);
            }
            reader.close();
            label.setText("Success!");
        } catch (Exception e) {
            label.setText("File "+dir+filename+" not found");
            e.printStackTrace();
        }
    }

    public void saveMembers(ArrayList<Member> members){
        members = Member.sortMembersLname(members);
        try {
            File meetingFile = new File(dir+"attendance"+calendar.get(Calendar.MONTH)+"-"+(calendar.get(Calendar.DATE)+1)+"-"+calendar.get(Calendar.YEAR)+".csv");
            BufferedWriter attendanceWriter = new BufferedWriter(new FileWriter(meetingFile));

            File rosterFile = new File(filePath);
            BufferedWriter rosterWriter = new BufferedWriter(new FileWriter(rosterFile));

            for (Member m : members) {
                String meetingOutput = m.getMeetingSaveOutput();
                attendanceWriter.write(meetingOutput);

                String rosterOutput = m.getRosterSaveOutput();
                rosterWriter.write(rosterOutput);
            }
            attendanceWriter.flush();
            attendanceWriter.close();

            rosterWriter.flush();
            rosterWriter.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private int nameSearch(String[] arr, String target){
        for(int i=0;i<arr.length;i++){
            if (arr[i].equalsIgnoreCase(target)){
                return i;
            }
        }
        return -1;
    }
}
