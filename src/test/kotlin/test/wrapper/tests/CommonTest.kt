package test.wrapper.tests

import com.kotoframework.KotoApp
import com.kotoframework.utils.toKPojo
import com.kotoframework.utils.toMap
import org.junit.jupiter.api.Test
import test.wrapper.beans.TbUser
import kotlin.test.assertEquals

/**
 * Created by ousc on 2022/8/2 10:49
 */
class CommonTest {
    init {
        KotoApp.setLog("console", true)
    }

    private val user = TbUser(
        id = 9938,
        userName = "Leinbo",
        password = "Leinbo",
        nickname = "Leinbo",
        email = "mail@leinbo.com",
        age = 18,
        birthday = "2020-08-02",
        telephone = "13888888888",
        sex = "male",
        avatar = "http://cdn.leinbo.com/avatar.png"
    )

    @Test
    fun testToKPojoAndToMap() {
        val b = user.toMap().toKPojo<TbUser>()
        val c = user.toMap()
        val d = b.toMap()
        assertEquals(
            user,
            b
        )

        assertEquals(
            c,
            d
        )
    }
}
