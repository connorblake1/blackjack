import java.util.Scanner;
public class BlackJackReferee {
    private Scanner userInput;
    private String input;
    private CardPile dealerHand, playerHand, drawDeck, auxDeck; //aux deck is used for making the superdeck and as discard
    private boolean exit, handOver;
    private int gameNum, balance, pushHold;

    //TODO: Create a constructor for the ref to do things like creating a deck. This is where you might initialize (i.e.,
    //      give initial values to) the variables you made above.
    public BlackJackReferee() {
        userInput = new Scanner(System.in);
        drawDeck = new CardPile();
        for (int i = 0; i < 4; i++) {
            auxDeck = CardPile.makeDeck(CardPile.EQUAL_FACES);
            drawDeck.takeAllCardsFromPile(auxDeck);}
        drawDeck.shuffle();
        dealerHand = new CardPile();
        playerHand = new CardPile();
        exit = false;
        handOver = false;
        gameNum = 1;
        balance = 1000;
        pushHold = 0;
        input = ""; //this is so that when the game starts if you hit blackjack the game does not crash
        System.out.println("Created");
    }



    public void playGame()
    {
        System.out.println("Starting Game...");
        while (!exit) {
            //initial
            handOver = false;
            int bet = 0;
            System.out.println("Casino Balance: " + balance);
            System.out.println("Would you like to bet? ('no'/'yes')");
            input = userInput.nextLine();
            input += "1"; // so hitting ENTER does not crash it with a length - 0 problem
            if (!input.substring(0,1).equals(("n"))) {
                System.out.println("How much would you like to bet?");
                bet = userInput.nextInt();
                if (bet > balance || bet < 0) {
                    bet = 0;
                    System.out.println("Invalid bet... I guess you can't bet this hand");}
                balance -= bet;
                String dummyLine = userInput.nextLine();
            }
            System.out.println("Dealing.");
            dealerHand.addCard(drawDeck.dealCard());
            System.out.println("Dealer's Hand:");
            System.out.println(dealerHand);
            System.out.println("Value: " + printPileValue(dealerHand));
            Card tempCard = drawDeck.dealCard();
            String dealerCard2 = tempCard.toString();
            dealerHand.addCard(tempCard); // these three lines add the next card to the dealer's deck while retaining them in string form to print later
            playerHand.addCard(drawDeck.dealCard());
            playerHand.addCard(drawDeck.dealCard());
            System.out.println("Your Hand:");
            System.out.println(playerHand);
            System.out.println("Value: " + printPileValue(playerHand));
            //check for blackjack section
            Card temp1 = playerHand.dealCard();
            Card temp2 = playerHand.dealCard();
            if ((temp1.getFace().equals(" A") && temp2.getValue() == 10) || (temp2.getFace().equals(" A") && temp1.getValue() == 10)) {
                System.out.println("BLACKJACK");
                System.out.println("You win.");
                balance += 2.5*bet + 2*pushHold;
                pushHold = 0;
                handOver = true;}
            playerHand.addCard(temp1);
            playerHand.addCard(temp2);
            //your turn
            if (!handOver) {
                System.out.println("Type 'hit' to hit or 'stand' to stand.");
                input = userInput.nextLine();
                input += "1"; // so hitting ENTER does not crash it with a length - 0 problem
                }
            while (input.substring(0,1).equals("h")) {
                input = "a";
                tempCard = drawDeck.dealCard();
                System.out.println("Hit:" + tempCard.toString());
                playerHand.addCard(tempCard);
                System.out.println(playerHand);
                System.out.println("Value: " + printPileValue(playerHand));
                if (getScoreForPile(playerHand) > 21) {
                    System.out.println("You busted. Dealer wins.");
                    handOver = true;
                    break;}
                else if (getScoreForPile(playerHand) == 21){
                    break;}
                else {
                    System.out.println("Type 'hit' to hit or 'stand' to stand.");
                    input = userInput.nextLine();
                    input += "1"; // so hitting ENTER does not crash it with a length - 0 problem
                    }}
            if (!input.substring(0,1).equals("h") && !handOver) {
                System.out.println("You stood at " + getBestPileValue(playerHand) + ".");}
            //dealer turn
            if (!handOver) {
                System.out.println("The dealer's second card was a " + dealerCard2);
                while (getBestPileValue(dealerHand) <= 16) {
                    tempCard = drawDeck.dealCard();
                    System.out.println("The dealer hit at " + printPileValue(dealerHand) + " and got a " + tempCard.toString());
                    dealerHand.addCard(tempCard);}
                if (getScoreForPile(dealerHand) < 22) { System.out.println("The dealer stood at " + getBestPileValue(dealerHand) + ".");}
                System.out.println("Dealer's Hand:");
                System.out.println(dealerHand);
                //outcomes, excluding you busting early or blackjack
                if (getScoreForPile(dealerHand) > 21) {
                    System.out.println("The dealer busted. You win at " + getBestPileValue(playerHand) + ".");
                    balance += bet*2 + pushHold*2;
                    pushHold = 0;}
                else if (getBestPileValue(dealerHand) < getBestPileValue(playerHand)) {
                    System.out.println("You beat the dealer.");
                    balance += bet*2 + pushHold*2;
                    pushHold = 0;}
                else if (getBestPileValue(dealerHand) > getBestPileValue(playerHand)) {
                    System.out.println("You lost to the dealer.");}
                else {
                    System.out.println("You tied with the dealer. PUSH");
                    pushHold = bet;}}
            System.out.println("Would you like to play again? (yes/no)");
            input = userInput.nextLine();
            input += "1"; // so hitting ENTER does not crash it with a length - 0 problem
            if (input.substring(0,1).equals("n")) {
                exit = true;}
            else { //resets everything to start the game again
                gameNum++;
                System.out.println("Game " + gameNum + " is starting...");
                auxDeck.takeAllCardsFromPile(dealerHand);
                auxDeck.takeAllCardsFromPile(playerHand);
                if (drawDeck.size() < 10) {
                    auxDeck.shuffle();
                    drawDeck.takeAllCardsFromPile(auxDeck);
                    System.out.println("Reshuffling...");}}
        }
        System.out.println("Casino Balance: " + balance);
        System.out.println("Thank you for playing.");


    }

