package passwords.shiqian.com.passwords.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import passwords.shiqian.com.passwords.PasswordColActivity;
import passwords.shiqian.com.passwords.data.Category;
import passwords.shiqian.com.passwords.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> mProject = new ArrayList<>();
    private Activity mActivity;
    private static float moveX = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View projectView;
        TextView categoryName;
        private ViewHolder(final View view){
            super(view);
            projectView = view;
            categoryName = (TextView)view.findViewById(R.id.categorycol_name);
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
        long createTime = project.getCreatTime();
        Log.v("iniDatas", categoryName);
        holder.categoryName.setText(categoryName);
    }

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

    private void showFeedbackDialog(String feedBack, final int projectId) {
        //加载布局并初始化组件
//        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.layout_timefeedback,null);
//        final EditText edit_text = (EditText) dialogView.findViewById(R.id.time_feedback_edit_content);
//        edit_text.setText(feedBack);
//        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(mActivity);
//        layoutDialog.setTitle("反馈内容");
//        layoutDialog.setView(dialogView);
//        layoutDialog.setCancelable(false);
//        layoutDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String content = edit_text.getText().toString();
//                ProjectData projectData = LitePal.find(ProjectData.class, projectId);
//                projectData.setFeedback(content);
//                projectData.save();
//            }
//        });
//
//        layoutDialog.create().show();
    }

    private void showEditDialog(final Category project, final View view) {
//        final View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.layout_bottom1,null);
//        TabHost host = (TabHost)dialogView.findViewById(R.id.pro_buttom_tabHost);
//        host.setup();
//
//        int proType = project.getType();
//
//        final int[] taskTimeType = {project.getTimeType()};
//        final Date[] endTme = {new Date(project.getEndTimeTime())};
//        final TextView listTasktype = (TextView)dialogView.findViewById(R.id.pro_txt_tab1_tasktype);
//        final EditText listTitleEdit = (EditText) dialogView.findViewById(R.id.pro_buttom_tab1_add);
//        final EditText listTotolTime = (EditText) dialogView.findViewById(R.id.pro_buttom_tab1_totaltime);
//        final EditText listGoalEdit = (EditText) dialogView.findViewById(R.id.pro_buttom_tab1_goal);
//        final Button listTaskUp = (Button)dialogView.findViewById(R.id.pro_btn_tab1_up);
//        final Button listTaskDown = (Button)dialogView.findViewById(R.id.pro_btn_tab1_down);
//        final TextView listEndTimeView = (TextView)dialogView.findViewById(R.id.pro_txt_tab1_deadtime);
//
//        final EditText taskContentEdit = (EditText) dialogView.findViewById(R.id.pro_buttom_tab2_content);
//        final EditText taskUseTimeEdit = (EditText) dialogView.findViewById(R.id.pro_buttom_tab2_usetime);
//        TabHost.TabSpec spec = host.newTabSpec("Tab One");
//
//        listTaskUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endTme[0] = TimeTool.getLastDateByTimetype(endTme[0], taskTimeType[0]);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//                listEndTimeView.setText(sdf.format(endTme[0]));
//            }
//        });
//
//        listTaskDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endTme[0] = TimeTool.getNextDateByTimetype(endTme[0], taskTimeType[0]);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//                listEndTimeView.setText(sdf.format(endTme[0]));
//            }
//        });
//
//        if(proType == Const.pro_type_list) {
//            spec.setContent(R.id.pro_buttom_tab1);
//            spec.setIndicator("清单修改");
//            dialogView.findViewById(R.id.pro_buttom_tab2).setVisibility(View.GONE);
//
//            listTitleEdit.setText(project.getName());
//            listTotolTime.setText(project.getPlanUseTime()/60.0 + "");
//            listGoalEdit.setText(project.getGoal());
//            if(taskTimeType[0] == Const.pro_type_time_week){
//                listTasktype.setText("周计划");
//            }else if(taskTimeType[0] == Const.pro_type_time_month){
//                listTasktype.setText("月计划");
//            }else if(taskTimeType[0] == Const.pro_type_time_year){
//                listTasktype.setText("年计划");
//            }else {
//                listTasktype.setText("自定义");
//            }
//        }else {
//            spec.setContent(R.id.pro_buttom_tab2);
//            spec.setIndicator("任务修改");
//            dialogView.findViewById(R.id.pro_buttom_tab1).setVisibility(View.GONE);
//            // 隐藏tab2 开始按钮
//            dialogView.findViewById(R.id.pro_buttom_tab2_confirm_layout).setVisibility(View.GONE);
//            taskContentEdit.setText(project.getName());
//            taskUseTimeEdit.setText(project.getPlanUseTime() + "");
//        }
//        host.addTab(spec);
//
//        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(mActivity);
//        layoutDialog.setView(dialogView);
//        final AlertDialog AlertDialogView = layoutDialog.create();
//        AlertDialogView.show();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//        listEndTimeView.setText(sdf.format(endTme[0]));
//        dialogView.findViewById(R.id.pro_buttom_tab1_tasktype).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v("taskTimeType", taskTimeType[0] + "");
//                switch (taskTimeType[0]) {
//                    case Const.pro_type_time_week:
//                        taskTimeType[0] = Const.pro_type_time_month;
//                        listTasktype.setText("月计划");
//                        endTme[0] = TimeTool.getThisMonthEndDay();
//                        break;
//                    case Const.pro_type_time_month:
//                        taskTimeType[0] = Const.pro_type_time_year;
//                        listTasktype.setText("年度计划");
//                        endTme[0] = TimeTool.getCurrYearLast();
//                        break;
//                    case Const.pro_type_time_year:
//                        taskTimeType[0] = Const.pro_type_time_self;
//                        listTasktype.setText("自定义");
//                        Date date=new Date();//此时date为当前的时间
//                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");//设置当前时间的格式，为年-月-日
//                        String day = dateFormat.format(date);
//                        int year = Integer.parseInt(day.split("-")[0]);
//                        int month = Integer.parseInt(day.split("-")[1]);
//                        final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
//                        dialog.show();
//                        dialog.setCancelable(false);
//                        DatePicker picker = new DatePicker(mActivity);
//                        picker.setDate(year, month);
//                        picker.setMode(DPMode.SINGLE);
//                        picker.setFestivalDisplay(true);
//                        picker.setTodayDisplay(false);
//                        picker.setHolidayDisplay(true);
//                        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//                            @Override
//                            public void onDatePicked(String date) {
//                                try {
//                                    endTme[0] = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//                                    listEndTimeView.setText(sdf.format(endTme[0]));
//                                }catch (Exception e){
//                                }
//                                dialog.dismiss();
//                            }
//                        });
//                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        dialog.getWindow().setContentView(picker, params);
//                        dialog.getWindow().setGravity(Gravity.CENTER);
//                        break;
//                    case Const.pro_type_time_self:
//                        taskTimeType[0] = Const.pro_type_time_week;
//                        listTasktype.setText("周计划");
//                        endTme[0] = TimeTool.getThisWeekSunday();
//                        break;
//                    default:
//                        taskTimeType[0] = Const.pro_type_time_week;
//                        listTasktype.setText("周计划");
//                        endTme[0] = TimeTool.getThisWeekSunday();
//                        break;
//                }
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//                listEndTimeView.setText(sdf.format(endTme[0]));
//            }
//        });
//
//        // 修改清单
//        dialogView.findViewById(R.id.pro_buttom_tab1_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String projectStr = listTitleEdit.getText().toString();
//                String goalStr = listGoalEdit.getText().toString();
//                float planUseTime = Float.parseFloat(listTotolTime.getText().toString()) * 60;
//
//                //  更新数据库
//                ProjectData projectData = LitePal.find(ProjectData.class, project.getId());
//                projectData.setPlanUseTime(planUseTime);
//                projectData.setName(projectStr);
//                projectData.setGoal(goalStr);
//                projectData.setTimeType( taskTimeType[0]);
//                projectData.setEndTimeTime(endTme[0].getTime());
//                projectData.save();
//
//                float useTime = project.getUseTime();
//                float rate = useTime / planUseTime *100.0f;
//                String content = "计划用时:";
//                if(planUseTime > 60) {
//                    content += String.format("%.1f", planUseTime/60) + "小时,目前已完成:";
//                }else {
//                    content += String.format("%.0f", planUseTime) + "分钟,目前已完成:";
//                }
//                if(useTime > 60) {
//                    content += String.format("%.1f", useTime/60) + "小时,";
//                }else {
//                    content += String.format("%.0f", useTime) + "分钟,";
//                }
//                content += "进度:" + String.format("%.2f", rate) + "%";
//
//
//                TextView listTitleView = (TextView)view.findViewById(R.id.pro_itemco_list_title);
//                TextView listUseTimeView = (TextView)view.findViewById(R.id.pro_itemco_list_usetime);
//                TextView listEndTimeView = (TextView)view.findViewById(R.id.pro_itemco_list_deadtime);
//                listUseTimeView.setText(content);
//                listTitleView.setText(projectStr);
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//                Date d = new Date(endTme[0].getTime());
//                listEndTimeView.setText("截止" + dateFormat.format(d) + "完成");
//
//                AlertDialogView.dismiss();
//            }
//        });
//
//        // 修改任务
//        dialogView.findViewById(R.id.pro_buttom_tab2_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float planUseTime = Float.parseFloat(taskUseTimeEdit.getText().toString());
//                float planHour = planUseTime / 60;
//                String name = taskContentEdit.getText().toString();
//
//                //  更新数据库
//                ProjectData projectData = LitePal.find(ProjectData.class, project.getId());
//                projectData.setPlanUseTime(planUseTime);
//                projectData.setTimeType(Const.pro_type_time_day);
//                projectData.setName(name);
//                projectData.save();
//
//                float useTime = project.getUseTime();
//                float useHour = useTime / 60;
//                String content = "";
//                if(planHour >= 1.0) {
//                    content += "计划耗时"  + planHour + "小时";
//                }else {
//                    content += "计划耗时" + String.format("%.0f", planUseTime) + "分钟";
//                }
//                if(useTime > 0) {
//                    if(useHour >= 1.0) {
//                        content += "，实际用时" + useHour + "小时";
//                    }else {
//                        content += "，实际用时" + String.format("%.0f", useTime) + "分钟";
//                    }
//                }else {
//                    content += ",还未开始";
//                }
//                TextView taskTitleView = (TextView)view.findViewById(R.id.pro_itemco_task_title);
//                TextView taskUseTimeView = (TextView)view.findViewById(R.id.pro_itemco_task_usetime);
//
//                taskTitleView.setText(name);
//                taskUseTimeView.setText(content);
//
//                AlertDialogView.dismiss();
//            }
//        });
    }

    private void showCompleteDialog(final int projectId, final int position) {
//        //加载布局并初始化组件
//        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.layout_timefeedback,null);
//        final EditText edit_text = (EditText) dialogView.findViewById(R.id.time_feedback_edit_content);
//        final ProjectData projectData = LitePal.find(ProjectData.class, projectId);
//        edit_text.setHint(R.string.listcomlete);
//        edit_text.setText(projectData.getFeedback());
//        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(mActivity);
//        layoutDialog.setTitle("是否确定完成?");
//        layoutDialog.setView(dialogView);
//        layoutDialog.setCancelable(false);
//        layoutDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String content = edit_text.getText().toString();
//                projectData.setFeedback(content);
//                projectData.setIsConplete(1);
//                Log.v("initProjectTask", projectData.getName());
//                projectData.save();
//                removeData(position);
//            }
//        });
//        layoutDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ProjectData projectData = LitePal.find(ProjectData.class, projectId);
//                projectData.setIsConplete(0);
//                projectData.save();
//            }
//        });
//
//        layoutDialog.create().show();
    }
}
