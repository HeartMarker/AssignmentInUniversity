import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Hand implements Handable,TestableHand{

    private LinkedList<Cardable> hand;
    //description of poker hand

    public Hand()
    {
        hand = new LinkedList<>();
    }

    public Hand(Deck deck)
    {
        this.hand = new LinkedList<>();

        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            hand.add(deck.drawACard(true));
        }
    }

    @Override
    public Cardable getCard(int i) {
        Cardable tmp;

        tmp = hand.get(i);

        return tmp;
    }

    @Override
    public void draw(Deckable d, boolean faceUp) {

        //draw until the hand has 5 cards
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            if(hand.get(i) == null)
            {
                hand.remove(i);
                hand.addFirst(d.drawACard(faceUp));
            }
        }

    }

    @Override
    public void showAllCards() {

        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            hand.get(i).setFaceUp(true);
        }

    }

    @Override
    public LinkedList<Cardable> discard() {
        LinkedList<Cardable> discarded = new LinkedList<Cardable>();

        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            if(getCard(i).getSelected() == true)
            {
                //add discarded card to discard list
                discarded.add(getCard(i));
                //discard the selected card
                //after removing the card in linkedList, the list itself will become shorter one, therefore set null instead of remove
                hand.set(i,null);
            }
        }

        return discarded;
    }

    @Override
    public LinkedList<Cardable> returnCards() {
        LinkedList<Cardable> discarded = new LinkedList<Cardable>(hand);

        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            hand.set(i,null);
        }


        return discarded;
    }

    @Override
    public String evaluateHand() {
        String result = null;
        if (isStraight() && isFlush()) {
            result = "Straight Flush " + getHighestValue(1);
        } else if (evaluateHelper() == 1) {
            result = "Four of a kind "+ getHighestValue(8);
        } else if (evaluateHelper() == 2) {
            result = "Full House " + getHighestValue(7);
        } else if (isFlush()) {
            result = "Flush" + getHighestValue(1);
        } else if (isStraight()) {
            result = "Straight" + getHighestValue(1);
        } else if (evaluateHelper() == 3) {
            result = "Three of a Kind " + getHighestValue(4);
        } else if (evaluateHelper() == 4) {
            result = "Two Pairs " + getHighestValue(3);
        } else if (evaluateHelper() == 5) {
            result = "Pair "+ getHighestValue(2);
        } else if (evaluateHelper() == 6) {
            result = "Nothing " + getHighestValue(1);
        }

        return result;
    }

    @Override
    public int compareTo(Handable o) {
        int result = 0;

        int rankPlayer = getHandRank(this);
        int rankO = getHandRank(o);

        if(rankPlayer > rankO)
            return 1;
        else if(rankPlayer < rankO)
            return -1;
        else
        {
            //means two hands have same rank

            return breakTie(o,rankPlayer);
        }
    }



    //bunch of methods to help to evaluate hands
    public int getHandRank(Handable o)
    {
        int result = 0;
        if(o.evaluateHand().startsWith("Straight Flush"))
        {
            result = 9;
        }else if(o.evaluateHand().startsWith("Four of a kind"))
        {
            result = 8;
        }else if(o.evaluateHand().startsWith("Full House"))
        {
            result = 7;
        }else if(o.evaluateHand().startsWith("Flush"))
        {
            result = 6;
        }else if(o.evaluateHand().startsWith("Straight"))
        {
            result = 5;
        }else if(o.evaluateHand().startsWith("Three of a Kind"))
        {
            result = 4;
        }else if(o.evaluateHand().startsWith("Two Pairs"))
        {
            result = 3;
        }else if(o.evaluateHand().startsWith("Pair"))
        {
            result = 2;
        }else if(o.evaluateHand().startsWith("Nothing"))
        {
            result = 1;
        }

        return result;
    }

    //sort the card with value, if every next value is 1 bigger than last, it is straight
    public boolean isStraight()
    {
        boolean result = true;

        int[] cardValue = new int[Handable.HAND_SIZE];
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            Card curr = (Card)hand.get(i);
            cardValue[i] = curr.getValue();
        }
        Arrays.sort(cardValue);

        //check the normal case
        for(int i=0;i<Handable.HAND_SIZE-1;i++)
        {
            if (cardValue[i + 1] != cardValue[i] + 1) {
                result = false;
                break;
            }
        }

        //check the special case: 5-4-3-2-A, known as a wheel
        if(cardValue[0]==2 && cardValue[1]==3&&cardValue[2]==4&&cardValue[3]==5&&cardValue[4]==14)
        {
            return true;
        }

        return result;
    }

    //check is flush or not
    public boolean isFlush()
    {
        boolean result = true;
        Cardable.Suit compareSuit = hand.getFirst().getSuit();

        for(int i=1;i<Handable.HAND_SIZE;i++)
        {

            if(hand.get(i).getSuit() != compareSuit) {
                result = false;
                break;
            }
        }

        return result;
    }

    //check other hand status
    //return 1 if four of a kind
    //return 2 if full house
    //return 3 if three of a kind
    //return 4 if two pairs
    //return 5 if pair
    //return 6 if nothing
    public int evaluateHelper()
    {
        //sort a int array
        int[] cardValue = new int[Handable.HAND_SIZE];
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            Card curr = (Card)hand.get(i);
            cardValue[i] = curr.getValue();
        }
        Arrays.sort(cardValue);

        int count = 0;
        for(int i=0; i<Handable.HAND_SIZE-1; i++)
        {
            if(cardValue[i] == cardValue[i+1])
                count++;
        }

        if(count == 3)
        {
            if(cardValue[1] == cardValue[3])
            {
                //four of a kind
                return 1;
            }else
            {
                //full house
                return 2;
            }
        }
        if(count == 2)
        {
            if(cardValue[0] == cardValue[2] || cardValue[1] == cardValue[3] || cardValue[2] == cardValue[4])
            {
                //three of a kind
                return 3;
            }else
            {
                //two pairs
                return 4;
            }
        }
        if(count == 1)
        {
            //pair
            return 5;
        }
        return 6;
    }

    //return the index of max in hand
    public int getMax()
    {
        int result = 0;
        Card tmp = (Card)hand.getFirst();
        for(int i = 1;i<Handable.HAND_SIZE;i++)
        {
            if(tmp.compareTo((Card)hand.get(i)) < 0)
            {
                tmp = (Card)hand.get(i);
                result = i;
            }

        }

        return result;
    }

    //the param indicates that there are i values that not repeat
    //return the int[] contains the index of card that not repeat
    public int[] getIndexWithoutDuplicate(int n)
    {
        int index = 0;
        int[] indexes = new int[n];

        Card[] cards = new Card[Handable.HAND_SIZE];
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            cards[i] = (Card)hand.get(i);
        }

        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            int counter = 0;

            for(int j=0;j<Handable.HAND_SIZE;j++)
            {
                if(i == j)
                    continue;
                if(cards[i].getValue() == cards[j].getValue())
                    counter++;
            }
            if(counter == 1 && index < n)
            {
                indexes[index] = i;
                index++;
            }
        }
        return indexes;
    }


    @Override
    public void addCards(Cardable[] cards) {
        Collections.addAll(hand, cards);
    }

    public String getHighestValue(int rank)
    {
        Card[] cards = new Card[Handable.HAND_SIZE];
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            cards[i] = (Card)hand.get(i);
        }
        Arrays.sort(cards);

        if(rank == 8)
        {
            //consider of "four of a kind"
            //need to return the value of that kind
            if(cards[0].getValue() == cards[1].getValue())
                return cards[0].toString(4);
            else
                return cards[1].toString(4);

        }else if(rank == 7)
        {
            //full house
            if(cards[0].getValue() == cards[2].getValue())
                return cards[0].toString(3);
            else
                return cards[2].toString(3);
        }else if(rank == 4)
        {
            //three of a kind
            if(cards[0].getValue() == cards[1].getValue())
                return cards[0].toString(3);
            else if(cards[1].getValue() == cards[2].getValue())
                return cards[1].toString(3);
            else
                return cards[2].toString(3);
        }else if(rank == 3)
        {
            //two pairs
            if(cards[4].getValue() == cards[3].getValue())
                return cards[4].toString(2);
            else
                return cards[3].toString(2);
        }else if(rank == 2)
        {
            //pairs
            if(cards[0].getValue() == cards[1].getValue())
                return cards[0].toString(2);
            else if(cards[1].getValue() == cards[2].getValue())
                return cards[1].toString(2);
            else if(cards[2].getValue() == cards[3].getValue())
                return cards[2].toString(2);
            else
                return cards[3].toString(2);
        }else
        {
            return cards[4].toString(1);
        }
    }

    public int breakTie(Handable o, int rankPlayer)
    {
        int result = 0;

        Card[] cardsPlayer = new Card[Handable.HAND_SIZE];
        Card[] cardsCPU = new Card[Handable.HAND_SIZE];
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            cardsPlayer[i] = (Card)hand.get(i);
            cardsCPU[i] = (Card)o.getCard(i);
        }
        Arrays.sort(cardsPlayer);
        Arrays.sort(cardsCPU);

        if(rankPlayer == 9 || rankPlayer == 5)
        {
            //means it is straight or straight flush
            //we only need to check the highest number of two hands
            //if equals, its completely equal

            //but there is a special case: WHEEL
            if(cardsPlayer[4].getValue() == 14 && cardsPlayer[0].getValue() == 2)
            {
                if(cardsCPU[4].getValue() == 14 && cardsCPU[0].getValue() == 2)
                    return 0;
                else
                    return -1;
            }else if(cardsCPU[4].getValue() == 14 && cardsCPU[0].getValue() == 2)
            {
                if(cardsPlayer[4].getValue() == 14 && cardsPlayer[0].getValue() == 2)
                    return 0;
                else
                    return 1;
            }

            return Integer.compare(cardsPlayer[4].getValue(), cardsCPU[4].getValue());
        }else if(rankPlayer == 6 || rankPlayer == 1)
        {
            //flush or nothing
            //we need to check from the highest to lowest
            for(int i=4; i>=0; i--)
            {
                if(cardsPlayer[i].getValue()>cardsCPU[i].getValue())
                    return 1;
                else if(cardsPlayer[i].getValue()<cardsCPU[i].getValue())
                    return -1;
            }
            //if totally equal
            return 0;
        }else if(rankPlayer == 8)
        {
            //four of a kind
            //firstly compare two 4 cards
            if(cardsPlayer[1].getValue() > cardsCPU[1].getValue())
                return 1;
            else
                return -1;
            //we have only one deck in the game so there is no possibility to have two same 4 of a kind
            /*else
            {
                //then compare the single card
                if(cardsPlayer[0].getValue() == cardsPlayer[1].getValue())
                {
                    //single card is card[4] for player
                    if(cardsCPU[0].getValue() == cardsCPU[1].getValue())
                    {
                        //single card is card[4] for CPU
                        if(cardsPlayer[4].getValue() > cardsCPU[4].getValue())
                            return 1;
                        else if(cardsPlayer[4].getValue() < cardsCPU[4].getValue())
                            return -1;
                        else
                            return 0;
                    }else
                    {
                        //single card is card[0] for CPU
                        if(cardsPlayer[4].getValue() > cardsCPU[0].getValue())
                            return 1;
                        else if(cardsPlayer[4].getValue() < cardsCPU[0].getValue())
                            return -1;
                        else
                            return 0;
                    }
                }else
                {
                    //single card is card[0] for player
                    if(cardsCPU[0].getValue() == cardsCPU[1].getValue())
                    {
                        //single card is card[4] for CPU
                        if(cardsPlayer[0].getValue() > cardsCPU[4].getValue())
                            return 1;
                        else if(cardsPlayer[4].getValue() < cardsCPU[4].getValue())
                            return -1;
                        else
                            return 0;
                    }else
                    {
                        //single card is card[0] for CPU
                        if(cardsPlayer[0].getValue() > cardsCPU[0].getValue())
                            return 1;
                        else if(cardsPlayer[4].getValue() < cardsCPU[0].getValue())
                            return -1;
                        else
                            return 0;
                    }
                }
            }*/
        }else if(rankPlayer == 7 || rankPlayer == 4)
        {
            //full house or three of a kind
            //cards[2] always be the 3 of a kind card
            if(cardsPlayer[2].getValue() > cardsCPU[2].getValue())
                return 1;
            else
                return -1;
        }else if(rankPlayer == 3)
        {
            //two pairs
            //cards[3] always be the bigger pair
            //cards[1] always be the smaller pair
            if(cardsPlayer[3].getValue() > cardsCPU[3].getValue())
                return 1;
            else if(cardsPlayer[3].getValue() < cardsCPU[3].getValue())
                return -1;
            else
            {
                if(cardsPlayer[1].getValue() > cardsCPU[1].getValue())
                    return 1;
                else if(cardsPlayer[1].getValue() < cardsCPU[1].getValue())
                    return -1;
                else
                {
                    //compare the single card
                    if(cardsPlayer[0].getValue() != cardsPlayer[1].getValue())
                    {
                        //means the single card is cards[0] for player hand
                        if(cardsCPU[0].getValue() != cardsCPU[1].getValue())
                        {
                            return Integer.compare(cardsPlayer[0].getValue(), cardsCPU[0].getValue());
                        }else if(cardsCPU[2].getValue() != cardsCPU[3].getValue())
                        {
                        //means the single card is cards[2]
                            return Integer.compare(cardsPlayer[0].getValue(), cardsCPU[2].getValue());
                        }else
                        {
                        //means the single card is cards[4]
                            return Integer.compare(cardsPlayer[0].getValue(), cardsCPU[4].getValue());
                        }
                    }else if(cardsPlayer[2].getValue() != cardsPlayer[3].getValue())
                    {
                        if(cardsCPU[0].getValue() != cardsCPU[1].getValue())
                        {
                            return Integer.compare(cardsPlayer[2].getValue(), cardsCPU[0].getValue());
                        }else if(cardsCPU[2].getValue() != cardsCPU[3].getValue())
                        {
                            return Integer.compare(cardsPlayer[2].getValue(), cardsCPU[2].getValue());
                        }else
                        {
                            return Integer.compare(cardsPlayer[2].getValue(), cardsCPU[4].getValue());
                        }
                    }else
                    {
                        if(cardsCPU[0].getValue() != cardsCPU[1].getValue())
                        {
                            return Integer.compare(cardsPlayer[4].getValue(), cardsCPU[0].getValue());
                        }else if(cardsCPU[2].getValue() != cardsCPU[3].getValue())
                        {
                            return Integer.compare(cardsPlayer[4].getValue(), cardsCPU[2].getValue());
                        }else
                        {
                            return Integer.compare(cardsPlayer[4].getValue(), cardsCPU[4].getValue());
                        }
                    }
                }
            }
        }else
        {
            //Pair
            //It has 16 cases......
            int pairIndexPlayer = getPairIndex(this);
            int pairIndexCPU = getPairIndex(o);
            if(cardsPlayer[pairIndexPlayer].getValue() > cardsCPU[pairIndexCPU].getValue())
                return 1;
            else if(cardsPlayer[pairIndexPlayer].getValue() < cardsCPU[pairIndexCPU].getValue())
                return -1;
            else
            {
                //there are 16 cases, so remove the pair in the array first
                Card[] cardsPlayerNew = new Card[Handable.HAND_SIZE];
                Card[] cardsCPUNew = new Card[Handable.HAND_SIZE];
                for(int i=0; i<Handable.HAND_SIZE; i++)
                {
                    if(i != pairIndexPlayer && i != pairIndexPlayer + 1)
                        cardsPlayerNew[i] = cardsPlayer[i];
                    else
                        cardsPlayerNew[i] = new Card(2, Cardable.Suit.HEART);
                    if(i != pairIndexCPU && i != pairIndexCPU + 1)
                        cardsCPUNew[i] = cardsCPU[i];
                    else
                        cardsCPUNew[i] = new Card(2, Cardable.Suit.HEART);
                }
                Arrays.sort(cardsPlayerNew);
                Arrays.sort(cardsCPUNew);
                for(int i=4;i>=0;i--)
                {
                    if(cardsPlayerNew[i].getValue() > cardsCPUNew[i].getValue())
                        return 1;
                    else if(cardsPlayerNew[i].getValue() < cardsCPUNew[i].getValue())
                        return -1;
                }
                return 0;
            }
        }

    }

    // return the pair index of hand to help break the Pair tie
    private int getPairIndex(Handable o)
    {
        int result = 0;

        Card[] cards = new Card[Handable.HAND_SIZE];
        for(int i=0;i<Handable.HAND_SIZE;i++)
        {
            cards[i] = (Card)o.getCard(i);
        }
        Arrays.sort(cards);

        if(cards[0].getValue() == cards[1].getValue())
            result = 0;
        else if(cards[1].getValue() == cards[2].getValue())
            result = 1;
        else if(cards[2].getValue() == cards[3].getValue())
            result = 2;
        else if(cards[3].getValue() == cards[4].getValue())
            result = 3;

        return result;
    }


}
