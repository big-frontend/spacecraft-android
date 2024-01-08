package com.jamesfchen.network.json

import com.squareup.moshi.Json


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/13/2019  Tue
 */
//@JsonClass(generateAdapter = true)
data class GithubApiForMoShi2(
        @Json(name = "current_user_url")
        val currentUserUrl: String, // https://api.github.com/user
        @Json(name = "current_user_authorizations_html_url")
        val currentUserAuthorizationsHtmlUrl: String, // https://github.com/settings/connections/applications{/client_id}
        @Json(name = "authorizations_url")
        val authorizationsUrl: String, // https://api.github.com/authorizations
        @Json(name = "code_search_url")
        val codeSearchUrl: String, // https://api.github.com/search/code?q={query}{&page,per_page,sort,order}
        @Json(name = "commit_search_url")
        val commitSearchUrl: String, // https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}
        @Json(name = "emails_url")
        val emailsUrl: String, // https://api.github.com/user/emails
        @Json(name = "emojis_url")
        val emojisUrl: String, // https://api.github.com/emojis
        @Json(name = "events_url")
        val eventsUrl: String, // https://api.github.com/events
        @Json(name = "feeds_url")
        val feedsUrl: String, // https://api.github.com/feeds
        @Json(name = "followers_url")
        val followersUrl: String, // https://api.github.com/user/followers
        @Json(name = "following_url")
        val followingUrl: String, // https://api.github.com/user/following{/target}
        @Json(name = "gists_url")
        val gistsUrl: String, // https://api.github.com/gists{/gist_id}
        @Json(name = "hub_url")
        val hubUrl: String, // https://api.github.com/hub
        @Json(name = "issue_search_url")
        val issueSearchUrl: String, // https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}
        @Json(name = "issues_url")
        val issuesUrl: String, // https://api.github.com/issues
        @Json(name = "keys_url")
        val keysUrl: String, // https://api.github.com/user/keys
        @Json(name = "notifications_url")
        val notificationsUrl: String, // https://api.github.com/notifications
        @Json(name = "organization_repositories_url")
        val organizationRepositoriesUrl: String, // https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}
        @Json(name = "organization_url")
        val organizationUrl: String, // https://api.github.com/orgs/{org}
        @Json(name = "public_gists_url")
        val publicGistsUrl: String, // https://api.github.com/gists/public
        @Json(name = "rate_limit_url")
        val rateLimitUrl: String, // https://api.github.com/rate_limit
        @Json(name = "repository_url")
        val repositoryUrl: String, // https://api.github.com/repos/{owner}/{repo}
        @Json(name = "repository_search_url")
        val repositorySearchUrl: String, // https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}
        @Json(name = "current_user_repositories_url")
        val currentUserRepositoriesUrl: String, // https://api.github.com/user/repos{?type,page,per_page,sort}
        @Json(name = "starred_url")
        val starredUrl: String, // https://api.github.com/user/starred{/owner}{/repo}
        @Json(name = "starred_gists_url")
        val starredGistsUrl: String, // https://api.github.com/gists/starred
        @Json(name = "team_url")
        val teamUrl: String, // https://api.github.com/teams
        @Json(name = "user_url")
        val userUrl: String, // https://api.github.com/users/{user}
        @Json(name = "user_organizations_url")
        val userOrganizationsUrl: String, // https://api.github.com/user/orgs
        @Json(name = "user_repositories_url")
        val userRepositoriesUrl: String, // https://api.github.com/users/{user}/repos{?type,page,per_page,sort}
        @Json(name = "user_search_url")
        val userSearchUrl: String, // https://api.github.com/search/users?q={query}{&page,per_page,sort,order}
        @Json(name = "titlebar")
        val titlebar: TitleBar,
        @Json(name = "text")
        val text: String // afasdfaf

) {
    data class TitleBar(var content: String, var title: String)

}
