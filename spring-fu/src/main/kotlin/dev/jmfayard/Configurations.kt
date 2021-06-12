package dev.jmfayard

import io.r2dbc.spi.ConnectionFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.templating.mustache
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator


val dataConfig = configuration {
    beans {
        bean<UserRepository>()
        bean {
            ConnectionFactoryInitializer().apply {
                setConnectionFactory(ref())
                setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("db/tables.sql")))
            }
        }
        bean {
            R2dbcEntityTemplate(ref<ConnectionFactory>())
        }
    }
    r2dbc {
        url = "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
    }
}

val webConfig = configuration {
    beans {
        bean<UserHandler>()
        bean<HttpBinHandler>()
        bean(::routes)
    }
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        mustache()
        codecs {
            string()
            jackson()
        }
    }
}
