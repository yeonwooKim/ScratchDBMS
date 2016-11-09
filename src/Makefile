jj: ScratchDBMSGrammar.jj
	java -classpath ../javacc-6.0/bin/lib/javacc.jar javacc ScratchDBMSGrammar.jj

compile: jj
	javac -cp ".:../lib/je-7.0.6.jar" ScratchDBMSParser.java

run: compile
	java -cp ".:../lib/je-7.0.6.jar" ScratchDBMSParser

clean:
	rm *.class Scratch*.java Simple*.java ParseException.java Token.java TokenMgrError.java
