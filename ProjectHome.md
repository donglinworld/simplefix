A simple Java FIX (Financial Information eXchange http://www.fixtradingcommunity.org/) Engine based on QuickFIX/J v1.5.2 (http://www.quickfixj.org/)

## Main improvements: ##

1). **Providing one simple abstract FIX layer (interfaces)**<br>
Application java source code does not need to directly depend on QuickFIX/J FIX engine classes.<br>
Application based on simplefix interfaces is very easy to shift to other FIX engine in future.<br>

2). <b>Integrated implementation of Acceptor and Initiator.</b><br>
Application java source code does not need to be aware of Acceptor or Initiator, configuration in init file only is enough.<br>
<br>
<h2>Getting started:</h2>
1). Download simplefix-example-1.X.X-sources.jar which includes  simplfix example source code and simplefix needed binary jar files.<br>
2). Create a directory named "SimpleFIX-Example", and decompress above file into this directory.<br>
3). Import the project into Eclipse and try to run simplefix.examples.executor.Executor.java.<br>
4). Try to understand how simpler it is than the original Executor.java in QuickFIX/J.<br>
5). Develop your own FIX Application.<br>
6). If needed, download simple-quickfix-1.X.X-sources.jar or check out source files by SVN for further understanding.<br>
7). simplefix has been updated after 1.0.3, but it could not be put into downloads, please check out the latest version by SVN.<br>
<br>
<h2>Notice:</h2>
simplefix does not include the mass of classes for every message type in every FIX version which are created by QuickFIX/J automatically.<br>
This makes the FIX engine is much lighter and simpler.<br>
And this also makes the interface is more closed with other commercial FIX engine, such as NYFIX APPIA, ULLINK ULFIX.<br>
If you think QuickFIX/J's approach is more convenient for your Application, please use QuickFIX/J directly.