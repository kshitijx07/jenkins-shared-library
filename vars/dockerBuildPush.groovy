def call(Map config) {

    if (!config.user || !config.image || !config.version || !config.dir) {
        error "dockerBuildPush: user, image, version, dir are REQUIRED"
    }

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"
    def buildDir = config.dir.trim()

    echo "🐳 Building Docker image: ${imageTag}"
    echo "📁 Build context: ${buildDir}"

    sh """
        cd ${buildDir}
        docker build --network=host -t ${imageTag} .
        docker tag ${imageTag} ${latest}
        docker push ${imageTag}
        docker push ${latest}
    """
}
