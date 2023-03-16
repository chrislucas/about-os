package com.example.declarativerestclient.feature.declarativerestclient.githubapi.http.client

import com.example.declarativerestclient.feature.declarativerestclient.githubapi.models.GithubRelease
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import java.net.http.HttpResponse

@HttpExchange(
    url = "\${github.base-api}", accept = [MediaType.APPLICATION_JSON_VALUE]
)
interface ReleaseRepositoryClient {

    @GetExchange("/repos/\${github.organization}/\${github.repo}/releases")
    fun fetchOrgReleases(): HttpResponse<List<GithubRelease>?>

    @GetExchange("/repos/{owner}/{repo}/releases")
    fun fetchReleases(
        @PathVariable("owner") owner: String,
        @PathVariable("repo") repo: String
    ): HttpResponse<List<GithubRelease>?>

    @GetExchange("/repos/{owner}/{repo}/release/{id}}")
    fun fetchReleasesById(
        @PathVariable("owner") owner: String,
        @PathVariable("repo") repo: String,
        @PathVariable("id") id: String
    ): HttpResponse<List<GithubRelease>?>
}