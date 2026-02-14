/**
 * Write a description of class main here.
 * Blackjack
 * @author (Dandan Rabinovitz)
 * @version (1.1)
 */
//comments will explain what the line or block of code below them do.
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class main
{
    //creates a new scanner() object called Interface to detect what the player is saying.
    Scanner Interface = new Scanner(System.in);
    //creates a new random() object to generate random numbers.
    Random rand = new Random();
    //credits int variable, used to store the player's credits.
    int credits =  50;
    //min_bet int variable, used to choose the minimum bet the player can do, pre-set to 5.
    int min_bet = 5;
    //command String variable, used to store what the player is saying.
    String command;
    //constructor.
    public main()
    {
        //deck array list, used to store all the cards in the game.
        ArrayList<card> deck = createDeck();
        //player_hand and dealer_hand array lists, used to the the player's cards and the dealer's cards.
        ArrayList<card> player_hand = new ArrayList<card>();
        ArrayList<card> dealer_hand = new ArrayList<card>();
        //calls the shuffleDeck() method. declared later.
        shuffleDeck(deck);
        //introduction message.
        System.out.println("welcome to the text casino! this is the Blackjack table, you start out with 50 credits, to start \ngambling, you must bet 5 credits or more, to view all the commands you can use, type command list.");
        //main loop, never stops unless the player says or runs out of money.
        while (true) {
            //calls the gameLoop() method, and inputs the deck, player_hand and dealer_hand array lists. declared later.
            gameLoop(deck, player_hand, dealer_hand);
            //calls the gameEnd() method, and inputs the deck, player_hand and dealer_hand array lists. declared later.
            gameEnd(deck, player_hand, dealer_hand);
            //command loop after a game, asks the player if he wants to play again.
            while (true) {
                //play again message.
                System.out.println("would you like to play again?");
                //sets the command variable to the scanCommand() method, which checks returns what the player says, declared later.
                command = scanCommand();
                //if the command equals to "yes", checks if the player has enough credits to bet. if not, prints a message and closes the software. if he does, breaks out of the loop back to the main loop, starting another game.
                if(command.equals("yes")) {
                    if (credits < min_bet) {
                        System.out.println("you're out of credits... better luck next time!");
                        System.exit(0);
                    }
                    break;
                }
                //if the command equals to "no", prints a message and closes the software.
                else if (command.equals("no")) {
                    System.out.println("you finished with " + credits + " credits.");
                    System.exit(0);
                }
                else if(command.equals("command list")) {
                    System.out.println("command list - displays a list of your usable commands. \nhit - gives you a card from the deck.\nhit (number) - gives you the amount of cards you typed from the deck.\nstand - skips you and goes to the dealer's turn. \ndouble down - doubles your bet, gives you one card, then makes you stand. \nsplit - if you have two of the same card, splits your hand to two hands and gives them each a card, the new hand gets an equal bet to the original hand. \nsurrender - makes you lose the game, but gives you half of your bet back, can only be used on the first turn.\ndisplay cards - displays your cards. \ndisplay dealer cards - displays the dealer's cards. \ncredits - displays how many credits you have.");
                }
                else if(command.equals("credits")) {
                    System.out.println("you have " + credits + " credits");
                }
            } 
        }
    }
    
    //easyRandomInt methods, making it easy on myself to generate random numbers by using a more memorable name, starting at 1 instead of 0, and making the max number inclusive. takes in either a max parameter and starting at 1, or both a max and a min parameter.
    public int easyRandomInt(int max) {
        return rand.nextInt(1, max + 1);
    }
    
    public int easyRandomInt(int min,int max) {
        return rand.nextInt(min, max + 1);
    }
    
    //scanCommand() method, making it easy on myself to use the scanner interface.
    private String scanCommand() {
        //returns the next thing that the player says.
        return Interface.nextLine();
    }
    
    //createDeck() method, returns a created "deck" array list, containing all 52 cards of a standard card deck.
    public ArrayList<card> createDeck() {
        //creates a new "deck" array list.
        ArrayList<card> deck = new ArrayList<card>();
        //creates a rank, suit and value variables, all parts of a card in a standard card deck
        String rank;
        String suit;
        int value;
        //for loop making all the cards and populating the deck with them.
        for (int i = 1; i <= 4; i++) {
            //sets the suit variable to "hearts", "diamonds", "spades" or "clubs" based on the turn of the for loop.
            if (i == 1) {
                suit = "hearts";
            }
            else if (i == 2) {
                suit = "diamonds";
            }
            else if (i == 3) {
                suit = "spades";
            }
            else {
                suit = "clubs";
            }
            //for loop setting the "rank" and "value" variables to numbers based on the turn of the for loop.
            for(int j = 1; j <= 13; j++) {
                if(j == 1) {
                    rank = "ace";
                    value = 11;
                }
                else if(j > 10) {
                    value = 10;
                    if (j == 11) {
                        rank = "J";
                    }
                    else if (j == 12) {
                        rank = "Q";
                    }
                    else {
                        rank = "K";
                    }
                }
                else {
                    rank = String.valueOf(j);
                    value = j;
                }
                //makes a new "card" object and inputs it with the rank, suit and value variables. aswell as making the face_up parameter true.
                card new_card = new card(rank, suit, value, true);
                //populates the "deck" array list with the cards.
                deck.add(new_card);
            }
        }
        //returns the deck.
        return deck;
    }
    
    //cardDisplay() method, returns all the parameters of an inputed card.
    public String cardDisplay(card card) {
        return (card.getRank() + " of " + card.getSuit() + ", value: " + card.getValue());
    }
    
    //displayCard() method, prints all the parameters of all the cards in an inputed card array list.
    public void displayCards(ArrayList<card> arr) {
        //for-each loop going through every card in the array list and prints their parameters, unless their face_up parameter equals to false.
        for (card i: arr) {
            if (i.getFaceUp() == false) {
                System.out.println("face-down card");
            }
            else {
                System.out.println(i.getRank() + " of " + i.getSuit() + ", value: " + i.getValue());
            }
        }
        //prints the sum of all the "value" parameters of the cards in the array list.
        System.out.println("overall cards value: " + cardsValue(arr));
    }
    
    //shuffleDeck() method, goes to every card in an inputed card array list one by one, and makes it switch places with a random card in the array list.
    public void shuffleDeck(ArrayList<card> deck) {
        for (int i = 0; i<deck.size(); i++) {
            if (deck.get(i).getRank() == "ace" && deck.get(i).getValue() == 1) {
                deck.get(i).setValue(11);
            }
            Collections.swap(deck, i, easyRandomInt(0,getLastCardIndex(deck)));
        }
    }
    
    //getLastCardIndex() method, returns the last index of an inputed card array list.
    public int getLastCardIndex(ArrayList<card> arr) {
        return arr.size() - 1;
    }
    
    //drawCard() method, takes in a "giver" and "taker" card array list parameter, and takes one card from the "giver" array list and gives it to the "taker" array list. also takes in a String parameter to determine what to print.
    public void drawCard(ArrayList<card> giver, ArrayList<card> taker, String Shand) {
        taker.add(giver.get(getLastCardIndex(giver)));
        giver.remove(getLastCardIndex(giver));
        if (Shand.equals("player")) { 
            if (taker.get(getLastCardIndex(taker)).getRank().equals("ace") || taker.get(getLastCardIndex(taker)).getRank().equals("8")) {
                System.out.println("you pulled an " + cardDisplay(taker.get(getLastCardIndex(taker))) + ", hand value: " + cardsValue(taker));
            }
            else 
            {
                System.out.println("you pulled a " + cardDisplay(taker.get(getLastCardIndex(taker))) + ", hand value: " + cardsValue(taker));
            }
        }
        else if(Shand.equals("dealer")) {
            if (taker.get(getLastCardIndex(taker)).getRank().equals("ace") || taker.get(getLastCardIndex(taker)).getRank().equals("8")) {
                System.out.println("the dealer pulled an " + cardDisplay(taker.get(getLastCardIndex(taker))) + ", hand value: " + cardsValue(taker));
            }
            else 
            {
                System.out.println("the dealer pulled a " + cardDisplay(taker.get(getLastCardIndex(taker))) + ", hand value: " + cardsValue(taker));
            }
        }
        else if(Shand.equals("FDdealer")) {
            taker.get(getLastCardIndex(taker)).setFaceUp(false);
            System.out.println("the dealer pulled a face-down card.");
        }
        else if(Shand.equals("back")) {
            taker.get(getLastCardIndex(taker)).setFaceUp(true);
        }
    }
    
    //second drawCard() method, also takes in an int paramter and draws the cards the amount of times that the int tells it.
    public void drawCard(ArrayList<card> giver, ArrayList<card> taker, String Shand, int amount) {
        while (amount > 0) {
            drawCard(giver, taker, Shand);
            amount--;
        }
    }
    
    //cardsValue() method, uses a for-each loop to return the sum of the values of all the cards in an inputed card array list.
    public int cardsValue(ArrayList<card> hand) {
        int total = 0;
        for (card i: hand) {
            if(i.getFaceUp() == true) {
                total += i.getValue();
            }
        }
        return total;
    }
    
    //isBust() method, returns a boolean based on if the sum of the values of all the cards in an inputed card array list goes over 21. if one of those cards are an ace, and the value does go over 21, the ace's value becomes 1 (instead of 11).
    public boolean isBust(ArrayList<card> hand) {
        if (cardsValue(hand) > 21) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRank() == "ace" && hand.get(i).getValue() == 11) {
                    hand.get(i).setValue(1);
                    System.out.println("your ace saved you from busting, it's value is now 1, hand value: " + cardsValue(hand) + ".");
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    //second isBust() method, adds a boolean parameter checking if to print messages fitting the dealer instead of the player.
    public boolean isBust(ArrayList<card> hand, boolean dealer) {
        if (cardsValue(hand) > 21) {
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getRank() == "ace" && hand.get(i).getValue() == 11) {
                    hand.get(i).setValue(1);
                    if(dealer == true) {
                        System.out.println("the dealer's ace saved him from busting, it's value is now 1, hand value: " + cardsValue(hand) + ".");
                        return false;
                    }
                    System.out.println("your ace saved you from busting, it's value is now 1, hand value: " + cardsValue(hand) + ".");
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    //gameLoop() method, creates a loop of what happens in a game itself.
    public void gameLoop(ArrayList<card> deck, ArrayList<card> player_hand, ArrayList<card> dealer_hand) {
        //creates a variable of the player's bet, by calling the betLoop() method. declared later.
        int bet = betLoop();
        //boolean variables representing the first turn.
        boolean first = true;
        //introduction message.
        System.out.println("alright, lets start!");
        //gives two cards to the player, one face-up card to the dealer and one face-down card to the dealer. 
        drawCard(deck, player_hand, "player");
        drawCard(deck, dealer_hand, "FDdealer");
        drawCard(deck, player_hand, "player");
        drawCard(deck, dealer_hand, "dealer");
        //check if the player gets a blackjack.
        if(cardsValue(player_hand) == 21) {
            System.out.println("BLACKJACK");
            end(true, bet, true);
            return;
        }
        //command loop of the player's turn.
        while (true) {
            //sets the command variable to a called scanCommand() method.
            command = scanCommand();
            //if the command equals to "hit" calls the drawCard() method and inputs the deck as the giver and the player_hand as the taker, then takes away his bet if he busted.
            if(command.equals("hit")) {
                drawCard(deck, player_hand, "player");
                if(isBust(player_hand) == true) {
                    System.out.println("you busted!");
                    end(false, bet);
                    return;
                }
                first = false;
            }
            //if the command equals to "stand" skips to the dealer's turn.
            else if(command.equals("stand")) {
                break;
            }
            //if the command equals to "display cards" calls the display cards method and inputs the player_hand.
            else if(command.equals("display cards")) {
                displayCards(player_hand);
            }
            //if the command equals to "displayer dealer cards" calls the display card method and inputs the dealer_hand.
            else if(command.equals("display dealer cards")) {
                displayCards(dealer_hand);
            }
            //if the command equals to "double down" doubles the player's bet, hits once then stands.
            else if(command.equals("double down")) {
                if(credits >= bet * 2) {
                    System.out.println("you double your bet from " + bet + " to " + bet * 2 + ".");
                    bet = bet * 2;
                    drawCard(deck, player_hand, "player");
                    if(isBust(player_hand) == true) {
                        System.out.println("you busted!");
                        end(false, bet);
                        return;
                    }
                    break;
                }
                else {
                    System.out.println("you don't have enough credits to double down.");
                }
            }
            //if the command equals to "surrender" makes the player lose and takes away half his bet..
            else if (command.equals("surrender")) {
                if(first == false) {
                    System.out.println("you can only surrender as your first move.");
                }
                else {
                    System.out.println("you surrender!");
                    System.out.println("you lost " + bet/2 + " credits, you now have " + (credits - bet/2) + " credits.");
                    credits -= bet/2;
                    return;
                }
            }
            //if the command equals to "command list" displays the list of the player's usable commands.
            else if(command.equals("command list")) {
                System.out.println("command list - displays a list of your usable commands. \nhit - gives you a card from the deck.\nhit (number) - gives you the amount of cards you typed from the deck.\nstand - skips you and goes to the dealer's turn. \ndouble down - doubles your bet, gives you one card, then makes you stand. \nsplit - if you have two of the same card, splits your hand to two hands and gives them each a card, the new hand gets an equal bet to the original hand. \ndisplay cards - displays your cards. \ndisplay dealer cards - displays the dealer's cards. \ncredits - displays how many credits you have.");
            }
            //if the command equals to "credits" shows how many credits the player has.
            else if(command.equals("credits")) {
                System.out.println("you have " + credits + " credits");
            }
            //if the command equals to "hit" then a number, hits the amount of times the number says.
            else if(command.substring(0, 3).equals("hit") && isNumeric(command.substring(4)) == true) {  
                drawCard(deck, player_hand, "player", Integer.valueOf(command.substring(4)));
                if(isBust(player_hand) == true) {
                    System.out.println("you busted!");
                    end(false, bet);
                    return;
                }
                first = false;
            }
            //if the command equals to "split" and the player has two cards of the same rank, calls the splitLoop() method declared later.
            else if(command.equals("split")) {
                if (first == false || player_hand.get(0).getRank() != player_hand.get(1).getRank()) {
                    System.out.println("you can only split if your first two cards are of the same rank.");
                }
                else {
                    System.out.println("you split your cards to two identical hands, each having a bet of " + bet + ".");
                    splitLoop(deck,player_hand, dealer_hand, bet);
                    return;
                }
                System.out.println("you split your cards to two identical hands, each having a bet of " + bet + ".");
                splitLoop(deck,player_hand, dealer_hand, bet);
                return;
            }
        }
        //message declaring it is the dealer's turn, if by the end of the dealer's turn he either busts or his overall value is less close to 21 than the player, he loses, otherwise he wins.
        System.out.println("dealer's turn! \nthe dealer flips his face-down card, and now has:");
        //flips the dealer's face-down card.
        dealer_hand.get(0).setFaceUp(true);
        //displays the dealer's cards
        displayCards(dealer_hand);
        //command loop of the dealer's turn.
        while(true) {
                if(distanceFrom21(cardsValue(dealer_hand)) < distanceFrom21(cardsValue(player_hand))) {
                    System.out.println("the dealer's hand value is closer to 21 than yours.");
                    end(false, bet);
                    return;
                }
                else if(distanceFrom21(cardsValue(dealer_hand)) > distanceFrom21(cardsValue(player_hand))) {
                    System.out.println("your hand value is closer to 21 than the dealer's.");
                    end(true, bet);
                    return;
                }
                else if(distanceFrom21(cardsValue(dealer_hand)) == distanceFrom21(cardsValue(player_hand))) {
                    System.out.println("your hand value is the same the the dealer's hand value.");
                    System.out.println("you keep your " + bet + " credits bet, you now have " + credits + " credits.");
                    return;
                }
            else {
                //if the dealer's hand value is 17 or greater, he must stand.
                if (cardsValue(dealer_hand) >= 17) {
                    System.out.println("the dealer's hand value is 17 or greater, so he must stand.");
                    if(distanceFrom21(cardsValue(dealer_hand)) < distanceFrom21(cardsValue(player_hand))) {
                        System.out.println("the dealer's hand value is closer to 21 than yours.");
                        end(false, bet);
                        return;
                    }
                    else if(distanceFrom21(cardsValue(dealer_hand)) > distanceFrom21(cardsValue(player_hand))) {
                        System.out.println("your hand value is closer to 21 than the dealer's.");
                        end(true, bet);
                        return;
                    }
                    else if(distanceFrom21(cardsValue(dealer_hand)) == distanceFrom21(cardsValue(player_hand))) {
                        System.out.println("your hand value is the same the the dealer's hand value.");
                        System.out.println("you keep your " + bet + " credits bet, you now have " + credits + " credits.");
                        return;
                    }  
                }
                //if the dealer's hand value is 16 or smaller, he must hit.
                else if (cardsValue(dealer_hand) < 17) {
                    System.out.println("the dealer's hand value is 16 or smaller, so he must hit.");
                    drawCard(deck, dealer_hand, "dealer");
                    if(isBust(dealer_hand, true)) {
                        System.out.println("the dealer busted!");
                        end(true, bet);
                        return;
                    }
                }
            }
        }
    }
    
    //betLoop() method, uses a command loop to ask the player how much he wants to bet than returns it.
    public int betLoop() {
        System.out.println("let's start! place your bets.");
        while(true) {
            command = scanCommand();
            if(isNumeric(command) == true) { 
                if(Integer.valueOf(command) < min_bet) {
                    System.out.println("you must bet " + min_bet + " credits or more");
                }
                else if(Integer.valueOf(command) > credits) {
                    System.out.println("you don't have enough credits");
                }
                else {
                    return Integer.valueOf(command);
                }
            }
            else if(command.equals("credits")) {
                System.out.println("you have " + credits + " credits");
            }
            else {
                System.out.println("please type a number you would like to bet.");
            }
        }
    }
    
    //gameEnd() method, returns the player's cards and the dealer's cards to the deck, then shuffles them.
    public void gameEnd(ArrayList<card> deck, ArrayList<card> player_hand, ArrayList<card> dealer_hand) {
        while (player_hand.size() > 0) {
            drawCard(player_hand, deck, "back");
        }
        while (dealer_hand.size() > 0) {
            drawCard(dealer_hand, deck, "back");
        }
        shuffleDeck(deck);
    }
    
    //isNumeric() method, a way to check if a string is a number.
    public boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
    
    //end() methods, give or takes away the player's bet to the player based on the parameters.
    public void end(boolean WorL, int bet) {
        if(WorL == false) {
            credits -= bet;
            System.out.println("you lost " + bet + " credits, you now have " + credits + " credits.");
        }
        else {
            credits += bet;
            System.out.println("you won " +  bet + " credits, you now have " + credits + " credits.");
        }
    }
    
    public void end(boolean WorL, int bet, boolean blackjack) {
        if(WorL == false) {
            credits -= bet;
            System.out.println("you lost " + bet + " credits, you now have " + credits + " credits.");
        }
        else if(blackjack == true) {
            credits += Math.floor(bet * 1.5);
            System.out.println("you won " + Math.floor(bet * 1.5) + " credits, you now have " + credits + " credits.");
        }
        else {
            credits += bet;
            System.out.println("you won " +  bet + " credits, you now have " + credits + " credits.");
        }
    }
    
    //distanceFrom21() method, returns the distance of an int parameter from 21.
    public int distanceFrom21(int num) {
        return 21 - num;
    }
    
    //splitLoop() method, splits the player's hand to two identical hands, then gives them each a card, acts like a normal game except for some small differences.
    public void splitLoop(ArrayList<card> deck, ArrayList<card> player_hand, ArrayList<card> dealer_hand, int bet) {
        boolean first = true;
        boolean isBust1 = false;
        boolean isBust2 = false;
        int bet2 = bet;
        ArrayList<card> hand1 = new ArrayList<card>();
        ArrayList<card> hand2 = new ArrayList<card>();
        drawCard(player_hand, hand1, "null");
        drawCard(player_hand, hand2, "null");
        drawCard(deck, hand1, "null");
        System.out.println("your first hand pulled a " + hand1.get(1).getRank() + " of " + hand1.get(1).getSuit() + ", value: " + hand1.get(1).getValue() + ", hand value: " + cardsValue(hand1));
        drawCard(deck, hand2, "null");
        System.out.println("your second hand pulled a " + hand2.get(1).getRank() + " of " + hand2.get(1).getSuit() + ", value: " + hand2.get(1).getValue() + ", hand value: " + cardsValue(hand2));
        System.out.println("first hand's turn! cards:");
        displayCards(hand1);
        while (true) {
            command = scanCommand();
            if(command.equals("hit")) {
                drawCard(deck, hand1, "player");
                if(isBust(hand1) == true) {
                    System.out.println("you busted!");
                    end(false, bet);
                    isBust1 = true;
                    break;
                }
                first = false;
            }
            else if(command.equals("stand")) {
                break;
            }
            else if(command.equals("display cards")) {
                displayCards(hand1);
            }
            else if(command.equals("display dealer cards")) {
                displayCards(dealer_hand);
            }
            else if(command.equals("double down")) {
                if(credits >= bet * 2) {
                    System.out.println("you double your bet from " + bet + " to " + bet * 2 + ".");
                    bet = bet * 2;
                    drawCard(deck, hand1, "player");
                    if(isBust(hand1) == true) {
                        System.out.println("you busted!");
                        end(false, bet);
                        isBust1 = true;
                        break;
                    }
                    break;
                }
                else {
                    System.out.println("you don't have enough credits to double down.");
                }
            }
            else if (command.equals("surrender")) {
                if(first == false) {
                    System.out.println("sorry, you can only surrender as your first move.");
                }
                else {
                    System.out.println("you surrender!");
                    System.out.println("you lost " + Math.floor(bet/2) + " credits, you now have " + (credits - bet/2) + " credits.");
                    credits -= Math.floor(bet/2);
                    break;
                }
            }
            else if(command.equals("command list")) {
                System.out.println("command list - displays a list of your usable commands. \nhit - gives you a card from the deck.\nhit (number) - gives you the amount of cards you typed from the deck.\nstand - skips you and goes to the dealer's turn. \ndouble down - doubles your bet, gives you one card, then makes you stand. \nsplit - if you have two of the same card, splits your hand to two hands and gives them each a card, the new hand gets an equal bet to the original hand. \nsurrender - makes you lose the game, but gives you half of your bet back, can only be used on the first turn.\ndisplay cards - displays your cards. \ndisplay dealer cards - displays the dealer's cards. \ncredits - displays how many credits you have.");
            }
            else if(command.equals("credits")) {
                System.out.println("you have " + credits + " credits");
            }
            else if(command.substring(0, 3).equals("hit") && isNumeric(command.substring(4)) == true) {  
                drawCard(deck, hand1, "player", Integer.valueOf(command.substring(4)));
                if(isBust(hand1) == true) {
                    System.out.println("you busted!");
                    end(false, bet);
                    isBust1 = true;
                    break;
                }
                first = false;
            }
        }
        System.out.println("second hand's turn.");
        displayCards(hand2);
        first = true;
        while (true) {
            command = scanCommand();
            if(command.equals("hit")) {
                drawCard(deck, hand2, "player");
                if(isBust(hand2) == true) {
                    System.out.println("you busted!");
                    end(false, bet2);
                    isBust2 = true;
                    break;
                }
                first = false;
            }
            else if(command.equals("stand")) {
                break;
            }
            else if(command.equals("display cards")) {
                displayCards(hand2);
            }
            else if(command.equals("display dealer cards")) {
                displayCards(dealer_hand);
            }
            else if(command.equals("double down")) {
                if(credits >= bet2 * 2) {
                    System.out.println("you double your bet from " + bet2 + " to " + bet2 * 2 + ".");
                    bet2 = bet2 * 2;
                    drawCard(deck, hand2, "player");
                    if(isBust(hand2) == true) {
                        System.out.println("you busted!");
                        end(false, bet2);
                        isBust2 = true;
                        break;
                    }
                    break;
                }
                else {
                    System.out.println("you don't have enough credits to double down.");
                }
            }
            else if (command.equals("surrender")) {
                if(first == false) {
                    System.out.println("sorry, you can only surrender as your first move.");
                }
                else {
                    System.out.println("you surrender!");
                    System.out.println("you lost " + Math.floor(bet2/2) + " credits, you now have " + (credits - bet2/2) + " credits.");
                    credits -= Math.floor(bet2/2);
                    break;
                }
            }
            else if(command.equals("command list")) {
                System.out.println("command list - displays a list of your usable commands. \nhit - gives you a card from the deck.\nhit (number) - gives you the amount of cards you typed from the deck.\nstand - skips you and goes to the dealer's turn. \ndouble down - doubles your bet, gives you one card, then makes you stand. \nsplit - if you have two of the same card, splits your hand to two hands and gives them each a card, the new hand gets an equal bet to the original hand. \nsurrender - makes you lose the game, but gives you half of your bet back, can only be used on the first turn.\ndisplay cards - displays your cards. \ndisplay dealer cards - displays the dealer's cards. \ncredits - displays how many credits you have.");
            }
            else if(command.equals("credits")) {
                System.out.println("you have " + credits + " credits");
            }
            else if(command.substring(0, 3).equals("hit") && isNumeric(command.substring(4)) == true) {  
                drawCard(deck, hand2, "player", Integer.valueOf(command.substring(4)));
                if(isBust(hand1) == true) {
                    System.out.println("you busted!");
                    end(false, bet2);
                    isBust2 = true;
                    break;
                }
                first = false;
            }
        }
        System.out.println("dealer's turn");
        dealer_hand.get(0).setFaceUp(true);
        displayCards(dealer_hand);
        while(true) {
            if (cardsValue(dealer_hand) >= 17) {
                System.out.println("the dealer's hand value is 17 or greater, so he must stand.");
                if(distanceFrom21(cardsValue(dealer_hand)) < distanceFrom21(cardsValue(hand1))) {
                    System.out.println("the dealer's hand value is closer to 21 than your first hand.");
                    end(false, bet);
                }
                else if(distanceFrom21(cardsValue(dealer_hand)) > distanceFrom21(cardsValue(hand1))) {
                    System.out.println("your first hand's value is closer to 21 than the dealer's.");
                    end(true, bet);
                }
                else if(distanceFrom21(cardsValue(dealer_hand)) == distanceFrom21(cardsValue(hand1))) {
                    System.out.println("your hand value is the same the the dealer's hand value.");
                    System.out.println("you keep your " + bet + " credits bet, you now have " + credits + " credits.");
                }
                if(distanceFrom21(cardsValue(dealer_hand)) < distanceFrom21(cardsValue(hand2))) {
                    System.out.println("the dealer's hand value is closer to 21 than your first hand.");
                    end(false, bet);
                }
                else if(distanceFrom21(cardsValue(dealer_hand)) > distanceFrom21(cardsValue(hand2))) {
                    System.out.println("your first hand's value is closer to 21 than the dealer's.");
                    end(true, bet);
                }
                else if(distanceFrom21(cardsValue(dealer_hand)) == distanceFrom21(cardsValue(hand2))) {
                    System.out.println("your hand value is the same the the dealer's hand value.");
                    System.out.println("you keep your " + bet + " credits bet, you now have " + credits + " credits.");
                }
                break;
            }
            else if (cardsValue(dealer_hand) < 17) {
                System.out.println("the dealer's hand value is 16 or smaller, so he must hit.");
                drawCard(deck, dealer_hand, "dealer");
                if(isBust(dealer_hand, true)) {
                    System.out.println("the dealer busted!");
                    if (isBust1 == false) {
                        System.out.println("your first hand didn't bust, so it wins.");
                        end(true, bet);
                    }
                    else {
                        System.out.println("your first hand already busted, so it loses anyway.");
                    }
                    if (isBust2 == false) {
                        System.out.println("your second hand didn't bust, so it wins.");
                        end(true, bet);
                    }
                    else {
                        System.out.println("your second hand already busted, so it loses anyway.");
                    }
                    break;
                }
            }
        }
    }
}
