package gameBoardTests;

import static org.junit.Assert.*;

import org.junit.Test;

import gameBoard.Board;

/**
 * Unit tests for the Board class.
 */
public class BoardTest {

	
	/*
	 * assertEquals(expected, actual)
	 */
	
	@Test
	public void testGetColumn() {
		assertEquals(1, Board.getColumn(31));
		assertEquals(0, Board.getColumn(0));
		assertEquals(3, Board.getColumn(3));
	}
	
	@Test
	public void testGetRow() {
		assertEquals(0, Board.getRow(0));
		assertEquals(3, Board.getRow(31));
		assertEquals(9, Board.getRow(98));
	}
	
	@Test
	public void testGetIndex() {
		assertEquals(59, Board.getIndex(5, 9));
	}
	
//	@Test
//	public void testIsValidQueenMove() {
//		assertEquals(true, Board.isValidQueenMove(2, 35));
//		assertEquals(false, Board.isValidQueenMove(5, 64));
//	}
}
