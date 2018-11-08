import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MemberListReader {
    private BufferedReader reader;
    private BufferedWriter attendanceWriter;
    private File rosterFile;
    private Calendar calendar;

    public ArrayList<String> getNames() {
        return names;
    }

    private ArrayList<String> names;

    public MemberListReader(String filename){
        calendar = new GregorianCalendar();
        rosterFile = new File(filename);
        names = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(rosterFile));
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null){
                names.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean findName(ArrayList<String> arr, String f, String l){
        String[] fNamesArr = new String[arr.size()];
        String[] lNamesArr = new String[arr.size()];
        for(int i=0;i<fNamesArr.length;i++){
            String[] temp = arr.get(i).split(",");
            fNamesArr[i] = temp[0];
            lNamesArr[i] = temp[1];
        }
//        int fIndex = binarySearch(fNamesArr, 0, fNamesArr.length-1, f);
//        int lIndex = binarySearch(lNamesArr, 0, lNamesArr.length-1, l);
        int fIndex = nameSearch(fNamesArr, f);
        int lIndex = nameSearch(lNamesArr, l);

        if(fIndex<0 || lIndex<0){
            System.out.println(f+" "+l+" not found");
            return false;
        }
        System.out.println(f+" "+l+" found");
        return true;
    }

    public void saveMembers(ArrayList<Member> members){
        try {
            File file = new File("C:\\Users\\pb8xe\\IdeaProjects\\attendanceSystem\\src\\attendance\\attendance"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE)+"-"+calendar.get(Calendar.YEAR)+".csv");
            attendanceWriter = new BufferedWriter(new FileWriter(file));
            for (Member m : members) {
                String line = m.getSaveOutput();
                attendanceWriter.write(line);
            }
            attendanceWriter.flush();
            attendanceWriter.close();

            String[] fMembersArr = new String[members.size()];
            String[] lMembersArr = new String[members.size()];
            for(int i=0;i<fMembersArr.length;i++){
                fMembersArr[i] = members.get(i).getfName();
                lMembersArr[i] = members.get(i).getlName();
            }
            String[] fNamesArr = new String[names.size()];
            String[] lNamesArr = new String[names.size()];
            for(int i=0;i<fNamesArr.length;i++){
                String[] temp = names.get(i).split(",");
                fNamesArr[i] = temp[0];
                lNamesArr[i] = temp[1];
            }
            for (int i=0;i<fNamesArr.length;i++){
                int fIndex = nameSearch(fMembersArr, fNamesArr[i]);
                int lIndex = nameSearch(lMembersArr, lNamesArr[i]);
                String[] arr = names.get(i).split(",");
                if (fIndex>=0 && lIndex>=0){
                    arr[2]=""+(Integer.parseInt(arr[2])+1);
                    int difference = members.get(fIndex).getDifference();
                    arr[4]=""+(Integer.parseInt(arr[4])+difference);
                }
                else {
                    arr[3]=""+(Integer.parseInt(arr[3])+1);
                }
                String line = arr[0]+","+arr[1]+","+arr[2]+","+arr[3]+"\n";
                names.set(i, line);
            }
            BufferedWriter rosterWriter = new BufferedWriter(new FileWriter(rosterFile));
            for (String n : names){
                rosterWriter.write(n);
            }
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
