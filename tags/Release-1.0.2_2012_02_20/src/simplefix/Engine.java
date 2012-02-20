package simplefix;

public interface Engine {

    /**
     * pass initial parameters to Engine
     * 
     * @param initParas
     */
    void initEngine(String... initParas);

    /**
     * start FIX Engine in the same process with Application
     * 
     * @param app
     */
    void startInProcess(Application app);

    /**
     * connect FIX Engine which is different process from Application
     * 
     * @param app
     */
    void connect(Application app);

    /**
     * stop the in process engine or disconnect with out process engine
     */
    void stop();

    /**
     * look up session
     */
    Session lookupSession(String senderCompID, String targetCompID);
}
