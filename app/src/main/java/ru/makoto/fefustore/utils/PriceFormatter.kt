package ru.makoto.fefustore.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object PriceFormatter {
    private val formatter = DecimalFormat("#,##0.##", DecimalFormatSymbols(Locale("ru", "RU")).apply {
        groupingSeparator = ' '
        decimalSeparator = ','
    })

    fun format(priceInKopecks: Int): String {
        return formatter.format(priceInKopecks / 100.0) + " ₽"
    }
}