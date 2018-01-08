package cs2s03;

abstract class Value {
	Mode m;
	abstract public String toString();
}

class IntVal extends Value{
	int val;
	IntVal (int x){
		m = Mode.INTEGER;
		this.val = x;
	}
	@Override
	public String toString() {
		return Integer.toString(this.val);
	}
}

class DblVal extends Value{
	double val;
	DblVal (double x){
		m = Mode.FLOAT;
		this.val = x;
	}
	@Override
	public String toString() {
		return String.format("%.6f", (this.val));
	}
}