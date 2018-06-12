package data;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionToCSRFMap {

    public SessionToCSRFMap() {
        sessionToCSRFMap = new HashMap<String, String>();
        csrfToSessionMap = new HashMap<String, String>();
    }

    private Map<String,String> sessionToCSRFMap,csrfToSessionMap;
    private static SessionToCSRFMap stm = null;

    public String getCSRFToken(String sessionID){
        return sessionToCSRFMap.get(sessionID);
    }

    /*public String getSessionID(String csrfToken){
        return csrfToSessionMap.get(csrfToken);
    }
     Unused so commented it out
     */

    public void addSession(String sessionID, String csrfToken){

        sessionToCSRFMap.put(sessionID, csrfToken);
        csrfToSessionMap.put(csrfToken,sessionID);
    }

    public static SessionToCSRFMap getInstance(){
        if(stm==null)
            stm = new SessionToCSRFMap();
        return stm;
    }

    public boolean isLoggedIn(String session){
        boolean b =false;
        Iterator it = sessionToCSRFMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getKey().equals(session))
                b = true;
        }
        return b;
    }
    public boolean destroySession(String session){
        try{
            String temp = getCSRFToken(session);
            sessionToCSRFMap.remove(session);
            csrfToSessionMap.remove(temp);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}