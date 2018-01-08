package cs2s03;

//@SuppressWarnings("serial")
class NotAnInteger extends Exception {
	NotAnInteger(String message) {
		super(message); }
}

abstract class Expr {
	abstract public String toString();
	abstract public boolean isGround();
	abstract public int evalToInt() throws NotAnInteger;
	abstract public double evalToFloat();

	static void print(Expr e) { System.out.println(e.toString()); }

	static String betweenParens(final Expr e) {
		return (e.isGround()) ? e.toString() 
				: "( " + e.toString() + " )"; }

	/*
	public static void main(String[] args) {
		String s1 = "2+(3*7)*(5/3)-2";
		Parser p = new Parser(s1);
		try {
			Expr e5 = p.parse();
			print(e5);
		} catch (ParseError e) {
			System.out.println(e);
		}
	}
	*/
}
