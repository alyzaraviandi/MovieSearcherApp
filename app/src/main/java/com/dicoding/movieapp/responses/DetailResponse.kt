package com.dicoding.movieapp.responses

import com.google.gson.annotations.SerializedName

data class DetailResponse(

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
