package ru.makoto.fefustore.Entity

data class Clothes(
    val id: Int,
    val category: String,
    val img: Int,
    val title: String,
    val description: String,
    val price: Int,
    val sizes: List<Size>,
    val longDescription: String

)

enum class Size {
    XXL,
    XS,
    S,
    M,
    L,
    XL
}