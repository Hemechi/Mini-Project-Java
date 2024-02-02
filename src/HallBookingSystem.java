import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class HallBookingSystem {
    static int totalRows;
    static int totalSeatsPerRow;
    static String[][] seatsMorning;
    static String[][] seatsAfterrnoon;
    static String[][] seatsNight;
    static String Hall;
    static String[] bookingHistory = new String[50];
    static int bookingCount = 0;
    public static void configureHall() {
        Scanner scanner = new Scanner(System.in);
        String regex = "\\d+";

        System.out.print("> Config total rows in a hall: ");
        String input = scanner.nextLine();

        while (!input.matches(regex)) {
            System.out.println("Invalid input. Please enter a positive integer.");
            System.out.print("> Config total rows in a hall: ");
            input = scanner.nextLine();
        }

        int totalRows = Integer.parseInt(input);

        System.out.print("> Config total seats per row in a hall: ");
        input = scanner.nextLine();

        while (!input.matches(regex)) {
            System.out.println("Invalid input. Please enter a positive integer.");
            System.out.print("> Config total seats per row in a hall: ");
            input = scanner.nextLine();
        }

        int totalSeatsPerRow = Integer.parseInt(input);

        seatsMorning = new String[totalRows][totalSeatsPerRow];
        seatsAfterrnoon = new String[totalRows][totalSeatsPerRow];
        seatsNight = new String[totalRows][totalSeatsPerRow];
        initializeSeats();
        System.out.println("\nHall configured successfully!");
    }

    public static void initializeSeats() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalSeatsPerRow; j++) {
                seatsMorning[i][j] = "AV";
                seatsAfterrnoon[i][j] = "AV";
                seatsNight[i][j] = "AV";
            }
        }
    }

    public static void displayHallA(String[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                char row = (char) ('A' + i);
                String seatStatus = seats[i][j] == null ? "AV" : seats[i][j];
                System.out.print("|" + row + "-" + (j + 1) + "::" + seatStatus + "| ");
            }
            System.out.println();
        }
    }

    public static void displayHallB(String[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                char row = (char) ('A' + i);
                String seatStatus = seats[i][j] == null ? "AV" : seats[i][j];
                System.out.print("|" + row + "-" + (j + 1) + "::" + seatStatus + "| ");
            }
            System.out.println();
        }
    }

    public static void displayHallC(String[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                char row = (char) ('A' + i);
                String seatStatus = seats[i][j] == null ? "AV" : seats[i][j];
                System.out.print("|" + row + "-" + (j + 1) + "::" + seatStatus + "| ");
            }
            System.out.println();
        }
    }

    public static void bookSeat() {
        System.out.println("+-".repeat(25));
        System.out.println("# Start booking process");
        System.out.println("+-".repeat(25));
        System.out.println("# Showtime Information");
        showtime();
        Scanner scanner = new Scanner(System.in);
        System.out.println("+-".repeat(25));
        System.out.print("> Please select show time ( A | B | C ): ");
        String showtimeOption = scanner.next().toUpperCase();
        System.out.println("+-".repeat(25));
        if (showtimeOption.matches("[A-C]")) {
            switch (showtimeOption) {
                case "A" -> {
                    System.out.println("# HALL A");
                    Hall="A";
                    displayHallA(seatsMorning);
                    System.out.println("+-".repeat(25));

                    System.out.println("# INSTRUCTION");
                    System.out.println("# Single: C-1");
                    System.out.println("# Multiple (separate by comma): C-1, C-2");
                    bookSeatsInHall(seatsMorning,Hall);
                }
                case "B" -> {
                    System.out.println("# HALL B");
                    displayHallB(seatsAfterrnoon);
                    Hall="B";
                    System.out.println("+-".repeat(25));

                    System.out.println("# INSTRUCTION");
                    System.out.println("# Single: C-1");
                    System.out.println("# Multiple (separate by comma): C-1, C-2");
                    bookSeatsInHall(seatsAfterrnoon,Hall);
                }
                case "C" -> {
                    System.out.println("# HALL C");
                    displayHallC(seatsNight);
                    System.out.println("+-".repeat(25));
                    Hall="C";
                    System.out.println("# INSTRUCTION");
                    System.out.println("# Single: C-1");
                    System.out.println("# Multiple (separate by comma): C-1, C-2");
                    bookSeatsInHall(seatsNight,Hall);
                }
            }
        } else {
            System.out.println("Invalid showtime option. Please try again.");
        }
    }
    public static void bookSeatsInHall(String[][] seats, String hall) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> Please select available seat: ");
        String input = scanner.nextLine().toUpperCase();

        String[] seatInputs = input.split(",");

        int validSeatCount = 0;
        String[] validSeatInputs = new String[seatInputs.length];

        for (String s : seatInputs) {
            String seatInput = s.trim();
            if (seatInput.matches("[A-Z]-\\d+")) {
                validSeatInputs[validSeatCount++] = seatInput;
            } else {
                System.out.println("+-".repeat(25));
                System.out.println("Invalid seat format: " + seatInput);
            }
        }

        if (validSeatCount == 0) {
            System.out.println("No valid seats entered.");
            System.out.println("+-".repeat(25));
            return;
        }

        System.out.print("> Please enter student ID: ");
        String studentId = scanner.next();
        System.out.println("+-".repeat(25));
        System.out.print("> Are you sure to book? (Y/N): ");
        String confirmation = scanner.next().toUpperCase();
        System.out.println("+-".repeat(25));

        if (confirmation.equals("Y")) {
            for (int i = 0; i < validSeatCount; i++) {
                String seatInput = validSeatInputs[i];
                String rowStr = seatInput.replaceAll("(\\w)-(\\d+)", "$1");
                char row = rowStr.charAt(0);
                int col = Integer.parseInt(seatInput.replaceAll("(\\w)-(\\d+)", "$2")) - 1;

                if (row >= 'A' && row <= 'A' + seats.length - 1 && col >= 0 && col <= seats[row - 'A'].length - 1) {

                    if (seats[row - 'A'][col] == null) {
                        System.out.println("# [" + seatInput + "]" + " booked successfully");
                        System.out.println("+-".repeat(25));
                        bookingHistory(input, studentId, hall);
                        seats[row - 'A'][col] = "BO";
                        break;
                    } else {
                        System.out.println("Seat [" + seatInput + "] is already booked.");
                        System.out.println("+-".repeat(25));
                    }
                } else {
                    System.out.println("Invalid seat: " + seatInput);
                }
            }
        } else {
            System.out.println("Booking canceled.");
        }
    }
    public static void searchHalls() {
        System.out.println("+-".repeat(25));
        System.out.println("# Hall Information");
        System.out.println("+-".repeat(25));
        System.out.println("# HALL - Morning : ");
        displayHallA(seatsMorning);
        System.out.println("+-".repeat(25));
        System.out.println("# HALL - Afternoon : ");
        displayHallB(seatsAfterrnoon);
        System.out.println("+-".repeat(25));
        System.out.println("# HALL - Night : ");
        displayHallC(seatsNight);
        System.out.println("+-".repeat(25));
    }

    public static void showtime(){
        System.out.println("# A) Morning (10:00AM - 12:30PM)");
        System.out.println("# B) Afternoon (03:00PM - 05:30PM)");
        System.out.println("# C) Night (07:00PM - 09:30PM)");
    }
    public static void rebootShowtime(String[][] seats) {
        bookingCount = 0;
        for (String[] seat : seats) {
            Arrays.fill(seat, null);
        }
    }
    static void bookingHistory(String seats, String studentId, String hallName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String bookingDetails = "Seat(s): " + seats + ", Student ID: " + studentId + ", Timestamp: " + timestamp + ", Hall: " + hallName;
        bookingHistory[bookingCount++] = bookingDetails;
    }
    public static void viewHistory() {
        if (bookingCount == 0) {
            System.out.println("+-".repeat(25));
            System.out.println("There is no history yet...");
            System.out.println("+-".repeat(25));
        }
        for (int i = 0; i < bookingCount; i++) {
            String[] parts = bookingHistory[i].split(", ");
            String entry = getString(parts, i);
            System.out.println(entry);
        }
    }

    private static String getString(String[] parts, int i) {
        String seats = parts[0].split(": ")[1];
        String studentId = parts[1].split(": ")[1];
        String timestamp = parts[2].split(": ")[1];
        String hallName = parts[3].split(": ")[1];

        return String.format("""
    ----------------------------------------------------
    #NO: %d
    #SEATS: [%s]
    #HALL       #STU.ID      |   #CREATED AT
    HALL %s      %s           %s
    ----------------------------------------------------""", i + 1, seats, hallName, studentId, timestamp);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+-".repeat(25));
        System.out.println("CSTAD HALL BOOKING SYSTEM");
        System.out.println("+-".repeat(25));
        configureHall();
        while (true) {
            System.out.println("\n"+"[[ Application Menu ]]");
            System.out.println("<A> Booking");
            System.out.println("<B> Hall");
            System.out.println("<C> Showtime");
            System.out.println("<D> Reboot Showtime");
            System.out.println("<E> History");
            System.out.println("<F> Exit");
            System.out.print("> Please select menu no: ");

            String choice = scanner.next().toUpperCase();
            switch (choice) {
                case "A"->
                    bookSeat();
                case "B"->
                    searchHalls();
                case "C"->{
                    System.out.println("\n"+"-+".repeat(25));
                    System.out.println("# Daily Showtime of CSTAD Hall:");
                    showtime();
                    System.out.println("-+".repeat(25));
                }

                case "D"-> {
                    System.out.println("+-".repeat(25));
                    System.out.println("Start rebooting the hall...");
                    rebootShowtime(seatsMorning);
                    rebootShowtime(seatsAfterrnoon);
                    rebootShowtime(seatsNight);
                    System.out.println("Successfully rebooted");
                    System.out.println("+-".repeat(25));
                }
                case "E"->
                    viewHistory();
                case "F" -> {
                    System.out.println("Thank you for using the Hall Booking System!");
                    System.exit(0);
                }
                default->
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}