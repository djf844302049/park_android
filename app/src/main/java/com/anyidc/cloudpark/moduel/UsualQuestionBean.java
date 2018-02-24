package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UsualQuestionBean {

    /**
     * question_id : 4
     * question : 如果损坏了停车设备，用户具体是怎么赔偿的呢？
     * answer : 惩罚措施：当普通停车有损坏设备时，相应的扣除100元（可灵活根据市场来定）作为惩罚（监控找到损坏的车主后A：设置赔偿金后发送二维码赔偿金额，车主支付款项；B：拒绝后，先从押金扣除部分金额，待法律手段来解决――后台操作）
     * create_time : 2018-02-09 15:04:13
     */

    private int question_id;
    private String question;
    private String answer;
    private String create_time;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
