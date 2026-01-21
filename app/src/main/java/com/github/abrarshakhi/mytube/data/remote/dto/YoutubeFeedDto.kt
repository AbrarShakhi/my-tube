package com.github.abrarshakhi.mytube.data.remote.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "feed")
data class YoutubeFeedDto(
    @param:Element(name = "title")
    val title: String?,

    @param:Element(name = "entry")
    val entries: List<YoutubeVideoEntryDto>?
)

@Xml(name = "entry")
data class YoutubeVideoEntryDto(
    @param:Element(name = "yt:videoId")
    val videoId: String?,

    @param:Element(name = "title")
    val title: String?,

    @param:Element(name = "published")
    val published: String?,

    @param:Element(name = "link")
    val links: List<VideoLinkDto>?,

    @param:Element(name = "media:group")
    val mediaGroup: MediaGroupDto?
)

@Xml(name = "link")
data class VideoLinkDto(
    @param:Attribute(name = "href")
    val href: String?,

    @param:Attribute(name = "rel")
    val rel: String?
)

@Xml(name = "media:group")
data class MediaGroupDto(
    @param:Element(name = "media:thumbnail")
    val thumbnails: List<MediaThumbnailDto>?
)

@Xml(name = "media:thumbnail")
data class MediaThumbnailDto(
    @param:Attribute(name = "url")
    val url: String?
)
