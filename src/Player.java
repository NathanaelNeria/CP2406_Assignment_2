import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Rico on 1/4/2017.
 */
public class Player {
    private ArrayList<Card> hand;
    private String name;                //Defining the variable

    Player(String nm)
    {
        hand = new ArrayList<Card>();
        name = nm;
    }                                       //Constructing the class

    public String getName() {
        return name;
    }

    public Card getCard(int x)
    {
        return hand.get(x);
    }       //Making the getter

    public void drawCard(Card card)
    {
        hand.add(card);
    }       //Method to put the card into the hand

    public void getTurn(TableGame table)                    //Every action during their turn
    {
        int cardNum;
        boolean next = false;
        String lastCardDescription;                             //Defining all the variable to be used later
        if(table.anyCardPlayed() && !(table.playerGetTurnAgain(this))) {        //Conditioning to see if hedidn't get his turn again twice while in game
            if(table.getLastCard() instanceof NormalCard){                      //COnditioning for previous card
            lastCardDescription =
                    "\nLast card = Name: " + table.getLastCard().getName() + "   " +
                            "Hardness: " + ((NormalCard) table.getLastCard()).getHardness() + "   " +
                            "Specific Gravity: " + ((NormalCard) table.getLastCard()).getSpecificGravity() + "   " +
                            "Cleavage: " + ((NormalCard) table.getLastCard()).getCleavage() + "   " +
                            "Crystal Abundance: " + ((NormalCard) table.getLastCard()).getCrustalAbundance() + "   " +
                            "Economic Value: " + ((NormalCard) table.getLastCard()).getEcoValue() + "\n";}          //Description for normal card if used previously
            else{
                lastCardDescription = "\nPrevious player play the " +((SupertrumpCard) table.getLastCard()).getName()+ " card\n";
                    //Description for supertrump card if used previously
            }
        }
        else if(table.anyCardPlayed() && table.playerGetTurnAgain(this)) {  //Conditioning to see if anyone except if have passed
            JOptionPane.showMessageDialog(null,"You are granted to choose the trump mode again");
            lastCardDescription = "You may pick again as you made them all pass";
            startGame(table);               //Allowing the player to pick the trump mode again
        }
        else                    //Conditioning if it is the start of the game
        {
            lastCardDescription = "No cards have been played";
            startGame(table);               //Allowing the first player to pick the mode
        }
        while (!next) {             //Looping till the person did action that allow the next player to move
            String choice = JOptionPane.showInputDialog(null, table.getGameMode() + "\n" + lastCardDescription +"\n"
                            +showAllCard(), getName() + ", enter the card number" +
                            " you want to play or simply enter PASS to pass",
                    JOptionPane.QUESTION_MESSAGE);
            if (choice.toUpperCase().equals("PASS")) {
                drawCard(table.getDeckCard().cardDrawn());
                next = true;                        //Draw card if the user pass
            }
            else {
                try {
                    cardNum = Integer.parseInt(choice);             //Changing the input into int
                    Card cardplayed = getCard(cardNum);             //Trying to get the card inputted
                    boolean gameContinue = table.playCard(cardplayed,this);         //Trying to put/play the card into the table
                    if(table.getCategoryMode().equals("NEW"))           //Decision if a special supertrump card that allow player to pick the trump mode is played
                    {   table.putCard(cardplayed);
                        hand.remove((cardNum));
                        startGame(table);                               //Repick the trump mode
                        table.setLastPlayerTurn(this.getName());
                        next = true;
                    }
                    else if(table.getCategoryMode().equals("SS"))     //Check if it is SS category
                    {
                        if(checkWinningCard())                          //Check if the player got the winning card
                        {
                            for(Card cardInHand : hand)
                            {
                                table.putCard(cardInHand);
                                hand.remove(cardInHand);
                                table.setLastPlayerTurn(this.getName());
                            }
                            table.setCategoryMode("S");                 //Return the mode back to Specific gravity
                            next = true;
                        }
                        else
                        {table.setCategoryMode("S");}                   //Return the mode back to Specific gravity
                        if(gameContinue) {                              //For playing the card
                            table.putCard(cardplayed);
                            hand.remove(cardNum);
                            table.setLastPlayerTurn(this.getName());
                            next = true;
                        }
                    }
                    else {                                              //For normal card play(without the supertrump) if the user enter the correct card
                        if (gameContinue) {
                            table.putCard(cardplayed);
                            hand.remove(cardNum);
                            table.setLastPlayerTurn(this.getName());
                            next = true;
                        }
                    }
                } catch (Throwable e) {                                 //For any error might occur such as index error
                    System.out.println(e.getMessage());
                }
            }
        }
        if(hand.size() == 0)          //If the player got no more card
        {
            table.setLastPlayerTurn(table.getGameplayers().get((table.getGameplayers().indexOf(this)+1)%table.getGameplayers().size()).getName());
            leaveGame(table);           //They leave the game
        }

    }

