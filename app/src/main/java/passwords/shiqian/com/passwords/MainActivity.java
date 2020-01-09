package passwords.shiqian.com.passwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

import passwords.shiqian.com.passwords.data.LoginData;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultLoginUsr();

        Intent detailIntent = new Intent(this, LoginActivity.class);
        this.startActivity(detailIntent);
    }

    /**
     * 设置默认登录用户
     * @return false -- 未设置
     */
    public boolean setDefaultLoginUsr() {
        List<LoginData> logindata = LitePal.findAll(LoginData.class);
        // 设置默认用户名
        if(logindata.size() == 0) {
            LoginData  defaultUsr = new LoginData();
            defaultUsr.setUsrName("default");
            defaultUsr.setUsrPasswords("000000");
            defaultUsr.setCreatTime(new Date().getTime());
            defaultUsr.save();
            return false;
        }
        return  true;
    }
}
