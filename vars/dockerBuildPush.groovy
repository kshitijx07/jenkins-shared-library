def call(Map config) {
    if (!config.user || !config.image || !config.version || !config.dir) {
        error "dockerBuildPush: user, image, version, dir are REQUIRED"
    }

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"
    def buildDir = config.dir.trim()

    echo "🐳 Building Docker image: ${imageTag}"
    echo "📁 Build context: ${buildDir}"
    echo "🚀 dockerBuildPush: BuildKit disabled, using absolute path"

    sh """
        set -e
        cd ${buildDir}
        DOCKER_BUILDKIT=0 docker build --network=host -t ${imageTag} \$(pwd)
        docker tag ${imageTag} ${latest}
        docker push ${imageTag}
        docker push ${latest}
    """
}
