package com.lenovohit.hospitalgroup.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.lenovohit.hospitalgroup.R;
import com.lenovohit.lartemis_api.annotation.ContentView;
import com.lenovohit.lartemis_api.base.BaseController;
import com.lenovohit.lartemis_api.base.CoreFragment;
import com.lenovohit.lartemis_api.core.LArtemis;
import com.lenovohit.lartemis_api.ui.controller.MainController;

/**
 * Created by yuzhijun on 2017/6/29.
 */
@ContentView(R.layout.lx_app_practical_fragment)
public class PraticalityFragment extends CoreFragment<MainController.MainUiCallbacks> implements MainController.MainUi{

    public static PraticalityFragment newInstance() {
        return new PraticalityFragment();
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        isShowToolBar(true);
        setCenterTitle("实用");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void handleArguments(Bundle arguments) {

    }

    @Override
    protected BaseController getController() {
        return  LArtemis.getInstance().getMainController();
    }

}
