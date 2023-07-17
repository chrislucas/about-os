package com.br.sample.redis

import redis.clients.jedis.JedisPool

/*
    https://redis.io/docs/clients/java/
    https://www.baeldung.com/jedis-java-redis-client-library
    https://www.tutorialspoint.com/redis/redis_java.htm
 */



fun main() {


    /*
        Try with resource do kotlin
        https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/use.html
        https://stackoverflow.com/questions/26969800/try-with-resources-in-kotlin
     */
    JedisPool("localhost", 6379).use { pool ->
        val alpha = 'a' .. 'z'

       val jedis = pool.resource.apply {
            alpha.forEachIndexed { i, data ->
                set(i.toString(), data.toString())
            }
        }

        println(jedis.get("0"))

    }



}