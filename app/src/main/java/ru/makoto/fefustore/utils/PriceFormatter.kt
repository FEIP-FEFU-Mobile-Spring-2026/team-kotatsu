package ru.makoto.fefustore.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object PriceFormatter {
    private val formatter: DecimalFormat by lazy {
        val symbols = DecimalFormatSymbols.getInstance(Locale("ru", "RU")).apply {
            groupingSeparator = ' '
            decimalSeparator = ','
        }
        DecimalFormat("#,###", symbols).apply {
            isGroupingUsed = true
        }
    }
    fun format(price: Int): String {
        return "${formatter.format(price)} ₽"
    }
}