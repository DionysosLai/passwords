package passwords.shiqian.com.passwords.data;

import org.litepal.crud.LitePalSupport;

public class Category extends LitePalSupport {
    private int id;
    private String typeName;    // 类别名称
    private int categoryid;       // 密码类别
    private long creatTime;     // 创建时间

    public Category(){
    }

    public Category(String typeName, int id, int categoryid, long creatTime){
        this.id = id;
        this.typeName = typeName;
        this.creatTime = creatTime;
        this.categoryid = categoryid;
    }

    public int getId() {
        return id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public long getCreatTime() {
        return creatTime;
    }
}
