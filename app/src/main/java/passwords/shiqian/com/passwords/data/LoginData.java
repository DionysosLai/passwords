package passwords.shiqian.com.passwords.data;

import org.litepal.crud.LitePalSupport;

public class LoginData extends LitePalSupport {
    private int id;
    private String usrName;        // 用户名
    private String usrPasswords;  // 用户密码
    private long creatTime;      // 创建时间

    public int getId() {
        return id;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrPasswords(String usrPasswords) {
        this.usrPasswords = usrPasswords;
    }

    public String getUsrPasswords() {
        return usrPasswords;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public long getCreatTime() {
        return creatTime;
    }
}
