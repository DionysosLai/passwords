package passwords.shiqian.com.passwords;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import passwords.shiqian.com.passwords.data.LoginData;

public class LoginActivity extends AppCompatActivity {
    private Activity mActivity;

    private LinearLayout loginLayout;
    private LinearLayout unLoginLayout;

    // 登陆界面
    private EditText loginEdit;
    private TextView loginText;
    private Button loginButton;
    // 确认界面
    private EditText unLoginEdit1;
    private EditText unLoginEdit2;
    private TextView unLoginText;
    private Button unLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initRes();
        if(isFirstLogin()){
            unLoginLayout.setVisibility(View.VISIBLE);
            unLoginText.setVisibility(View.INVISIBLE);
            loginLayout.setVisibility(View.INVISIBLE);
        }else{
            unLoginLayout.setVisibility(View.INVISIBLE);
            loginLayout.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.INVISIBLE);
        }

        final LoginData defaultUsr = getDefautlUsr();
        unLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwords1 = unLoginEdit1.getText().toString();
                String passwords2 = unLoginEdit2.getText().toString();
                Log.v("login","passwords1:=" + passwords1);
                Log.v("login","passwords2:=" + passwords2);
                if(passwords1.equals(passwords2)) {
                    unLoginText.setVisibility(View.INVISIBLE);
                    if(defaultUsr != null) {
                        defaultUsr.setUsrPasswords(passwords1);
                        defaultUsr.save();
                        Log.v("login","passwords2:=" + defaultUsr.getUsrPasswords());
                    }
                }else {
                    unLoginText.setVisibility(View.VISIBLE);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(defaultUsr != null) {
                    String passwords = loginEdit.getText().toString();
                    String defaultUsrPasswords = defaultUsr.getUsrPasswords();
                    Log.v("unlogin","passwords:=" + passwords);
                    Log.v("unlogin","defaultUsrPasswords:=" + defaultUsrPasswords);
                    if(passwords.equals(defaultUsrPasswords)) {
                        loginText.setVisibility(View.INVISIBLE);
                        mActivity.finish();
                    }else {
                        loginText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * 初始化资源
     */
    public void initRes() {
        mActivity = this;

        loginLayout = (LinearLayout)findViewById(R.id.login_layout);
        unLoginLayout = (LinearLayout)findViewById(R.id.unlogin_layout);

        loginEdit = (EditText)findViewById(R.id.login_edit);
        loginText = (TextView)findViewById(R.id.login_text);
        loginButton = (Button )findViewById(R.id.login_comfirm);
        // 确认界面
        unLoginEdit1 = (EditText)findViewById(R.id.unlogin_edit1);
        unLoginEdit2 = (EditText)findViewById(R.id.unlogin_edit2);
        unLoginText = (TextView)findViewById(R.id.unlogin_text);
        unLoginButton = (Button)findViewById(R.id.unlogin_comfirm);
    }

    /**
     *  是否是第一次登陆
     * @return true -- 第一次登陆
     */
    public boolean isFirstLogin(){
        List<LoginData> logindata = LitePal.findAll(LoginData.class);
        // 如果用户数据为空，比如设置默认用户名
        if(logindata.size() == 0) {
            LoginData  defaultUsr = new LoginData();
            defaultUsr.setUsrName("default");
            defaultUsr.setUsrPasswords("000000");
            defaultUsr.setCreatTime(new Date().getTime());
            defaultUsr.save();
        }
        for(LoginData item : logindata) {
            String usrName = item.getUsrName();
            String usrPasswords = item.getUsrPasswords();
            // 默认用户为"defalut, 000000"
            Log.v("isFirstLogin", "usrName:"+usrName);
            Log.v("isFirstLogin", "usrPasswords:"+usrPasswords);
            if(usrName.equals(new String("default")) && usrName.equals(new String("000000"))) {
                return true;
            }
        }
        return  false;
    }

    /**
     *  获得默认用户id
     * @return 默认用户id
     */
    public LoginData getDefautlUsr() {
        List<LoginData> logindata = LitePal.findAll(LoginData.class);
        for(LoginData item : logindata) {
            String usrName = item.getUsrName();
            String usrPasswords = item.getUsrPasswords();
            if(usrName.equals(new String("default")) ) {
                return item;
            }
        }
        return null;
    }
}
