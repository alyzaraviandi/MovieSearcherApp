package com.dicoding.movieapp.responses

import com.google.gson.annotations.SerializedName

data class FindResponse(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("@type")
	val type: String? = null,

	@field:SerializedName("query")
	val query: String? = null,

	@field:SerializedName("@meta")
	val meta: Meta? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class PrincipalsItem(

	@field:SerializedName("characters")
	val characters: List<String?>? = null,

	@field:SerializedName("legacyNameText")
	val legacyNameText: String? = null,

	@field:SerializedName("episodeCount")
	val episodeCount: Int? = null,

	@field:SerializedName("roles")
	val roles: List<RolesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("startYear")
	val startYear: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("endYear")
	val endYear: Int? = null,

	@field:SerializedName("disambiguation")
	val disambiguation: String? = null,

	@field:SerializedName("billing")
	val billing: Int? = null,

	@field:SerializedName("attr")
	val attr: List<String?>? = null
)

data class Image(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class ResultsItem(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("titleType")
	val titleType: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("principals")
	val principals: List<PrincipalsItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("runningTimeInMinutes")
	val runningTimeInMinutes: Int? = null,

	@field:SerializedName("numberOfEpisodes")
	val numberOfEpisodes: Int? = null,

	@field:SerializedName("seriesStartYear")
	val seriesStartYear: Int? = null,

	@field:SerializedName("nextEpisode")
	val nextEpisode: String? = null,

	@field:SerializedName("seriesEndYear")
	val seriesEndYear: Int? = null
)

data class RolesItem(

	@field:SerializedName("character")
	val character: String? = null,

	@field:SerializedName("characterId")
	val characterId: String? = null
)

data class Meta(

	@field:SerializedName("requestId")
	val requestId: String? = null,

	@field:SerializedName("serviceTimeMs")
	val serviceTimeMs: Any? = null,

	@field:SerializedName("operation")
	val operation: String? = null
)
