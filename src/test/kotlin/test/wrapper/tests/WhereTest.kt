package test.wrapper.tests

import com.kotoframework.KotoApp
import test.wrapper.beans.UserInfo
import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.condition.*
import com.kotoframework.core.where.Where
import com.kotoframework.definition._l
import com.kotoframework.utils.deleted
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class WhereTest : KPojo {
    private val searchData = UserInfo(
        userName = "ousc",
        active = true,
        sex = "male",
        age = 99
    )

    init {
        KotoApp.setLog("console")
    }

    @Test
    fun testBasicWhere() {
        val (prepared) = Where(searchData)
        val expectedParamMap = mutableMapOf(
            "id" to null,
            "userName" to "ousc",
            "nickName" to null,
            "telephone" to null,
            "emailAddress" to null,
            "active" to true,
            "birthday" to null,
            "sex" to "male",
            "habit" to null,
            "age" to 99,
            "roles" to null
        )
        assertEquals(
            "${deleted()} && `sex` = :sex && `active` = :active && `user_name` = :userName && `age` = :age",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }


    @Test
    fun testMapFunc() { // 测试覆盖参数功能
        val (prepared) = Where(searchData).map("active" to false)
        val expectedParamMap = mutableMapOf(
            "id" to null,
            "userName" to "ousc",
            "nickName" to null,
            "telephone" to null,
            "emailAddress" to null,
            "active" to false,
            "birthday" to null,
            "sex" to "male",
            "habit" to null,
            "age" to 99,
            "roles" to null
        )
        assertEquals(
            "${deleted()} && `sex` = :sex && `active` = :active && `user_name` = :userName && `age` = :age",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }


    @Test
    fun testDeletedFunc() { // 测试查询已删除功能
        val (prepared) = Where(searchData).deleted()
        val expectedParamMap = mutableMapOf(
            "id" to null,
            "userName" to "ousc",
            "nickName" to null,
            "telephone" to null,
            "emailAddress" to null,
            "active" to true,
            "birthday" to null,
            "sex" to "male",
            "habit" to null,
            "age" to 99,
            "roles" to null
        )
        assertEquals(
            "${deleted(1)} && `sex` = :sex && `active` = :active && `user_name` = :userName && `age` = :age",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }

    @Test
    fun testSuffixFunc() { // 测试后缀功能
        val (prepared) = Where(searchData).suffix("group by sex")
        val expectedParamMap = mutableMapOf(
            "id" to null,
            "userName" to "ousc",
            "nickName" to null,
            "telephone" to null,
            "emailAddress" to null,
            "active" to true,
            "birthday" to null,
            "sex" to "male",
            "habit" to null,
            "age" to 99,
            "roles" to null
        )
        assertEquals(
            "${deleted()} && `sex` = :sex && `active` = :active && `user_name` = :userName && `age` = :age group by sex",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }

    @Test //测试复杂功能
    fun testComplexFunc() {
        val user = UserInfo(
            userName = "ousc",
            nickName = "daiyue",
            telephone = "13800138000",
            emailAddress = "hangzhou@qq.com",
            roles = "admin, user",
            habit = "eat",
            birthday = "2020-01-01",
            active = true,
            sex = "male",
            age = 99
        )
        val (prepared) = Where(user) {
            it.userName.eq &&
                    it.active.eq &&
                    it.nickName.matchLeft &&
                    it.telephone.neq &&
                    "length" > 123 &&
                    it.age between 10..20 &&
                    it.age > 10 &&
                    it.birthday between "2020-01-01".."2020-01-02" &&
                    it.roles in _l["admin", "user"] &&
                    it.emailAddress.isNull &&
                    it.habit.notNull &&
                    "DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday".asSql() &&
                    when (searchData.sex) {
                        "male" -> it.habit == "basketball"
                        "female" -> it.habit == "soccer"
                        else -> true
                    } &&
                    "`sex` is not null".asSql() &&
                    "`age` < 50".asSql()
        }

        val expectedParamMap = mutableMapOf(
            "active" to true,
            "emailAddress" to "hangzhou@qq.com",
            "habit" to "eat",
            "id" to null,
            "nickName" to "%daiyue",
            "roles" to listOf("admin", "user"),
            "sex" to "male",
            "telephone" to "13800138000",
            "userName" to "ousc",
            "ageMin" to 10,
            "ageMax" to 20,
            "qtyMin" to 10,
            "weightMax" to 100,
            "heightMin" to 175,
            "lengthMax" to 175,
            "depthMin" to 10.5,
            "depthMax" to 20.3,
            "birthdayMin" to "2020-01-01",
            "birthdayMax" to "2020-01-02"
        )
        assertEquals(
            "${deleted()} && `user_name` = :userName && `active` = :active && `nick_name` like :nickName && `telephone` != :telephone && `age` between :ageMin && :ageMax && `qty` > :qtyMin && `weight` < :weightMax && `height` >= :heightMin && `length` <= :lengthMax && `depth` between :depthMin && :depthMax && `birthday` between :birthdayMin && :birthdayMax && `roles` in (:roles) && `email_address` is null && `habit` is not null && DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday && `height` > 175 && `sex` is not null && `age` < 50",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }

    @Test
    fun testUsageForOr() {
        val user = UserInfo(
            userName = "ousc",
            nickName = "daiyue",
            telephone = "13800138000",
            emailAddress = "hangzhou@qq.com",
            roles = "admin,user",
            habit = "eat",
            birthday = "2020-01-01",
            active = true,
            sex = "male",
            age = 99
        )

        val expectedParamMap = mutableMapOf(
            "id" to null,
            "userName" to "ousc",
            "nickName" to "daiyue",
            "telephone" to "13800138000",
            "emailAddress" to "hangzhou@qq.com",
            "roles" to listOf("admin", "user"),
            "habit" to "eat",
            "birthday" to "2020-01-01",
            "active" to true,
            "sex" to "male",
            "age" to 99,
            "ageMax" to 99
        )
        val (prepared) = Where(user) {
            ("userName".eq || "nickName".eq) &&
                    ("telephone".eq || "emailAddress".eq) &&
                    ("emailAddress".eq || "roles" in listOf("admin", "user")) &&
                    ("habit".eq || "active".eq) &&
                    ("birthday".eq || "age".lt) &&
                    "srq is not null".asSql()
        }

        assertEquals(
            "${deleted()} && (`user_name` = :userName || `nick_name` = :nickName) && (`telephone` = :telephone || `email_address` = :emailAddress) && (`email_address` = :emailAddress || `roles` in (:roles)) && (`habit` = :habit || `active` = :active) && (`birthday` = :birthday || `age` < :ageMax) && srq is not null",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }
}
