package test.wrapper.tests

import com.kotoframework.KotoApp
import test.wrapper.beans.UserInfo
import com.kotoframework.interfaces.KPojo
import com.kotoframework.core.condition.*
import com.kotoframework.core.where.Where
import com.kotoframework.utils.Common.deleted
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
            "${deleted()} and `sex` = :sex and `active` = :active and `user_name` = :userName and `age` = :age",
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
            "${deleted()} and `sex` = :sex and `active` = :active and `user_name` = :userName and `age` = :age",
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
            "${deleted(1)} and `sex` = :sex and `active` = :active and `user_name` = :userName and `age` = :age",
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
            "${deleted()} and `sex` = :sex and `active` = :active and `user_name` = :userName and `age` = :age group by sex",
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
            roles = listOf("admin", "user"),
            habit = "eat",
            birthday = "2020-01-01",
            active = true,
            sex = "male",
            age = 99
        )
        val (prepared) = Where(user) {
            it::userName.eq() and
                    it::active.eq() and
                    it::nickName.like().matchLeft() and
                    "telephone".notEq() and
                    it::age.between(10..20) and
                    "qty".gt(10) and
                    "weight".lt(100) and
                    "height".ge(175) and
                    "length".le(175) and
                    "depth".between(10.5..20.3) and
                    "birthday".between("2020-01-01".."2020-01-02") and
                    "roles".isIn(listOf("admin", "user")) and
                    "emailAddress".isNull() and
                    it::habit.notNull() and
                    "DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday" and
                    when (searchData.sex) {
                        "male" -> "`height` > 175"
                        "female" -> "`height` > 165"
                        else -> null
                    } and
                    "`sex` is not null" and
                    "`age` < 50"
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
            "${deleted()} and `user_name` = :userName and `active` = :active and `nick_name` like :nickName and `telephone` != :telephone and `age` between :ageMin and :ageMax and `qty` > :qtyMin and `weight` < :weightMax and `height` >= :heightMin and `length` <= :lengthMax and `depth` between :depthMin and :depthMax and `birthday` between :birthdayMin and :birthdayMax and `roles` in (:roles) and `email_address` is null and `habit` is not null and DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday and `height` > 175 and `sex` is not null and `age` < 50",
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
            roles = listOf("admin", "user"),
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
            ("userName".eq() or "nickName".eq()) and
                    ("telephone".eq() or "emailAddress".eq()) and
                    ("emailAddress".eq() or "roles".isIn(listOf("admin", "user"))) and
                    ("habit".eq() or "active".eq()) and
                    ("birthday".eq() or "age".lt()) and
                    "srq is not null"
        }

        assertEquals(
            "${deleted()} and (`user_name` = :userName or `nick_name` = :nickName) and (`telephone` = :telephone or `email_address` = :emailAddress) and (`email_address` = :emailAddress or `roles` in (:roles)) and (`habit` = :habit or `active` = :active) and (`birthday` = :birthday or `age` < :ageMax) and srq is not null",
            prepared.sql.trim()
        )
        assertEquals(
            expectedParamMap, prepared.paramMap
        )
    }
}
