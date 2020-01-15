package passwords.shiqian.com.passwords;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.litepal.LitePal;


import java.util.ArrayList;
import java.util.List;

import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.data.Const;
import passwords.shiqian.com.passwords.data.PasswordsData;
import passwords.shiqian.com.passwords.view.PasswordAdapter;

public class PasswordColActivity extends AppCompatActivity {
    private int mCategoryId;
    private Category mCategory;

    // 密码目录
    private RecyclerView mRecyclerViewList;
    private PasswordAdapter mPasswordsAdper;
    private List<PasswordsData> passwordsDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordcol);
        initFloatintBar(this);

        Intent intent = getIntent();
        mCategoryId = intent.getIntExtra("categoryid", 0);
        mCategory = LitePal.find(Category.class, mCategoryId);

        // 初始类别
        initPasswords();
        mRecyclerViewList = (RecyclerView)findViewById(R.id.passwordcol_recye);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewList.setLayoutManager(linearLayoutManager);
        mPasswordsAdper = new PasswordAdapter(passwordsDataList, this);
        mRecyclerViewList.setAdapter(mPasswordsAdper);
    }

    /**
     * 初始化悬浮按钮
     * @param activity
     */
    private void initFloatintBar(final Activity activity) {
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.passwordcol_fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(activity, PasswordActivity.class);
                detailIntent.putExtra("categoryid", mCategoryId);
                detailIntent.putExtra("passwordid", -1);
                detailIntent.putExtra("passactype", Const.pass_create);
                activity.startActivity(detailIntent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }
        });
    }
    /**
     * 初始化密码
     */
    private void initPasswords(){
        List<PasswordsData> passwordsDatas = LitePal.findAll(PasswordsData.class);
        for(PasswordsData item : passwordsDatas){
            passwordsDataList.add(item);
        }
    }
}
