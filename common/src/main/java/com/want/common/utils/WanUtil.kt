package com.want.common.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import com.want.common.BaseApplication
import com.want.common.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

/**
 * @author jhb
 * @date 2020/10/23
 */
enum class LogEnum {
    VERBOSE, DEBUG, INFO, WARN, ERROR
}

fun String.log(logEnum: LogEnum = LogEnum.ERROR) {
    if (BaseApplication.isDebug()) {
        when (logEnum) {
            LogEnum.VERBOSE -> Log.v("czf", this)
            LogEnum.DEBUG -> Log.d("czf", this)
            LogEnum.INFO -> Log.i("czf", this)
            LogEnum.WARN -> Log.w("czf", this)
            LogEnum.ERROR -> Log.e("czf", this)
        }
    }
}

fun String.logWithTag(tag: String, logEnum: LogEnum = LogEnum.ERROR) {
    if (BaseApplication.isDebug()) {
        when (logEnum) {
            LogEnum.VERBOSE -> Log.v(tag, this)
            LogEnum.DEBUG -> Log.d(tag, this)
            LogEnum.INFO -> Log.i(tag, this)
            LogEnum.WARN -> Log.w(tag, this)
            LogEnum.ERROR -> Log.e(tag, this)
        }
    }
}


fun String.showToast() {
    ToastUtils.showShort(this)
}

fun Int.getDrawable() = ActivityCompat.getDrawable(BaseApplication.getContext(), this)

fun Int.getResString() = BaseApplication.getContext().getString(this)

fun <T> Observable<T>.subIoObsMain(observer: Observer<T>) {
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)
}

fun Int.delay(runnable: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(runnable, this.toLong())
}

fun Int.getResDimen() = BaseApplication.getContext().resources.getDimension(this)

fun Int.getResDrawable() = ContextCompat.getDrawable(BaseApplication.getContext(), this)

fun Int.getResColor() = ContextCompat.getColor(BaseApplication.getContext(), this)

fun Int.delay(action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ action.invoke() }, this.toLong())
}

fun Any?.isNull() = this == null

fun Any?.isNotNull() = !isNull()

fun Boolean?.truely() = this != null && this

fun Boolean?.falsely() = !truely()

private val mColors = arrayOf(
        R.color.colorBlue.getResColor(),
        R.color.colorBlueLight.getResColor(),
        R.color.colorBlueLighter.getResColor(),
        R.color.colorBlueDark.getResColor(),
        R.color.colorCyan.getResColor(),
        R.color.colorRed.getResColor(),
        R.color.colorRedDark.getResColor(),
        R.color.colorPink.getResColor(),
        R.color.colorPurple.getResColor(),
        R.color.colorPurpleDark.getResColor(),
        R.color.colorOrigin.getResColor(),
        R.color.colorOriginDark.getResColor(),
        R.color.colorYellowDark.getResColor(),
        R.color.colorYellowDarker.getResColor(),
        R.color.colorGreen.getResColor(),
        R.color.colorGreenLight.getResColor(),
        R.color.colorGreenDark.getResColor(),
        R.color.colorGreenDarker.getResColor(),
        R.color.colorBrown.getResColor(),
        R.color.colorBrownLight.getResColor()
)

fun randomInt(size: Int) = Random.nextInt(0, size)

fun getRandomColor() = mColors[randomInt(mColors.size)]

