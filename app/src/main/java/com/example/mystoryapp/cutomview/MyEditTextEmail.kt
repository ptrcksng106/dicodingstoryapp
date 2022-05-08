package com.example.mystoryapp.cutomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.mystoryapp.R

class MyEditButtonEmail : AppCompatEditText {
    private lateinit var warningButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }


    private fun init() {
        warningButtonImage =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_warning) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(password: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(password: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (password.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(password).matches()) {
                    setButtonDrawables(endOfTheText = warningButtonImage)
                    error = "Email yang kamu masukkan tidak sesuai"
                } else {
                    setButtonDrawables()
                }
            }

            override fun afterTextChanged(password: Editable) {
            }

        })
    }
}