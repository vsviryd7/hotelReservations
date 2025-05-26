package ui;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;
public class AdminMenu {
    public static AdminResource adminResource = AdminResource.getSINGLETON();

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        String selection = "";
        System.out.print("MENU ADMIN: \n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "Please enter a number(1-5):\n");
        selection = scanner.next();
        switch (selection)
        {
            case "1":
                System.out.println("Your choices option 1(See all Customers).");
                checkAllCustomers();
                break;
            case "2":
                System.out.println("Your choices option 2(See all Rooms).");
                checkRooms();
                break;
            case "3":
                System.out.println("Your choices option 3(See all Reservations).");
                checkReservations();
                break;
            case "4":
                System.out.println("Your choices option 4(Add a Room).");
                addNewRooms();
                break;
            case "5":
                MainMenu.mainMenu();
                break;
            default:
                System.out.println("Error! Please enter a valid option\"");
                AdminMenu.adminMenu();
        }
    }
    public static void checkAllCustomers()
    {
        Collection<Customer> customerCollections = adminResource.getAllCustomers();
        if(customerCollections.isEmpty())
        {
            System.out.println("List of Customers is empty.");
            MainMenu.mainMenu();
        }
        else
        {
            Iterator<Customer> it = customerCollections.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

            adminMenu();
        }
    }
    public static void checkRooms()
    {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if(rooms.isEmpty())
        {
            System.out.println("Sorry. No available rooms");
            MainMenu.mainMenu();
        }
        else
        {
            Iterator<IRoom> it = rooms.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

            adminMenu();
        }
    }

    public static void addNewRooms()
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of new room: ");
        String room_no = scanner.nextLine();
        System.out.println("Enter the price per night: ");
        String roomPriceStr = scanner.next();
        double roomPrice = 0.0;
        try {
            roomPrice= Double.parseDouble(roomPriceStr);
        }
        catch(Exception e) {
            System.out.println("Error. Enter price again.");
            addNewRooms();
        }
        String roomTypeAdd ="Enter the room type: SINGLE for single bed, DOUBLE for double bed";
        System.out.println(roomTypeAdd);
        String roomTypeStr = scanner.next();
        if(!(roomTypeStr.contentEquals("SINGLE"))&!(roomTypeStr.contentEquals("DOUBLE"))){
            String invalidoutput="Error type  room ! Please enter words - SINGLE or DOUBLE: ";
            System.out.println(invalidoutput);
            addNewRooms();
        }
        RoomType room_type = RoomType.valueOf(roomTypeStr);
        Room room = new Room(room_no,roomPrice,room_type);
        System.out.println("New room added.");
        adminResource.addRoom(Collections.singletonList(room));
        try {
            requestMoreRoom();
        }catch (StringIndexOutOfBoundsException ex) {
            requestMoreRoom();
        }

    }
    public static void requestMoreRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to add another room (y/n): ");
        String request1 = scanner.next();
        char request = request1.charAt(0);
        while(request != 'y' && request!= 'n') {
            System.out.println("Enter y (yes) or n (no)");
            request = scanner.next().charAt(0);
        }
        if(request == 'y') {
            addNewRooms();
        }
        else if(request== 'n') {
            adminMenu();

        }
    }
    public static void checkReservations()
    {
        adminResource.displayAllReservations();
        MainMenu.mainMenu();
    }

}
