package com.example.customviewpointerclocklib

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoField
import kotlin.math.min

class CustomViewClock @JvmOverloads constructor(
    context: Context?,
    attributeSet: AttributeSet? = null,
    i: Int = 0
) : View(context, attributeSet, i) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var mPaint: Paint
    private lateinit var minPath: Path
    private lateinit var hourPath: Path
    private lateinit var secPath: Path
    private var hourColor: Int
    private var minColor: Int
    private var secColor: Int
    private var textColor: Int
    private var dotColor: Int
    private val backgroundColor: Int
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f

    fun setTextColor(@ColorInt textColor: Int) {
        this.textColor = textColor
        dotColor = textColor
        init()
    }

    fun setHourColor(@ColorInt hourColor: Int) {
        this.hourColor = hourColor
    }

    fun setMinColor(@ColorInt minColor: Int) {
        this.minColor = minColor
    }

    fun setSecColor(@ColorInt secColor: Int) {
        this.secColor = secColor
    }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val typedArray =
            getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomViewClock)

        backgroundColor =
            typedArray.getColor(R.styleable.CustomViewClock_backgroundColor, Color.WHITE)
        hourColor = typedArray.getColor(R.styleable.CustomViewClock_hourColor, Color.BLACK)
        minColor = typedArray.getColor(R.styleable.CustomViewClock_minColor, Color.BLACK)
        secColor = typedArray.getColor(R.styleable.CustomViewClock_secColor, Color.BLACK)
        textColor = typedArray.getColor(R.styleable.CustomViewClock_textColor, Color.BLACK)
        dotColor = typedArray.getColor(R.styleable.CustomViewClock_dotColor, Color.BLACK)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (width > 0 || height > 0) {
            init()
        }
    }

    private fun init() {
        centerX = width / 2.0f
        centerY = height / 2.0f
        radius = min(width / 2.0f, height / 2.0f)


        var bottomY = width / 1.7f
        var topPadding = radius / 14.0f * 5.0f
        var width = radius / 49.0f

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = radius / 14.0f
        mPaint.color = Color.BLACK
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint)
        mPaint.setShadowLayer(
            radius / 7.0f,
            0f,
            0f,
            Color.rgb(70, 70, 70)
        )

        minPath = Path()
        minPath.moveTo(getWidth() / 2.0f, topPadding)
        minPath.addRoundRect(
            RectF(centerX - width, topPadding, centerX + width, bottomY),
            0f,
            0f,
            Path.Direction.CW
        )

        val widthHour = radius / 30.0f
        topPadding = radius / 14.0f * 6.0f
        hourPath = Path()
        hourPath.moveTo(getWidth() / 2.0f, topPadding)
        hourPath.addRoundRect(
            RectF(
                centerX - widthHour,
                topPadding,
                centerX + widthHour,
                bottomY
            ), 0f, 0f, Path.Direction.CW
        )
        bottomY = getWidth() / 1.7f
        width = radius / 91.7f
        topPadding = radius / 5.0f
        secPath = Path()
        secPath.moveTo(getWidth() / 2.0f, topPadding)
        secPath.addRoundRect(
            RectF(centerX - width, topPadding, centerX + width, bottomY),
            0f,
            0f,
            Path.Direction.CW
        )
        paint.style = Paint.Style.FILL
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        val path = Path()
        path.addCircle(centerX, centerY, radius, Path.Direction.CW)
        canvas.clipPath(path)
        paint.style = Paint.Style.FILL
        paint.color = backgroundColor

        canvas.drawCircle(centerX, centerY, radius - radius / 28.0f, mPaint)

        paint.color = hourColor
        canvas.drawCircle(centerX, centerY, radius / 25.7f, paint)
        val now = LocalDateTime.now()
        var hour = now.hour
        val minute = now.minute
        val second = now.second
        val millis = now[ChronoField.MILLI_OF_SECOND]
        hour = if (hour > 12) hour - 12 else hour
        paint.color = hourColor
        canvas.save()
        canvas.rotate(hour * 30.0f + minute / 60.0f * 30.0f, centerX, centerY)
        canvas.drawPath(hourPath, paint)
        canvas.restore()

        paint.color = minColor
        canvas.save()
        canvas.rotate(6.0f * minute + second / 60.0f * 6.0f, centerX, centerY)
        canvas.drawPath(minPath, paint)
        canvas.restore()

        paint.color = secColor
        canvas.save()
        canvas.rotate(6.0f * second + millis / 1000.0f * 6.0f, centerX, centerY)
        canvas.drawPath(secPath, paint)
        canvas.restore()

        canvas.drawCircle(centerX, centerX, radius / 45.0f, paint)
        super.onDraw(canvas)
    }


}