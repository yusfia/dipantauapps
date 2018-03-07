package id.towercontroller.org.towercontroller.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import id.towercontroller.org.towercontroller.LoginActivity;
import id.towercontroller.org.towercontroller.model.User;

/**
 * Created by Hafid on 11/28/2017.
 */

public class TowerControllerSessionManager extends SessionManager{

    public static String KEY_PREFERENCES_NAME = "homecare_pref_name";
    public static String KEY_USER_INFOS_JSON = "user_json_infos";
    public static String KEY_IS_LOGIN = "isLogin";

    public TowerControllerSessionManager(Activity activity, Context context){
        super(activity, context, KEY_PREFERENCES_NAME);
    }

    public void createLoginSession(User user){
        super.createLoginSession(user, KEY_USER_INFOS_JSON, KEY_IS_LOGIN);
    }

    public User getUserDetail(){
        return super.getUserDetail(KEY_USER_INFOS_JSON);
    }

    public boolean isLogin(){
        return super.isLogin(KEY_IS_LOGIN);
    }

    public void logout(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
        super.logout(KEY_IS_LOGIN);
    }
}
