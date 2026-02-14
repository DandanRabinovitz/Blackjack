/**
 * Write a description of class main here.
 *  Blackjack
 * @author (Dandan Rabinovitz)
 * @version (1.1)
 */
public class card
{
    //rank, suit, value and face_up variables, used as parameters of each card class object.
    private String rank;
    private String suit;
    private int value;
    private boolean face_up;
    /**
     * Constructor for objects of class card
     */
    //constructor, takes in parameters and sets the declared variables to them when an object is made.
    public card(String rank, String suit, int value, boolean face_up)
    {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.face_up = face_up;
    }
    
    //setRank() method, sets the rank variable to an inputed parameter.
    public void setRank(String new_rank) {
        rank = new_rank;
    }
    
    //setSuit() method, sets the suit variable to an inputed parameter.
    public void setSuit(String new_suit) {
        suit = new_suit;
    }
    
    //setValue() method, sets the value variable to an inputed parameter.
    public void setValue(int new_value) {
        value = new_value;
    }
    
    //setFaceUp() method, sets the face_up variable to an inputed parameter.
    public void setFaceUp(boolean new_face_up) {
        face_up = new_face_up;
    }
    
    //getRank() method, returns the rank varaible.
    public String getRank() {
        return rank;
    }
    
    //getSuit() method, returns the suit varaible.
    public String getSuit() {
        return suit;
    }
    
    //getValue() method, returns the value varaible.
    public int getValue() {
        return value;
    }
    
    //getFaceUp() method, returns the face_up varaible.
    public boolean getFaceUp() {
        return face_up;
    }
    
    //compareValue method, returns 1 if the the user card's value is closer to 21 than the given card's value, -1 if the opposite, or 0 if the values are equal.
    public int compareValue(int other_value) {
        if (distanceFrom21(value) < distanceFrom21(other_value)) {
            return 1;
        }
        else if (distanceFrom21(value) > distanceFrom21(other_value)) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    //distanceFrom21 method, return the distance of the number parameter from 21.
    private int distanceFrom21(int num) {
        return 21 - num;
    }
    
    //toString() method, overwrites the toString() method to print all the card class variables.
    public String toString() {
        return "rank: " + rank + ", suit: " + suit + ", value: " + value;
    }
}