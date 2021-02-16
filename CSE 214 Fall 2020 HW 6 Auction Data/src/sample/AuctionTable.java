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
import java.util.Hashtable;
import java.util.Set;
import big.data.DataSource;

public class AuctionTable implements Serializable  {

    Hashtable<String, Auction> table = new Hashtable<String, Auction>();

    /**
     * uses the BigData library to construct an AuctionTable from a remote data source
     * @param URL string representing the URL for the remote data source
     * <dt>preconditions</dt>
     *            <dd>URL represents a data source which can be connected to using the BigData library</dd>
     *            <dd>the data source has proper syntax</dd>
     * @return the AuctionTable constructed from the remote data source
     * @throws IllegalArgumentException thrown if the URL does not represent a valid datasource(can't connect or invalid syntax)
     */

    public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException {
        Hashtable<String, Auction> newTable = new Hashtable<String, Auction>();
        AuctionTable URLTable = new AuctionTable();

        DataSource ds = DataSource.connect(URL).load();
        Set<String> IDs = newTable.keySet();

        for (String ID : IDs) {
            String str = ds.fetchString(newTable.get(ID).getAuctionID());
            String[] myList = ds.fetchStringArray(newTable.get(ID).getAuctionID());
        }

//        System.out.printf("%7s%5s%7s%5s%15s%10s%15s%10s%10s%7s%10s", "Auction ID", "|",
//                "Bid", "|", "Seller", "|", "Buyer", "|",
//                "Time", "|", "Item Info\n");
//        System.out.println("===================================================" +
//                "==============================================================" +
//                "==================");
//
//
//
//        Set<String> newIDs = newTable.keySet();
//        for (String ID : newIDs) {
//            System.out.printf("%7s%5s%7s%5s%15s%10s%15s%10s%10s%7s%10s%n", newTable.get(ID).getAuctionID(), "| $",
//                    newTable.get(ID).getCurrentBid(), "|", newTable.get(ID).getSellerName(), "|", newTable.get(ID).getBuyerName(), "|",
//                    newTable.get(ID).getTimeRemaining(), "|", newTable.get(ID).getItemInfo());
//        }

        return URLTable;
    }

    /**
     * manually posts an auction, and add it into a table
     * @param auctionID the unique key for this object
     * @param auction the auction to insert into the table with the corresponding auctionID
     * <dt>post-conditions</dt>
     *                <dd>the item will be added to the table if all given parameters are correct</dd>
     * @throws IllegalArgumentException if the given auctionID is already stored in the table
     */

    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
        if (table.containsKey(auctionID)) {
            throw new IllegalArgumentException("This auction ID is already in the table.");
        } else {
            table.put(auctionID, auction);
        }
    }

    /**
     * get the information of an auction that contains the given ID as key
     * @param auctionID the unique key for this object
     * @return an auction object with the given key, null otherwise
     */

    public Auction getAuction(String auctionID) {
        if (table.isEmpty()) {
            return null;
        } else {
            System.out.println("Auction " + table.get(auctionID).getAuctionID() + ": ");
            System.out.println("    Seller: " + table.get(auctionID).getSellerName());
            System.out.println("    Buyer: " + table.get(auctionID).getBuyerName());
            System.out.println("    Time: " + table.get(auctionID).getTimeRemaining());
            System.out.println("    Info: " + table.get(auctionID).getItemInfo());
            return null;
        }
    }

    /**
     * simulates the passing of time
     * @param numHours the number of hours to decrease the timeRemaining value by
     * <dt>post-conditions</dt>
     *                 <dd>all auctions in the table have their timeRemaining timer decreased</dd>
     *                 <dd>if the original value is less than the decreased value, set the value to 0</dd>
     * @throws IllegalArgumentException thrown if the given numHours is non positive
     */

    public void letTimePass(int numHours) throws IllegalArgumentException {
        if(numHours < 0) {
            throw new IllegalArgumentException("The number of hours given are non-positive.");
        } else {
            Set<String> IDs = table.keySet();
            for (String ID : IDs) {
                int IDsTimeRemaining = table.get(ID).getTimeRemaining();
                int modifiedTime = IDsTimeRemaining - numHours;
                if (IDsTimeRemaining < modifiedTime) {
                    table.get(ID).decrementTimeRemaining(IDsTimeRemaining);
                } else {
                    table.get(ID).decrementTimeRemaining(numHours);
                    //IDsTimeRemaining -= numHours;
                }
            }
        }
    }

    /**
     * iterates over all Auction objects in the table and removes them if they are expired
     * <dt>post-conditions</dt>
     *                  <dd>only open Auction remain in the table</dd>
     */

    public void removeExpiredAuctions() {
        Set<String> IDs = table.keySet();
        for (String ID : IDs) {

            int IDsTimeRemaining = table.get(ID).getTimeRemaining();
            if (IDsTimeRemaining == 0) {
                table.remove(ID);
            }
        }
    }

    /**
     * prints the AuctionTable in tabular form
     */

    public void printTable() {
        System.out.printf("%7s%5s%7s%5s%15s%10s%15s%10s%10s%7s%10s", "Auction ID", "|",
                "Bid", "|", "Seller", "|", "Buyer", "|",
                "Time", "|", "Item Info\n");
        System.out.println("===================================================" +
                "==============================================================" +
                "==================");



        Set<String> IDs = table.keySet();
        //String nullString = "   ";
        for (String ID : IDs) {
//            double currentBid = table.get(ID).getCurrentBid();
//            if (table.get(ID).getCurrentBid() == 0 ) {
//                double nullStrings = Double.parseDouble(nullString);
//                currentBid = nullStrings;
//            }
            System.out.printf("%7s%5s%7s%5s%15s%10s%15s%10s%10s%7s%10s%n", table.get(ID).getAuctionID(), "| $",
                    table.get(ID).getCurrentBid(), "|", table.get(ID).getSellerName(), "|", table.get(ID).getBuyerName(), "|",
                    table.get(ID).getTimeRemaining(), "|", table.get(ID).getItemInfo());
        }


    }



}
