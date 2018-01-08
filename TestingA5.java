package cs2s03;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestingA5 {
	Expr exprT;
	Evaluate evalT;
	String test;
	IntVal intValT;
	DblVal dblValT;
	
	@Test
	public void BasicTestingExprClass() throws NotAnInteger {
		exprT = new Divide(new Integer2(10), new Integer2(5));
		assertEquals(2, exprT.evalToInt());
		assertEquals(2, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new Minus(new Integer2(4), new Integer2(3));
		assertEquals(1, exprT.evalToInt());
		assertEquals(1, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new Plus(new Integer2(5), new Integer2(6));
		assertEquals(11, exprT.evalToInt());
		assertEquals(11, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new Times(new Integer2(4), new Integer2(4));
		assertEquals(16, exprT.evalToInt());
		assertEquals(16, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new UnaryMinus(new Integer2(4));
		assertEquals(-4, exprT.evalToInt());
		assertEquals(-4, exprT.evalToFloat(), 1.0e-6);
	}
	
	@Test
	public void MoreTestingExprClass() throws NotAnInteger { 
		// Except for unary minus, added another of its own type, one level deeper
		exprT = new Divide(new Integer2(10), new Divide(new Integer2(10), new Integer2(2)));
		assertEquals(2, exprT.evalToInt());
		assertEquals(2, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new Minus(new Integer2(4), new Minus(new Integer2(6), new Integer2(3)));
		assertEquals(1, exprT.evalToInt());
		assertEquals(1, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new Plus(new Integer2(5), new Plus(new Integer2(3), new Integer2(3)));
		assertEquals(11, exprT.evalToInt());
		assertEquals(11, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new Times(new Integer2(4), new Times(new Integer2(1), new Integer2(4)));
		assertEquals(16, exprT.evalToInt());
		assertEquals(16, exprT.evalToFloat(), 1.0e-6);
		
		exprT = new UnaryMinus(new Times(new Integer2(2), new Integer2(2)));
		assertEquals(-4, exprT.evalToInt());
		assertEquals(-4, exprT.evalToFloat(), 1.0e-6);
	}
	
	@Test
	public void BasicTestingEvaluateClass() throws ParseError, NotAnInteger { 
		test = "10/5";
		
		evalT = new Evaluate(test, Mode.INTEGER);
		intValT = (IntVal)(evalT.eval());
		assertEquals(2, intValT.val);
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(2, dblValT.val, 1.0e-6);
		
		test = "4-3";
		
		evalT = new Evaluate(test, Mode.INTEGER);
		intValT = (IntVal)(evalT.eval());
		assertEquals(1, intValT.val);
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(1, dblValT.val, 1.0e-6);
		
		test = "5+6";
		
		evalT = new Evaluate(test, Mode.INTEGER);
		intValT = (IntVal)(evalT.eval());
		assertEquals(11, intValT.val);
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(11, dblValT.val, 1.0e-6);
		
		test = "4*4";
		
		evalT = new Evaluate(test, Mode.INTEGER);
		intValT = (IntVal)(evalT.eval());
		assertEquals(16, intValT.val);
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(16, dblValT.val, 1.0e-6);
	}
	
	@Test
	public void MoreTestingEvaluateClass() throws ParseError, NotAnInteger { 
		test = "((2+(6/6)-(3/3))*3)/6";
		
		evalT = new Evaluate(test, Mode.INTEGER);
		intValT = (IntVal)(evalT.eval());
		assertEquals(1, intValT.val);
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(1, dblValT.val, 1.0e-6);
		
		test = "5/2";
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(2.5, dblValT.val, 1.0e-6);

		test = "1+((5+(5/2)+(100/2)-(-16/2))/(-10))";
		
		evalT = new Evaluate(test, Mode.FLOAT);
		dblValT = (DblVal)(evalT.eval());
		assertEquals(-5.55, dblValT.val, 1.0e-6);
	}
	
	@Test(expected = ParseError.class)
	public void BasicParseErrorTest() throws ParseError, NotAnInteger { 
		Parser test = new Parser("5*a");
		test.parse();
	}
	
	@Test(expected = ParseError.class)
	public void LessSimpleParseErrorTest() throws ParseError, NotAnInteger { 
		Parser test = new Parser("2*(0-5)/(4*a)");
		test.parse();
	}
	
	@Test(expected = ParseError.class)
	public void LeastSimpleParseErrorTest() throws ParseError, NotAnInteger { 
		Parser test = new Parser("1+(2/3)/(0*10-5)/(4*(234/(5-(-a))))");
		test.parse();
	}
	
	@Test(expected = NotAnInteger.class)
	public void BasicNotIntErrorTest() throws ParseError, NotAnInteger { 
		Expr test = new Divide(new Integer2(1), new Integer2(2));
		test.evalToInt();
	}
	
	@Test(expected = NotAnInteger.class)
	public void EvalBasicNotIntErrorTest() throws ParseError, NotAnInteger { 
		Evaluate test = new Evaluate("1/2", Mode.INTEGER);
		test.eval();
	}
	
	@Test(expected = ParseError.class)
	public void EvalBasicParseErrorTest() throws ParseError, NotAnInteger { 
		Evaluate test = new Evaluate("5/a", Mode.INTEGER);
		test.eval();
	}
	
	@Test(expected = NotAnInteger.class)
	public void MoreNotIntErrorTest() throws ParseError, NotAnInteger { 
		Expr test = new Divide(new Times(new Plus(new Integer2(2), new Integer2(3)), new Minus(new Integer2(5), new Integer2(6))), new Times(new Integer2(2), new Integer2(2)));
		test.evalToInt();
	}
	
	@Test(expected = NotAnInteger.class)
	public void EvalMoreNotIntErrorTest() throws ParseError, NotAnInteger { 
		Evaluate test = new Evaluate("1+(2/3)/(0*10-5)/(4*(234/(5-(-5))))", Mode.INTEGER);
		test.eval();
	}
	
	@Test(expected = ParseError.class)
	public void EvalMoreParseErrorTest() throws ParseError, NotAnInteger { 
		Evaluate test = new Evaluate("1+(2/3)/(0*10-5)/(4*(234/(5-(-a))))", Mode.INTEGER);
		test.eval();
	}
	
	@Test(expected = ArithmeticException.class)
	public void ArithErrorTest() throws ParseError, NotAnInteger { 
		Expr test = new Divide(new Times(new Integer2(2), new Integer2(2)), new Integer2(0));
		test.evalToInt();
	}
	
	@Test(expected = ArithmeticException.class)
	public void EvalArithErrorTest() throws ParseError, NotAnInteger { 
		Evaluate test = new Evaluate("1+(3+3)/7/0", Mode.INTEGER);
		test.eval();
	}
}
