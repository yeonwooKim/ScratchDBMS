/* ScratchDBMSParser.java */
/* Generated By:JavaCC: Do not edit this line. ScratchDBMSParser.java */
public class ScratchDBMSParser implements ScratchDBMSParserConstants {
  public static final int PRINT_SYNTAX_ERROR = 0;
  public static final int PRINT_CREATE_TABLE = 1;
  public static final int PRINT_DROP_TABLE = 2;
  public static final int PRINT_DESC = 3;
  public static final int PRINT_SELECT = 4;
  public static final int PRINT_INSERT = 5;
  public static final int PRINT_DELETE = 6;
  public static final int PRINT_SHOW_TABLES = 7;

  public static void main(String args[]) throws ParseException
  {
    ScratchDBMSParser parser = new ScratchDBMSParser(System.in);
    System.out.print("DB_2014-17184> ");

    while (true)
    {
      try
      {
        parser.command();
      }
      catch (Exception e)
      {
        printMessage(PRINT_SYNTAX_ERROR);
        ScratchDBMSParser.ReInit(System.in);
      }
    }
  }

  public static void printMessage(int q)
  {
    switch(q)
    {
      case PRINT_SYNTAX_ERROR:
        System.out.println("Syntax error");
        break;
      case PRINT_CREATE_TABLE:
        System.out.println("\u005c'CREATE TABLE\u005c' requested");
        break;
      case PRINT_DROP_TABLE:
        System.out.println("\u005c'DROP TABLE\u005c' requested");
        break;
      case PRINT_DESC:
        System.out.println("\u005c'DESC\u005c' requested");
        break;
      case PRINT_SELECT:
        System.out.println("\u005c'SELECT\u005c' requested");
        break;
      case PRINT_INSERT:
        System.out.println("\u005c'INSERT\u005c' requested");
        break;
      case PRINT_DELETE:
        System.out.println("\u005c'DELETE\u005c' requested");
        break;
      case PRINT_SHOW_TABLES:
        System.out.println("\u005c'SHOW TABLES\u005c' requested");
        break;
      default:
        System.out.println("Syntax error");
        break;
    }
    System.out.print("DB_2014-17184> ");
  }

