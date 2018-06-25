package com.ys.administrator.ysinteriortest.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.ys.administrator.ysinteriortest.Constant;
import com.ys.administrator.ysinteriortest.R;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by amos on 2016/5/23.
 */
public class LoginUtil {

    public static final String LOG_TAG = LoginUtil.class.getSimpleName();

    /**
     * 检测登录信息是否正确
     *
     * @param loginUserName 用户名的edittext
     * @param loginPassword 密码的edittext
     */
    public static boolean checkLoginParams(Activity activity, EditText loginUserName, EditText loginPassword) {
        String loginUserNameStr = getInputInfo(loginUserName);
        String loginPasswordStr = getInputInfo(loginPassword);
        if (!isInputEmpty(loginUserNameStr)) {//检测用户名不能为空
            if (!isInputEmpty(loginPasswordStr)) {//检测密码不能为空
                if (isPasswordLength(loginPassword)) {//检测密码的长度
                    return true;
                } else
                    MyToast.showToast(activity, R.string.login_password_length);
            } else
                MyToast.showToast(activity, R.string.login_password_cannot_empty);
        } else
            MyToast.showToast(activity, R.string.login_username_cannot_empty);
        return false;
    }

//    public static boolean isMatchingAccount(String userName, String password) {
//        HashMap<String, String> userListMap = UserManage.getInstence().getUserList();
//        Set<Map.Entry<String, String>> entrySet = userListMap.entrySet();
//        for (Map.Entry<String, String> entry : entrySet) {
//            LogXmc.i(LOG_TAG, "userName = " + entry.getKey() + "........" + "passWord = " + entry.getValue());
//            System.out.println(entry.getKey() + ".........." + entry.getValue());
//            if (entry.getKey().equals(userName) && entry.getValue().equals(password)) {
//                return true;
//            }
//        }
//        return false;
//    }

    private static boolean isPasswordLength(EditText loginPassword) {
        String password = getInputInfo(loginPassword);
        if (password.length() < 6 || password.length() > 20) {
            return false;
        }
        return true;
    }

    /**
     * 检查输入的信息是否为空
     *
     * @param str 输入的信息
     */
    public static boolean isInputEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 获取用户输入的信息
     */
    public static String getInputInfo(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 进行加密
     *
     * @param account  用户名
     * @param password 密码
     */
    public static String encode(String account, String password) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", account);
        map.put("password", password);
        String str = new GsonBuilder().create().toJson(map);
        byte[] raw = Constant.SEED.getBytes();
        byte[] data;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(raw, "AES"), new IvParameterSpec(raw));
            data = cipher.doFinal(str.getBytes());
            String enc_data = Base64.encodeToString(data, Base64.DEFAULT);
            return enc_data;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return "";
    }

}
