# ⛓️Continuous table query

In koto, use the `associate` function to connect multiple tables to query data. This chapter will use the following entity classes as examples of how to use the join table function:

```kotlin
@SoftDelete
data class TbUser(
    val id: Int? = null,
    val userName: String? = null,
    val password: String? = null,
    val nickname: String? = null,
    @Column("phone_number") val telephone: String? = null,
    val avatar: String? = null
) : KPojo

@SoftDelete
data class TbGoodCategory (
    val id: Int? = null, // id
    val name: String? = null, // name
): KPojo

@SoftDelete
data class TbGood (
    val id: Int? = null, // id
    val categoryId: Int? = null, // category_id
    val name: String? = null, // name
    val keywords: String? = null, // keywords
    val picture: String? = null, // picture
    val description: String? = null, // description
    val price: String? = null, // price
    val score: String? = null, // score
): KPojo

@SoftDelete
data class TbShoppingCart(
    val id: Int? = null, // id
    val userId: Int? = null, // user_id
    val goodId: Int? = null, // good_id
    val qty: Int? = null, // qty
) : KPojo
```

> Limited by the syntax of kotlin generics, currently koto only supports connecting up to 10 tables at a time

Koto makes join table query easier:

## ` .associate()` select multiple tables

You can select multiple tables by passing in KPojo or just specifying the type:

```kotlin
//Pass in the KPojo entity class
associate(
    TbUser(), TbGoodCategory(), TbGood(), TbShoppingCart()
)
// or use generic writing:
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
```



## `.on()` function to connect multiple tables

> Note: put the joined table behind

```kotlin
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
  .on{user, goodCategory, good, shoppingCart -> //You can name it yourself
    user::id.eq(shoppingCart::userId) and
    good::categoryId.eq(goodCategory::id) and
    good::id.eq(shoppingCart::goodId)
  }
```



## `.left()|.right()|.inner()|.cross()` select the connection direction

There are four connection methods: LeftJoin, RightJoin, InnerJoin, CrossJoin

Koto uses left connection by default, switch the connection method by calling `.right()|.inner()|.cross()`



## `.select()|.addFields` select query

If the select function is not used, all fields of all tables are selected by default

koto automatically aliases fields and adds "@" after conflicting columns to avoid duplication.

```kotlin
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
  .on{user, goodCategory, good, shoppingCart -> //You can name it yourself
    user::id.eq(shoppingCart::userId) and
    good::categoryId.eq(goodCategory::id) and
    good::id.eq(shoppingCart::goodId)
  }
  .select{user, goodCategory, good, shoppingCart -> //You can name it yourself
  addFields(
      user,//select all fields of the table
      user::userName,
      good::name,
      good::updateTime,//The field name is user.update_time, and the alias is updateTime
      goodCategory::name,//The field name is good_category.update_time, and the alias is name@
      goodCategory::updateTime,//The field name is good_category.update_time, and the alias is updateTime@
      shoppingCart::qty,
      shoppingCart::updateTime to "ut"//Manually alias by passing in Pair
    )
  }
```



## `.where()` Set query conditions and complete the query

```
**Please note that the condition should be added in where or on, and placing it in different places may affect the query results. **
```

```kotlin
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
  .on{user, goodCategory, good, shoppingCart -> //You can name it yourself
    user::id.eq(shoppingCart::userId) and
    good::categoryId.eq(goodCategory::id) and
    good::id.eq(shoppingCart::goodId)
  }
  .select{user, goodCategory, good, shoppingCart -> //You can name it yourself
  addFields(
      user,//select all fields of the table
      user::userName,
      good::name,
      good::updateTime,//The field name is user.update_time, and the alias is updateTime
      goodCategory::name,//The field name is good_category.update_time, and the alias is name@
      goodCategory::updateTime,//The field name is good_category.update_time, and the alias is updateTime@
      shoppingCart::qty,
      shoppingCart::updateTime to "ut"//Manually alias by passing in Pair
    )
  }
  .where { user, _, _, _ -> // unwanted objects can be written _
    user::userName.eq("koto")
  }
  .orderBy(TbUser::age.desc())
  .groupBy(TbUser::age)
  .page(1, 10)
.query()
```



## future features: from function

The `from` function simplifies the problem of repeatedly writing object names in complex queries, and the .addFields function can be omitted when selecting the columns of the query. The usage is as follows:

```kotlin
from<TbUser, TbGoodCategoryKPojo, TbGoodKPojo, TbShoppingCartKPojo> { user, goodCategory, good, shoppingCart -> only need to be defined once
associate(user, goodCategory, good, shoppingCart)
.on(
user::id.eq(shoppingCart::userId) and
good::categoryId.eq(goodCategory::id) and
good::id.eq(shoppingCart::goodId)
)
.select( //Select columns directly without addFields()
user,
good::name,
goodCategory::name,
shoppingCart::qty
)
.where(user::userName.eq("ousc"))
.orderBy(user::age.desc())
.groupBy(user::age)
.page(1, 10)
}
.build()
```
