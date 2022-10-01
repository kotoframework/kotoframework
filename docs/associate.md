# ⛓️连表查询

在koto中，使用`associate`函数连接多表中查询数据，本章将以以下几个实体类举例如何使用连表功能：

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

> 受kotlin泛型的语法限制，目前koto最多仅支持一次连接10张表

Koto让连表查询变得更加简单：

## ` .asscociate()`选中多张表

可以传入KPojo或只指定类型来选中多张表：

```kotlin
//传入KPojo实体类
associate(
    TbUser(), TbGoodCategory(), TbGood(), TbShoppingCart()
)
// 或使用泛型的写法：
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
```



## `.on()`函数连接多张表

> 注意：将被连接的表放在后面

```kotlin
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
  .on{user, goodCategory, good, shoppingCart -> //可以自己命名
    user::id.eq(shoppingCart::userId) and
    good::categoryId.eq(goodCategory::id) and
    good::id.eq(shoppingCart::goodId)
  }
```



## `.left()|.right()|.inner()|.cross()`选择连接方向

共有四种连接方式：LeftJoin、RightJoin、InnerJoin, CrossJoin

Koto默认使用左连接，通过调用`.right()|.inner()|.cross()`切换连接方式



## `.select()|.addFields`选择查询

若不掉用select函数，则默认选择所有表的全部字段

koto会自动给字段起别名，并且会在冲突的列后+“@”来避免重复。

```kotlin
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
  .on{user, goodCategory, good, shoppingCart -> //可以自己命名
    user::id.eq(shoppingCart::userId) and
    good::categoryId.eq(goodCategory::id) and
    good::id.eq(shoppingCart::goodId)
  }
  .select{user, goodCategory, good, shoppingCart -> //可以自己命名
  	addFields(
      user,//选择表的全部字段
      user::userName,
      good::name,
      good::updateTime,//字段名为user.update_time，别名为updateTime
      goodCategory::name,//字段名为good_category.update_time，别名为name@
      goodCategory::updateTime,//字段名为good_category.update_time，别名为updateTime@
      shoppingCart::qty,
      shoppingCart::updateTime to "ut"//通过传入Pair手动起别名
    )
  }
```



## `.where()`设置查询条件，完成查询

```
**请注意条件应加在where中还是在on中，放在不同的地方可能会影响查询结果。**
```

```kotlin
associate<TbUser, TbGoodCategory, TbGood, TbShoppingCart>()
  .on{user, goodCategory, good, shoppingCart -> //可以自己命名
    user::id.eq(shoppingCart::userId) and
    good::categoryId.eq(goodCategory::id) and
    good::id.eq(shoppingCart::goodId)
  }
  .select{user, goodCategory, good, shoppingCart -> //可以自己命名
  	addFields(
      user,//选择表的全部字段
      user::userName,
      good::name,
      good::updateTime,//字段名为user.update_time，别名为updateTime
      goodCategory::name,//字段名为good_category.update_time，别名为name@
      goodCategory::updateTime,//字段名为good_category.update_time，别名为updateTime@
      shoppingCart::qty,
      shoppingCart::updateTime to "ut"//通过传入Pair手动起别名
    )
  }
  .where { user, _, _, _ -> //不需要的对象可以写作_
    user::userName.eq("koto")
  }
  .orderBy(TbUser::age.desc())
  .groupBy(TbUser::age)
  .page(1, 10)
	.query()
```



##  future特性：from函数

`from`函数简化了复杂查询时需要重复写对象名的问题，并且在选择查询的列时，可以不使用.addFields函数，使用方法如下：

```kotlin
from<TbUser, TbGoodCategoryDto, TbGoodDto, TbShoppingCartDto> { user, goodCategory, good, shoppingCart ->只需定义一次
	associate(user, goodCategory, good, shoppingCart)
		.on(
			user::id.eq(shoppingCart::userId) and
			good::categoryId.eq(goodCategory::id) and
			good::id.eq(shoppingCart::goodId)
		)
		.select( //直接选择列，无需addFields()
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



