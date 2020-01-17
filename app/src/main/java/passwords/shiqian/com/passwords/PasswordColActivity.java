package passwords.shiqian.com.passwords;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;


import java.util.ArrayList;
import java.util.List;

import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.data.Const;
import passwords.shiqian.com.passwords.data.PasswordsData;
import passwords.shiqian.com.passwords.tool.CustomActionBar;
import passwords.shiqian.com.passwords.view.PasswordAdapter;

public class PasswordColActivity extends AppCompatActivity {
    private int mCategoryId;
    private Category mCategory;

    private TextView categaoryname;

    // 密码目录
    private RecyclerView mRecyclerViewList;
    private PasswordAdapter mPasswordsAdper;
    private List<PasswordsData> passwordsDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordcol);
        initFloatintBar(this);
        initToolBar(this);

        Intent intent = getIntent();
        mCategoryId = intent.getIntExtra("categoryid", 0);
        mCategory = LitePal.find(Category.class, mCategoryId);
        Log.v("PassCol", "category id:" + mCategoryId);
        categaoryname = (TextView)findViewById(R.id.ac_passwordcol_title);
        categaoryname.setText(mCategory.getTypeName());

        // 初始类别
        initPasswords();
        mRecyclerViewList = (RecyclerView)findViewById(R.id.ac_passwordcol_recye);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewList.setLayoutManager(linearLayoutManager);
        mPasswordsAdper = new PasswordAdapter(passwordsDataList, this);
        mRecyclerViewList.setAdapter(mPasswordsAdper);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("PasswordColActivity", "onActivityResult");
        Log.v("PasswordColActivity", "requestCode:" + requestCode + "" +
                ", resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Const.passcol_to_pass:
                if(resultCode == RESULT_OK){
                    int passid = data.getIntExtra("passid", 0);
                    Log.v("PasswordColActivity", "passid:" + passid);

                    PasswordsData item = LitePal.find(PasswordsData.class, passid);
                    mPasswordsAdper.addData(mPasswordsAdper.getItemCount(), item);
                    mRecyclerViewList.scrollToPosition(mPasswordsAdper.getItemCount());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化悬浮按钮
     * @param activity
     */
    private void initFloatintBar(final Activity activity) {
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.ac_passwordcol_fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(activity, PasswordActivity.class);
                detailIntent.putExtra("categoryid", mCategoryId);
                detailIntent.putExtra("passwordid", -1);
                detailIntent.putExtra("passactype", Const.pass_create);
                activity.startActivityForResult(detailIntent, Const.passcol_to_pass);
            }
        });
    }
    /**
     * 初始化密码
     */
    private void initPasswords(){
        List<PasswordsData> passwordsDatas = LitePal.findAll(PasswordsData.class);
        for(PasswordsData item : passwordsDatas){
            if(item.getCategoryid() == mCategoryId) {
                passwordsDataList.add(item);
            }
        }
    }

    /**
     * 初始化工具栏
     * @param activity
     */
    private void initToolBar(final Activity activity){
        final CustomActionBar actionBar0 = (CustomActionBar) findViewById(R.id.ac_passwordcol_actionbar);
        //返回键 + 标题 + 菜单图标
        actionBar0.setStyle("UPass", R.mipmap.more_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
