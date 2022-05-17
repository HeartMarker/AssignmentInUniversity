import java.util.LinkedList;
import java.util.Random;//to shuffle the deck

public class Deck implements Deckable{

    private LinkedList<Cardable> deck;

    public Deck()
    {
        this.deck = new LinkedList<>();

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<13;j++)
            {
                deck.add(new Card(j+2,Cardable.Suit.values()[i]));
            }
        }

        //shuffle after creating the deck
        shuffle();

    }




    @Override
    public void shuffle() {
        //use random number to shuffle the deck
        Random rand = new Random();

        //swap 200 times
        for(int i=0;i<200;i++)
        {
            //create numbers less than 52
            int index1 = rand.nextInt(deck.size());
            int index2 = rand.nextInt(deck.size());

            //get two card with the index
            Card c1 = (Card)deck.get(index1);
            Card c2 = (Card)deck.get(index2);

            //swap and set the cards
            deck.set(index2,c1);
            deck.set(index1,c2);
        }


    }

    @Override
    public void returnToDeck(LinkedList<Cardable> discarded) {

        deck.addAll(discarded);
    }

    @Override
    public Cardable drawACard(boolean faceUp) {
        Cardable card;

        card = deck.pop();
        card.setFaceUp(faceUp);

        return card;
    }
}
