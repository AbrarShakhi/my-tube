package com.github.abrarshakhi.mytube.data.remote.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "feed")
data class YoutubeFeed(

    @field:JacksonXmlProperty(localName = "title")
    val title: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "entry")
    val entries: List<YoutubeVideoEntry>? = null
)

@JacksonXmlRootElement(localName = "entry")
data class YoutubeVideoEntry(

    @field:JacksonXmlProperty(localName = "id")
    val id: String? = null,

    @field:JacksonXmlProperty(localName = "title")
    val title: String? = null,

    @field:JacksonXmlProperty(localName = "published")
    val published: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "link")
    val links: List<VideoLink>? = null,

    @field:JacksonXmlProperty(localName = "group", namespace = "http://search.yahoo.com/mrss/")
    val mediaGroup: MediaGroup? = null
) {
    fun videoUrl(): String? =
        links?.firstOrNull { it.rel == "alternate" }?.href
            ?: id?.substringAfter("yt:video:")?.let {
                "https://www.youtube.com/watch?v=$it"
            }
}

data class VideoLink(

    @field:JacksonXmlProperty(isAttribute = true, localName = "href")
    val href: String? = null,

    @field:JacksonXmlProperty(isAttribute = true, localName = "rel")
    val rel: String? = null
)

data class MediaGroup(

    @field:JacksonXmlProperty(localName = "thumbnail", namespace = "http://search.yahoo.com/mrss/")
    val thumbnail: MediaThumbnail? = null
)

data class MediaThumbnail(

    @field:JacksonXmlProperty(isAttribute = true, localName = "url")
    val url: String? = null
)