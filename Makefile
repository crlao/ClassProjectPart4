JAVAC=javac
JAVA=java
CLASSPATH=.:lib/*
OUTDIR=out

SOURCEDIR=src

sources = $(wildcard $(SOURCEDIR)/**/StatusCode.java $(SOURCEDIR)/**/DBConf.java $(SOURCEDIR)/**/models/*Type.java $(SOURCEDIR)/**/models/Record.java $(SOURCEDIR)/**/models/*Operator.java $(SOURCEDIR)/**/utils/AlgebraicUtils.java $(SOURCEDIR)/**/utils/ComparisonUtils.java $(SOURCEDIR)/**/models/*.java $(SOURCEDIR)/**/fdb/FDBKVPair.java $(SOURCEDIR)/**/fdb/FDBHelper.java $(SOURCEDIR)/**/utils/*.java $(SOURCEDIR)/**/TableManager.java $(SOURCEDIR)/**/TableMetadataTransformer.java $(SOURCEDIR)/**/TableManagerImpl.java $(SOURCEDIR)/**/RecordsTransformer.java $(SOURCEDIR)/**/Indexes.java $(SOURCEDIR)/**/IndexTransformer.java $(SOURCEDIR)/**/Cursor.java $(SOURCEDIR)/**/Records.java $(SOURCEDIR)/**/RecordsImpl.java $(SOURCEDIR)/**/IndexesImpl.java $(SOURCEDIR)/**/*Iterator.java $(SOURCEDIR)/**/RelationalAlgebraOperators*.java $(SOURCEDIR)/**/test/*.java)
classes = $(sources:.java=.class)

preparation: clean
	mkdir -p ${OUTDIR}

clean:
	rm -rf ${OUTDIR}

%.class: %.java
	$(JAVAC) -d "$(OUTDIR)" -cp "$(OUTDIR):$(CLASSPATH)" $<

part1Test: preparation $(classes)
	mkdir -p $(OUTDIR)
	$(JAVA) -cp "$(OUTDIR):$(CLASSPATH)" org.junit.runner.JUnitCore CSCI485ClassProject.test.Part1Test

part2Test: preparation $(classes)
	mkdir -p $(OUTDIR)
	$(JAVA) -cp "$(OUTDIR):$(CLASSPATH)" org.junit.runner.JUnitCore CSCI485ClassProject.test.Part2Test

part3Test: preparation $(classes)
	mkdir -p $(OUTDIR)
	$(JAVA) -cp "$(OUTDIR):$(CLASSPATH)" org.junit.runner.JUnitCore CSCI485ClassProject.test.Part3Test

part4Test: preparation $(classes)
	mkdir -p $(OUTDIR)
	$(JAVA) -cp "$(OUTDIR):$(CLASSPATH)" org.junit.runner.JUnitCore CSCI485ClassProject.test.Part4Test

.PHONY: part1Test part2Test part3Test part4Test clean preparation