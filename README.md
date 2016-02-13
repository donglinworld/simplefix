# simplefix
Simple Java FIX Engine

A simple Java FIX (Financial Information eXchange http://www.fixtradingcommunity.org/) Engine based on QuickFIX/J v1.5.2 (http://www.quickfixj.org/)

Main improvements:

1). Providing one simple abstract FIX layer (interfaces)
Application java source code does not need to directly depend on QuickFIX/J FIX engine classes.
Application based on simplefix interfaces is very easy to shift to other FIX engine in future.

2). Integrated implementation of Acceptor and Initiator.
Application java source code does not need to be aware of Acceptor or Initiator, configuration in init file only is enough.


Getting started:

1). Download simplefix-example-1.X.X-sources.jar which includes simplfix example source code and simplefix needed binary jar files.

2). Create a directory named "SimpleFIX-Example", and decompress above file into this directory.

3). Import the project into Eclipse and try to run simplefix.examples.executor.Executor.java.

4). Try to understand how simpler it is than the original Executor.java in QuickFIX/J.

5). Develop your own FIX Application.

6). If needed, download simple-quickfix-1.X.X-sources.jar or check out source files by SVN for further understanding.

7). simplefix has been updated after 1.0.3, but it could not be put into downloads, please check out the latest version by SVN.


Notice:

simplefix does not include the mass of classes for every message type in every FIX version which are created by QuickFIX/J automatically.
This makes the FIX engine is much lighter and simpler.
And this also makes the interface is more closed with other commercial FIX engine, such as NYFIX APPIA, ULLINK ULFIX.
If you think QuickFIX/J's approach is more convenient for your Application, please use QuickFIX/J directly.