  static final public void command() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CREATE_TABLE:
    case DROP_TABLE:
    case DESC:
    case SHOW_TABLES:
    case SELECT:
    case INSERT_INTO:
    case DELETE_FROM:{
      queryList();
      break;
      }
    case EXIT:{
      jj_consume_token(EXIT);
      jj_consume_token(SEMICOLON);
System.exit(0);
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void queryList() throws ParseException {int q;
    label_1:
    while (true) {
      q = query();
      jj_consume_token(SEMICOLON);
printMessage(q);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CREATE_TABLE:
      case DROP_TABLE:
      case DESC:
      case SHOW_TABLES:
      case SELECT:
      case INSERT_INTO:
      case DELETE_FROM:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
  }

  static final public int query() throws ParseException {int q;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CREATE_TABLE:{
      createTableQuery();
q = PRINT_CREATE_TABLE;
      break;
      }
    case DROP_TABLE:{
      dropTableQuery();
q = PRINT_DROP_TABLE;
      break;
      }
    case DESC:{
      descQuery();
q = PRINT_DESC;
      break;
      }
    case SELECT:{
      selectQuery();
q = PRINT_SELECT;
      break;
      }
    case INSERT_INTO:{
      insertQuery();
q = PRINT_INSERT;
      break;
      }
    case DELETE_FROM:{
      deleteQuery();
q = PRINT_DELETE;
      break;
      }
    case SHOW_TABLES:{
      showTablesQuery();
q = PRINT_SHOW_TABLES;
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return q;}
    throw new Error("Missing return statement in function");
  }

  static final public void createTableQuery() throws ParseException {
    jj_consume_token(CREATE_TABLE);
    legalIdentifier();
    tableElementList();
  }

  static final public void tableElementList() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    tableElement();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      tableElement();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  static final public void tableElement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ALPHABET:{
      columnDefinition();
      break;
      }
    case PRIMARY_KEY:
    case FOREIGN_KEY:{
      tableConstraintDefinition();
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void columnDefinition() throws ParseException {
    legalIdentifier();
    dataType();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT_NULL:{
      jj_consume_token(NOT_NULL);
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      ;
    }
  }

  static final public void tableConstraintDefinition() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PRIMARY_KEY:{
      primaryKeyConstraint();
      break;
      }
    case FOREIGN_KEY:{
      referentialConstraint();
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void primaryKeyConstraint() throws ParseException {
    jj_consume_token(PRIMARY_KEY);
    columnNameList();
  }

  static final public void referentialConstraint() throws ParseException {
    jj_consume_token(FOREIGN_KEY);
    columnNameList();
    jj_consume_token(REFERENCES);
    legalIdentifier();
    columnNameList();
  }

  static final public void columnNameList() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    legalIdentifier();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      legalIdentifier();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  static final public void dataType() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
      break;
      }
    case CHAR:{
      jj_consume_token(CHAR);
      jj_consume_token(LEFT_PAREN);
      intValue();
      jj_consume_token(RIGHT_PAREN);
      break;
      }
    case DATE:{
      jj_consume_token(DATE);
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void intValue() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SIGN:{
      jj_consume_token(SIGN);
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      ;
    }
    label_4:
    while (true) {
      jj_consume_token(DIGIT);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DIGIT:{
        ;
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        break label_4;
      }
    }
  }

  static final public void legalIdentifier() throws ParseException {
    jj_consume_token(ALPHABET);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case UNDERSCORE:
      case ALPHABET:{
        ;
        break;
        }
      default:
        jj_la1[11] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ALPHABET:{
        jj_consume_token(ALPHABET);
        break;
        }
      case UNDERSCORE:{
        jj_consume_token(UNDERSCORE);
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void dropTableQuery() throws ParseException {
    jj_consume_token(DROP_TABLE);
    legalIdentifier();
  }

  static final public void descQuery() throws ParseException {
    jj_consume_token(DESC);
    legalIdentifier();
  }

  static final public void showTablesQuery() throws ParseException {
    jj_consume_token(SHOW_TABLES);
  }

  static final public void selectQuery() throws ParseException {
    jj_consume_token(SELECT);
    selectList();
    tableExpression();
  }

  static final public void selectList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASTERISK:{
      jj_consume_token(ASTERISK);
      break;
      }
    case ALPHABET:{
      selectedColumn();
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[13] = jj_gen;
          break label_6;
        }
        jj_consume_token(COMMA);
        selectedColumn();
      }
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void selectedColumn() throws ParseException {
    tableColumn();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AS:{
      jj_consume_token(AS);
      legalIdentifier();
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      ;
    }
  }

  static final public void tableExpression() throws ParseException {
    fromClause();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WHERE:{
      whereClause();
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      ;
    }
  }

  static final public void fromClause() throws ParseException {
    jj_consume_token(FROM);
    tableReferenceList();
  }

  static final public void tableReferenceList() throws ParseException {
    referredTable();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[17] = jj_gen;
        break label_7;
      }
      jj_consume_token(COMMA);
      referredTable();
    }
  }

  static final public void referredTable() throws ParseException {
    legalIdentifier();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AS:{
      jj_consume_token(AS);
      legalIdentifier();
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      ;
    }
  }

  static final public void whereClause() throws ParseException {
    jj_consume_token(WHERE);
    booleanValueExpression();
  }

  static final public void booleanValueExpression() throws ParseException {
    booleanTerm();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        break label_8;
      }
      jj_consume_token(OR);
      booleanTerm();
    }
  }

