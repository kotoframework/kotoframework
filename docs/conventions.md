# 🧷Yuēdìng: ### 1. Shùjù biǎo biǎo mínghé zìduàn mòrèn shǐyòng xiǎoxiě zìmǔ, yǐ xiàhuáxiàn gé kāi; duìyìng de KPojo shǐyòng qí zhuǎnhuàn de tuófēng xíngshì; ```kotlin UserInfo -> user_info userName -> user_name ``` ### 2. Měi zhāng biǎo xūyào create_time hé update_time zìduàn, yóu koto wánquán tuōguǎn, wúxū zài KPojo zhōng tǐxiàn; ##### 3. Lèixíng zhuǎnhuàn: ```Kotlin (int) -> Kotlin.Int (float) -> Kotlin.Float (unsigned int) -> Kotlin.Long (tinyint) -> Kotlin.Boolean(if you need Kotlin.Int, change the datasource url: TinyInt1isBit=false) (Date/Datetime) -> format as Kotlin.String (varchar/text/others) -> Kotlin.String ``` ### 4. Zài cháxún yǔjù zhōng, dāng shǐyòng where() jiǎnlüè cháxún shí,KPojo zhōng wèi null de shǔxìng jiāng bèi guòlǜ, rú: ```Kotlin data class User(val id: Int? = Null, val name: String? = Null) val user = User(1) val koto = select(user).Where().Query() // select id, name from user where id = 1 ``` ##### ruò xiǎng yào ràng shǔxìng wèi null shí zìdòng zhuǎnhuàn wèi is null, zé kěyǐ shǐyòng allowNull/orNull: ```Kotlin select(user).Where().AllowNull().Query() //huò select(user).Where(user::Id.Eq.OrNull() and user::Name.Eq.OrNull()).Query() // select id, name from user where id = 1 and name is null ``` ##### zài update,remove shí,KPojo zhōng wèi null de shǔxìng jiāng zhuǎnhuàn wèi is null, rú: ```Kotlin remove(user).Execute() //remove user where id = 1 and name is null ```
展开
1,085 / 5,000
翻译结果
# 🧷Convention:

### 1. The table names and fields of the data table use lowercase letters by default, separated by underscores; the corresponding KPojo uses its converted camel case;

```kotlin
UserInfo -> user_info

userName -> user_name
````

### 2. Each table needs create_time and update_time fields, which are fully managed by koto and do not need to be reflected in KPojo;

##### 3. Type conversion:

```kotlin
(int) -> Kotlin.Int
(float) -> Kotlin.Float
(unsigned int) -> Kotlin.Long
(tinyint) -> Kotlin.Boolean(if you need Kotlin.Int, change the datasource url: tinyInt1isBit=false)
(Date/Datetime) -> format as Kotlin.String
(varchar/text/others) -> Kotlin.String
````

### 4. In the query statement, when using where() abbreviated query, the properties that are null in KPojo will be filtered, such as:

```kotlin
data class User(val id: Int? = null, val name: String? = null)

val user = User(1)
val koto = select(user).where().query()
// select id, name from user where id = 1
````

##### If you want to automatically convert the property to is null when it is null, you can use allowNull/orNull:

```kotlin
select(user).where().allowNull().query()
//or
select(user).where(user::id.eq.orNull() and user::name.eq.orNull()).query()
// select id, name from user where id = 1 and name is null
````

##### During update and remove, the properties that are null in KPojo will be converted to is null, such as:

```kotlin
remove(user).execute()
//remove user where id = 1 and name is null
````
