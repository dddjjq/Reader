package com.welson.reader.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.welson.reader.R;
import com.welson.reader.activity.MainActivity;
import com.welson.reader.constant.Constants;
import com.welson.reader.util.SharedPreferenceUtil;

public class GenderSelectFragment extends DialogFragment implements View.OnClickListener{

    private static final String TAG = "GenderSelectFragment";
    private View view;
    private ImageView genderClose;
    private Button maleBtn;
    private Button femaleBtn;
    private MainActivity activity;
    private boolean isMale = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(R.style.GenderAnim);
        view = inflater.inflate(R.layout.fragment_gender_select, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        init();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG,"dismiss");
        SharedPreferenceUtil.putBoolean(getContext(), Constants.SP_IS_SELECT_GENDER,true);
        SharedPreferenceUtil.putBoolean(getContext(), Constants.SP_GENDER_IS_MALE,isMale);
        activity.firstLoadData(isMale);
    }

    private void initView(){
        genderClose = view.findViewById(R.id.gender_close_image);
        maleBtn = view.findViewById(R.id.gender_male_button);
        femaleBtn = view.findViewById(R.id.gender_female_button);
        genderClose.setOnClickListener(this);
        maleBtn.setOnClickListener(this);
        femaleBtn.setOnClickListener(this);
    }

    private void init(){
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setDimAmount(0.0f);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.START|Gravity.END;
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        activity = (MainActivity)getContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gender_close_image:
                getDialog().dismiss();
                break;
            case R.id.gender_male_button:
                isMale = true;
                getDialog().dismiss();
                break;
            case R.id.gender_female_button:
                isMale = false;
                getDialog().dismiss();
                break;
        }
    }
}
