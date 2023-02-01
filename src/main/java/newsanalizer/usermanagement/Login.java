package newsanalizer.usermanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Login {

    public void doLogin() {

        // load from db the list of users
        List<User> listUsers = loadDB();
//        for(User u: listUsers)
//            System.out.println(u);

        // read from kb a user
        //while user from kb ! = a user in db stay here

        boolean succes = false;
        do {
            System.out.println("username:");
            String kbUsername = new Scanner(System.in).nextLine();
            System.out.println("pwd:");
            String kbPwd = new Scanner(System.in).nextLine();

            User userKb = new User();
            userKb.setUsername(kbUsername);
            userKb.setPassword(kbPwd);

            for(User u: listUsers) {

                if(u.equalsUsers(userKb))
                {
                    System.out.println("ok, you are logged in now");
                    succes=true;
                }
            }

        }
        while(!succes);



    }

    private List<User> loadDB() {

        Path path= Paths.get("users.txt");
        List<User> listOfUsers = new ArrayList<>();
        try {
            List<String> listOfUsersAsStrings= Files.readAllLines(path);
            System.out.println(listOfUsersAsStrings);

            for(int i = 0; i<listOfUsersAsStrings.size();i++) {
                User uObj = new User();
                String currentLineOfText = listOfUsersAsStrings.get(i);
                StringTokenizer st = new StringTokenizer(currentLineOfText, ",");
                while(st.hasMoreTokens()) {
                    String u = st.nextToken();
                    String p = st.nextToken();


                    uObj.setUsername(u.trim());
                    uObj.setPassword(p.trim());


                }
                listOfUsers.add(uObj);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }

        return listOfUsers;
    }

}