    public String showAllCard()         //Method to show all the card in a format
    {
        String allHand = "";
        int cardNo = 0;
        for(Card cards : hand)
        {

            String cardDescription = "";
            if(cards instanceof NormalCard)         //For normal card
            {
                cardDescription = "No: "+ cardNo + "   "+
                        "Name: " + cards.getName() + "   " +
                        "Hardness: " + ((NormalCard) cards).getHardness() + "   " +
                        "Specific Gravity: " + ((NormalCard) cards).getSpecificGravity() + "   " +
                        "Cleavage: " + ((NormalCard) cards).getCleavage() + "   " +
                        "Crystal Abundance: " + ((NormalCard) cards).getCrustalAbundance() + "   " +
                        "Economic Value: " + ((NormalCard) cards).getEcoValue()+"\n";
            }
            else                                //For supertrump
            {
                cardDescription = "No: "+ cardNo+ "   "+ "Name: " + cards.getName()+ "   Description: "+
                        ((SupertrumpCard) cards).cardDescription()+"\n";
            }
            cardNo+=1;
            allHand += cardDescription; //Concatenating all the card description
        }
        return allHand;
    }

    public void startGame(TableGame gameStarter)        //Method to allow them to pick the trump mode
    {
        String mode = JOptionPane.showInputDialog(null,showAllCard()+"\n H = Hardness, S = Specific Gravity," +
                        " C = Cleavage, A = Crystal Abundance, E= Economic Value",getName()+ ", " +
                        "enter the mode you want to play",
                JOptionPane.QUESTION_MESSAGE);
        while(!(mode.equals("H") ||mode.equals("S")||mode.equals("C")||mode.equals("A")||mode.equals("E")||
        mode.equals("h") ||mode.equals("s")||mode.equals("c")||mode.equals("a")||mode.equals("e")) )    //To error check
        {
            JOptionPane.showMessageDialog(null,"Invalid mode");
            mode = JOptionPane.showInputDialog(null, showAllCard() + "\n H = Hardness, S = Specific Gravity," +
                            " C = Cleavage, A = Crystal Abundance, E= Economic Value", getName() + ", " +
                            "enter the mode you want to play",
                    JOptionPane.QUESTION_MESSAGE);
        }
        gameStarter.setCategoryMode(mode.toUpperCase());
    }

    public boolean checkWinningCard()       //Method to check if the player got the winning card
    {
        boolean winningCard = false;
        for(Card cards: hand)
        {
            if(cards.getName().equals("Magnetite"))     //If the player got magnetite he will win
            {
                winningCard = true;
            }
        }
        return winningCard;
    }

    public void leaveGame(TableGame table)          //Method to remove the player from the table
    {
        table.getGameplayers().remove(this);
        JOptionPane.showMessageDialog(null,"Player " + this.getName() + " have left the game");
    }

}
