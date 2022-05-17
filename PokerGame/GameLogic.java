import java.util.LinkedList;
import java.util.Random;

public class GameLogic implements GameLogicable{

    //everything about the game
    //the players, the deck of cards, the hands of both players, the game number, etc.
    private int state;
    private Deck deck;
    private Hand handPlayer;
    private Hand handCPU;
    private static int roundNum = 1;
    private static int CPUWin = 0;
    private static int playerWin = 0;
    private boolean dumbMode = true;//AI switch, default is true
    private LinkedList<Cardable> discarded;

    //constructor
    public GameLogic()
    {
        state = 1;
        //create a deck
        deck = new Deck();
        handPlayer = new Hand(deck);
        handCPU = new Hand(deck);
        discarded = new LinkedList<Cardable>();

        //CPU cards should be face down
        for(int i=0;i<Handable.HAND_SIZE;i++)
            handCPU.getCard(i).setFaceUp(false);


    }

    @Override
    public Handable getCPUHand() {
        return handCPU;
    }

    @Override
    public Handable getHumanHand() {
        return handPlayer;
    }

    @Override
    public boolean nextState(String[] messages) {
        if(state == 1)
        {
            //after first round, need to make human cards face up
            handPlayer.showAllCards();
            messages[0]="Beginning of Game "+roundNum;
            messages[1]="Player 1,choose which cards to discard";
            messages[2]="and click on the Proceed button";
            state++;
        }else if(state == 2)
        {
            messages[0]="Player 1 has discarded cards";
            messages[1]="Dumb CPU is thinking...";
            //discard the human selected card and add them to discarded
            discarded.addAll(handPlayer.discard());

            state++;
        }else if(state ==3)
        {
            //CPU needs to do something in this state

            //CPU will move randomly if the dumbMode is true
            if(dumbMode)
            {
                messages[0]="Dumb CPU has discarded cards";
                messages[1]="Each player will be dealt the same number of card they discarded";
                Random rand = new Random();
                //To determine how many cards the CPU will choose, max is 5
                int selected = rand.nextInt(Handable.HAND_SIZE+1);

                //To determine the index of hand it will choose
                int index = 0;
                boolean[] bool = new boolean[Handable.HAND_SIZE+1];
                for(int i=0;i<selected;i++)
                {
                    //use bool array to create some no-repeat random number
                    do
                    {
                        index = rand.nextInt(Handable.HAND_SIZE);
                    }while(bool[index]);
                    bool[index] = true;
                    //select cpu hand
                    handCPU.getCard(index).switchSelectedState();
                }
                discarded.addAll(handCPU.discard());
            }else
            {
                messages[0]="Clever CPU has discarded cards";
                messages[1]="Each player will be dealt the same number of card they discarded";

                int rankCPU = handCPU.evaluateHelper();

                if(handCPU.isStraight() || handCPU.isFlush() || rankCPU == 1 || rankCPU == 2)
                {
                    //do nothing because have straight, flush, four of a kind or full house
                }else
                {
                    if(rankCPU == 6)
                    {
                        //if there is nothing, hold the biggest card and discard others
                        for(int i=0;i<Handable.HAND_SIZE;i++)
                        {
                            if(i != handCPU.getMax())
                                handCPU.getCard(i).switchSelectedState();
                        }
                        discarded.addAll(handCPU.discard());
                    }else if(rankCPU == 4 )
                    {
                        //two pairs, should discard the single card
                        int[] index = handCPU.getIndexWithoutDuplicate(1);
                        handCPU.getCard(index[0]).switchSelectedState();
                        discarded.addAll(handCPU.discard());
                    }else if(rankCPU == 5)
                    {
                        //pair, should hold the pair
                        //there are 3 cards without repeat
                        int[] index = handCPU.getIndexWithoutDuplicate(3);
                        for(int i=0;i<3;i++)
                        {
                            handCPU.getCard(index[i]).switchSelectedState();
                        }
                        discarded.addAll(handCPU.discard());
                    }else
                    {
                        //three of a kind, should hold the 3-of-a-kind
                        int[] index = handCPU.getIndexWithoutDuplicate(2);
                        for(int i=0;i<2;i++)
                        {
                            handCPU.getCard(index[i]).switchSelectedState();
                        }
                        discarded.addAll(handCPU.discard());
                    }
                }


            }

            state++;
        }else if(state == 4)
        {
            messages[0]="Each player has been dealt new cards";
            messages[1]="Click on Proceed button to see the winner!";
            //player and CPU draw cards
            handPlayer.draw(deck,true);
            handCPU.draw(deck,false);

            state++;
        }else if(state ==5)
        {
            //showdown their hands
            handCPU.showAllCards();
            handPlayer.showAllCards();

            //print each hand
            messages[0] = "Dumb CPU has:" +handCPU.evaluateHand();
            messages[1] = "Player 1 has:" +handPlayer.evaluateHand();

            //To determine the winner
            int isWin = 0;
            isWin = handPlayer.compareTo(handCPU);
            if(isWin > 0 )
            {
                //player wins
                playerWin++;
                messages[2]="Player 1 wins";
            }else if(isWin == 0)
            {
                //if both hands are the exact same strength
                //very rare!
                messages[2]="draw!!!";
            }else
            {
                CPUWin++;
                messages[2]="Dumb CPU wins";
            }
            roundNum++;
            messages[3] = "player has won "+playerWin+" games,Dumb CPU has won "+CPUWin+" games";
            state++;
        }else
        {
            messages[0]="Click on Proceed to play a new game!";

            //discard hands of human and CPU
            discarded.addAll(handPlayer.returnCards());
            discarded.addAll(handCPU.returnCards());

            //reset the selected status of discarded list, because selected card must be discarded
            for(int i=0;i< discarded.size();i++)
                discarded.get(i).resetSelected();

            //before redrawing, need to return the deck
            deck.returnToDeck(discarded);
            deck.shuffle();
            discarded.clear();

            //redraw
            handPlayer.draw(deck,false);
            handCPU.draw(deck,false);

            state = 1;
        }
        //always return true
        return true;
    }
}
