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

import java.io.Serializable;

public class Auction implements Serializable {

    private int timeRemaining;
    private double currentBid;
    private String auctionID;
    private String sellerName;
    private String buyerName;
    private String itemInfo;

    /**
     * constructor for this class
     * @param timeRemaining time remaining in the auction
     * @param currentBid value of the current bid placed on the item
     * @param auctionID string value of the auction's ID
     * @param sellerName name of the seller of the item
     * @param buyerName name of the buyer of the item
     * @param itemInfo additional information about the item
     */

    public Auction(int timeRemaining, double currentBid, String auctionID, String sellerName, String buyerName, String itemInfo){
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;
    }

    /**
     * getter for the timeRemaining variable
     * @return gets how much time is remaining in the auction
     */

    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    /**
     * getter for the currentBid variable
     * @return gets the value of the current bid placed on the item
     */

    public double getCurrentBid() {
        return this.currentBid;
    }

    /**
     * getter for the auctionID variable
     * @return gets the string value of the auction's ID
     */

    public String getAuctionID() {
        return this.auctionID;
    }

    /**
     * getter for the sellerName variable
     * @return gets the name of the seller of the item
     */

    public String getSellerName() {
        return this.sellerName;
    }

    /**
     * getter for the buyerName variable
     * @return gets the name of the buyer of the item
     */

    public String getBuyerName() {
        return this.buyerName;
    }

    /**
     * getter for the itemInfo variable
     * @return gets the additional information about the item
     */

    public String getItemInfo() {
        return this.itemInfo;
    }

    /**
     * decreases the time remaining for this auction by the specified amount
     * @param time the amount the time remaining needs to be reduced by
     * <dt>post-conditions</dt>
     *             <dd>timeRemaining has been decremented by the indicated amount and is greater than or equal to 0</dd>
     */

    public void decrementTimeRemaining(int time) {
        if(time > getTimeRemaining()) {
            this.timeRemaining = 0;
        } else {
            this.timeRemaining -= time;
        }
    }

    /**
     * makes a new bid on this auction
     * @param bidderName the name of the bidder who is making the new bid
     * @param bidAmt the amount of the new bid being placed
     * <dt>preconditions</dt>
     *               <dd>the auction is not closed</dd>
     * <dt>post-conditions</dt>
     *               <dd>currentBid reflects the largest bid placed on this object<dd>
     *               </dd>if the auction is closed, ClosedAuctionException is thrown</dd>
     * @throws ClosedAuctionException thrown if the auction is closed and no more bids can be placed
     */

    public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException {
        if (getTimeRemaining() == 0) {
            throw new ClosedAuctionException("The auction is closed.");
        } else if (bidAmt > getCurrentBid()) {
            System.out.println("Auction " + this.auctionID + " is OPEN");
            if(this.currentBid == 0) {
                System.out.println("Current Bid: None");
            } else {
                System.out.println("Current Bid: " + this.currentBid);
            }
            this.currentBid = bidAmt;
            this.buyerName = bidderName;
        }
    }

    /**
     * @return string of data members in tabular form
     */

    public String toString() {
        return "";
    }
}
