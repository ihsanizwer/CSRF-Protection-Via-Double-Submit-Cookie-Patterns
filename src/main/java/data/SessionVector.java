package data;

import java.util.Vector;

public class SessionVector {
    public SessionVector() {
        vector = new Vector<String>();
    }
    private Vector<String> vector;
    private static SessionVector sv;

    public static SessionVector getInstance(){
        if(sv==null)
            sv = new SessionVector();
        return sv;
    }

    public boolean isLoggedIn(String sessionID){
        if(sv!=null) {
            for (String str : vector) {
                if (str.equals(sessionID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setSession(String session){
        vector.add(session);
    }

    public boolean destroySession(String session){
        if(sv!=null){
            if(isLoggedIn(session)){
                vector.remove(session);
            }
        }
        return false;
    }
}
