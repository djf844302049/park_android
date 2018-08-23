package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.QuestionAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.UsualQuestionBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UsualQuestionActivity extends BaseActivity {
    private QuestionAdapter adapter;
    private List<UsualQuestionBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_usual_question;
    }

    @Override
    protected void initData() {
        initTitle("常见问题");
        RecyclerView rlvQues = findViewById(R.id.rlv_question);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rlvQues.setLayoutManager(manager);
        adapter = new QuestionAdapter(list);
        adapter.setOnItemClickListener((v, position) -> WebViewActivity.actionStart(UsualQuestionActivity.this
                , list.get(position).getUrl()));
        rlvQues.setAdapter(adapter);
        getQuestion();
    }

    private void getQuestion() {
        getTime(Api.getDefaultService().getQuestion(),
                new RxObserver<BaseEntity<List<UsualQuestionBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<UsualQuestionBean>> baseEntity) {
                        list.addAll(baseEntity.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
