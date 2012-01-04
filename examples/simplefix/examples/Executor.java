package simplefix.examples;

import simplefix.Application;
import simplefix.Engine;
import simplefix.EngineFactory;
import simplefix.Message;
import simplefix.MsgType;
import simplefix.Session;

public class Executor {

    private static EngineFactory _engineFact;

    public static void main(final String args[]) throws Exception {
        try {
            String engineName = args[0];
            String initParas = args[1];

            Class<?> classobj = Class.forName(engineName);
            Object engineobj = classobj.newInstance();

            if (engineobj instanceof EngineFactory) {

                _engineFact = (EngineFactory) engineobj;
                Engine engine = _engineFact.getEngine();
                engine.initEngine(initParas);

                Application application = new _Application();

                engine.startInProcess(application);

                System.out.println("press <enter> to quit");
                System.in.read();

                engine.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class _Application implements Application {

        public _Application() {
            // TODO Auto-generated constructor stub
        }

        public void onLogon(final Session sessionId) {
            // TODO Auto-generated method stub

        }

        public void onLogout(final Session sessionId) {
            // TODO Auto-generated method stub

        }

        public void onAppMessage(final Message message, final Session sessionId) {
            if (MsgType.ORDER_SINGLE.equals(message.getMsgType())) {
                Message replyMsg = _engineFact.createMessage(MsgType.EXECUTION_REPORT);

                sessionId.sendAppMessage(replyMsg);

            }

        }

    }

}
