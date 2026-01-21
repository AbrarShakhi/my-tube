package com.github.abrarshakhi.mytube.data.remote.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "feed")
data class YoutubeFeedDto(
    @Element(name = "title")
    val title: String?,

    @Element(name = "entry")
    val entries: List<YoutubeVideoEntryDto>?
)

@Xml(name = "entry")
data class YoutubeVideoEntryDto(
    @Element(name = "yt:videoId")
    val videoId: String?,

    @Element(name = "title")
    val title: String?,

    @Element(name = "published")
    val published: String?,

    @Element(name = "link")
    val links: List<VideoLinkDto>?,

    @Element(name = "media:group")
    val mediaGroup: MediaGroupDto?
)

@Xml(name = "link")
data class VideoLinkDto(
    @Attribute(name = "href")
    val href: String?,

    @Attribute(name = "rel")
    val rel: String?
)

@Xml(name = "media:group")
data class MediaGroupDto(
    @Element(name = "media:thumbnail")
    val thumbnails: List<MediaThumbnailDto>?
)

@Xml(name = "media:thumbnail")
data class MediaThumbnailDto(
    @Attribute(name = "url")
    val url: String?
)
