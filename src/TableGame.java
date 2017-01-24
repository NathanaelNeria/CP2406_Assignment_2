import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Rico on 1/5/2017.
 */
public class TableGame {
    private ArrayList<Card> usedCard;
    private Deck deckCard;
    private ArrayList<Player> gameplayers;
    private String categoryMode;
    private String lastPlayerTurn;          //Defining all the variable

    TableGame(int numberOfPlayers, Deck deckcard)       //Constructing the class
    {
        categoryMode = "";
        usedCard = new ArrayList<Card>();
        gameplayers = new ArrayList<Player>();
        deckCard = deckcard;
        lastPlayerTurn = "";
        for(int x = 0; x < numberOfPlayers; x++)
        {
            String nameOfPlayer = JOptionPane.showInputDialog(null,"Enter player name", "Name",
                    JOptionPane.INFORMATION_MESSAGE);
            gameplayers.add(new Player(nameOfPlayer));              //Asking the player name and add the to the table as an arraylist
        }
        for(int x = 0; x<8; x++) {
            for (Player player : gameplayers) {
                player.drawCard(deckcard.cardDrawn());
            }
        }                                                   //Sharing the card to all the player
    }

    public String getCategoryMode() {                   //To get the trump mode
        return categoryMode;
    }

    public String getGameMode()                             //To get the game mode to be displayed
    {
        String game = "";
        if(categoryMode.equals("H"))
        {
            game = "It is a game of hardness";
        }
        else if(categoryMode.equals("S"))
        {
            game = "It is a game of specific gravity";
        }
        else if(categoryMode.equals("C"))
        {
            game = "It is a game of cleavage";
        }
        else if(categoryMode.equals("A"))
        {
            game = "It is a game of crustal abundance";
        }
        else if(categoryMode.equals("E")) {
            game = "It is a game of economic value";
        }
        return game;
    }

    public void setCategoryMode(String categoryMode) {
        this.categoryMode = categoryMode;
    }   //Setting the trump mode



    public void addBackCard()                   //Method to put all the card back into the deck if there are no more card in the deck
    {
        ArrayList<Card> reStore = new ArrayList<Card>();
        for(Card cards: usedCard)
        {
            reStore.add(cards);
        }
        setDeckCard(new Deck(reStore));
        usedCard.clear();
        usedCard.add(reStore.get(reStore.size()-1));

    }
    public ArrayList<Player> getGameplayers() {
        return gameplayers;
    }       //Method to get the araylist of player

    public Card getLastCard() {         //Method to get the last card played
        return usedCard.get(usedCard.size()-1);
    }

    public boolean anyCardPlayed()      //To check if it is the first card played or not
    {
        boolean played = false;
        if(usedCard.size()>0)
        {played = true;}
        return played;
    }

    public Deck getDeckCard() {
        return deckCard;
    }   //To get the deck card


    public boolean playCard(Card card, Player play)     //Method to play the card to see whether it is allowed or not
    {
        boolean isHigher = false;
        int comparison = 0;
        if(usedCard.size()==0 || this.playerGetTurnAgain(play))     //Decision if it is the start or the player get to play again
        {
            if(card  instanceof SupertrumpCard)
            {
                categoryMode = ((SupertrumpCard) card).useEffect();
            }
            isHigher = true;
        }
        else {
            if(card instanceof NormalCard) {            //Check if the card played is normal card
                if (getLastCard() instanceof NormalCard) {          //Check if the previous card is a normal card
                    if (categoryMode.equals("H")) {
                        Float current = new Float(((NormalCard) card).getHardness());
                        Float previous = new Float(((NormalCard) getLastCard()).getHardness());
                        comparison = current.compareTo(previous);
                    } else if (categoryMode.equals("S")) {
                        Float current = new Float(((NormalCard) card).getSpecificGravity());
                        Float previous = new Float(((NormalCard) getLastCard()).getSpecificGravity());
                        comparison = current.compareTo(previous);
                    } else if (categoryMode.equals("C")) {
                        Float current = new Float(((NormalCard) card).getCleavageValue());
                        Float previous = new Float(((NormalCard) getLastCard()).getCleavageValue());
                        comparison = current.compareTo(previous);
                    } else if (categoryMode.equals("A")) {
                        Float current = new Float(((NormalCard) card).getCrustalAbundanceValue());
                        Float previous = new Float(((NormalCard) getLastCard()).getCrustalAbundanceValue());
                        comparison = current.compareTo(previous);
                    } else if (categoryMode.equals("E")) {
                        Float current = new Float(((NormalCard) card).getEcoValueValue());
                        Float previous = new Float(((NormalCard) getLastCard()).getEcoValueValue());
                        comparison = current.compareTo(previous);
                    }               //Comparing the value according to the trump category
                    if (comparison > 0) {
                        isHigher = true;
                    }       //Allow to continue if the value is higher
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Invalid selection, your choice don't have enough value to fight the last card");
                    }   //Show error message
                } else {        //If user played supertrump card previously
                    isHigher = true;        //Just allow
                }
            }
            else
            {           //If user play supertrump card now
                setCategoryMode(((SupertrumpCard) card).useEffect());       //Change the category accordingly
                isHigher = true;
            }
        }
        return isHigher;
    }

    public void putCard(Card card)
    {
        usedCard.add(card);
    }           //Method to put card to the table

    public String getLastPlayerTurn() {                 //Method to get the last player name
        return lastPlayerTurn;
    }

    public void setLastPlayerTurn(String lastPlayerTurn) {      //Method to set the last player who didn't pass
        this.lastPlayerTurn = lastPlayerTurn;
    }

    public boolean playerGetTurnAgain(Player gamePlayer)        //Method to check if all except him pass
    {
        boolean statement = false;
        if(getLastPlayerTurn().equals(gamePlayer.getName()))
        {
            statement = true;
        }
        return statement;
    }

    public void setDeckCard(Deck deckCard) {                    //Method to set the deckcard again
        this.deckCard = deckCard;
    }
}