    /**
     * determines the number of points associated with this pile of cards.
     * For example:
     * <ul>
     * <li> 3 Hearts, J Clubs, 4 Diamonds --> 17</li>
     * <li> A Spades, K Clubs  --> 21</li>
     * <li> Q Spades, 8 Hearts, A Diamond --> 19</li>
     * </ul>
     * @param pileToScore
     * @return how many points this hand is worth
     * postcondition: The pileToScore should be left in the same condition as it was given by the end of this method.
     */
    public int getScoreForPile(CardPile pileToScore) {
        int score = 0;
        CardPile counter = new CardPile();
        Card tempCard1;
        while (pileToScore.hasCard()) {
            tempCard1 = pileToScore.dealCard();
            score += tempCard1.getValue();
            counter.addCard(tempCard1);}
        pileToScore.takeAllCardsFromPile(counter);
        return score;}
    public int aceCount(CardPile pileToCheck) {
        int acesCount = 0;
        Card tempCard;
        CardPile tempPile = new CardPile();
        while (pileToCheck.hasCard()) {
            tempCard = pileToCheck.dealCard();
            if (tempCard.getFace().equals(" A")) {
                acesCount++;}
            tempPile.addCard(tempCard);}
        pileToCheck.takeAllCardsFromPile(tempPile);
        return acesCount;}
    public String printPileValue(CardPile pileToCount) {
        if (aceCount(pileToCount) == 0) {
            return Integer.toString(getScoreForPile(pileToCount));}
        else {
            String possibilities = "";
            for (int i = 0; i < aceCount(pileToCount); i++) {
                possibilities += "/" + (10*(i+1) + getScoreForPile(pileToCount)); }
            return getScoreForPile(pileToCount) + possibilities;}
    }
    public int getBestPileValue(CardPile pileToCount) {
        for (int i = aceCount(pileToCount); i >= 0; i--) {
            if (getScoreForPile(pileToCount) + 10*aceCount(pileToCount) < 22) {
                return getScoreForPile(pileToCount) + 10*aceCount(pileToCount);}}
        return getScoreForPile(pileToCount);
    }



}
