import java.net.URI
import java.util.Date

data class BlogEntry(
    var title: String,
    var description: String,
    val publishTime: Date,
    val approved: Boolean?,
    val lastUpdated: Date,
    val url: URI,
    val commentCount: Int?,
    val topTags: List<String>,
    val email: String?
)