package sample.ktutorial.introduction.feature.channels.contributors

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

@Serializable
data class User(val login: String, val contribution: Int)


@Serializable
data class Repo(val id: Long, val name: String)

interface GithubService {

    @GET("orgs/{org}/repos?per_page={perpage}")
    fun getOrgRepos(@Path("org") org: String, @Path("perpage") perPage: String): Call<List<Repo>>
}