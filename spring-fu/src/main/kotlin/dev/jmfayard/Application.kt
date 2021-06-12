package dev.jmfayard

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
    configurationProperties<SampleProperties>(prefix = "dev.jmfayard")
    enable(dataConfig)
    enable(webConfig)
}

//fun main() = app.run()
fun main() {
    app.run(profiles = "dev")
}


