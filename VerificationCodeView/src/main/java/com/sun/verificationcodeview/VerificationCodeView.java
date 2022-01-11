package com.sun.verificationcodeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class VerificationCodeView extends LinearLayout implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    private Context mContext;

    private int mInputNumber;
    private int mEditTextWidth;
    private int mInputTextColor;
    private float mInputTextSize;
    private int mEditTextBackground;
    private int mSpacing;
    private boolean isBisect;
    private int mBisectSpacing;
    private InputType mInputType;
    private boolean isCursorVisible;
    private int mCursorColor;
    private int mViewWidth;
    private OnCodeFinishListener codeFinishListener;

    public VerificationCodeView(Context context) {
        super(context);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);
        mInputNumber = a.getInteger(R.styleable.VerificationCodeView_inputNumber, 4);
        mEditTextWidth = a.getInteger(R.styleable.VerificationCodeView_editTextWidth, 190);
        mInputType = InputType.values()[a.getInteger(R.styleable.VerificationCodeView_android_inputType, InputType.TEXT.ordinal())];
        mInputTextColor = a.getDimensionPixelSize(R.styleable.VerificationCodeView_inputTextColor, Color.BLACK);
        mInputTextSize = a.getDimensionPixelSize(R.styleable.VerificationCodeView_inputTextSize, 16);
        mEditTextBackground = a.getResourceId(R.styleable.VerificationCodeView_editTextBackground, R.drawable.edt_background);
        mCursorColor = a.getResourceId(R.styleable.VerificationCodeView_editTextCursor, R.drawable.edt_cursor);
        isCursorVisible = a.getBoolean(R.styleable.VerificationCodeView_isCursorVisible, true);
        isBisect = a.hasValue(R.styleable.VerificationCodeView_spacing);

        if (isBisect) {
            mSpacing = a.getDimensionPixelSize(R.styleable.VerificationCodeView_spacing, 0);
        }

        initView();
        a.recycle();
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setInputNumber(int mInputNumber) {
        this.mInputNumber = mInputNumber;
    }

    public void setInputTextColor(int mInputTextColor) {
        this.mInputTextColor = mInputTextColor;
    }

    public void setInputTextSize(float mInputTextSize) {
        this.mInputTextSize = mInputTextSize;
    }

    public void setEditViewBackground(int mEditViewBackground) {
        this.mEditTextBackground = mEditViewBackground;
    }

    public void setSpacing(int mSpacing) {
        this.mSpacing = mSpacing;
    }

    public void setBisect(boolean bisect) {
        isBisect = bisect;
    }

    public void setBisectSpacing(int mBisectSpacing) {
        this.mBisectSpacing = mBisectSpacing;
    }

    public void setInputType(InputType mInputType) {
        this.mInputType = mInputType;
    }

    public void setCursorVisible(boolean cursorVisible) {
        isCursorVisible = cursorVisible;
    }

    public void setCursorStyle(int mCursorStyle) {
        this.mCursorColor = mCursorStyle;
    }

    public void setViewWidth(int mWidth) {
        this.mEditTextWidth = mWidth;
    }

    public void setOnCodeFinishListener(OnCodeFinishListener codeFinishListener) {
        this.codeFinishListener = codeFinishListener;
    }

    private void initView() {
        for (int i = 0; i < mInputNumber; i++) {
            EditText editText = new EditText(mContext);
            editText.setFocusable(true);
            initEditText(editText, i);
            addView(editText);
            if (i == 0) {
                editText.setFocusable(true);
                editText.setCursorVisible(true);
            }
        }
    }

    private void initEditText(EditText editText, int i) {
        editText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        editText.setGravity(Gravity.CENTER);
        editText.setId(i);
        editText.setCursorVisible(false);
        editText.setMaxEms(1);
        editText.setTextColor(mInputTextColor);
        editText.setTextSize(mInputTextSize);
        editText.setCursorVisible(isCursorVisible);
        editText.setTextColor(mCursorColor);
        editText.setMaxLines(1);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        switch (mInputType) {
            case TEXT:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                break;
            case PASSWORD:
                editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        }
        editText.setPadding(0, 0, 0, 0);
        editText.setOnKeyListener(this);
        editText.setBackgroundResource(mEditTextBackground);
        setEditTextCursorColor(editText);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
    }

    public void setEditTextCursorColor(EditText editText) {
        if (isCursorVisible) {
            try {
                @SuppressLint("SoonBlockedPrivateApi") Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
                field.setAccessible(true);
                field.set(editText, mCursorColor);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private LinearLayout.LayoutParams getInputLayoutParams(int i) {
        LinearLayout.LayoutParams lp = new LayoutParams(mEditTextWidth, mEditTextWidth);
        if (!isBisect) {
            mBisectSpacing = (mViewWidth - mEditTextWidth * mInputNumber) / (mInputNumber + 1);
            if (i == 0) {
                lp.leftMargin = mBisectSpacing;
                lp.rightMargin = mBisectSpacing / 2;
            } else if (i == mInputNumber - 1) {
                lp.leftMargin = mBisectSpacing / 2;
                lp.rightMargin = mBisectSpacing;
            } else {
                lp.leftMargin = mBisectSpacing / 2;
                lp.rightMargin = mBisectSpacing / 2;
            }
        } else {
            lp.leftMargin = mSpacing / 2;
            lp.rightMargin = mSpacing / 2;
        }

        lp.gravity = Gravity.CENTER;
        return lp;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
            focus();
        }
        if (codeFinishListener != null) {
            codeFinishListener.onTextChange(this, getCode());
            EditText editText = (EditText) getChildAt(mInputNumber - 1);
            if (editText.getText().length() > 0) {
                codeFinishListener.onComplete(this, getCode());
            }
        }
    }

    //获取输入的验证码
    private String getCode() {
        StringBuffer buffer = new StringBuffer();
        EditText editText;
        for (int i = 0; i < mInputNumber; i++) {
            editText = (EditText) getChildAt(i);
            buffer.append(editText.getText());
        }
        return buffer.toString();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            focus();
        }
    }

    private void focus() {
        int count = getChildCount();
        EditText editText;
        for (int i = 0; i < count; i++) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() < 1) {
                if (isCursorVisible) {
                    editText.setCursorVisible(true);
                } else {
                    editText.setCursorVisible(false);
                }
                editText.requestFocus();
                return;
            } else {
                editText.setCursorVisible(false);
                if (i == count - 1) {
                    editText.requestFocus();
                }
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
            deleteInput();
        }
        return false;
    }


    @Override
    public void setEnabled(boolean enabled) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    private void deleteInput() {
        EditText editText;
        for (int i = mInputNumber - 1; i >= 0; i--) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() >= 1) {
                editText.setText("");
                if (isCursorVisible) {
                    editText.setCursorVisible(true);
                } else {
                    editText.setCursorVisible(false);
                }
                editText.requestFocus();
                return;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        setEditTextMargin();
    }

    private void setEditTextMargin() {
        for (int i = 0; i < mInputNumber; i++) {
            EditText editText = (EditText) getChildAt(i);
            editText.setLayoutParams(getInputLayoutParams(i));
        }
    }

    public enum InputType {
        TEXT,
        PASSWORD
    }

    public interface OnCodeFinishListener {

        void onTextChange(View view, String content);

        void onComplete(View view, String content);
    }
}
