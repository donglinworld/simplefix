package simplefix.examples;

import simplefix.Application;
import simplefix.Engine;
import simplefix.Message;
import simplefix.Session;

public class Executor {

    public static void main(final String args[]) throws Exception {
        try {
            String engineName = args[0];
            String initParas = args[1];

            Class<?> classobj = Class.forName(engineName);
            Object engineobj = classobj.newInstance();

            if (engineobj instanceof Engine) {

                Engine engine = (Engine) engineobj;
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
            // TODO Auto-generated method stub

        }

    }

}
