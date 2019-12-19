package com.frcusb6880.attendance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AttendanceForm {
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JButton submitButton;
    private JPanel formField;
    private JLabel status;
    private ArrayList<Member> members;
    private ArrayList<String> names=null;
    private MemberListReader memberListReader=null;
    private RosterFileFinder rosterFileFinder;

    public void setDir(String dir, String filename, JLabel label) {
        this.dir = dir;
        this.filename = filename;
        memberListReader = new MemberListReader(dir, filename, label, members);
        names = memberListReader.getNames();
    }

    private String dir ="";
    private String filename = "";

    public AttendanceForm(){
        rosterFileFinder = new RosterFileFinder(this);
        rosterFileFinder.createWindow(this);
        submitButton.addActionListener(new SubmitButtonClicked());
        lastNameTextField.addActionListener(new EnterClicked());
        members = new ArrayList<Member>();
    }

    private class EnterClicked implements ActionListener{
        private String fName;
        private String lName;

        public EnterClicked(){}

        @Override
        public void actionPerformed(ActionEvent e){
            fName = firstNameTextField.getText();
            lName = lastNameTextField.getText();
            processForm(fName, lName);
        }
    }

    private class SubmitButtonClicked implements ActionListener{
        private String fName;
        private String lName;

        public SubmitButtonClicked(){
        }

        @Override
        public void actionPerformed(ActionEvent e){
            fName = firstNameTextField.getText();
            lName = lastNameTextField.getText();
            processForm(fName, lName);
        }
    }

    private void processForm(String fName, String lName){
        ArrayList<String> memberNames = new ArrayList<String>();
        for (int i=0;i<members.size();i++){
            memberNames.add(members.get(i).getfName()+","+members.get(i).getlName());
        }
        if(fName.equalsIgnoreCase("end")&&lName.equalsIgnoreCase("end")){
            memberListReader.saveMembers(members);
            status.setText("Saved");
            System.exit(0);
        }
        Member member = getMember(fName, lName);
        if (member == null) {
            ConfirmName dialog = new ConfirmName(this, fName, lName);
            dialog.pack();
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            status.setText("Enter Name");
            firstNameTextField.setText("");
            lastNameTextField.setText("");
        } else if (member.isSignedIn()){
            member.signOut();
            System.out.println("Signing out "+fName+" "+lName);
            status.setText("Signed Out");
            status.setText("Enter Name");
            firstNameTextField.setText("");
            lastNameTextField.setText("");
        } else {
            System.out.println("Signing in "+fName+" "+lName);
            member.signIn();
            status.setText("Success!");
            status.setText("Enter Name");
            firstNameTextField.setText("");
            lastNameTextField.setText("");
        }
    }

    public void addName(String first, String last){
        Member m = new Member(first, last);
        m.signIn();
        System.out.println("Added "+first+" "+last+" to roster and signed in");
        status.setText("Success!");
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Attendance Form");
        frame.setContentPane(new AttendanceForm().formField);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Member getMember(String fName, String lName) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getfName().equalsIgnoreCase(fName) && members.get(i).getlName().equalsIgnoreCase(lName)) {
                return members.get(i);
            }
        }
        return null;
    }
}