  static final public void booleanTerm() throws ParseException {
    booleanFactor();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        break label_9;
      }
      jj_consume_token(AND);
      booleanFactor();
    }
  }

  static final public void booleanFactor() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      ;
    }
    booleanTest();
  }

  static final public void booleanTest() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ALPHABET:
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:{
      predicate();
      break;
      }
    case LEFT_PAREN:{
      parenthesizedBooleanExpression();
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void parenthesizedBooleanExpression() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    booleanValueExpression();
    jj_consume_token(RIGHT_PAREN);
  }

  static final public void predicate() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ALPHABET:{
      identifierPredicate();
      break;
      }
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:{
      constantPredicate();
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void identifierPredicate() throws ParseException {
    tableColumn();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMP_OP:{
      jj_consume_token(COMP_OP);
      compOperand();
      break;
      }
    case IS_NULL:
    case IS_NOT_NULL:{
      nullOperation();
      break;
      }
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void constantPredicate() throws ParseException {
    comparableValue();
    jj_consume_token(COMP_OP);
    compOperand();
  }

/*
void comparisonPredicate() :
 {}
 {
     compOperand()
     < COMP_OP >
     compOperand()
 }
*/
  static final public 
void compOperand() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:{
      comparableValue();
      break;
      }
    case ALPHABET:{
      tableColumn();
      break;
      }
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void comparableValue() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT_VALUE:{
      jj_consume_token(INT_VALUE);
      break;
      }
    case CHAR_STRING:{
      jj_consume_token(CHAR_STRING);
      break;
      }
    case DATE_VALUE:{
      jj_consume_token(DATE_VALUE);
      break;
      }
    default:
      jj_la1[26] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

/*
void nullPredicate() :
{}
{
		tableColumn()
    nullOperation()
}
*/
  static final public 
void nullOperation() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IS_NULL:{
      jj_consume_token(IS_NULL);
      break;
      }
    case IS_NOT_NULL:{
      jj_consume_token(IS_NOT_NULL);
      break;
      }
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void insertQuery() throws ParseException {
    jj_consume_token(INSERT_INTO);
    legalIdentifier();
    insertColumnsAndSource();
  }

  static final public void insertColumnsAndSource() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LEFT_PAREN:{
      columnNameList();
      break;
      }
    default:
      jj_la1[28] = jj_gen;
      ;
    }
    valueList();
  }

  static final public void valueList() throws ParseException {
    jj_consume_token(VALUES);
    jj_consume_token(LEFT_PAREN);
    value();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[29] = jj_gen;
        break label_10;
      }
      jj_consume_token(COMMA);
      value();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  static final public void value() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NULL:{
      jj_consume_token(NULL);
      break;
      }
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:{
      comparableValue();
      break;
      }
    default:
      jj_la1[30] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void deleteQuery() throws ParseException {
    jj_consume_token(DELETE_FROM);
    legalIdentifier();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WHERE:{
      whereClause();
      break;
      }
    default:
      jj_la1[31] = jj_gen;
      ;
    }
  }

  static final public void tableColumn() throws ParseException {
    legalIdentifier();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PERIOD:{
      jj_consume_token(PERIOD);
      legalIdentifier();
      break;
      }
    default:
      jj_la1[32] = jj_gen;
      ;
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ScratchDBMSParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[33];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x28078220,0x28078200,0x28078200,0x0,0x3000,0x400,0x3000,0x0,0x1c0,0x0,0x0,0x0,0x0,0x0,0x0,0x100000,0x200000,0x0,0x100000,0x400000,0x800000,0x1000000,0x80000000,0x0,0x6000000,0x0,0x0,0x6000000,0x80000000,0x0,0x800,0x200000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x2,0x20,0x0,0x0,0x2,0x0,0x8,0x10,0x24,0x24,0x2,0x60,0x0,0x0,0x2,0x0,0x0,0x0,0x0,0x720,0x720,0x4000,0x720,0x700,0x0,0x0,0x2,0x700,0x0,0x80,};
   }

  /** Constructor with InputStream. */
  public ScratchDBMSParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ScratchDBMSParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ScratchDBMSParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 33; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 33; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ScratchDBMSParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ScratchDBMSParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 33; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 33; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ScratchDBMSParser(ScratchDBMSParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 33; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ScratchDBMSParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 33; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[47];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 33; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 47; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
