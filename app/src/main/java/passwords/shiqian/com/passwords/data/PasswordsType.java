package passwords.shiqian.com.passwords.data;

import org.litepal.crud.LitePalSupport;

public class PasswordsType extends LitePalSupport {
    private int id;
    private String typeName;        // 类别名称
    private int passwordType;       // 密码类别
    private long creatTime;      // 创建时间

    public int getId() {
        return id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setPasswordType(int passwordType) {
        this.passwordType = passwordType;
    }

    public int getPasswordType() {
        return passwordType;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public long getCreatTime() {
        return creatTime;
    }
}
