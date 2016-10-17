all: jj
	javac ScratchDBMSParser.java

jj: src/ScratchDBMSGrammar.jj
	java -classpath javacc-6.0/bin/lib/javacc.jar javacc src/ScratchDBMSGrammar.jj

clean:
	rm *.class Scratch*.java Simple*.java ParseException.java Token.java TokenMgrError.java
