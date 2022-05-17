import java.util.Collections;

public class Card implements Cardable,Comparable<Card>
{

    private int value;
    private Suit suit;
    private boolean selected;
    private boolean faceUp;

    public Card(int value, Suit suit)
    {
        this.value = value;
        this.suit = suit;

        this.faceUp = false;
        this.selected = false;
    }


    //return a string with value and suit
    public String toString()
    {
        StringBuilder tmp = new StringBuilder();


        switch(value)
        {
            case 2:tmp.append("2");
                break;
            case 3:tmp.append("3");
                break;
            case 4:tmp.append("4");
                break;
            case 5:tmp.append("5");
                break;
            case 6:tmp.append("6");
                break;
            case 7:tmp.append("7");
                break;
            case 8:tmp.append("8");
                break;
            case 9:tmp.append("9");
                break;
            case 10:tmp.append("10");
                break;
            case 11:tmp.append("J");
                break;
            case 12:tmp.append("Q");
                break;
            case 13:tmp.append("K");
                break;
            case 14:tmp.append("A");
                break;
        }

        tmp.append(" ");

        switch(suit)
        {
            case HEART : tmp.append('\u2665');
                break;
            case DIAMOND : tmp.append('\u2666');
                break;
            case SPADE: tmp.append('\u2660');
                break;
            case CLUB : tmp.append('\u2663');
                break;
        }

        return tmp.toString();
    }

    //overload toString() to help to get the result of hand
    public String toString(int i)
    {
        StringBuilder tmp = new StringBuilder();

        switch(i)
        {
            case 4 : tmp.append("Four ");
            break;
            case 3 : tmp.append("Three ");
            break;
            case 2 : tmp.append("Two ");
            break;
            case 1 : tmp.append("The Highest Card is ");
            break;
        }

        switch(value)
        {
            case 2:tmp.append("2");
                break;
            case 3:tmp.append("3");
                break;
            case 4:tmp.append("4");
                break;
            case 5:tmp.append("5");
                break;
            case 6:tmp.append("6");
                break;
            case 7:tmp.append("7");
                break;
            case 8:tmp.append("8");
                break;
            case 9:tmp.append("9");
                break;
            case 10:tmp.append("10");
                break;
            case 11:tmp.append("J");
                break;
            case 12:tmp.append("Q");
                break;
            case 13:tmp.append("K");
                break;
            case 14:tmp.append("A");
                break;
        }

        return tmp.toString();
    }

    public int compareTo(Card c)
    {
        return Integer.compare(value, c.getValue());
    }

    //method to get number
    public int getValue()
    {
        return value;
    }



    @Override
    public boolean getSelected() {
        return selected;
    }

    @Override
    public boolean getFaceUp() {
        return faceUp;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public void switchSelectedState() {
        if(selected)
            selected = false;
        else
            selected = true;
    }

    @Override
    public void resetSelected() {
        selected = false;
    }

    @Override
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
}
