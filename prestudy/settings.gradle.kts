rootProject.name = "prestudy-post"

include(":bootstrap")
include(":common")

include(":domain:post:application")
project(":domain:post:application").name = "post-application"

include(":domain:post:adapter-in")
project(":domain:post:adapter-in").name = "post-adapter-in"

include(":domain:post:adapter-out")
project(":domain:post:adapter-out").name = "post-adapter-out"
