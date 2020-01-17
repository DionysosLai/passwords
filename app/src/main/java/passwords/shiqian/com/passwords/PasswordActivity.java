package passwords.shiqian.com.passwords;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.Date;

import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.data.Const;
import passwords.shiqian.com.passwords.data.PasswordsData;
import passwords.shiqian.com.passwords.tool.CustomActionBar;

public class PasswordActivity extends AppCompatActivity {
    private int mCategoryId;
    private int mPasswordId;
    private Category category;
    private PasswordsData passwordsData;
    private int mAcType; // 表示进入当前activity 类别

    // res
    private TextView mTextTitle;
    private EditText mEditName;
    private EditText mEditUsrname;
    private EditText mEditUsrpssword;
    private EditText mEditPhonenum;
    private EditText mEditMail;
    private EditText mEditUrl;
    private Button mConfirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initToolBar(this);
        initRes();

        Intent intent = getIntent();
        mCategoryId = intent.getIntExtra("categoryid", 0);
        mPasswordId = intent.getIntExtra("passwordid", 0);
        if(mCategoryId > 0) {
            category = LitePal.find(Category.class, mCategoryId);
            mTextTitle.setText(category.getTypeName());
        }
        if(mPasswordId > 0) {
            passwordsData = LitePal.find(PasswordsData.class, mCategoryId);
        }

        mAcType = intent.getIntExtra("passactype", Const.pass_create);

        if(Const.pass_create == mAcType) {
            mConfirmBtn.setVisibility(View.VISIBLE);
            mConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PasswordsData passwordsData =  savePassword();

                    // activity 跳转，返回信息
                    Intent backIntent = new Intent();
                    backIntent.putExtra("passid", passwordsData.getId());
                    PasswordActivity.this.setResult(RESULT_OK, backIntent);
                    PasswordActivity.this.finish();
                }
        });
        }else {
            mConfirmBtn.setVisibility(View.INVISIBLE);
            initView();
        }
    }

    private void initRes() {
        mTextTitle = (TextView)findViewById(R.id.ac_password_title);
        mEditName = (EditText)findViewById(R.id.ac_password_name);
        mEditUsrname = (EditText)findViewById(R.id.ac_password_usrname);
        mEditUsrpssword = (EditText) findViewById(R.id.ac_password_usrpassword);
        mEditPhonenum = (EditText) findViewById(R.id.ac_password_phonenum);
        mEditMail = (EditText) findViewById(R.id.ac_password_mail);
        mEditUrl = (EditText) findViewById(R.id.ac_password_weburl);
        mConfirmBtn = (Button)findViewById(R.id.ac_password_confirm);
    }

    /**
     * 初始化界面
     */
    private void initView(){
        PasswordsData item = LitePal.find(PasswordsData.class, mPasswordId);
        if(item == null){
            Log.v("PasswordActivity", "can not find passworId:"+mPasswordId);
            return;
        }
        mEditName.setEnabled(false);
        mEditName.setText(item.getName());
        mEditUsrname.setEnabled(false);
        mEditUsrname.setText(item.getUsrName());
        mEditUsrpssword.setEnabled(false);
        mEditUsrpssword.setText(item.getUsrPasswords());
        mEditPhonenum.setText(item.getPhoneNum());
        mEditUrl.setText(item.getWebUrl());
        mEditMail.setText(item.getMail());
    }
    /**
     * 初始化工具栏
     * @param activity
     */
    private void initToolBar(final Activity activity){
        final CustomActionBar actionBar0 = (CustomActionBar) findViewById(R.id.ac_password_actionbar);
        //返回键 + 标题 + 菜单图标
        actionBar0.setStyle("UPass", R.mipmap.more_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * 存储密码到数据库
     * @return
     */
    private PasswordsData savePassword(){
        String name = mEditName.getText().toString();
        String usrname = mEditUsrname.getText().toString();
        String usrpassword = mEditUsrpssword.getText().toString();
        String phonenum = mEditPhonenum.getText().toString();
        String mail = mEditMail.getText().toString();
        String url = mEditUrl.getText().toString();

        PasswordsData passdata = new PasswordsData(name, usrname, usrpassword, new Date().getTime(), url,
                phonenum, mail, mCategoryId);
        passdata.save();
        return  passdata;
    }
}
