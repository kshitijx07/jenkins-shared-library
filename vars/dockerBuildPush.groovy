def call(Map config) {

    if (!config.user || !config.image || !config.version) {
        error "dockerBuildPush: user, image, and version are REQUIRED"
    }

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"

    def buildDir = config.dir?.trim() ?: '.'

    echo "🐳 Building Docker image: ${imageTag}"
    echo "📁 Build context: ${buildDir}"

    sh "docker build --network=host -t ${imageTag} ${buildDir}"
    sh "docker tag ${imageTag} ${latest}"
    sh "docker push ${imageTag}"
    sh "docker push ${latest}"
}
