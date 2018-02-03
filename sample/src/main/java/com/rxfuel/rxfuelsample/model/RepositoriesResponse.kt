package com.rxfuel.rxfuelsample.model

/**
 * Created by salah on 3/2/18.
 */

data class RepositoriesResponse(
		val total_count: Int, //2
		val incomplete_results: Boolean, //false
		val items: List<Repo>
)

data class Repo(
		val id: Int, //119365558
		val name: String, //rxfuel.github.io
		val full_name: String, //rxfuel/rxfuel.github.io
		val owner: Owner,
		val private: Boolean, //false
		val html_url: String, //https://github.com/rxfuel/rxfuel.github.io
		val description: String, //RxFuel website
		val fork: Boolean, //false
		val url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io
		val forks_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/forks
		val keys_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/keys{/key_id}
		val collaborators_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/collaborators{/collaborator}
		val teams_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/teams
		val hooks_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/hooks
		val issue_events_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/issues/events{/number}
		val events_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/events
		val assignees_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/assignees{/user}
		val branches_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/branches{/branch}
		val tags_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/tags
		val blobs_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/git/blobs{/sha}
		val git_tags_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/git/tags{/sha}
		val git_refs_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/git/refs{/sha}
		val trees_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/git/trees{/sha}
		val statuses_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/statuses/{sha}
		val languages_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/languages
		val stargazers_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/stargazers
		val contributors_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/contributors
		val subscribers_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/subscribers
		val subscription_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/subscription
		val commits_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/commits{/sha}
		val git_commits_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/git/commits{/sha}
		val comments_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/comments{/number}
		val issue_comment_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/issues/comments{/number}
		val contents_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/contents/{+path}
		val compare_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/compare/{base}...{head}
		val merges_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/merges
		val archive_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/{archive_format}{/ref}
		val downloads_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/downloads
		val issues_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/issues{/number}
		val pulls_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/pulls{/number}
		val milestones_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/milestones{/number}
		val notifications_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/notifications{?since,all,participating}
		val labels_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/labels{/name}
		val releases_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/releases{/id}
		val deployments_url: String, //https://api.github.com/repos/rxfuel/rxfuel.github.io/deployments
		val created_at: String, //2018-01-29T10:05:42Z
		val updated_at: String, //2018-01-29T10:07:28Z
		val pushed_at: String, //2018-01-29T10:07:27Z
		val git_url: String, //git://github.com/rxfuel/rxfuel.github.io.git
		val ssh_url: String, //git@github.com:rxfuel/rxfuel.github.io.git
		val clone_url: String, //https://github.com/rxfuel/rxfuel.github.io.git
		val svn_url: String, //https://github.com/rxfuel/rxfuel.github.io
		val homepage: Any, //null
		val size: Int, //0
		val stargazers_count: Int, //0
		val watchers_count: Int, //0
		val language: String, //HTML
		val has_issues: Boolean, //true
		val has_projects: Boolean, //true
		val has_downloads: Boolean, //true
		val has_wiki: Boolean, //true
		val has_pages: Boolean, //true
		val forks_count: Int, //0
		val mirror_url: Any, //null
		val archived: Boolean, //false
		val open_issues_count: Int, //0
		val license: Any, //null
		val forks: Int, //0
		val open_issues: Int, //0
		val watchers: Int, //0
		val default_branch: String, //master
		val score: Double //7.947754
)

data class Owner(
		val login: String, //rxfuel
		val id: Int, //35918240
		val avatar_url: String, //https://avatars1.githubusercontent.com/u/35918240?v=4
		val gravatar_id: String,
		val url: String, //https://api.github.com/users/rxfuel
		val html_url: String, //https://github.com/rxfuel
		val followers_url: String, //https://api.github.com/users/rxfuel/followers
		val following_url: String, //https://api.github.com/users/rxfuel/following{/other_user}
		val gists_url: String, //https://api.github.com/users/rxfuel/gists{/gist_id}
		val starred_url: String, //https://api.github.com/users/rxfuel/starred{/owner}{/repo}
		val subscriptions_url: String, //https://api.github.com/users/rxfuel/subscriptions
		val organizations_url: String, //https://api.github.com/users/rxfuel/orgs
		val repos_url: String, //https://api.github.com/users/rxfuel/repos
		val events_url: String, //https://api.github.com/users/rxfuel/events{/privacy}
		val received_events_url: String, //https://api.github.com/users/rxfuel/received_events
		val type: String, //Organization
		val site_admin: Boolean //false
)