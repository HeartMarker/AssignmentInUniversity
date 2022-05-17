import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JUnitTests {

    @Test
    public void test1() 
	{
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);
		
		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);
		
		assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    }

	@Test
	public void higherRank_Beat_LowerRank_Test1()
	{
		//Straight Flush
		Cardable[] cards1 = {new Card(14, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		//Four of a kind
		Cardable[] cards2 = {new Card(13, Cardable.Suit.HEART), new Card(13, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.SPADE)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		//Full House
		Cardable[] cards3 = {new Card(14, Cardable.Suit.SPADE), new Card(14, Cardable.Suit.SPADE), new Card(14, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.SPADE)};
		TestableHand th3 = new Hand();
		th3.addCards(cards3);

		//Flush
		Cardable[] cards4 = {new Card(2, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE)};
		TestableHand th4 = new Hand();
		th4.addCards(cards4);

		//Straight
		Cardable[] cards5 = {new Card(5, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.DIAMOND), new Card(8, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE)};
		TestableHand th5 = new Hand();
		th5.addCards(cards5);

		//Three of a kind
		Cardable[] cards6 = {new Card(4, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th6 = new Hand();
		th6.addCards(cards6);

		//Two Pairs
		Cardable[] cards7 = {new Card(4, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th7 = new Hand();
		th7.addCards(cards7);

		//Pair
		Cardable[] cards8 = {new Card(5, Cardable.Suit.CLUB), new Card(5, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.DIAMOND)};
		TestableHand th8 = new Hand();
		th8.addCards(cards8);

		//Nothing
		Cardable[] cards9 = {new Card(4, Cardable.Suit.CLUB), new Card(6, Cardable.Suit.HEART), new Card(8, Cardable.Suit.CLUB), new Card(10, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.DIAMOND)};
		TestableHand th9 = new Hand();
		th9.addCards(cards9);

		assertTrue(th1.compareTo(th2) > 0, "Straight Flush beats Four of a kind.");
		assertTrue(th1.compareTo(th3) > 0, "Straight Flush beats Full House.");
		assertTrue(th1.compareTo(th4) > 0, "Straight Flush beats Flush.");
		assertTrue(th1.compareTo(th5) > 0, "Straight Flush beats Straight.");
		assertTrue(th1.compareTo(th6) > 0, "Straight Flush beats three of a kind.");
		assertTrue(th1.compareTo(th7) > 0, "Straight Flush beats Two Pairs.");
		assertTrue(th1.compareTo(th8) > 0, "Straight Flush beats Pair.");
		assertTrue(th1.compareTo(th9) > 0, "Straight Flush beats Nothing.");

		assertTrue(th2.compareTo(th3) > 0, "Four of a kind beats Full House.");
		assertTrue(th3.compareTo(th4) > 0, "Full House beats Flush.");
		assertTrue(th4.compareTo(th5) > 0, "Flush beats Straight.");
		assertTrue(th5.compareTo(th6) > 0, "Straight beats three of a kind.");
		assertTrue(th6.compareTo(th7) > 0, "three of a kind beats Two Pairs.");
		assertTrue(th7.compareTo(th8) > 0, "Two Pairs beats Pair.");
		assertTrue(th8.compareTo(th9) > 0, "Pair beats Nothing.");
	}

	@Test
	public void tieWithSameRank_Normal_Test()
	{
		Cardable[] cards1 = {new Card(14, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		//Spade straight flush vs wake Heart straight flush
		assertTrue(th1.compareTo(th2) > 0, "Spade straight flush beat wake Heart straight flush.");

		Cardable[] cards3 = {new Card(9, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE)};
		TestableHand th3 = new Hand();
		th3.addCards(cards3);

		Cardable[] cards4 = {new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.SPADE)};
		TestableHand th4 = new Hand();
		th4.addCards(cards4);

		assertTrue(th3.compareTo(th4) < 0, "four 12 beat four 9.");

		Cardable[] cards5 = {new Card(9, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE)};
		TestableHand th5 = new Hand();
		th5.addCards(cards5);

		Cardable[] cards6 = {new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE)};
		TestableHand th6 = new Hand();
		th6.addCards(cards6);

		assertTrue(th6.compareTo(th5) > 0, "three 12 full house beat three 9 full house");

		Cardable[] cards7 = {new Card(14, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.SPADE)};
		TestableHand th7 = new Hand();
		th7.addCards(cards7);

		Cardable[] cards8 = {new Card(13, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART)};
		TestableHand th8 = new Hand();
		th8.addCards(cards8);

		assertTrue(th7.compareTo(th8) > 0, "A flush beat K flush");

		Cardable[] cards9 = {new Card(7, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.SPADE)};
		TestableHand th9 = new Hand();
		th9.addCards(cards9);

		Cardable[] cards10 = {new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART)};
		TestableHand th10 = new Hand();
		th10.addCards(cards10);

		assertTrue(th9.compareTo(th10) > 0, "6-7-8-9-10 beat 4-5-6-7-8");

		Cardable[] cards11 = {new Card(7, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE)};
		TestableHand th11 = new Hand();
		th11.addCards(cards11);

		Cardable[] cards12 = {new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART)};
		TestableHand th12 = new Hand();
		th12.addCards(cards12);

		assertTrue(th11.compareTo(th12) > 0, "7-7-10-10-8 beat 9-9-6-6-4");

		Cardable[] cards13 = {new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE)};
		TestableHand th13 = new Hand();
		th13.addCards(cards13);

		Cardable[] cards14 = {new Card(9, Cardable.Suit.HEART), new Card(11, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART)};
		TestableHand th14 = new Hand();
		th14.addCards(cards14);

		assertTrue(th13.compareTo(th14) > 0, "two 8 beat two 6");

		Cardable[] cards15 = {new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(10, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(14, Cardable.Suit.SPADE)};
		TestableHand th15 = new Hand();
		th15.addCards(cards15);

		Cardable[] cards16 = {new Card(9, Cardable.Suit.HEART), new Card(11, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.SPADE), new Card(3, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART)};
		TestableHand th16 = new Hand();
		th16.addCards(cards16);

		assertTrue(th15.compareTo(th16) > 0, "high card A beat high card 11");
	}

	@Test
	public void tieWithSameRank_Special_Test()
	{
		Cardable[] cards1 = {new Card(4, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(3, Cardable.Suit.DIAMOND), new Card(5, Cardable.Suit.DIAMOND), new Card(7, Cardable.Suit.DIAMOND), new Card(9, Cardable.Suit.DIAMOND), new Card(11, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) > 0, "flush 4-5-7-9-11 beat flush 3-5-7-9-11");

		//wheel case
		Cardable[] cards9 = {new Card(7, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.SPADE)};
		TestableHand th9 = new Hand();
		th9.addCards(cards9);

		Cardable[] cards10 = {new Card(14, Cardable.Suit.HEART), new Card(2, Cardable.Suit.DIAMOND), new Card(3, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART)};
		TestableHand th10 = new Hand();
		th10.addCards(cards10);

		assertTrue(th9.compareTo(th10) > 0, "6-7-8-9-10 beat A-2-3-4-5(wheel)");

		Cardable[] cards3 = {new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE)};
		TestableHand th3 = new Hand();
		th3.addCards(cards3);

		Cardable[] cards4 = {new Card(12, Cardable.Suit.HEART), new Card(12, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th4 = new Hand();
		th4.addCards(cards4);

		assertTrue(th3.compareTo(th4) > 0, "12-12-8-8-7 beat 12-12-6-6-9");

		Cardable[] cards31 = {new Card(14, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE)};
		TestableHand th31 = new Hand();
		th31.addCards(cards31);

		Cardable[] cards41 = {new Card(12, Cardable.Suit.HEART), new Card(12, Cardable.Suit.DIAMOND), new Card(8, Cardable.Suit.SPADE), new Card(8, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th41 = new Hand();
		th41.addCards(cards41);

		assertTrue(th31.compareTo(th41) > 0, "12-12-8-8-A beat 12-12-8-8-9");

		Cardable[] cards5 = {new Card(7, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE)};
		TestableHand th5 = new Hand();
		th5.addCards(cards5);

		Cardable[] cards6 = {new Card(7, Cardable.Suit.HEART), new Card(12, Cardable.Suit.DIAMOND), new Card(7, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART)};
		TestableHand th6 = new Hand();
		th6.addCards(cards6);

		assertTrue(th5.compareTo(th6) > 0, "7-7-12-11-8 beat 7-7-12-10-8");

		Cardable[] cards7 = {new Card(1, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE)};
		TestableHand th7 = new Hand();
		th7.addCards(cards7);

		Cardable[] cards8 = {new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND), new Card(5, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th8 = new Hand();
		th8.addCards(cards8);

		assertTrue(th7.compareTo(th8) < 0, "1-3-5-7-9 beat 2-3-5-7-9");


	}

	@Test
	public void tieWithSameRank_ExtremelyRare_Draw_Test()
	{

		Cardable[] cards1 = {new Card(14, Cardable.Suit.SPADE), new Card(13, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.SPADE)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(14, Cardable.Suit.HEART), new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) == 0, "Spade straight flush tie with Heart straight flush.");

		Cardable[] cards3 = {new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE)};
		TestableHand th3 = new Hand();
		th3.addCards(cards3);

		Cardable[] cards4 = {new Card(12, Cardable.Suit.HEART), new Card(12, Cardable.Suit.DIAMOND), new Card(8, Cardable.Suit.SPADE), new Card(8, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART)};
		TestableHand th4 = new Hand();
		th4.addCards(cards4);

		assertTrue(th3.compareTo(th4) == 0, "12-12-8-8-7 draw with 12-12-8-8-7");

		Cardable[] cards7 = {new Card(1, Cardable.Suit.CLUB), new Card(3, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.SPADE), new Card(9, Cardable.Suit.SPADE)};
		TestableHand th7 = new Hand();
		th7.addCards(cards7);

		Cardable[] cards8 = {new Card(1, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND), new Card(5, Cardable.Suit.SPADE), new Card(7, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th8 = new Hand();
		th8.addCards(cards8);

		assertTrue(th7.compareTo(th8) == 0, "1-3-5-7-9 draw with 1-3-5-7-9");

		Cardable[] cards5 = {new Card(7, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.SPADE)};
		TestableHand th5 = new Hand();
		th5.addCards(cards5);

		Cardable[] cards6 = {new Card(7, Cardable.Suit.HEART), new Card(12, Cardable.Suit.DIAMOND), new Card(7, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART)};
		TestableHand th6 = new Hand();
		th6.addCards(cards6);

		assertTrue(th5.compareTo(th6) == 0, "7-7-12-11-8 draw with 7-7-12-11-8");

	}

}
