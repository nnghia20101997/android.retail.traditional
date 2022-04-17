package vn.techres.saler.android.mvp.model.entity;

import com.core.app.base.BaseApplication;
import com.core.app.utils.ArmsUtils;
import com.core.app.utils.PrefUtils;

import vn.techres.saler.android.app.AppEnum;

public class CurrentUser {
    private static User mUserInfo;
    public static final String TOKEN_NO_LOGIN = "TOKEN_NO_LOGIN";

    public static User getUserInfo() {
        try {
            mUserInfo = ArmsUtils.obtainAppComponentFromContext(BaseApplication.getInstance().getBaseContext()).gson().fromJson(PrefUtils.getInstance().getString(AppEnum.CACHE_USER_INFO.name()), User.class);
        } catch (Exception e) {
            e.getStackTrace();
            return User.getUserDefault();
        }
        if (mUserInfo == null) mUserInfo = User.getUserDefault();
        return mUserInfo;
    }

    public static boolean isLogin() {
        return getUserInfo().getId() != 0;
    }

    public static void saveUserInfo(User userInfo) {
        try {
            if (userInfo == null) {
                userInfo = new User();
            }
            PrefUtils.getInstance().putString(AppEnum.CACHE_USER_INFO.toString(), ArmsUtils.obtainAppComponentFromContext(BaseApplication.getInstance().getBaseContext()).gson().toJson(userInfo));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUserInfo(User userInfo) {
        mUserInfo = userInfo;
    }

    public static String getToken() {
        if (isLogin()) {
            return getUserInfo().getToken_type() + " " + getUserInfo().getAccess_token() + "";
        } else {
            if (!PrefUtils.getInstance().getString(TOKEN_NO_LOGIN).isEmpty()) {
                return "Basic " + PrefUtils.getInstance().getString(TOKEN_NO_LOGIN);
            }
        }
        return "";
    }
}