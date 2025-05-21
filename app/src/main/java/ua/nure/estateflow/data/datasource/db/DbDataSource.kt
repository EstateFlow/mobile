package ua.nure.estateflow.data.datasource.db

import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.local.AppDb

interface DbDataSource {
    val dbFlow: Flow<AppDb>
    val db: AppDb
}