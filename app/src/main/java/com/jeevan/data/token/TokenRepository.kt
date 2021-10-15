package com.jeevan.data.token

import com.jeevan.data.token.source.TokenDataSource
import com.jeevan.fragment.Workspace_token
import javax.inject.Inject

class TokenRepository @Inject constructor(private val tokenDataSource: TokenDataSource) {
    suspend fun getTokenData(tokenId: String): Workspace_token {
        return tokenDataSource.getTokenData(tokenId)
    }
}