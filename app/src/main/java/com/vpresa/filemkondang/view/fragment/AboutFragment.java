package com.vpresa.filemkondang.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vpresa.filemkondang.R;
import com.vpresa.filemkondang.utility.AppConstant;
import com.vpresa.filemkondang.utility.CommonFunction;

/**
 * Created by alodokter-it on 10/05/17 -- AboutFragment.
 */

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageView aboutVpresa = (ImageView) view.findViewById(R.id.about_vpresa);

        CommonFunction.setImage(getContext(), AppConstant.VPRESA_LOGO_IMAGE_URL, aboutVpresa);
    }
}