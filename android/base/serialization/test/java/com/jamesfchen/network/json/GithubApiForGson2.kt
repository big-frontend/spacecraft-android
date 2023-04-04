package com.jamesfchen.network.json

import com.google.gson.annotations.SerializedName


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/13/2019  Tue
 */
data class GithubApiForGson2(
        @SerializedName("current_user_url")
        val currentUserUrl: String, // https://api.github.com/user
        @SerializedName("current_user_authorizations_html_url")
        val currentUserAuthorizationsHtmlUrl: String, // https://github.com/settings/connections/applications{/client_id}
        @SerializedName("authorizations_url")
        val authorizationsUrl: String, // https://api.github.com/authorizations
        @SerializedName("code_search_url")
        val codeSearchUrl: String, // https://api.github.com/search/code?q={query}{&page,per_page,sort,order}
        @SerializedName("commit_search_url")
        val commitSearchUrl: String, // https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}
        @SerializedName("emails_url")
        val emailsUrl: String, // https://api.github.com/user/emails
        @SerializedName("emojis_url")
        val emojisUrl: String, // https://api.github.com/emojis
        @SerializedName("events_url")
        val eventsUrl: String, // https://api.github.com/events
        @SerializedName("feeds_url")
        val feedsUrl: String, // https://api.github.com/feeds
        @SerializedName("followers_url")
        val followersUrl: String, // https://api.github.com/user/followers
        @SerializedName("following_url")
        val followingUrl: String, // https://api.github.com/user/following{/target}
        @SerializedName("gists_url")
        val gistsUrl: String, // https://api.github.com/gists{/gist_id}
        @SerializedName("hub_url")
        val hubUrl: String, // https://api.github.com/hub
        @SerializedName("issue_search_url")
        val issueSearchUrl: String, // https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}
        @SerializedName("issues_url")
        val issuesUrl: String, // https://api.github.com/issues
        @SerializedName("keys_url")
        val keysUrl: String, // https://api.github.com/user/keys
        @SerializedName("notifications_url")
        val notificationsUrl: String, // https://api.github.com/notifications
        @SerializedName("organization_repositories_url")
        val organizationRepositoriesUrl: String, // https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}
        @SerializedName("organization_url")
        val organizationUrl: String, // https://api.github.com/orgs/{org}
        @SerializedName("public_gists_url")
        val publicGistsUrl: String, // https://api.github.com/gists/public
        @SerializedName("rate_limit_url")
        val rateLimitUrl: String, // https://api.github.com/rate_limit
        @SerializedName("repository_url")
        val repositoryUrl: String, // https://api.github.com/repos/{owner}/{repo}
        @SerializedName("repository_search_url")
        val repositorySearchUrl: String, // https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}
        @SerializedName("current_user_repositories_url")
        val currentUserRepositoriesUrl: String, // https://api.github.com/user/repos{?type,page,per_page,sort}
        @SerializedName("starred_url")
        val starredUrl: String, // https://api.github.com/user/starred{/owner}{/repo}
        @SerializedName("starred_gists_url")
        val starredGistsUrl: String, // https://api.github.com/gists/starred
        @SerializedName("team_url")
        val teamUrl: String, // https://api.github.com/teams
        @SerializedName("user_url")
        val userUrl: String, // https://api.github.com/users/{user}
        @SerializedName("user_organizations_url")
        val userOrganizationsUrl: String, // https://api.github.com/user/orgs
        @SerializedName("user_repositories_url")
        val userRepositoriesUrl: String, // https://api.github.com/users/{user}/repos{?type,page,per_page,sort}
        @SerializedName("user_search_url")
        val userSearchUrl: String // https://api.github.com/search/users?q={query}{&page,per_page,sort,order}
)