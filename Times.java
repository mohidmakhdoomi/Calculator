package cs2s03;

class Times extends BinaryOp {
	Times(Expr x, Expr y) { left = x; right = y; }
	public String toString() { 
		// we are the context
		return super.toString(this, " * "); }
	public boolean isSame(Expr e) { return e instanceof Times; }
	@Override
	public int evalToInt() throws NotAnInteger {
		int resInt = left.evalToInt() * right.evalToInt();
		double resDbl = left.evalToFloat() * right.evalToFloat();
		
		int tempInt = (int)resInt;
		double tempFloat = resDbl;
		
		if (tempInt != tempFloat)
			throw new NotAnInteger("Not an Integer: " + left.toString() + " * " + right.toString());
		
		return resInt;
	}
	@Override
	public double evalToFloat() {
		return left.evalToFloat() * right.evalToFloat();
	}
}
