package ui;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
public class MainMenu {
    public static HotelResource hotelResource = HotelResource.getSINGLETON();

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        String selection = "";
        do {
            try {
                System.out.println("MENU MAIN: \n" +
                        "1. Find and reserve a room\n" +
                        "2. See my reservations\n" +
                        "3. Create an account\n" +
                        "4. Admin\n" +
                        "5. Exit\n" +
                        "Please enter a number(1-5): ");
                selection = scanner.next();

                switch (selection) {
                    case "1":
                        System.out.println("Your choices option 1(finding a room).");
                        findAndReserveARoom();
                        break;
                    case "2":
                        System.out.println("Your choices option 2(see reservation).");
                        seeMyReservations();
                        break;
                    case "3":
                        System.out.println("Your choices option 3(create account).");
                        createAccount();
                        break;
                    case "4":
                        System.out.println("Your choices option 4(Admin menu).");
                        AdminMenu.adminMenu();
                        break;
                    case "5":
                        System.out.println("Exit program.");
                        break;
                    default:
                        System.out.println("Error.Please enter a valid option");
                        break;
                }
            } catch (NoSuchElementException exception) {
                System.out.println("Problem error.Contact support");

            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!selection.equals("4") && !selection.equals("5"));
    }
    public static void requestReserveRoom(Date checkIn, Date checkOut, Collection<IRoom> rooms)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to reserve room (yes/no)");
        String addRoom=scanner.next();
        boolean decide = false;
        while(!(addRoom.equals("yes") || addRoom.equals("no"))) {
            System.out.println("Enter yes or no");
            addRoom = scanner.next();
        }
        if(addRoom.equals("yes")) {
            System.out.println("Do you have an account? yes/no");
            String user = scanner.next();
            while(!(user.equals("yes")|| user.equals("no"))) {
                String optional="Enter yes or no";
                System.out.println(optional);
                user = scanner.next();
            }
            customerfunction(user,decide,checkIn,checkOut,rooms);
        }
        else
        {
            mainMenu();
        }

    }
    public static void customerfunction(String user,boolean decide,Date checkIn, Date checkOut, Collection<IRoom> rooms) {
        Scanner scanner = new Scanner(System.in);
        if(user.equals("yes"))
        {
            System.out.println("Enter email ID: ");
            String mail = scanner.next();
            try {
                hotelResource.getCustomer(mail);
                String reserveroom="Enter room number to reserve:";
                System.out.println(reserveroom);
                String roomNo = scanner.next();
                Iterator<IRoom> it = rooms.iterator();
                while (it.hasNext()) {
                    if(it.next().getRoomNumber().equals(roomNo))
                    {
                        IRoom emptyRoom = hotelResource.getRoom(roomNo);
                        decide = true;
                        Reservation reservation = hotelResource.bookARoom(mail,emptyRoom,checkIn,checkOut);
                        finaloutput(reservation);
                    }
                }

                if(decide == false)
                {
                    System.out.println("room numer invalid.please select from available rooms");
                    requestReserveRoom(checkIn,checkOut,rooms);
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Email not in list!! Create a new account");
            }

            mainMenu();
        }
        else
        {
            System.out.println("Create an account!!");
            mainMenu();
        }
    }
    public static void finaloutput(Reservation reservation) {
        System.out.println("Reservation completed.\n");
        System.out.println(reservation);

    }

    public static void findAndReserveARoom() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

        System.out.print("Enter the check-in date (dd/MM/yyyy): ");
        String checkInString = scanner.nextLine();

        System.out.print("Enter the check-out date (dd/MM/yyyy): ");
        String checkOutString = scanner.nextLine();

        try {
            Date checkInDate = dateFormat.parse(checkInString);
            Date checkOutDate = dateFormat.parse(checkOutString);

            dateFormat.setTimeZone(timeZone);
            System.out.println("Current date in the USA: " + dateFormat.format(new Date()));
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate,checkOutDate);
            boolean isAvailable = availableRooms.isEmpty();
            if (isAvailable) {
                System.out.println("The room is available for the given dates.");
                requestReserveRoom(checkInDate,checkOutDate,availableRooms);
            } else {
                System.out.println("The room is not available for the given dates.");
                System.out.println("Searching for recommended rooms...");
                Date newCheckIn = addDays(checkInDate, 7);
                Date newCheckOut = addDays(checkOutDate, 7);

                boolean newAvailability = availableRooms.isEmpty();
                if (newAvailability) {
                    System.out.println("Recommended room is available for the dates: " + dateFormat.format(newCheckIn) + " to " + dateFormat.format(newCheckOut));
                } else {
                    System.out.println("No recommended rooms are available at this time.");
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
        }
    }

    private static boolean checkAvailability(Date checkIn, Date checkOut) {

        return true;
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static void seeMyReservations()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("For check your reservation enter your email: ");
        Collection<Reservation> myReserv = Collections.emptyList();
        String email = scanner.nextLine();
        myReserv = hotelResource.getCustomersReservations(email);
        try {
            if(myReserv == null){
                System.out.println("No reservations found for that user");
                mainMenu();
            }
            else {
                Iterator<Reservation> it = myReserv.iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }

                mainMenu();
            }
        }
        catch (IllegalArgumentException e){
            System.out.print(e);
        }
    }
    public static void createAccount()
    {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter first name: ");
            String fname = scanner.nextLine();
            System.out.println("Enter last name: ");
            String lname = scanner.nextLine();
            System.out.println("Enter email(email format example:j@domain.com): ");
            String email = scanner.nextLine();
            hotelResource.createACustomer(email,fname,lname);
            System.out.println("Account created!");
            mainMenu();
        }
        catch (Exception e){
            System.out.println("Error.Try again (enter correct email):");
            mainMenu();
        }

    }



}