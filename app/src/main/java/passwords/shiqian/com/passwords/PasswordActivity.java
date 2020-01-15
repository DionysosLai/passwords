package passwords.shiqian.com.passwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.litepal.LitePal;

import java.util.Date;

import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.data.Const;
import passwords.shiqian.com.passwords.data.PasswordsData;

public class PasswordActivity extends AppCompatActivity {
    private int mCategoryId;
    private int mPasswordId;
    private Category category;
    private PasswordsData passwordsData;
    private int mAcType; // 表示进入当前activity 类别

    // res
    private EditText mEditName;
    private EditText mEditUsrname;
    private EditText mEditUsrpssword;
    private Button mConfirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initRes();

        Intent intent = getIntent();
        mCategoryId = intent.getIntExtra("categoryid", 0);
        mPasswordId = intent.getIntExtra("passwordid", 0);
        if(mCategoryId > 0) {
            category = LitePal.find(Category.class, mCategoryId);
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
                    String name = mEditName.getText().toString();
                    String usrname = mEditUsrname.getText().toString();
                    String usrpassword = mEditUsrpssword.getText().toString();
                    PasswordsData passdata = new PasswordsData(name, usrname, usrpassword, new Date().getTime(), "",
                            "", "", mCategoryId);
                    passdata.save();
                    PasswordActivity.this.finish();
                }
        });
        }else {
            mConfirmBtn.setVisibility(View.INVISIBLE);
            initView();
        }
    }

    private void initRes() {
        mEditName = (EditText)findViewById(R.id.password_name);
        mEditUsrname = (EditText)findViewById(R.id.password_usrname);
        mEditUsrpssword = (EditText) findViewById(R.id.password_usrpassword);
        mConfirmBtn = (Button)findViewById(R.id.password_confirm);

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
    }
}
