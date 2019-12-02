package com.pxz.pxzutils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * 类说明：监听多个edt输入工具类
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/2 9:33
 */
public class MonitorEditTextUtil {
    private static IEditTextChangeListener mChangeListener;

    public static void setChangeListener(IEditTextChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    /**
     * 检测输入框是否都输入了内容
     * 从而改变按钮的是否可点击
     * 以及输入框后面的X的可见性，X点击删除输入框的内容
     */
    public static class textChangeListener {
        private TextView tv;
        private List<EditText> editTexts;

        public textChangeListener(TextView tv) {
            this.tv = tv;
        }

        public textChangeListener addAllEditText(List<EditText> editTexts) {
            this.editTexts = editTexts;
            initEditListener();
            return this;
        }

        private void initEditListener() {
            /*遍历edt*/
            for (EditText editText : editTexts) {
                editText.addTextChangedListener(new textChange());
            }
        }

        /**
         * edt输入的变化来改变按钮的是否点击
         */
        private class textChange implements TextWatcher {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (checkAllEdit()) {
                    /*所有edt有值*/
                    tv.setEnabled(true);
                } else {
                    /*有edt没值*/
                    tv.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        }

        /**
         * 检查所有的edt是否输入了数据
         */
        private boolean checkAllEdit() {
            for (EditText editText : editTexts) {
                if (!TextUtils.isEmpty(editText.getText() + "")) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 接口
     */
    public interface IEditTextChangeListener {
        void textChange(boolean isHashContext);
    }

    /**
     * 防止内存泄漏
     */
    public static void chear(){
        mChangeListener=null;
    }
}