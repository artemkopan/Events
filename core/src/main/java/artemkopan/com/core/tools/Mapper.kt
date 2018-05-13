package artemkopan.com.core.tools

@Suppress("MemberVisibilityCanBePrivate", "unused", "UNUSED_PARAMETER")
abstract class Mapper<To, From> {

    abstract fun map(from: From): To

    fun reverseMap(to: To): From {
        throw notImplementedException()
    }

    fun notImplementedException(): AbstractMethodError {
        return AbstractMethodError("Not implemented method")
    }

    fun mapList(typeList: List<From>): List<To> {

        val list = ArrayList<To>()

        for (type in typeList) {

            list.add(map(type))

        }

        return list
    }

    fun reverseMapList(typeList: List<To>): List<From> {

        val list = ArrayList<From>()

        for (type in typeList) {

            list.add(reverseMap(type))

        }

        return list

    }

}