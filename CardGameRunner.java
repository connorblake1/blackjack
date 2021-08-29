import java.util.Scanner;
public class CardGameRunner
{

    public static void main(String[] args)
    {
        BlackJackReferee referee = new BlackJackReferee();
        referee.playGame();


        /*
        // Create and print two cards
        Card one = new Card();
        Card two = new Card(" 2",2,"Spades");

        System.out.println(one);
        System.out.println(two);
        System.out.println("^^^^^^^^^^^^^");

        //Create and print a new, empty Card Pile.
        CardPile cp = new CardPile();
        System.out.println(cp);
        System.out.println("*****************");

        // add the two cards to the card pile and print it.
        cp.addCard(one);
        cp.addCard(two);
        System.out.println(cp);
        System.out.println("##################");

        // create another cardpile, this one filled with a deck of cards, and print it.
        CardPile deck = CardPile.makeDeck(CardPile.ACE_LOW);
        System.out.println(deck);
        System.out.println("@@@@@@@@@@@@@@@@@@");

        // take all the cards from the first cardpile and add them to the second cardpile.
        deck.takeAllCardsFromPile(cp);
        System.out.println("Deck: \n"+deck);
        System.out.println("Original Pile: \n"+cp);
        System.out.println("&&&&&&&&&");

        // deal 20 cards from the deck;
        for (int i=0; i<20; i++)
        {
            Card temp = deck.dealCard();
            System.out.println(i + "-->\t" + temp+"\t value: "+temp.getValue());
        }
        System.out.println("Remainder of deck: \n"+deck);

         */
    }
}
