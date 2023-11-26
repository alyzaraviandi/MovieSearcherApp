package com.dicoding.movieapp.responses

import com.google.gson.annotations.SerializedName

data class OverviewDetailResponses(

	@field:SerializedName("certificates")
	val certificates: Certificates? = null,

	@field:SerializedName("releaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("ratings")
	val ratings: Ratings? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("plotOutline")
	val plotOutline: PlotOutline? = null,

	@field:SerializedName("plotSummary")
	val plotSummary: PlotSummary? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: Title? = null
)

data class Ratings(

	@field:SerializedName("canRate")
	val canRate: Boolean? = null,

	@field:SerializedName("otherRanks")
	val otherRanks: List<OtherRanksItem?>? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("ratingCount")
	val ratingCount: Int? = null
)

data class OtherRanksItem(

	@field:SerializedName("rankType")
	val rankType: String? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null
)

data class PlotOutline(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)

data class Certificates(

	@field:SerializedName("US")
	val uS: List<USItem?>? = null
)

data class Title(

	@field:SerializedName("numberOfEpisodes")
	val numberOfEpisodes: Int? = null,

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("runningTimeInMinutes")
	val runningTimeInMinutes: Int? = null,

	@field:SerializedName("seriesStartYear")
	val seriesStartYear: Int? = null,

	@field:SerializedName("titleType")
	val titleType: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("@type")
	val type: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("nextEpisode")
	val nextEpisode: String? = null,

	@field:SerializedName("seriesEndYear")
	val seriesEndYear: Int? = null
)

data class PlotSummary(

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)

data class USItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("certificate")
	val certificate: String? = null,

	@field:SerializedName("attributes")
	val attributes: List<String?>? = null
)
