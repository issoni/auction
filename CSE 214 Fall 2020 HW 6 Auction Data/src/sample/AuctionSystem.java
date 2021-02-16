package sample;
//package is commented out for grader

/**
 * Auction implemented using hashtable
 * @author Ishika Soni
 * @e-mail: ishika.soni@stonybrook.edu
 * @authorID: Stony Brook ID: 113492059
 * @HW#: 6
 * @course: CSE 214
 * @recitation: R02
 * @TA: William Simunek
 */

import java.io.*;
import java.util.Scanner;

public class AuctionSystem implements Serializable {

    private static String username;
    private AuctionTable auctionTable;

    /**
     * menu that prints out everytime the user is done with a function
     */

    public static void menu() {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("    (D) - Import Data from URL");
        System.out.println("    (A) - Create a New Auction");
        System.out.println("    (B) - Bid on an Item");
        System.out.println("    (I) - Get Info on Auction");
        System.out.println("    (P) - Print All Auctions");
        System.out.println("    (R) - Remove Expired Auctions");
        System.out.println("    (T) - Let Time Pass");
        System.out.println("    (Q) - Quit");
        System.out.println();
    }

    /**
     * manages the auction system
     * @param args
     */

    public static void main(String[] args) {
        System.out.println("Starting... ");

        FileOutputStream file = null;
        try {
            file = new FileOutputStream("auction.obj");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No previous auction table detected.");
        }
        AuctionTable auctions = new AuctionTable(/*Constructor Parameters*/);

        try {
            outStream.writeObject(auctions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Creating new table...");

        Scanner input = new Scanner(System.in);
        System.out.print("Please select a username: ");
        String userName = input.nextLine();
        username = userName;

        AuctionTable newTable = new AuctionTable();

        while(true) {

            menu();
            System.out.print("Please select an option: ");
            String option = input.nextLine();

            if("d".equalsIgnoreCase(option)) { //import data from url

                System.out.print("Please enter a URL: ");
                String URL = input.nextLine();

                try {
                    System.out.println("Loading...");
                    AuctionTable.buildFromURL(URL);
                    System.out.println("Auction data loaded successfully.");
                } catch (IllegalArgumentException e){
                    System.out.println("The URL does not represent a valid datasource.");
                }

            } else if("a".equalsIgnoreCase(option)) { //creates a new auction

                System.out.println("Creating new Auction as " + username + ".");

                System.out.print("Please enter an Auction ID: ");
                String auctionID = input.nextLine();

                System.out.print("Please enter an Auction time (hours): ");
                String timeRemaining = input.nextLine();
                int time = Integer.parseInt(timeRemaining);

                System.out.print("Please enter some Item Info: ");
                String itemInfo = input.nextLine();

                Auction newAuction = new Auction(time, 0, auctionID, username, " ", itemInfo);

                try {
                    newTable.putAuction(auctionID, newAuction);
                    System.out.println("Auction " + auctionID + " inserted into table.");
                } catch (IllegalArgumentException e) {
                    System.out.println("This auction ID is already in the table.");
                }

            } else if("b".equalsIgnoreCase(option)) { //bid on item

                System.out.print("Please enter an Auction ID: ");
                String ID = input.nextLine();

                System.out.print("What would you like to bid?: ");
                String bid = input.nextLine();
                double newBid = Double.parseDouble(bid);
                System.out.println("Bid accepted.");

                try {
                    newTable.table.get(ID).newBid(username, newBid);
                } catch (ClosedAuctionException e) {
                    System.out.println("The auction is closed.");
                }


            } else if("i".equalsIgnoreCase(option)) { //get info on auction

                System.out.print("Please enter an Auction ID: ");
                String ID = input.nextLine();

                newTable.getAuction(ID);

            } else if("p".equalsIgnoreCase(option)) { //print all auctions

                newTable.printTable();

            } else if("r".equalsIgnoreCase(option)) { //remove expired auctions

                System.out.println("Removing expired auctions...");
                newTable.removeExpiredAuctions();
                System.out.println("All expired auctions removed.");

            } else if("t".equalsIgnoreCase(option)) { //let time pass

                System.out.print("How many hours should pass: ");
                String timePass = input.nextLine();
                int time = Integer.parseInt(timePass);

                try {
                    System.out.println("Time passing...");
                    newTable.letTimePass(time);
                    System.out.println("Auction times updated.");
                } catch (IllegalArgumentException e) {
                    System.out.println("The number of hours given are non-positive.");
                }

            } else if("q".equalsIgnoreCase(option)) { //quit

                System.out.println("Writing Auction Table to file... ");

                FileInputStream file2 = null;
                try {
                    file2 = new FileInputStream("auction.obj");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ObjectInputStream inStream = null;
                try {
                    inStream = new ObjectInputStream(file2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    auctions = (AuctionTable) inStream.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println("Done!");
                System.out.println();
                System.out.println("Goodbye.");
                System.exit(0);
            }
        }


    }
}
