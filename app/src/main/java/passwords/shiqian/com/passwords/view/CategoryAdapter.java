package passwords.shiqian.com.passwords.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import passwords.shiqian.com.passwords.PasswordColActivity;
import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.R;
import passwords.shiqian.com.passwords.data.PasswordsData;
import passwords.shiqian.com.passwords.tool.NineCellBitmapUtil;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> mProject = new ArrayList<>();
    private Activity mActivity;
    private ViewHolder holder;
    private static float moveX = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View projectView;
        TextView categoryName;
        CircleImageView circleImageView;
        NineCellBitmapUtil nineCellBitmapUtil;
        private ViewHolder(final View view){
            super(view);
            projectView = view;
            categoryName = (TextView)view.findViewById(R.id.categorycol_name);
            circleImageView = (CircleImageView)view.findViewById(R.id.categorycol_circle);
            // 实例化这个这个工具类，默认聚合的图片尺寸是1000像素，每张图的间距是20像素
            nineCellBitmapUtil = NineCellBitmapUtil.with().setBitmapSize(1000)
                    .setItemMargin(20).setPaddingSize(20).build(mActivity);
            ImageView deleteProject = (ImageView)view.findViewById(R.id.categorycol_more_delete);
            deleteProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加载布局并初始化组件
                    final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(mActivity);
                    layoutDialog.setTitle("确定删除?");
                    layoutDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData(getAdapterPosition());
                        }
                    });
                    layoutDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    layoutDialog.show();
                }
            });

            ImageView editProject = (ImageView)view.findViewById(R.id.categorycol_more_edit);
            editProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Category item = mProject.get(position);
                    showEditDialog(item, position, categoryName);
                }
            });
        }
    }

    public CategoryAdapter(List<Category> projectItem, Activity activity){
        mProject.addAll(projectItem);
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorycol, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        this.holder = holder;
        holder.projectView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        moveX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        if(Math.abs(event.getX() - moveX) < 5) {
                            int position = holder.getAdapterPosition();
                            Category project = mProject.get(position);
                            Intent detailIntent = new Intent(mActivity, PasswordColActivity.class);
                            detailIntent.putExtra("categoryid", project.getId());
                            mActivity.startActivity(detailIntent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                        }
                        break;
                }
                return false;//继续执行后面的代码
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category project = mProject.get(position);
        initDatas(project, position, holder);
    }

    @Override
    public int getItemCount() {
        return mProject.size();
    }

    //  添加数据
    public void addData(int position, Category project) {
        if(mProject == null){
            mProject = new ArrayList<>();
        }
        Log.v("addData1", getItemCount() + "");
        mProject.add(position,project);
        Log.v("addData2", getItemCount() + "");
        notifyItemInserted(position);//通知演示插入动画
        notifyItemRangeChanged(position, mProject.size()-position);//通知数据与界面重新绑定
    }

    public void addDatas(int position, List<Category> projects) {
        // 当数据为0，必须这样显示，否则无效
        if(getItemCount() <= 0) {
            position = 0;
            for(int i = 0; i < projects.size(); i++){
                mProject.add(projects.get(i));
                this.notifyItemChanged(position);
            }
        }else {
            mProject.addAll(projects);
            this.notifyItemRangeInserted(position, projects.size() - 1 + position);
        }
    }

    public void removeAllData() {
        int size = getItemCount();
        Log.v("removeAllData", getItemCount() + "");
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mProject.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void removeData(int Position) {
        if(Position < 0 || Position >= getItemCount()) {
            Log.v("removeData", "position is error:" + Position);
            return;
        }

        mProject.remove(Position);
        notifyItemRemoved(Position);
    }

    //
    public void deleteData(int Position){
        if(Position < 0 || Position >= getItemCount()) {
            Log.v("deleteData", "position is error:" + Position);
            return;
        }
        // 数据库移除数据
        Category project = mProject.get(Position);
        int id = project.getId();
        LitePal.delete(Category.class, id);

        mProject.remove(Position);
        notifyItemRemoved(Position);
    }

    private void initDatas(Category project, int position, ViewHolder holder) {
        String categoryName = project.getTypeName();
        int categoryId = project.getId();
        long createTime = project.getCreatTime();
        Log.v("iniDatas", categoryName);
        holder.categoryName.setText(categoryName);

        List<PasswordsData> passwordsDatas = LitePal.findAll(PasswordsData.class);
        String passwordName = "";
        int index = 0;
        for(PasswordsData item : passwordsDatas){
            if(item.getCategoryid() == categoryId && index < 9) {
                passwordName += item.getName().substring(0, 1);
                index++;
            }
        }
        if(passwordName.length() > 0) {
            holder.circleImageView.setImageBitmap(holder.nineCellBitmapUtil.formatNineCellBitmap(passwordName));
        } else {
            Bitmap bm = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.bird);
            List<Bitmap> bitmapList = new ArrayList<>();
            bitmapList.add(bm);
            holder.circleImageView.setImageBitmap(holder.nineCellBitmapUtil.formatNineCellBitmap(bitmapList));
        }
    }

//    private void initDatas(Category project, int position, TextView categoryName) {
//        String name = project.getTypeName();
//        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorycol, parent, false);
//        final ViewHolder holder = new ViewHolder(view);
//        categoryName.setText(name);
//    }

    /**
     * dp转为px
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }


    /**
     * 显示编辑界面
     * @param project
     */
    private void showEditDialog(final Category project, final int position, final TextView textView) {
        //加载布局并初始化组件
        final View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.addcategory,null);
        final EditText categoryname = (EditText) dialogView.findViewById(R.id.addcategory_categoryname);
        categoryname.setText(project.getTypeName());
        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(mActivity);
        layoutDialog.setView(dialogView);
        final AlertDialog AlertDialogView = layoutDialog.create();
        AlertDialogView.show();

        dialogView.findViewById(R.id.addcategory_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 存入数据库
                project.setTypeName(categoryname.getText().toString());
                project.save();
                AlertDialogView.dismiss();

                initDatas(project, position, holder);
            }
        });
    }
}
