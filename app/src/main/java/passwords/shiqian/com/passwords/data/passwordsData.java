package passwords.shiqian.com.passwords.data;

import org.litepal.crud.LitePalSupport;

public class PasswordsData extends LitePalSupport {
    private int id;
    private String name;    // 密码名称
    private String usrName;        // 用户名
    private String usrPasswords;  // 用户密码
    private long creatTime;      // 创建时间
    private String webUrl;         // 网址
    private String phoneNum;    // 手机号码
    private String mail;        // 邮箱地址
    private int categoryid;       // 密码类别

    public PasswordsData(){
    }

    public PasswordsData(String name, String usrName, String usrPasswords, long creatTime, String webUrl,
                         String phoneNum, String mail, int categoryid){
        this.name = name;
        this.usrName = usrName;
        this.usrPasswords = usrPasswords;
        this.creatTime = creatTime;
        this.webUrl = webUrl;
        this.phoneNum = phoneNum;
        this.mail = mail;
        this.categoryid = categoryid;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getCategoryid() {
        return categoryid;
    }
}
