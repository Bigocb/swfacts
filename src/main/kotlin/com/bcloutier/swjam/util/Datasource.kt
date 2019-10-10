package com.bcloutier.swjam.util

import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import javax.sql.DataSource


@Bean
fun dataSource(): DataSource {

    // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
    val builder = EmbeddedDatabaseBuilder()
    return builder
            .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
            .addScript("db/sql/create-db.sql")
            .addScript("db/sql/insert-data.sql")
            .build()
}