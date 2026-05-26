package com.example.arsitekturmvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT COUNT(*) FROM users")
    fun observeUserCount(): Flow<Int>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query(
        """
        SELECT * FROM users
        WHERE username = :username AND password = :password
        LIMIT 1
        """
    )
    fun observeUserByCredentials(username: String, password: String): Flow<UserEntity?>
}
