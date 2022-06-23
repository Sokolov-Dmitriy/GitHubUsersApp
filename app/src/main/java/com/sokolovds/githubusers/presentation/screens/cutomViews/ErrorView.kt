package com.sokolovds.githubusers.presentation.screens.cutomViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.sokolovds.githubusers.R
import com.sokolovds.githubusers.databinding.ErrorViewBinding

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ErrorViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.error_view, this, true)
        binding = ErrorViewBinding.bind(this)
        setupAttrs(attrs, defStyleAttr, defStyleRes)
    }

    fun setOnClickListener(block: () -> Unit) {
        binding.btn.setOnClickListener { block() }
    }

    var errorText: String
        set(value) {
            binding.errorMsg.text = value
        }
        get() = binding.errorMsg.text.toString()

    var errorMsgIsVisible: Boolean
        set(value) {
            binding.errorMsg.isVisible = value
        }
        get() = binding.errorMsg.isVisible

    var btnIsVisible: Boolean
        set(value) {
            binding.btn.isVisible = value
        }
        get() = binding.btn.isVisible

    private fun setupAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes)
        orientation = VERTICAL
        setupErrorMsg(typedArray)
        setupBtn(typedArray)
        setupSpace(typedArray)
        typedArray.recycle()

    }

    private fun setupErrorMsg(typedArray: TypedArray) = with(binding) {
        errorMsg.text = typedArray.getString(R.styleable.ErrorView_errorText) ?: ERROR_TEXT
        errorMsg.textSize =
            typedArray.getDimension(R.styleable.ErrorView_errorTextSize, ERROR_TEXT_SIZE)
        errorMsg.setTextColor(
            typedArray.getColor(
                R.styleable.ErrorView_errorTextColor,
                ERROR_TEXT_COLOR
            )
        )
    }

    private fun setupBtn(typedArray: TypedArray) = with(binding) {
        btn.text = typedArray.getString(R.styleable.ErrorView_buttonText) ?: BTN_TEXT
        btn.textSize =
            typedArray.getDimension(R.styleable.ErrorView_buttonTextSize, BTN_TEXT_SIZE)
        btn.setTextColor(
            typedArray.getColor(
                R.styleable.ErrorView_buttonTextColor,
                BTN_TEXT_COLOR
            )
        )
    }

    private fun setupSpace(typedArray: TypedArray) = with(binding.space) {
        layoutParams.width = SPACE_WIDTH
        layoutParams.height =
            typedArray.getDimensionPixelOffset(
                R.styleable.ErrorView_spaceSize,
                SPACE_HEIGHT
            )
        requestLayout()
    }


    companion object {
        private const val ERROR_TEXT = "Error!"
        private const val ERROR_TEXT_SIZE = 14f
        private const val ERROR_TEXT_COLOR = Color.BLACK

        private const val BTN_TEXT = "Try again!"
        private const val BTN_TEXT_SIZE = 14f
        private const val BTN_TEXT_COLOR = Color.BLACK

        private const val SPACE_WIDTH = 0
        private const val SPACE_HEIGHT = 32
    }

}