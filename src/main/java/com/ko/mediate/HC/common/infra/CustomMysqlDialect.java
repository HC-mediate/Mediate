package com.ko.mediate.HC.common.infra;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.spatial.dialect.mysql.MySQL56SpatialDialect;
import org.hibernate.type.StandardBasicTypes;

public class CustomMysqlDialect extends MySQL56SpatialDialect {
    public CustomMysqlDialect() {
        super();
        registerFunction(
                "ST_Distance_Sphere",
                new StandardSQLFunction("ST_Distance_Sphere", StandardBasicTypes.DOUBLE));
    }
}
