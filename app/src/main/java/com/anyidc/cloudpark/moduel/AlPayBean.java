package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/4/10.
 */

public class AlPayBean {
    /**
     * callback : alipay_sdk=alipay-sdk-php-20161101&app_id=2018041002530050&biz_content=%7B%22body%22%3A%22%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22subject%22%3A+%22%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A+%222018041109573552362%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%22extend_params%22%3A%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fpark.deyuelou.top%2Fapi%2Fv1.notify%2Falipay&sign_type=RSA2&timestamp=2018-04-11+09%3A57%3A35&version=1.0&sign=eY%2Bc%2FN5CGoZQdXkckUNwbLC8bOs%2FUPawMkDQTTsvllfYwKlz2E2WlL3COPh9aUm7opgYULrZ%2B95XvlA0SgONhN7bTaAx38bK%2FMCKsYU2vhLUNYOxgM2fTFRGHKmholFwhdgAfeegwxMjcTIK4HVqqy4jL3zhi1pIAWvw3%2BGhu4XkzIPAJk%2FsSaCwZgqCLYLlTUsZ3jChDJA0wRr%2BLdhKgUFsSlxBO8zNQ%2FI4scptRchIqaPh%2FY6HL6BGr1kE5x%2BSl%2BEHJBNKv4IFAIeH5AxPc%2BkFDvavb5z38LEmYDx0gQmUTSV8kHSL7WdqtKVAQpPgj7AGaAfo0AnKq%2FxAlkac8A%3D%3D
     */

    private String callback;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
