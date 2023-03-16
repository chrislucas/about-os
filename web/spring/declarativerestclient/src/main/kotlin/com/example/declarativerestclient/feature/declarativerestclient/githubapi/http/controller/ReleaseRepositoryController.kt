package com.example.declarativerestclient.feature.declarativerestclient.githubapi.http.controller

import com.example.declarativerestclient.feature.declarativerestclient.githubapi.http.client.config.BindWebClientToDeclarativeHttpClient
import com.example.declarativerestclient.feature.declarativerestclient.githubapi.models.GithubRelease

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.net.http.HttpResponse


@RestController
class ReleaseRepositoryController {

    val releasesApiClient =  BindWebClientToDeclarativeHttpClient.bind()

    @GetMapping("/releases-org-http", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun fetchOrgReleases(): HttpResponse<List<GithubRelease>?> =
        releasesApiClient.fetchOrgReleases()

    @GetMapping("/{owner}/{repo}/releases", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun fetchReleases(
        @PathVariable("owner") owner: String,
        @PathVariable("repo") repo: String
    ): HttpResponse<List<GithubRelease>?> =
        releasesApiClient.fetchReleases(owner, repo)


    @GetMapping("/{owner}/{repo}/releases/{id}", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun fetchReleasesById(
        @PathVariable("owner") owner: String,
        @PathVariable("repo") repo: String,
        @PathVariable("id") id: String
    ): HttpResponse<List<GithubRelease>?> =
        releasesApiClient.fetchReleasesById(owner, repo, id)

    @GetMapping("/test")
    fun test() = "test"
}