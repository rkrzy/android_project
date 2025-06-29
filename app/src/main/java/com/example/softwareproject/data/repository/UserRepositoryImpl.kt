package com.example.softwareproject.data.repository

import android.util.Log
import com.example.softwareproject.com.example.softwareproject.data.dto.user.BaekjoonInfoDto
import com.example.softwareproject.data.dto.user.GitHubInfoDto
import com.example.softwareproject.data.dto.user.UserAbilityDto
import com.example.softwareproject.data.dto.user.UserBattleLogDto
import com.example.softwareproject.data.dto.user.UserDto
import com.example.softwareproject.data.nosql_entity.GithubInfo
import com.example.softwareproject.data.nosql_entity.User
import com.example.softwareproject.data.nosql_entity.UserAbility
import com.example.softwareproject.data.nosql_entity.UserBattleLog
import com.example.softwareproject.data.remote.user.UserFullInfo
import com.example.softwareproject.data.remote.user.UserSaveInfo
import com.example.softwareproject.domain.repository.UserRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.softwareproject.com.example.softwareproject.module.BaekjoonApi as BaekjoonApi1

class UserRepositoryImpl @Inject constructor(
    private val fireBaseStore : FirebaseFirestore,
    private val baekjoonApi: BaekjoonApi1
) : UserRepository{

    override suspend fun createUser(user: UserDto) {
        try {
            fireBaseStore.collection("user")
                .document(user.userId)
                .set(user)
                .await()
        } catch (e: Exception) {
            Log.e("Repository", "createUser failed: ${e.message}")
        }
    }

    override suspend fun createUserAbility(userAbility: UserAbilityDto) {
        try {
            fireBaseStore.collection("user_ability")
                .document(userAbility.userId)
                .set(userAbility)
                .await()
        } catch (e: Exception) {
            Log.e("Repository", "createUserAbility failed: ${e.message}")
        }
    }

    override suspend fun createUserBattleLog(userBattleLogDto: UserBattleLogDto) {
        try {
            fireBaseStore.collection("user_battle_log")
                .document(userBattleLogDto.userId)
                .set(userBattleLogDto)
                .await()
        } catch (e: Exception) {
            Log.e("Repository", "createUserBattleLogDto failed: ${e.message}")
        }
    }

    override suspend fun createUserGithubInfo(githubInfo: GitHubInfoDto) {
        try {
            fireBaseStore.collection("github_info")
                .document(githubInfo.userId)
                .set(githubInfo)
                .await()
        } catch (e: Exception) {
            Log.e("Repository", "createUserGithubInfo failed: ${e.message}")
        }
    }
    override suspend fun createUserBaekjoonInfo(baekjoonInfo: BaekjoonInfoDto){
        try {
            fireBaseStore.collection("baekjoon_info")
                .document(baekjoonInfo.userId)
                .set(baekjoonInfo)
                .await()
        } catch (e: Exception) {
            Log.e("Repository", "createUserBaekjoonInfo failed: ${e.message}")
        }
    }

    override suspend fun saveBaekjoonInfo(userId: String, solvedAcHandle: String) {
        val allProblems = mutableListOf<String>()
        var page = 1
        var totalCount = 0

        while (true) {
            val response = baekjoonApi.getSolvedProblemByTag("s@$solvedAcHandle", page)

            // 첫 페이지일 때 총 문제 수 저장
            if (page == 1) {
                totalCount = response.count
            }

            val currentProblems = response.items.map { it.problemId.toString() }
            allProblems.addAll(currentProblems)

            // 더 이상 읽을 게 없거나 전부 읽었으면 종료
            if (currentProblems.isEmpty() || allProblems.size >= totalCount) break

            page++
        }

        val dto = BaekjoonInfoDto(
            userId = userId,
            baekjoonId = solvedAcHandle,
            count = totalCount,
            items = allProblems
        )

        fireBaseStore.collection("baekjoon_info")
            .document(userId)
            .set(dto)
            .await()
    }
    override suspend fun getUserInfo(userId: String): UserDto? {
        return try {
            val snapshot = fireBaseStore.collection("user")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(UserDto::class.java)
        } catch (e: Exception) {
            Log.e("Repository", "getUserInfo failed: ${e.message}")
            null
        }
    }


    override suspend fun getUserAbilityInfo(userId: String): UserAbilityDto? {
        return try {
            val snapshot = fireBaseStore.collection("user_ability")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(UserAbilityDto::class.java)
        } catch (e: Exception) {
            Log.e("Repository", "getUserAbilityInfo failed: ${e.message}")
            null
        }
    }

    override suspend fun getUserBattleLogInfo(userId: String): UserBattleLogDto? {
        return try {
            val snapshot = fireBaseStore.collection("user_battle_Log")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(UserBattleLogDto::class.java)
        } catch (e: Exception) {
            Log.e("Repository", "getUserBattleLogInfo failed: ${e.message}")
            null
        }
    }



    override suspend fun getUSerGithubInfo(userId: String): GitHubInfoDto? {
        return try {
            val snapshot = fireBaseStore.collection("github_info")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(GitHubInfoDto::class.java)
        } catch (e: Exception) {
            Log.e("Repository", "getUserGithubInfo failed: ${e.message}")
            null
        }
    }

    override suspend fun getUserGithubInfoByFirebaseUid(firebaseUid: String): GitHubInfoDto? {
        return try {
            val snapshot = fireBaseStore.collection("github_info")
                .whereEqualTo("firebaseUid", firebaseUid)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(GitHubInfoDto::class.java)
        } catch (e: Exception) {
            Log.e("Repository", "getUserGithubInfo failed: ${e.message}")
            null
        }
    }



    override suspend fun getUserFullInfo(userId: String) : UserFullInfo {
        val userQuery = fireBaseStore.collection("user")
            .whereEqualTo("userId", userId).get().await()
        val githubQuery = fireBaseStore.collection("github_info")
            .whereEqualTo("userId", userId).get().await()
        val abilityQuery = fireBaseStore.collection("user_ability")
            .whereEqualTo("userId", userId).get().await()
        val battleQuery = fireBaseStore.collection("user_battle_log")
            .whereEqualTo("userId", userId).get().await()

        if (userQuery.isEmpty || githubQuery.isEmpty || abilityQuery.isEmpty || battleQuery.isEmpty) {
            throw Exception("유저 정보 중 일부가 존재하지 않습니다.")
        }

        val user = userQuery.documents.first().toObject(User::class.java)!!
        val github = githubQuery.documents.first().toObject(GithubInfo::class.java)!!
        val ability = abilityQuery.documents.first().toObject(UserAbility::class.java)!!
        val battle = battleQuery.documents.first().toObject(UserBattleLog::class.java)!!

        return UserFullInfo(user, github, ability, battle)
    }

    override suspend fun createUserInfo(userSaveInfo: UserSaveInfo){
        val userData = User(
            userId = userSaveInfo.userId,
            createdAt = Timestamp.now(),
            updatedAt = Timestamp.now(),
        )

        val userAbility = UserAbility(
            userId = userSaveInfo.userId,
            exp = 0,
            level = 1,
            hp = 10,
            attack = 10,
            shield = 10,
        )

        val userBattleLog = UserBattleLog(
            userId = userSaveInfo.userId,
            match = 0,
            win = 0,
            lose = 0,
            rate = 0.0
        )
        val githubInfo = GithubInfo(
            userId = userSaveInfo.userId,
            avatarUrl = userSaveInfo.avatarUrl,
            githubName = userSaveInfo.name,
            email = userSaveInfo.email,
            bio = userSaveInfo.bio,
            followers = userSaveInfo.followings,
            following = userSaveInfo.followings,
            firebaseUid = userSaveInfo.firebaseUid
        )
        fireBaseStore.collection("user").document(userSaveInfo.userId).set(userData).await()
        fireBaseStore.collection("user_ability").document(userSaveInfo.userId).set(userAbility).await()
        fireBaseStore.collection("user_battle_Log").document(userSaveInfo.userId).set(userBattleLog).await()
        fireBaseStore.collection("github_info").document(userSaveInfo.userId).set(githubInfo).await()
    }

    override suspend fun isUserExists(userId: String): Boolean {
        val doc = fireBaseStore
            .collection("user")
            .document(userId)
            .get()
            .await()
        return doc.exists()
    }

    override suspend fun getUserInfoByGithubId(userId: String): UserFullInfo {
        return withContext(Dispatchers.IO) {

            // github_info: userId 기준으로 검색
            val githubQuery = fireBaseStore.collection("github_info")
                .whereEqualTo("userId", userId)
                .limit(1)
                .get()
                .await()

            val githubDoc = githubQuery.documents.firstOrNull()
                ?: throw Exception("해당 GitHub ID의 사용자 정보가 없습니다.")

            val githubInfo = githubDoc.toObject(GithubInfo::class.java)
                ?: throw Exception("GitHubInfo 변환 실패")

            // user: userId 필드 기준 검색
            val userQuery = fireBaseStore.collection("user")
                .whereEqualTo("userId", userId)
                .limit(1)
                .get()
                .await()

            val user = userQuery.documents.firstOrNull()
                ?.toObject(User::class.java)
                ?: throw Exception("User 정보 없음")

            // user_ability: userId 필드 기준 검색
            val abilityQuery = fireBaseStore.collection("user_ability")
                .whereEqualTo("userId", userId)
                .limit(1)
                .get()
                .await()

            val ability = abilityQuery.documents.firstOrNull()
                ?.toObject(UserAbility::class.java)
                ?: UserAbility(userId) // 기본값으로 fallback

            // user_battle_log: userId 필드 기준 검색
            val battleQuery = fireBaseStore.collection("user_battle_Log")
                .whereEqualTo("userId", userId)
                .limit(1)
                .get()
                .await()


            val battleLog = battleQuery.documents.firstOrNull()
                ?.toObject(UserBattleLog::class.java)
                ?: UserBattleLog(userId)
            Log.d("DEBUG", "battleQuery documents size = $userId")
            Log.d("DEBUG", "battleQuery documents size = ${battleQuery.documents.size}")
            Log.d("DEBUG", "battleQuery docs = ${battleQuery.documents.joinToString { it.id }}")

            return@withContext UserFullInfo(
                user = user,
                githubInfo = githubInfo,
                userAbility = ability,
                userBattleLog = battleLog
            )
        }
    }


    override suspend fun getUserBaekjoonInfoByUserId(userId: String): BaekjoonInfoDto? {
        return try {
            val snapshot = fireBaseStore.collection("baekjoon_info")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.toObject(BaekjoonInfoDto::class.java)
        } catch (e: Exception) {
            Log.e("UserRepository", "Baekjoon 정보 불러오기 실패: ${e.message}")
            null
        }
    }

    override suspend fun updateBaekjoonInfo(baekjoonInfoDto: BaekjoonInfoDto) {
        fireBaseStore.collection("baekjoon_info")
            .document(baekjoonInfoDto.userId)
            .set(baekjoonInfoDto) // 덮어쓰기
            .await()
    }
    override suspend fun updateBattleLogInfo(userBattleLogDto: UserBattleLogDto) {
        try {
            val snapshot = fireBaseStore.collection("user_battle_Log")
                .whereEqualTo("userId", userBattleLogDto.userId)
                .get()
                .await()

            val doc = snapshot.documents.firstOrNull()
            if (doc != null) {
                fireBaseStore.collection("user_battle_Log")
                    .document(doc.id)
                    .set(userBattleLogDto)
                    .await()
            } else {
                // 문서가 없으면 새로 생성 (선택 사항)
                fireBaseStore.collection("user_battle_Log")
                    .add(userBattleLogDto)
                    .await()
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "updateBattleLogInfo 실패: ${e.message}")
        }
    }

    override suspend fun updateUserAbilityInfo(userAbility: UserAbilityDto) {
        try {
            val snapshot = fireBaseStore.collection("user_ability")
                .whereEqualTo("userId", userAbility.userId)
                .get()
                .await()

            val doc = snapshot.documents.firstOrNull()
            if (doc != null) {
                fireBaseStore.collection("user_ability")
                    .document(doc.id)
                    .set(userAbility)
                    .await()
            } else {
                // 문서가 없으면 새로 생성 (선택 사항)
                fireBaseStore.collection("user_ability")
                    .add(userAbility)
                    .await()
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "updateUserAbilityInfo 실패: ${e.message}")
        }
    }


}