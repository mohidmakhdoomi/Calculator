package cs2s03;

abstract class BinaryOp extends Expr {
	Expr left;
	Expr right;
  /* this is different: it asks for a context, and an op */
	public String toString(BinaryOp b, String op) { 
		String l = (b.isSame(left)) ? left.toString() : betweenParens(left);
		String r = (b.isSame(right)) ? right.toString() : betweenParens(right);
		return l + op + r; }
	public boolean isGround() { return false; }
	abstract public boolean isSame(Expr e);
}
