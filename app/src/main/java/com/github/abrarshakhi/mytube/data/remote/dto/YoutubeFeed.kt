package com.github.abrarshakhi.mytube.data.remote.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Root
import com.tickaroo.tikxml.annotation.Attribute

@Root(name = "feed")
data class YoutubeFeed(

    @PropertyElement(name = "title")
    val title: String?,

    @Element(name = "entry")
    val entries: List<YoutubeVideoEntry>?
)

@Root(name = "entry")
data class YoutubeVideoEntry(

    @PropertyElement(name = "id")
    val id: String?,

    @PropertyElement(name = "title")
    val title: String?,

    @PropertyElement(name = "published")
    val published: String?,

    @Element(name = "link")
    val links: List<VideoLink>?,

    @Element(name = "group")
    val mediaGroup: MediaGroup?
) {
    fun videoUrl(): String? = links?.firstOrNull { it.rel == "alternate" }
            ?.href
            ?: id?.substringAfter("yt:video:")?.let {
                "https://www.youtube.com/watch?v=$it"
            }
}


@Root(name = "link")
data class VideoLink(

    @Attribute(name = "href")
    val href: String?,

    @Attribute(name = "rel")
    val rel: String?
)


@Root(name = "group")
data class MediaGroup(

    @Element(name = "thumbnail")
    val thumbnail: MediaThumbnail?
)

@Root(name = "thumbnail")
data class MediaThumbnail(

    @Attribute(name = "url")
    val url: String?
)
