package validation;

import data.SessionVector;

public class Validator {

    private static SessionVector stcm = SessionVector.getInstance();

    public static boolean isValidSession(String sessionID){
        if(stcm.isLoggedIn(sessionID)){
                return true;
        }
        return false;
    }
}
