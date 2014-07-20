package simplefix;

/**
 * This is the primary Simple FIX interface for processing session messages.
 */
public interface Application {

    /**
     * This callback notifies you when a valid logon has been established with a
     * counter party. This is called when a connection has been established and
     * the FIX logon process has completed with both parties exchanging valid
     * logon messages.
     * 
     * @param session
     *            FIX session
     */
    void onLogon(Session session);

    /**
     * This callback notifies you when an FIX session is no longer online. This
     * could happen during a normal logout exchange or because of a forced
     * termination or a loss of network connection.
     * 
     * @param session
     *            FIX session
     */
    void onLogout(Session session);

    /**
     * This callback receives messages for the application. This is one of the
     * core entry points for your FIX application. Every application level
     * request will come through here. If, for example, your application is a
     * sell-side OMS, this is where you will get your new order requests. If you
     * were a buy side, you would get your execution reports here. *
     * 
     * @param message
     *            FIX message
     * @param session
     *            FIX session
     */
    void onAppMessage(Message message, Session session);

    void toAppMessage(Message message, Session session);
}
