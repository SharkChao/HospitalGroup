package com.lenovohit.hospitalgroup.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lenovohit.hospitalgroup.R;
import com.lenovohit.lartemis_api.annotation.ContentView;
import com.lenovohit.lartemis_api.base.BaseController;
import com.lenovohit.lartemis_api.base.CoreActivity;
import com.lenovohit.lartemis_api.core.LArtemis;
import com.lenovohit.lartemis_api.data.UserData;
import com.lenovohit.lartemis_api.model.ResponseError;
import com.lenovohit.lartemis_api.model.Result;
import com.lenovohit.lartemis_api.model.User;
import com.lenovohit.lartemis_api.ui.controller.MainController;
import com.lenovohit.lartemis_api.utils.CommonUtil;
import com.lenovohit.lartemis_api.utils.Constants;
import com.lenovohit.lartemis_api.views.MyItemInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Administrator on 2017-07-04.
 * 患者个人信息设置界面
 */
@ContentView(R.layout.lx_user_info)
public class LX_UserInfoActivity extends CoreActivity<MainController.MainUiCallbacks> implements MainController.UserInfoEditUi {

    @BindView(R.id.lrvTX)
    RelativeLayout rlAvautar;
    @BindView(R.id.aivInfoHead)
    ImageView aivInfoHead;
    @BindView(R.id.loginOrOutBtn)
    Button loginOrOutBtn;
    @BindView(R.id.lrvXM)
    MyItemInfo lrvXM;
    @BindView(R.id.lrvSFZ)
    MyItemInfo lrvSFZ;
    @BindView(R.id.lrvSJHM)
    MyItemInfo lrvSJHM;
    @BindView(R.id.lrvXB)
    MyItemInfo lrvXB;
    private Unbinder bind;
    private String mSex;


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        bind = ButterKnife.bind(this);
        isShowToolBar(true);
        setCenterTitle("个人信息");
    }

    @Override
    public void initEvent() {
        /** 监听对话框里面的button点击事件 */
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出登录
                        CommonUtil.setShardPString(Constants.SP_USER_INFO, "");
                        UserData.setTempUser(null);
                        finish();
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:// "取消"按钮取消对话框
                        break;
                    default:
                        break;
                }
            }
        };
        loginOrOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建退出对话框
                AlertDialog isExit = new AlertDialog.Builder(
                        LX_UserInfoActivity.this).create();
                isExit.setTitle("提示");
                isExit.setMessage("确定要退出吗");
                isExit.setButton("确定",listener);
                isExit.setButton2("取消",listener);
                isExit.show();
            }
        });
        rlAvautar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lrvXM.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LX_UserInfoEditActivity.startUserInfoEditActivity(LX_UserInfoActivity.this,LX_UserInfoEditActivity.TYPE_NAME);
            }
        });
        lrvSFZ.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LX_UserInfoEditActivity.startUserInfoEditActivity(LX_UserInfoActivity.this,LX_UserInfoEditActivity.TYPE_SFZ);
            }
        });
        lrvXB.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionPicker picker = new OptionPicker(LX_UserInfoActivity.this, new String[]{
                        "男", "女"
                });
                picker.setCanceledOnTouchOutside(false);
                picker.setDividerRatio(WheelView.DividerConfig.FILL);
//                picker.setShadowColor(getResources().getColor(R.color.colorPrimary), 100);
                picker.setSelectedIndex(UserData.getTempUser().getBaseInfo().getSex().equals("男")?0:1);
                picker.setPressedTextColor(getResources().getColor(R.color.colorPrimary));
                picker.setDividerColor(getResources().getColor(R.color.grey_line_bg));
                picker.setTextColor(getResources().getColor(R.color.gray_5));
                picker.setCancelTextColor(getResources().getColor(R.color.gray_5));
                picker.setSubmitTextColor(getResources().getColor(R.color.gray_5));
                picker.setTopLineColor(getResources().getColor(R.color.gray_5));
                picker.setCycleDisable(true);
                picker.setTextSize(11);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        User tempUser = UserData.getTempUser();
                        if (tempUser!=null){
                            mSex=item;
                            getCallbacks().editUserInfo(tempUser.getBaseInfo().getUID(),tempUser.getBaseInfo().getName(),item,tempUser.getBaseInfo().getIDCard());
                        }
                    }
                });
                picker.show();
            }
        });
    }

    @Override
    protected BaseController getController() {
        return LArtemis.getInstance().getMainController();
    }

    public static void startUserInfoActivity(Context context) {
        context.startActivity(new Intent(context, LX_UserInfoActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User tempUser = UserData.getTempUser();
        if (tempUser!=null){
            User.UserBaseInfo baseInfo = tempUser.getBaseInfo();
            if (baseInfo!=null){
                if (!CommonUtil.isStrEmpty(baseInfo.getPhotoUrl())){
                    aivInfoHead.setImageURI(Uri.parse(baseInfo.getPhotoUrl()));
                }
                lrvXM.setItemInfo("姓名", CommonUtil.isStrEmpty(baseInfo.getName())?"":baseInfo.getName());
                lrvSFZ.setItemInfo("身份证",CommonUtil.isStrEmpty(baseInfo.getIDCard())?"":CommonUtil.getStarString(baseInfo.getIDCard(),0,baseInfo.getIDCard().length()-3));
                lrvSJHM.setItemInfo("手机号码",CommonUtil.isStrEmpty(baseInfo.getPhoneNumber())?"":CommonUtil.getStarString(baseInfo.getPhoneNumber(),0,baseInfo.getPhoneNumber().length()-3));
                lrvXB.setItemInfo("性别",CommonUtil.isStrEmpty(baseInfo.getSex())?"":baseInfo.getSex());
            }else {
                lrvXM.setItemInfo("姓名","");
                lrvSFZ.setItemInfo("身份证","");
                lrvSJHM.setItemInfo("手机号码","");
                lrvXB.setItemInfo("性别","");
            }
            lrvSJHM.showRightIcon(false);
        }
    }

    @Override
    public void editUserInfoCallBack(Result result) {
        if (CommonUtil.isNotEmpty(result)) {
            if (result.getState() <= 0) { // 表示错误
                CommonUtil.showSnackBar(aivInfoHead,result.getMsg());
            } else {
//                Toast.makeText(this,"修改成功!",Toast.LENGTH_SHORT).show();
                CommonUtil.showSnackBar(aivInfoHead,"修改成功!");
                lrvXB.setItemInfo("性别",mSex);
                UserData.getTempUser().getBaseInfo().setSex(mSex);
            }
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        CommonUtil.showSnackBar(aivInfoHead,error.getMessage());
    }
}
