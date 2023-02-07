package newsanalizer.usermanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Login {

    private static List<User> listUsers = loadDB();

    static Scanner scanner = new Scanner(System.in);

    public void doLogin() throws InterruptedException {

        // load from db the list of users

//        for(User u: listUsers)
//            System.out.println(u);

        // read from kb a user
        //while user from kb ! = a user in db stay here
        int maxTries = 1;
        boolean succes = false;
        User userKb = new User();
        do {
            System.out.print("username: ");
            String kbUsername = scanner.nextLine();
            System.out.print("pwd: ");
            String kbPwd = scanner.nextLine();
            System.out.print("analyst: ");
            String kbAnalyst = scanner.nextLine();
            System.out.print("admin: ");
            String kbAdmin = scanner.nextLine();

            userKb.setUsername(kbUsername);
            userKb.setPassword(kbPwd);
            userKb.setAnalyst(Boolean.parseBoolean(kbAnalyst));
            userKb.setAdmin(Boolean.parseBoolean(kbAdmin));

            for (User u : listUsers) {

                if (u.equalsUsers(userKb)) {
                    System.out.println("ok, you are logged in now");
                    succes = true;
                }

            }
            maxTries--;
            if (maxTries == 0) {
                maxTries = 1;
                System.out.println("you have to wait 10 seconds before you can try again :) ");
                TimeUnit.SECONDS.sleep(10);
            }
        }
        while (!succes);
        // when loggend in print menu based on parameters

        printMenu(userKb.isAnalyst(), userKb.isAdmin());

    }

    static void printMenu(boolean analyst, boolean admin) {
        if (analyst) {
            System.out.println("print news");
        }
        if (admin) {
            printAdminMeniu();
        }
    }
    // selectare pentru:
    // 1- print news
    // 2 - add user => new method addUser()
    // 2.1 - addUser() => input for username, pwd, isAnalyst, isAdmin

    public static void printAdminMeniu() {
        boolean cont = true;
        int option = -1;

        scanner = new Scanner(System.in);
        listUsers = new ArrayList<>();

        do {
            System.out.println("=== Select an Option ===");
            System.out.println("1. Add a User");
            System.out.println("2. Show all Users");
            System.out.println("0. Exit");

            System.out.print("\n  > ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                System.out.println("\n");

                switch (option) {
                    case 1:
                        addUser();
                        break;

                    case 2:
                        showUsers();
                        break;

                    case 0:
                        System.out.println("Good Bye!");
                        cont = false;
                        break;

                    default:
                        System.err.println("'" + option + "' is not a valid option. Please try again.\n");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid option selected.\n");
            }

        } while (cont == true);
    }


    public static void addUser() {

        System.out.println(">> Add User <<\n");

        System.out.print("Enter users name: ");
        String Name = scanner.nextLine();

        System.out.print("Enter user a password: ");
        String pwd = scanner.nextLine();

        User addUser = new User();
        addUser.setUsername(Name);
        addUser.setPassword(pwd);

        listUsers.add(addUser);
        System.out.println("\nUser added successfully!\n");
    }

    public static void showUsers() {
        System.out.println(">> All Customers <<\n");


        for (int i = 0; i < listUsers.size(); i++) {
            System.out.println(listUsers);
        }

        System.out.println("\n");
    }


    static void printNews() {

        // Still in progress

    }

    private static List<User> loadDB() {

        Path path = Paths.get("users.txt");
        List<User> listOfUsers = new ArrayList<>();
        try {
            List<String> listOfUsersAsStrings = Files.readAllLines(path);
            System.out.println(listOfUsersAsStrings);

            for (int i = 0; i < listOfUsersAsStrings.size(); i++) {
                User uObj = new User();
                String currentLineOfText = listOfUsersAsStrings.get(i);
                StringTokenizer st = new StringTokenizer(currentLineOfText, " ");
                while (st.hasMoreTokens()) {
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
