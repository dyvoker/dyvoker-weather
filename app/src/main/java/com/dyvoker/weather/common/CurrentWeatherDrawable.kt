package com.dyvoker.weather.common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.dyvoker.weather.core.DarkSkyUtils
import com.dyvoker.weather.core.data.CurrentWeatherData

/**
 * Custom drawable.
 * Will draw current weather status to view or bitmap.
 */
class CurrentWeatherDrawable(
    private val context: Context,
    private var data: CurrentWeatherData
) : Drawable() {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFFFFFFFF.toInt()
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = 0xFF2A2727.toInt()
        strokeWidth = 2f
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFF2A2727.toInt()
    }
    private val temperatureTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFF2A2727.toInt()
    }
    private var icon = VectorDrawableCompat
        .create(context.resources, DarkSkyUtils.getIconId(data.icon), null)!!
    private val tail = Path()

    fun setData(newData: CurrentWeatherData) {
        data = newData
        icon = VectorDrawableCompat
            .create(context.resources, DarkSkyUtils.getIconId(data.icon), null)!!
    }

    override fun draw(canvas: Canvas) {
        val centerX = bounds.width() / 2f
        val heightPiece = bounds.height() / 12f
        // We will have 12 pieces by height.
        // --------------------------------------------
        // |       8/12 Icon + temperature.
        // --------------------------------------------
        // |       3/12 Summary.
        // --------------------------------------------
        // |       1/12 Tail.
        // --------------------------------------------

        // Draw background + tail.
        val tailTopY = heightPiece * 11f
        val tailBottomY = heightPiece * 12f
        with (tail) {
            reset()
            moveTo(0f, 0f)
            lineTo(bounds.width().toFloat(), 0f)
            lineTo(bounds.width().toFloat(), tailTopY)
            lineTo(centerX + heightPiece, tailTopY)
            lineTo(centerX, tailBottomY)
            lineTo(centerX - heightPiece, tailTopY)
            lineTo(0f, tailTopY)
            close()
        }
        canvas.drawPath(tail, backgroundPaint)
        canvas.drawPath(tail, strokePaint)

        // TODO Draw add button.

        // Draw icon and temperature.
        val padding = px(8f)
        val iconSize = (heightPiece * 4f).toInt()
        val temperatureText = "${data.temperature.toCelsiusInt()}°C"
        temperatureTextPaint.textSize = heightPiece * 3f
        val temperatureWidth = temperatureTextPaint.measureText(temperatureText)
        val totalWidth = iconSize + padding + temperatureWidth
        val halfWidth = totalWidth / 2f
        val halfHeight = heightPiece * 4f
        val iconYShift = heightPiece * 2f
        canvas.save()
        canvas.translate(centerX - halfWidth, iconYShift)
        icon.setBounds(0, 0, iconSize, iconSize)
        icon.draw(canvas)
        canvas.translate(
            iconSize + padding,
            -iconYShift + halfHeight + temperatureTextPaint.textSize / 2f
        )
        canvas.drawText(
            temperatureText,
            0f, -temperatureTextPaint.textSize * 0.18f,
            temperatureTextPaint
        )
        canvas.restore()

        // Draw summary.
        val bottomBlockHeight = heightPiece * 6f
        textPaint.textSize = bottomBlockHeight / 2f
        val text = data.summary
        var textWidth = textPaint.measureText(text)
        if (textWidth > bounds.width() - 20) {
            // Rare case.
            textPaint.textSize = textPaint.textSize / 2f
            textWidth = textPaint.measureText(text)
        }
        val bottomBlockCenterY = heightPiece * 5f + bottomBlockHeight / 2f
        val textY = bottomBlockCenterY + textPaint.textSize / 2f
        canvas.drawText(text, centerX - textWidth / 2f, textY, textPaint)
    }

    private fun px(dp: Float) = dp * context.resources.displayMetrics.density

    override fun setAlpha(alpha: Int) {}
    override fun getOpacity(): Int = PixelFormat.OPAQUE
    override fun setColorFilter(colorFilter: ColorFilter?) {}
}