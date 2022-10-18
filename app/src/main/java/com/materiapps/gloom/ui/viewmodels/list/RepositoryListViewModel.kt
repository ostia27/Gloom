package com.materiapps.gloom.ui.viewmodels.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.materiapps.gloom.RepoListQuery
import com.materiapps.gloom.domain.manager.AuthManager
import com.materiapps.gloom.rest.utils.GraphQLUtils.response
import com.materiapps.gloom.rest.utils.getOrNull

class RepositoryListViewModel(
    client: ApolloClient,
    authManager: AuthManager,
    private val username: String
) : ViewModel() {

    val repos = Pager(PagingConfig(pageSize = 30)) {
        object : PagingSource<String, RepoListQuery.Node>() {
            override suspend fun load(params: LoadParams<String>): LoadResult<String, RepoListQuery.Node> {
                val page = params.key

                val response =
                    client.query(RepoListQuery(username, cursor = Optional.present(page)))
                        .response().getOrNull()

                val nextKey = response?.user?.repositories?.pageInfo?.endCursor

                val nodes = mutableListOf<RepoListQuery.Node>()
                response?.user?.repositories?.nodes?.forEach {
                    if (it != null) nodes.add(it)
                }

                return LoadResult.Page(
                    data = nodes,
                    nextKey = nextKey,
                    prevKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<String, RepoListQuery.Node>): String? =
                state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey
                }
        }
    }.flow.cachedIn(viewModelScope)

}