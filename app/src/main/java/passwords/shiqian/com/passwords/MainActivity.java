package passwords.shiqian.com.passwords;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.TabHost;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.data.LoginData;
import passwords.shiqian.com.passwords.tool.CustomActionBar;
import passwords.shiqian.com.passwords.view.CategoryAdapter;

public class MainActivity extends AppCompatActivity {
    // 列表目录
    private RecyclerView mRecyclerViewList;
    private CategoryAdapter mCategoryAdper;
    private List<Category> categoriesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化悬浮按钮
        initFloatintBar(this);
        // 初始化标题
        initToolBar(this);

        // 判断用户是否登录过
        setDefaultLoginUsr();
        Intent detailIntent = new Intent(this, LoginActivity.class);
        this.startActivity(detailIntent);


        // 初始类别
        initCategory();
        mRecyclerViewList = (RecyclerView)findViewById(R.id.project_recye);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewList.setLayoutManager(linearLayoutManager);
        mCategoryAdper = new CategoryAdapter(categoriesList, this);
        mRecyclerViewList.setAdapter(mCategoryAdper);
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
            Log.v("initusr", "user first login, and logindata initial!!!");
            return false;
        }
        return  true;
    }

    /**
     * 初始化悬浮按钮
     * @param activity
     */
    private void initFloatintBar(final Activity activity) {
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.main_fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory(activity);
            }
        });
    }

    /**
     * 初始化类别
     */
    private void initCategory(){
        List<Category> categories = LitePal.findAll(Category.class);
        //  设置默认分类
        if(categories.size() == 0) {
            String defaultCategory[] = {"阿里云", "邮箱", "网站登录", "应用"};
            for(int i = 0; i < defaultCategory.length; i++) {
                Category defaultPs = new Category();
                defaultPs.setTypeName(defaultCategory[i]);
                defaultPs.setCreatTime(new Date().getTime());
                defaultPs.save();
            }
        }

        categories = LitePal.findAll(Category.class);
        categoriesList.clear();
        for(Category item : categories){
            Log.v("mainactivity", "category, id:"+item.getId() + ", name:" + item.getTypeName());
            categoriesList.add(item);
        }
    }

    /**
     * 初始化工具栏
     * @param activity
     */
    private void initToolBar(final Activity activity){
        final CustomActionBar actionBar0 = (CustomActionBar) findViewById(R.id.main_actionbar);
        //返回键 + 标题 + 菜单图标
        actionBar0.setStyle("UPass", R.mipmap.more_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actionBar0.setNoBack();
    }

    /**
     * 增加一个类别
     * @param activity
     */
    private void addCategory(final Activity activity) {
        //加载布局并初始化组件
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.addcategory,null);
        final EditText categoryname = (EditText) dialogView.findViewById(R.id.addcategory_categoryname);
        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(activity);
        layoutDialog.setView(dialogView);
        final AlertDialog AlertDialogView = layoutDialog.create();
        AlertDialogView.show();

        dialogView.findViewById(R.id.addcategory_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 存入数据库
                Category item = new Category();
                item.setTypeName(categoryname.getText().toString());
                item.setCreatTime(new Date().getTime());
                item.save();
                AlertDialogView.dismiss();

                mCategoryAdper.addData(mCategoryAdper.getItemCount(), item);
                mRecyclerViewList.scrollToPosition(mCategoryAdper.getItemCount());
            }
        });
    }
}
