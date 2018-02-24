package com.anyidc.cloudpark.activity;

import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UsualQuestionActivity extends BaseActivity {
    private TextView tvQuestion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_usual_question;
    }

    @Override
    protected void initData() {
        initTitle("常见问题");
        tvQuestion = findViewById(R.id.tv_question);
        getQuestion();
    }

    private void getQuestion() {
        getTime(Api.getDefaultService().getQuestion(),
                new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {

                    }
                });
    }
}
