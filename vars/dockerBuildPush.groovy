def call(Map config) {

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"

    def buildDir = config.dir?.trim()
    if (!buildDir) {
        buildDir = '.'
    }

    sh "docker build --network=host -t ${imageTag} ${buildDir}"
    sh "docker tag ${imageTag} ${latest}"
    sh "docker push ${imageTag}"
    sh "docker push ${latest}"
}
