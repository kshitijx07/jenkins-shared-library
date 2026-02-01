def call(Map config) {

    echo "🔍 dockerBuildPush: received config -> ${config}"

    if (!config.user || !config.image || !config.version || !config.dir) {
        error "dockerBuildPush: user, image, version, dir are REQUIRED"
    }

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"
    def buildDir = config.dir.trim()

    echo "🐳 Image tag        : ${imageTag}"
    echo "🏷️  Latest tag      : ${latest}"
    echo "📁 Build directory  : ${buildDir}"
    echo "🚀 Mode             : Jenkins-safe (BuildKit OFF)"

    sh """
        set -e

        echo "➡️  Changing directory to: ${buildDir}"
        cd ${buildDir}

        echo "📍 Current working directory:"
        pwd

        echo "🐳 Running docker build command:"
        echo "DOCKER_BUILDKIT=0 docker build --network=host -t ${imageTag} \$(pwd)"

        DOCKER_BUILDKIT=0 docker build --network=host -t ${imageTag} \$(pwd)

        echo "🏷️  Tagging image as latest:"
        echo "docker tag ${imageTag} ${latest}"
        docker tag ${imageTag} ${latest}

        echo "📤 Pushing versioned image:"
        echo "docker push ${imageTag}"
        docker push ${imageTag}

        echo "📤 Pushing latest image:"
        echo "docker push ${latest}"
        docker push ${latest}

        echo "✅ dockerBuildPush completed successfully"
    """
}
