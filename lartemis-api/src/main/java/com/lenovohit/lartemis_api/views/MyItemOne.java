package com.lenovohit.lartemis_api.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovohit.lartemis_api.R;


/**
 * 自定义Item样式一
 * 作者：LinHao
 * 邮箱：439224@qq.com
 * 创建时间：2016/7/5 10:28
 */
public class MyItemOne extends LinearLayout {

    private ImageView ivRowOneICO;
    private TextView txRowOneContent;
    private TextView txRowOneSelect;

    public MyItemOne(Context context) {
        super(context);
    }

    public MyItemOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.lx_list_row_one, this);

        ivRowOneICO = (ImageView) findViewById(R.id.ivRowOneICO);
        txRowOneContent = (TextView) findViewById(R.id.txRowOneContent);
        txRowOneSelect = (TextView) findViewById(R.id.txRowOneSelect);
    }


    public void setItemInfo(int resid,String content,String selectName){
        if (resid == 0){
            ivRowOneICO.setVisibility(GONE);
        }else{
            ivRowOneICO.setVisibility(VISIBLE);
            ivRowOneICO.setImageResource(resid);
        }

        if (content.equals("")){
            txRowOneContent.setVisibility(GONE);
        }else{
            txRowOneContent.setVisibility(VISIBLE);
            txRowOneContent.setText(content);
        }

        if (selectName.equals("")){
            txRowOneSelect.setVisibility(GONE);
        }else{
            txRowOneSelect.setVisibility(VISIBLE);
            txRowOneSelect.setText(selectName);
        }
    }

}
