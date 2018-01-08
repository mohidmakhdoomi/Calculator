package cs2s03;

class Divide extends Expr { // NOT BinaryOp!
	Expr left;
	Expr right;
	Divide(Expr x, Expr y) { left = x; right = y; }
	public String toString() { 
		return betweenParens(left) + " / " + betweenParens(right); }
	public boolean isGround() { return false; }
	@Override
	public int evalToInt() throws NotAnInteger {
		int resInt = left.evalToInt() / right.evalToInt();
		double resDbl = left.evalToFloat() / right.evalToFloat();
		
		int tempInt = (int)resInt;
		double tempFloat = resDbl;
		
		if (tempInt != tempFloat)
			throw new NotAnInteger("Not an Integer: " + left.toString() + " / " + right.toString());
		
		return resInt;
	}
	@Override
	public double evalToFloat() {
		return left.evalToFloat() / right.evalToFloat();
	}
}
