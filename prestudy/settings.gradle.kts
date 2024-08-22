rootProject.name = "prestudy-post"

include(":bootstrap")
include(":common")

include(":domain:post:application")
project(":domain:post:application").name = "post-application"

include(":domain:post:adapter-in")
project(":domain:post:adapter-in").name = "post-adapter-in"

include(":domain:post:adapter-out")
project(":domain:post:adapter-out").name = "post-adapter-out"

include(":domain:auth:application")
project(":domain:auth:application").name = "auth-application"

include(":domain:auth:adapter-in")
project(":domain:auth:adapter-in").name = "auth-adapter-in"

include(":domain:auth:adapter-out")
project(":domain:auth:adapter-out").name = "auth-adapter-out"
