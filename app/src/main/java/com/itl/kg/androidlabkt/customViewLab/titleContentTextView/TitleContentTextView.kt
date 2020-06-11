package com.itl.kg.androidlabkt.customViewLab.titleContentTextView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.itl.kg.androidlabkt.R

/**
 *
 * Created by kenguerrilla on 2020/6/9.
 *
 */
class TitleContentTextView : ConstraintLayout {

    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView

    private var titleString: String = "title"
    private var contentString: String = "content"

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        init(context, attributeSet)
    }


    private fun init(context: Context, attributeSet: AttributeSet?) {
        View.inflate(context, R.layout.view_components_title_content_textview, this)

        // FOCUS_BLOCK_DESCENDANTS >> This view will block any of its descendants from getting focus, even if they are focusable.
        // 其子View將不會取得focus，即使其屬性為focusable
        descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS

        titleTextView = findViewById(R.id.textSectionTitle)
        contentTextView = findViewById(R.id.textSectionContent)

        titleString = "title"
        contentString = "content"

        attributeSet?.let {
            val attributes = context.theme.obtainStyledAttributes(attributeSet, R.styleable.TitleContentTextView, 0, 0)
            titleString = attributes.getString(R.styleable.TitleContentTextView_title_value)?: "Undefine"
            contentString = attributes.getString(R.styleable.TitleContentTextView_content_value)?: "Undefine"

            titleTextView.text = titleString
            contentTextView.text = contentString
        }

    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun setContent(content: String) {
        contentTextView.text = content
    }

}