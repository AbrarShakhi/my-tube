package com.github.abrarshakhi.mytube.data.remote.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "feed")
data class YoutubeFeedDto(

    @field:JacksonXmlProperty(localName = "title")
    val title: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "entry")
    val entries: List<YoutubeVideoEntryDto>? = null
)

@JacksonXmlRootElement(localName = "entry")
data class YoutubeVideoEntryDto(

    @field:JacksonXmlProperty(localName = "id")
    val id: String? = null,

    @field:JacksonXmlProperty(localName = "title")
    val title: String? = null,

    @field:JacksonXmlProperty(localName = "published")
    val published: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "link")
    val links: List<VideoLinkDto>? = null,

    @field:JacksonXmlProperty(localName = "group", namespace = "http://search.yahoo.com/mrss/")
    val mediaGroup: MediaGroupDto? = null
)

data class VideoLinkDto(

    @field:JacksonXmlProperty(isAttribute = true, localName = "href")
    val href: String? = null,

    @field:JacksonXmlProperty(isAttribute = true, localName = "rel")
    val rel: String? = null
)

data class MediaGroupDto(

    @field:JacksonXmlProperty(localName = "thumbnail", namespace = "http://search.yahoo.com/mrss/")
    val thumbnail: MediaThumbnailDto? = null
)

data class MediaThumbnailDto(

    @field:JacksonXmlProperty(isAttribute = true, localName = "url")
    val url: String? = null
)