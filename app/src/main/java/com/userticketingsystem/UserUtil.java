package com.userticketingsystem;

/**
 * Created by rohanarora on 22/12/16.
 */

public class UserUtil {


    public static boolean isUserLoggedIn() {
        return  getId() != -1 ;
    }

    public static void saveUser(UserResponse user) {
        if (user != null) {

            if (user.getEmail() != null && user.getEmail().length() != 0) {
                SharedPreferencesUtil.storeStringValue(SharedPreferencesUtil.USER_EMAIL, user.getEmail());
            }


            if (user.getName() != null && user.getName().length() != 0) {
                SharedPreferencesUtil.storeStringValue(SharedPreferencesUtil.USER_NAME, user.getName());
            }

            if (user.getId() >= 0) {
                SharedPreferencesUtil.storeStringValue(SharedPreferencesUtil.USER_SERVER_ID, String.valueOf(user.getId()));
            }

            if (user.getId() >= 0) {
                SharedPreferencesUtil.storeIntValue(SharedPreferencesUtil.USER_SERVER_ID, user.getId());
            }

            if(user.getProfilePic() != null && user.getProfilePic().length() != 0){
                SharedPreferencesUtil.storeStringValue(SharedPreferencesUtil.USER_PROFILE_PIC_URL, user.getProfilePic());
            }

        }
    }

    public static String getProfilePicURL(){
        return SharedPreferencesUtil.retrieveStringValue(SharedPreferencesUtil.USER_PROFILE_PIC_URL, "");
    }


    public static String getName() {
        return SharedPreferencesUtil.retrieveStringValue(SharedPreferencesUtil.USER_NAME, "");
    }

    public static String getEmail(){
        return SharedPreferencesUtil.retrieveStringValue(SharedPreferencesUtil.USER_EMAIL,"");
    }


    public static int getId() {
        return SharedPreferencesUtil.retrieveIntValue(SharedPreferencesUtil.USER_SERVER_ID, -1);
    }



    public static void logout(){
        SharedPreferencesUtil.removeKey(SharedPreferencesUtil.USER_EMAIL);
        SharedPreferencesUtil.removeKey(SharedPreferencesUtil.USER_NAME);
        SharedPreferencesUtil.removeKey(SharedPreferencesUtil.USER_SERVER_ID);
        SharedPreferencesUtil.removeKey(SharedPreferencesUtil.USER_PROFILE_PIC_URL);
    }

}
