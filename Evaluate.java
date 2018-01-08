package cs2s03;

class Evaluate {
	private Expr e;
	private Mode m;
	
	Evaluate(String s, Mode m) throws ParseError {
		this.m = m;
		Parser res = new Parser(s);
		this.e = res.parse();
	}
	
	public Value eval() throws NotAnInteger{
		if (this.m == Mode.INTEGER)
			return new IntVal(e.evalToInt());
		return new DblVal(e.evalToFloat());	
	}
}
