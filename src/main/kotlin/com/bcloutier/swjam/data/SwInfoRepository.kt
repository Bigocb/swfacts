package com.bcloutier.swjam.data

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class SwInfoRepository( val jooq: DSLContext) {

    fun addMgProperty(propName: String, propValue: String, username: String) {
        jooq
                .insertInto<Record, Any, Any, Any, Any>(
                        DSL.table("MGPROPERTY"),
                        DSL.field("SERVICE"),
                        DSL.field("SERVER"),
                        DSL.field("PROPNAME"),
                        DSL.field("PROPVALUE")

                )
                .values(
                        "DEFAULT",
                        "DEFAULT",
                        propName,
                        propValue
                )
                .execute()

    }

}