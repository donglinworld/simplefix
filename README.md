# SimpleFIX

--This is quite stable but based on QuickFIX/J v1.5.2 and no more update.

--To enjoy the simpleness of simplefix and new features of QuickFIX/J, please refer https://github.com/donglinworld/simplequickfix

A simple Java FIX (Financial Information eXchange https://www.fixtrading.org/) Engine based on QuickFIX/J v1.5.2 (http://www.quickfixj.org/)

## Main improvements:

1). Providing one simple abstract FIX layer (interfaces)
Application java source code does not need to directly depend on QuickFIX/J FIX engine classes.
Application based on simplefix interfaces is very easy to shift to other FIX engine in future.

2). Integrated implementation of Acceptor and Initiator.
Application java source code does not need to be aware of Acceptor or Initiator, configuration in init file only is enough.


## Getting started:

1). Download simplefix-master.zip from this page.

2). Decompress above file into this directory.

3). Import the project from above directory into Eclipse and try to run simplefix.examples.executor.Executor.java.

4). Try to understand how simpler it is than the original Executor.java in QuickFIX/J.

5). Develop your own FIX Application.


## Recompile simplefix lib from Source:

mvn clean; mvn package


## Notice:

simplefix does not include the mass of classes for every message type in every FIX version which are created by QuickFIX/J automatically.
This makes the FIX engine is much lighter and simpler.
And this also makes the interface is more closed with other commercial FIX engine, such as ULLINK APPIA, ULLINK ULFIX.
If you think QuickFIX/J's approach is more convenient for your Application, please use QuickFIX/J directly.
