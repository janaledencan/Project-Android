package hr.ferit.janaledencan.myurbandictionary

data class RDictionary(
    val author: String = "",
    val current_vote: String = "",
    var defid: String? = null,
    val definition: String = "",
    val example: String = "",
    val permalink: String = "",
    val thumbs_down: Int? = null,
    val thumbs_up: Int? = null,
    val word: String = "",
    var written_on: String = ""
)