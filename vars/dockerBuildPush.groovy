def call(Map config) {

    echo "🔍 dockerBuildPush: raw config -> ${config}"

    if (!config.user || !config.image || !config.version || !config.dir) {
        error "dockerBuildPush: user, image, version, dir are REQUIRED"
    }

    // 🔥 CRITICAL FIX: remove whitespace + newlines
    def cleanVersion = config.version.toString().trim().split()[0]

    def imageTag = "${config.user}/${config.image}:${cleanVersion}"
    def latest   = "${config.user}/${config.image}:latest"
    def buildDir = config.dir.trim()

    echo "🧹 Cleaned version   : '${cleanVersion}'"
    echo "🐳 Image tag         : ${imageTag}"
    echo "🏷️  Latest tag       : ${latest}"
    echo "📁 Build directory   : ${buildDir}"
    echo "🚀 Mode              : Jenkins-safe (BuildKit OFF)"

    sh """
        set -e

        echo "➡️  cd ${buildDir}"
        cd ${buildDir}

        echo "📍 pwd:"
        pwd

        echo "🐳 docker build command:"
        echo "DOCKER_BUILDKIT=0 docker build --network=host -t '${imageTag}' \$(pwd)"

        DOCKER_BUILDKIT=0 docker build --network=host -t '${imageTag}' \$(pwd)

        echo "🏷️  docker tag latest"
        docker tag '${imageTag}' '${latest}'

        echo "📤 docker push versioned"
        docker push '${imageTag}'

        echo "📤 docker push latest"
        docker push '${latest}'

        echo "✅ dockerBuildPush completed"
    """
}
