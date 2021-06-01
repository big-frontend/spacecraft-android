package com.jamesfchen.network.json;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Aug/17/2019  Sat
 */
public class GithubApiForMoShi {

    /**
     * current_user_url : https://api.github.com/user
     * current_user_authorizations_html_url : https://github.com/settings/connections/applications{/client_id}
     * authorizations_url : https://api.github.com/authorizations
     * code_search_url : https://api.github.com/search/code?q={query}{&page,per_page,sort,order}
     * commit_search_url : https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}
     * emails_url : https://api.github.com/user/emails
     * emojis_url : https://api.github.com/emojis
     * events_url : https://api.github.com/events
     * feeds_url : https://api.github.com/feeds
     * followers_url : https://api.github.com/user/followers
     * following_url : https://api.github.com/user/following{/target}
     * gists_url : https://api.github.com/gists{/gist_id}
     * hub_url : https://api.github.com/hub
     * issue_search_url : https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}
     * issues_url : https://api.github.com/issues
     * keys_url : https://api.github.com/user/keys
     * notifications_url : https://api.github.com/notifications
     * organization_repositories_url : https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}
     * organization_url : https://api.github.com/orgs/{org}
     * public_gists_url : https://api.github.com/gists/public
     * rate_limit_url : https://api.github.com/rate_limit
     * repository_url : https://api.github.com/repos/{owner}/{repo}
     * repository_search_url : https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}
     * current_user_repositories_url : https://api.github.com/user/repos{?type,page,per_page,sort}
     * starred_url : https://api.github.com/user/starred{/owner}{/repo}
     * starred_gists_url : https://api.github.com/gists/starred
     * team_url : https://api.github.com/teams
     * user_url : https://api.github.com/users/{user}
     * user_organizations_url : https://api.github.com/user/orgs
     * user_repositories_url : https://api.github.com/users/{user}/repos{?type,page,per_page,sort}
     * user_search_url : https://api.github.com/search/users?q={query}{&page,per_page,sort,order}
     * titlebar : {"content":"afasdfaf","title":"afasdfaf","left_title":"afasdfaf","right_title":"afasdfaf"}
     * text : afasdfaf
     */

    public String current_user_url;
    public String current_user_authorizations_html_url;
    public String authorizations_url;
    public String code_search_url;
    public String commit_search_url;
    public String emails_url;
    public String emojis_url;
    public String events_url;
    public String feeds_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String hub_url;
    public String issue_search_url;
    public String issues_url;
    public String keys_url;
    public String notifications_url;
    public String organization_repositories_url;
    public String organization_url;
    public String public_gists_url;
    public String rate_limit_url;
    public String repository_url;
    public String repository_search_url;
    public String current_user_repositories_url;
    public String starred_url;
    public String starred_gists_url;
    public String team_url;
    public String user_url;
    public String user_organizations_url;
    public String user_repositories_url;
    public String user_search_url;
    public TitleBar titlebar;
    public String text;

    @Override
    public String toString() {
        return "GithubApiForMoShi{" +
                "current_user_url='" + current_user_url + '\'' +
                ", current_user_authorizations_html_url='" + current_user_authorizations_html_url + '\'' +
                ", authorizations_url='" + authorizations_url + '\'' +
                ", code_search_url='" + code_search_url + '\'' +
                ", commit_search_url='" + commit_search_url + '\'' +
                ", emails_url='" + emails_url + '\'' +
                ", emojis_url='" + emojis_url + '\'' +
                ", events_url='" + events_url + '\'' +
                ", feeds_url='" + feeds_url + '\'' +
                ", followers_url='" + followers_url + '\'' +
                ", following_url='" + following_url + '\'' +
                ", gists_url='" + gists_url + '\'' +
                ", hub_url='" + hub_url + '\'' +
                ", issue_search_url='" + issue_search_url + '\'' +
                ", issues_url='" + issues_url + '\'' +
                ", keys_url='" + keys_url + '\'' +
                ", notifications_url='" + notifications_url + '\'' +
                ", organization_repositories_url='" + organization_repositories_url + '\'' +
                ", organization_url='" + organization_url + '\'' +
                ", public_gists_url='" + public_gists_url + '\'' +
                ", rate_limit_url='" + rate_limit_url + '\'' +
                ", repository_url='" + repository_url + '\'' +
                ", repository_search_url='" + repository_search_url + '\'' +
                ", current_user_repositories_url='" + current_user_repositories_url + '\'' +
                ", starred_url='" + starred_url + '\'' +
                ", starred_gists_url='" + starred_gists_url + '\'' +
                ", team_url='" + team_url + '\'' +
                ", user_url='" + user_url + '\'' +
                ", user_organizations_url='" + user_organizations_url + '\'' +
                ", user_repositories_url='" + user_repositories_url + '\'' +
                ", user_search_url='" + user_search_url + '\'' +
                ", titlebar=" + titlebar +
                ", text='" + text + '\'' +
                '}';
    }
}
