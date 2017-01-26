import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Rico on 1/25/2017.
 */
public class MainGame extends JFrame implements ActionListener{
    JLabel guide = new JLabel("Enter the number of player");
    JPanel mainScreen = new JPanel();
    JButton threePlayer = new JButton("3");
    JButton fourPlayer = new JButton("4");
    JButton fivePlayer = new JButton("5");
    JTextField playerName = new JTextField(20);
    JButton done = new JButton("Done");
    JPanel gameScreen = new JPanel();
    JPanel tableScreen = new JPanel(new FlowLayout());
    JPanel cardInHand = new JPanel(new GridLayout(2,8));
    ImageIcon deckCover = new ImageIcon("images\\slide65.jpg");
    JButton deckCard = new JButton(new ImageIcon(deckCover.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH)));
    JLabel lastCard = new JLabel("No Card played previously");
    JButton hardnessMode = new JButton("Hardness");
    JButton specificGravityMode = new JButton("Specific Gravity");
    JButton cleavageMode = new JButton("Cleavage");
    JButton crustalAbundanceMode = new JButton("Crustal Abundance");
    JButton economicValueMode = new JButton("Economic Value");
    ArrayList<JButton> listOfJButton = new ArrayList<>();
    boolean justPickMode = false;
    int playerTurn = 0;
    int doneCount = 1;
    int numberOfPlayer;
    TableGame gameTable;
    ArrayList<String> playersList;
    Deck gameDeck;
    int passCount=0;

    MainGame()
    {
        super("Mineral Supertrump");
        ArrayList<Card> cardList = new ArrayList<Card>();
        playersList = new ArrayList<String>();
        setLayout(new BorderLayout());
        gameScreen.setLayout(new BoxLayout(gameScreen,BoxLayout.Y_AXIS));
        cardInHand.setMaximumSize(new Dimension(1920,720));
        add(mainScreen,BorderLayout.CENTER);
        add(guide,BorderLayout.NORTH);
        guide.setHorizontalAlignment(JLabel.CENTER);
        done.addActionListener(this);
        deckCard.addActionListener(this);
        hardnessMode.addActionListener(this);
        specificGravityMode.addActionListener(this);
        cleavageMode.addActionListener(this);
        crustalAbundanceMode.addActionListener(this);
        economicValueMode.addActionListener(this);
        setSize(1920,1080);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String[] array;
        String string = "";
        Path file =
                Paths.get("C:\\Users\\Rico\\IdeaProjects\\CP2406 Assignment 2\\src\\card.txt");
        try
        {
            InputStream input = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            reader.readLine();
            int y=1;
            while ((string = reader.readLine()) != null){
                array = string.split(",");
                ImageIcon img = new ImageIcon("images\\slide"+(y)+".jpg");
                cardList.add(new NormalCardWithPictures(array[0],Float.valueOf(array[1]),Float.valueOf(array[2]),array[3],array[4],array[5],new ImageIcon(img.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH)) ));
                y++;
            }
            ImageIcon img1 = new ImageIcon("images\\Slide58.jpg");
            cardList.add(new SupertrumpCardWithPictures("The Mineralogist",new ImageIcon(img1.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            ImageIcon img2 = new ImageIcon("images\\Slide60.jpg");
            cardList.add(new SupertrumpCardWithPictures("The Geologist",new ImageIcon(img2.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            ImageIcon img3 = new ImageIcon("images\\Slide59.jpg");
            cardList.add(new SupertrumpCardWithPictures("The Geophysicist",new ImageIcon(img3.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            ImageIcon img4 = new ImageIcon("images\\Slide56.jpg");
            cardList.add(new SupertrumpCardWithPictures("The Petrologist",new ImageIcon(img4.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            ImageIcon img5 = new ImageIcon("images\\Slide55.jpg");
            cardList.add(new SupertrumpCardWithPictures("The Miner",new ImageIcon(img5.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
            ImageIcon img6 = new ImageIcon("images\\Slide57.jpg");
            cardList.add(new SupertrumpCardWithPictures("The Gemmologist",new ImageIcon(img6.getImage().getScaledInstance(200,300,Image.SCALE_SMOOTH))));
        }
        catch(Exception e)
        {
            guide.setText("Error, please reopen the application");
        }
        gameDeck = new Deck(cardList);
        playerSelection();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==threePlayer)
        {
            numberOfPlayer = 3;
            mainScreen.removeAll();
            enterPlayerName();
        }
        else if(e.getSource()==fourPlayer)
        {
            numberOfPlayer = 4;
            mainScreen.removeAll();
            enterPlayerName();
        }
        else if(e.getSource()==fivePlayer) {
            numberOfPlayer = 5;
            mainScreen.removeAll();
            enterPlayerName();
        }
        else if(e.getSource()==done)
        {
            if(doneCount<numberOfPlayer)
            {
                String name = playerName.getText();
                playersList.add(name);
                doneCount+=1;
                guide.setText("Enter player "+doneCount+" name");
                playerName.setText("");
            }
            else
            {
                String name = playerName.getText();
                playersList.add(name);
                gameTable = new TableGame(playersList,gameDeck);
                pickTrump();
            }
        }
        else if(e.getSource()==hardnessMode)
        {
            gameTable.setCategoryMode("H");
            justPickMode = true;
            passCount = 0;
            playCard();
        }
        else if(e.getSource()==specificGravityMode)
        {
            gameTable.setCategoryMode("S");
            justPickMode = true;
            passCount = 0;
            playCard();
        }
        else if(e.getSource()==cleavageMode)
        {
            gameTable.setCategoryMode("C");
            justPickMode = true;
            passCount = 0;
            playCard();
        }
        else if(e.getSource()==crustalAbundanceMode)
        {
            gameTable.setCategoryMode("A");
            justPickMode = true;
            passCount = 0;
            playCard();
        }
        else if(e.getSource()==economicValueMode)
        {
            gameTable.setCategoryMode("E");
            justPickMode = true;
            passCount = 0;
            playCard();
        }
        else if(e.getSource()==deckCard)
        {
            if (gameTable.getUsedCard().size()==0 || justPickMode)
            {guide.setText("You cannot pass, this is the first turn or you have picked the mode so you need to play a card");}
            else {
                gameTable.getGameplayers().get(playerTurn % (gameTable.getGameplayers().size())).drawCard(gameTable.getDeckCard().cardDrawn());
                playerTurn++;
                passCount++;
                playCard();
            }
        }

        revalidate();
        repaint();
    }

    public static void main(String[] args) {

        MainGame app = new MainGame();
    }

    public void playerSelection()
    {
        threePlayer.addActionListener(this);
        fourPlayer.addActionListener(this);
        fivePlayer.addActionListener(this);
        mainScreen.add(threePlayer);
        mainScreen.add(fourPlayer);
        mainScreen.add(fivePlayer);
    }

    public void pickTrump()
    {
        guide.setText(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).getName()+", choose the Trump mode you want to play");
        mainScreen.removeAll();
        gameScreen.removeAll();
        tableScreen.removeAll();
        cardInHand.removeAll();
        mainScreen.add(gameScreen);
        gameScreen.add(tableScreen);
        gameScreen.add(cardInHand);
        tableScreen.add(hardnessMode);
        tableScreen.add(specificGravityMode);
        tableScreen.add(cleavageMode);
        tableScreen.add(crustalAbundanceMode);
        tableScreen.add(economicValueMode);
        int turnOfXPlayer = playerTurn%(gameTable.getGameplayers().size());
        for(int x = 0; x< gameTable.getGameplayers().get(turnOfXPlayer).getHand().size();x++)
        {
            if(gameTable.getGameplayers().get(turnOfXPlayer).getCard(x) instanceof NormalCard)
            {
                JButton cardbtn = new JButton(new ImageIcon(((NormalCardWithPictures) gameTable.getGameplayers().get(turnOfXPlayer).getCard(x)).getCardImage().getImage()));
                cardbtn.setSize(150,200);
                cardInHand.add(cardbtn);

            }
            else if(gameTable.getGameplayers().get(turnOfXPlayer).getCard(x) instanceof SupertrumpCard)
            {
                JButton cardbtn = new JButton(new ImageIcon(((SupertrumpCardWithPictures) gameTable.getGameplayers().get(turnOfXPlayer).getCard(x)).getCardImage().getImage()));
                cardbtn.setSize(150,200);
                cardInHand.add(cardbtn);
            }
        }


    }

    public void enterPlayerName()
    {
        guide.setText("Enter player "+doneCount+" name");
        mainScreen.removeAll();
        mainScreen.add(playerName);
        mainScreen.add(done);
    }

    public void playCard()
    {
        if(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).getName().equals(gameTable.getLastPlayerTurn())&& passCount == gameTable.getGameplayers().size()-1)
        {
            pickTrump();
        }
            else {
            int turnOfXPlayer = playerTurn % (gameTable.getGameplayers().size());
            guide.setText("Your move " + gameTable.getGameplayers().get(turnOfXPlayer).getName() + ", click on the card you want to play. Current Trump Mode = " + gameTable.getGameMode() + " or click the deck to pass. Below is the card you have");
            mainScreen.removeAll();
            tableScreen.removeAll();
            cardInHand.removeAll();
            listOfJButton.clear();
            gameScreen.add(tableScreen);
            gameScreen.add(cardInHand);
            tableScreen.add(deckCard);
            tableScreen.add(lastCard);
            for (int x = 0; x < gameTable.getGameplayers().get(turnOfXPlayer).getHand().size(); x++) {
                if (gameTable.getGameplayers().get(turnOfXPlayer).getCard(x) instanceof NormalCard) {
                    JButton cardbtn = new JButton(new ImageIcon(((NormalCardWithPictures) gameTable.getGameplayers().get(turnOfXPlayer).getCard(x)).getCardImage().getImage()));
                    cardbtn.setSize(200, 300);
                    listOfJButton.add(cardbtn);
                } else {
                    JButton cardbtn = new JButton(new ImageIcon(((SupertrumpCardWithPictures) gameTable.getGameplayers().get(turnOfXPlayer).getCard(x)).getCardImage().getImage()));
                    cardbtn.setSize(200, 300);
                    listOfJButton.add(cardbtn);
                }
            }
            for (int button = 0; button < gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getHand().size(); button++) {
                listOfJButton.get(button).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton buttonSource = (JButton) e.getSource();
                        Card cardplayed = gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getCard(listOfJButton.indexOf(buttonSource));             //Trying to get the card inputted
                        boolean gameContinue = useCard(cardplayed, gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()));
                        if(gameTable.getCategoryMode().equals("NEW")) {
                            gameTable.putCard(cardplayed);
                            gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getHand().remove(listOfJButton.indexOf(buttonSource));
                            if(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).getHand().size()==0)
                            {
                                playerTurn = playerTurn%gameTable.getGameplayers().size();
                                gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).leaveGame(gameTable);
                            }
                            else {
                                justPickMode = false;
                                pickTrump();
                            }
                        }
                        else if(gameTable.getCategoryMode().equals("SS"))
                        {
                            if(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).checkWinningCard())                          //Check if the player got the winning card
                            {
                                for (int x = 0; x < gameTable.getGameplayers().get(turnOfXPlayer).getHand().size(); x++)
                                {
                                    gameTable.putCard(cardplayed);
                                    gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getHand().remove(listOfJButton.indexOf(buttonSource));
                                    gameTable.setLastPlayerTurn(gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getName());
                                }
                                gameTable.setCategoryMode("S");                 //Return the mode back to Specific gravity
                                if(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).getHand().size()==0)
                                {
                                    playerTurn = playerTurn%gameTable.getGameplayers().size();
                                    gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).leaveGame(gameTable);
                                }
                            }
                            else
                            {gameTable.setCategoryMode("S");}                   //Return the mode back to Specific gravity
                            if(gameContinue) {                              //For playing the card
                                gameTable.putCard(cardplayed);
                                gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getHand().remove(listOfJButton.indexOf(buttonSource));
                                gameTable.setLastPlayerTurn(gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getName());
                                if(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).getHand().size()==0)
                                {
                                    playerTurn = playerTurn%gameTable.getGameplayers().size();
                                    gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).leaveGame(gameTable);
                                }
                                else {
                                    playerTurn++;
                                    if (gameTable.getLastCard() instanceof SupertrumpCard) {
                                        lastCard = new JLabel(new ImageIcon(((SupertrumpCardWithPictures) gameTable.getLastCard()).getCardImage().getImage()));
                                    } else {
                                        lastCard = new JLabel(new ImageIcon(((NormalCardWithPictures) gameTable.getLastCard()).getCardImage().getImage()));
                                    }
                                    justPickMode = false;
                                    passCount = 0;
                                    playCard();
                                    revalidate();
                                    repaint();
                                }
                            }
                        }
                        else{
                        if (gameContinue) {
                            gameTable.putCard(cardplayed);
                            gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getHand().remove(listOfJButton.indexOf(buttonSource));
                            gameTable.setLastPlayerTurn(gameTable.getGameplayers().get(playerTurn % gameTable.getGameplayers().size()).getName());
                            if(gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).getHand().size()==0)
                            {
                                playerTurn = playerTurn%gameTable.getGameplayers().size();
                                gameTable.getGameplayers().get(playerTurn%gameTable.getGameplayers().size()).leaveGame(gameTable);
                            }
                            else {
                                playerTurn++;
                                if (gameTable.getLastCard() instanceof SupertrumpCard) {
                                    lastCard = new JLabel(new ImageIcon(((SupertrumpCardWithPictures) gameTable.getLastCard()).getCardImage().getImage()));
                                } else {
                                    lastCard = new JLabel(new ImageIcon(((NormalCardWithPictures) gameTable.getLastCard()).getCardImage().getImage()));
                                }
                                justPickMode = false;
                                passCount = 0;
                                playCard();
                                revalidate();
                                repaint();
                            }
                        } else {
                            guide.setText("Card is not playable, repick or pass. The Game mode is: " + gameTable.getGameMode());
                            revalidate();
                            repaint();
                        }}
                    }
                }
                );
                cardInHand.add(listOfJButton.get(button));
            }
            mainScreen.add(gameScreen);

        }
    }

    public boolean useCard(Card card, Player play)     //Method to play the card to see whether it is allowed or not
    {
        boolean isHigher = false;
        int comparison = 0;

        if(play.getName().equals(gameTable.getLastPlayerTurn()) || gameTable.getUsedCard().size()==0)     //Decision if it is the start or the player get to play again
        {
            if(card  instanceof SupertrumpCard)
            {
                gameTable.setCategoryMode(((SupertrumpCard) card).useEffect());
            }
            isHigher = true;
        }
        else {
            if(card instanceof NormalCard) {
                if (gameTable.getLastCard() instanceof NormalCard) {
                    if (gameTable.getCategoryMode().equals("H")) {
                        Float current = new Float(((NormalCard) card).getHardness());
                        Float previous = new Float(((NormalCard) gameTable.getLastCard()).getHardness());
                        comparison = current.compareTo(previous);
                    } else if (gameTable.getCategoryMode().equals("S")) {
                        Float current = new Float(((NormalCard) card).getSpecificGravity());
                        Float previous = new Float(((NormalCard) gameTable.getLastCard()).getSpecificGravity());
                        comparison = current.compareTo(previous);
                    } else if (gameTable.getCategoryMode().equals("C")) {
                        Float current = new Float(((NormalCard) card).getCleavageValue());
                        Float previous = new Float(((NormalCard) gameTable.getLastCard()).getCleavageValue());
                        comparison = current.compareTo(previous);
                    } else if (gameTable.getCategoryMode().equals("A")) {
                        Float current = new Float(((NormalCard) card).getCrustalAbundanceValue());
                        Float previous = new Float(((NormalCard) gameTable.getLastCard()).getCrustalAbundanceValue());
                        comparison = current.compareTo(previous);
                    } else if (gameTable.getCategoryMode().equals("E")) {
                        Float current = new Float(((NormalCard) card).getEcoValueValue());
                        Float previous = new Float(((NormalCard) gameTable.getLastCard()).getEcoValueValue());
                        comparison = current.compareTo(previous);
                    }               //Comparing the value according to the trump category
                    if (comparison > 0) {
                        isHigher = true;
                    }
                } else {
                    isHigher = true;
                }
            }
            else
            {
                gameTable.setCategoryMode(((SupertrumpCard) card).useEffect());       //Change the category accordingly
                isHigher = true;
            }
        }
        return isHigher;
    }
}
