package cs2s03;

//@SuppressWarnings("serial")
class ParseError extends Exception {
  ParseError(String message) {  
    super(message); }
}

class Parser {
  private int pos = 0;
  private int len = 0;
  private String s;

  Parser(String inp) { 
    this.s = inp; this.pos = 0; this.len = inp.length(); }

  public Expr parse() throws ParseError {
    boolean neg = false;
    char n = s.charAt(pos);
    if (n == '-') { neg = true; pos++; }
    Expr e = term(); // this will change pos
    if (neg) { e = new UnaryMinus(e); }
    if (pos == len) return e;
    n = s.charAt(pos);
    while ( isOper(n) ) {
      pos++;
      Expr next = term();
      switch (n) { // does not implement operator precedence!
        case '+': e = new Plus(e, next); break;
        case '*': e = new Times(e, next); break;
        case '-': e = new Minus(e, next); break;
        case '/': e = new Divide(e, next); break;
        default: throw new ParseError("impossible"); // impossible!
      }
      if (pos == len) return e;
      n = s.charAt(pos);
    }
    return e;
  }
  
  private Expr term() throws ParseError {
    char ch = s.charAt(pos);
    if (Character.isDigit(ch)) {
      int i = integer(ch);
      return new Integer2(i);
    } else if (ch == '(') {
      pos++;
      Expr e = parse();
      char peek = s.charAt(pos);
      if (peek != ')') {
          throw new ParseError("expected ) got " + Character.toString(peek)); }
      pos++;
      return e;
    } else {
      throw new ParseError("catch all error");
    }
  }

  private int integer(char c) {
    int acc = 0;
    char peek = c;
    do { 
      acc = (acc * 10) + Character.getNumericValue(peek);
      pos++;
      if (pos < len) {
        peek = s.charAt(pos);
      } else {
        peek = '\n';
      }
    } while (Character.isDigit(peek)) ;
    return acc;
  }
  private boolean isOper(char c) {
    return (c == '+' || c == '*' || c == '-' || c == '/'); }
}
