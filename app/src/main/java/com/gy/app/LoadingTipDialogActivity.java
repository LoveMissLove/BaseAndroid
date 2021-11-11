package com.gy.app;

import static com.example.ltbase.base_dialog.QMUITipDialogUtil.FAIL;
import static com.example.ltbase.base_dialog.QMUITipDialogUtil.INFO;
import static com.example.ltbase.base_dialog.QMUITipDialogUtil.LOADING;
import static com.example.ltbase.base_dialog.QMUITipDialogUtil.SUCCESS;

import android.view.View;
import android.widget.Button;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_dialog.AddressDialog;
import com.example.ltbase.base_dialog.BaseDialog;
import com.example.ltbase.base_dialog.DateDialog;
import com.example.ltbase.base_dialog.MenuDialog;
import com.example.ltbase.base_dialog.MessageDialog;
import com.example.ltbase.base_dialog.QMUITipDialogUtil;
import com.example.ltbase.base_dialog.TimeDialog;
import com.example.ltbase.base_manager.ThreadPoolManager;
import com.example.ltbase.base_utils.RxViewUtils;
import com.example.ltbase.base_utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者：王健 on 2021/9/3
 * 邮箱：845040970@qq.com
 * 描述：QMUITipDialogUtil 提示dialog
 */
public class LoadingTipDialogActivity extends BaseActivity {
    private Button btnLoading, btnSuccess, btnError, btnE, btnCancel, btnCustom, btnAddress, btnDate,btnTime,btnMessage,btnMenu;
    private QMUITipDialogUtil qmuiTipDialogUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading_tip_dialog;
    }

    @Override
    protected void initView() {
        qmuiTipDialogUtil = new QMUITipDialogUtil();
        btnLoading = F(R.id.btnLoading);
        btnSuccess = F(R.id.btnSuccess);
        btnError = F(R.id.btnError);
        btnE = F(R.id.btnE);
        btnCustom = F(R.id.btnCustom);
        btnCancel = F(R.id.btnCancel);
        btnAddress = F(R.id.btnAddress);
        btnDate = F(R.id.btnDate);
        btnTime=F(R.id.btnTime);
        btnMessage=F(R.id.btnMessage);
        btnMenu=F(R.id.btnMenu);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(btnLoading, btnSuccess, btnError, btnE, btnCancel, btnCustom, btnAddress, btnDate,btnTime,btnMessage,btnMenu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoading:
                qmuiTipDialogUtil.showDialog(context, LOADING, "加载中", true);
                if (null != qmuiTipDialogUtil.getDialog() && !qmuiTipDialogUtil.getDialog().isShowing()) {
                    qmuiTipDialogUtil.showDialog(context, LOADING, "加载中", true);
                    qmuiTipDialogUtil.showDialog(context, LOADING, "加载中", true);
                    qmuiTipDialogUtil.showDialog(context, LOADING, "加载中", true);
                }
                break;
            case R.id.btnSuccess:
                qmuiTipDialogUtil.showDialog(context, SUCCESS, "加载成功", true, 3000);
                break;
            case R.id.btnError:
                qmuiTipDialogUtil.showDialog(context, FAIL, "加载失败", true, 3000);
                break;
            case R.id.btnE:
                qmuiTipDialogUtil.showDialog(context, INFO, "加载异常", true, 3000);
                break;
            case R.id.btnCustom:
//               QMUITipDialog dialog= qmuiTipDialogUtil.showCustom(R.layout.dialog_qmui_custom);
//                ImageView img=dialog.findViewById(R.id.img);
//                TextView textView=dialog.findViewById(R.id.tvMsg);
//                img.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher));
//                textView.setText("我是自定义TipDialog");
//                qmuiTipDialogUtil.show();
//                qmuiTipDialogUtil.showTimeDialog(3000);
                break;
            case R.id.btnCancel:
                qmuiTipDialogUtil.dismissDialog();
                break;
            case R.id.btnAddress:
                // 选择地区对话框
                new AddressDialog.Builder(this)
                        .setTitle(getString(R.string.address_title))
                        // 设置默认省份
                        //.setProvince("广东省")
                        // 设置默认城市（必须要先设置默认省份）
                        //.setCity("广州市")
                        // 不选择县级区域
                        //.setIgnoreArea()
                        .setListener(new AddressDialog.OnListener() {

                            @Override
                            public void onSelected(BaseDialog dialog, String province, String city, String area) {
                                ToastUtils.showToast(province + city + area);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                ToastUtils.showToast("取消了");
                            }
                        })
                        .show();
                break;
            case R.id.btnDate:
                // 日期选择对话框
                new DateDialog.Builder(this)
                        .setTitle(getString(R.string.date_title))
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置日期
                        //.setDate("2018-12-31")
                        //.setDate("20181231")
                        //.setDate(1546263036137)
                        // 设置年份
//                        .setYear(2050)
                        // 设置月份
                        //.setMonth(2)
                        // 设置天数
                        //.setDay(20)
                        // 不选择天数
                        //.setIgnoreDay()
                        .setListener(new DateDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int year, int month, int day) {
                                ToastUtils.showToast(year + getString(R.string.common_year) + month + getString(R.string.common_month) + day + getString(R.string.common_day));

//                                // 如果不指定时分秒则默认为现在的时间
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.set(Calendar.YEAR, year);
//                                // 月份从零开始，所以需要减 1
//                                calendar.set(Calendar.MONTH, month - 1);
//                                calendar.set(Calendar.DAY_OF_MONTH, day);
//                                ToastUtils.showToast("时间戳：" + calendar.getTimeInMillis());
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                break;
            case R.id.btnTime:
                // 时间选择对话框
                new TimeDialog.Builder(this)
                        .setTitle(getString(R.string.time_title))
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置时间
                        //.setTime("23:59:59")
                        //.setTime("235959")
                        // 设置小时
                        //.setHour(23)
                        // 设置分钟
                        //.setMinute(59)
                        // 设置秒数
                        //.setSecond(59)
                        // 不选择秒数
//                        .setIgnoreSecond()
                        .setListener(new TimeDialog.OnListener() {

                            @Override
                            public void onSelected(BaseDialog dialog, int hour, int minute, int second) {

                                ToastUtils.showToast(hour + getString(R.string.common_hour) + minute + getString(R.string.common_minute) + second + getString(R.string.common_second));

//                                // 如果不指定年月日则默认为今天的日期
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.set(Calendar.HOUR_OF_DAY, hour);
//                                calendar.set(Calendar.MINUTE, minute);
//                                calendar.set(Calendar.SECOND, second);
//                                ToastUtils.showToast("时间戳：" + calendar.getTimeInMillis());
//                                //toast(new SimpleDateFormat("yyyy年MM月dd日 kk:mm:ss").format(calendar.getTime()));
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                break;
            case R.id.btnMessage:
                // 消息对话框
                new MessageDialog.Builder(getActivity())
                        // 标题可以不用填写
                        .setTitle("我是标题")
                        // 内容必须要填写
                        .setMessage("我是内容")
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                ToastUtils.showToast("确定了");
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                ToastUtils.showToast("取消了");
                            }
                        })
                        .show();

                break;
            case R.id.btnMenu:
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    data.add("我是数据" + (i + 1));
                }
                // 底部选择框
                new MenuDialog.Builder(this)
                        // 设置 null 表示不显示取消按钮
                        //.setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setList(data)
                        .setListener(new MenuDialog.OnListener<String>() {

                            @Override
                            public void onSelected(BaseDialog dialog, int position, String string) {
                                ToastUtils.showToast("位置：" + position + "，文本：" + string);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                break;
            default:
        }
    }

}
