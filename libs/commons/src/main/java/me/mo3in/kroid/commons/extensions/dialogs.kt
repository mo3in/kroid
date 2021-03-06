package me.mo3in.kroid.commons.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import me.mo3in.kroid.commons.widgets.dialogs.AlertBuilder
import me.mo3in.kroid.commons.widgets.dialogs.AndroidAlertBuilder

typealias AlertBuilderFactory<D> = (Context) -> AlertBuilder<D>


fun <D : DialogInterface> Context.alert(factory: AlertBuilderFactory<D>, message: String, title: String? = null, init: (AlertBuilder<D>.() -> Unit)? = null): AlertBuilder<D> {
    return factory(this).apply {
        if (title != null) {
            this.title = title
        }
        this.message = message
        if (init != null) init()
    }
}

fun <D : DialogInterface> Context.alert(factory: AlertBuilderFactory<D>, messageResource: Int, titleResource: Int? = null, init: (AlertBuilder<D>.() -> Unit)? = null): AlertBuilder<D> {
    return factory(this).apply {
        if (titleResource != null) {
            this.titleResource = titleResource
        }
        this.messageResource = messageResource
        if (init != null) init()
    }
}

fun <D : DialogInterface> Context.alert(factory: AlertBuilderFactory<D>, init: AlertBuilder<D>.() -> Unit): AlertBuilder<D> = factory(this).apply { init() }


fun Context.alert(message: CharSequence, title: CharSequence? = null, init: (AlertBuilder<DialogInterface>.() -> Unit)? = null): AlertBuilder<AlertDialog> {
    return AndroidAlertBuilder(this).apply {
        if (title != null) {
            this.title = title
        }
        this.message = message
        if (init != null) init()
    }
}

fun Context.alert(messageResource: Int, titleResource: Int? = null, init: (AlertBuilder<DialogInterface>.() -> Unit)? = null): AlertBuilder<DialogInterface> {
    return AndroidAlertBuilder(this).apply {
        if (titleResource != null) {
            this.titleResource = titleResource
        }
        this.messageResource = messageResource
        if (init != null) init()
    }
}


fun Context.alert(init: AlertBuilder<DialogInterface>.() -> Unit): AlertBuilder<DialogInterface> = AndroidAlertBuilder(this).apply { init() }


fun <D : DialogInterface> Context.selector(factory: AlertBuilderFactory<D>, title: CharSequence? = null, items: List<CharSequence>, onClick: (DialogInterface, CharSequence, Int) -> Unit) {
    with(factory(this)) {
        if (title != null) {
            this.title = title
        }
        items(items, onClick)
        show()
    }
}


fun Context.selector(title: CharSequence? = null, items: List<CharSequence>, onClick: (DialogInterface, Int) -> Unit) {
    with(AndroidAlertBuilder(this)) {
        if (title != null) {
            this.title = title
        }
        items(items, onClick)
        show()
    }
}

// for fragments

fun Fragment.selector(
        title: CharSequence? = null,
        items: List<CharSequence>,
        onClick: (DialogInterface, Int) -> Unit
): Unit = requireActivity().selector(title, items, onClick)

fun Fragment.alert(
        message: String,
        title: String? = null,
        init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = requireActivity().alert(message, title, init)

fun Fragment.alert(
        message: Int,
        title: Int? = null,
        init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = requireActivity().alert(message, title, init)

fun Fragment.alert(init: AlertBuilder<DialogInterface>.() -> Unit) = requireActivity().alert(init)