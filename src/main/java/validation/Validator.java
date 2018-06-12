package validation;

import data.SessionToCSRFMap;

public class Validator {

    private static SessionToCSRFMap stcm = SessionToCSRFMap.getInstance();

    public static boolean isValidSession(String sessionID, String csrfToken){
        if(stcm.isLoggedIn(sessionID)){
            if(stcm.getCSRFToken(sessionID).equals(csrfToken)){
                return true;
            }
        }
        return false;
    }
}
