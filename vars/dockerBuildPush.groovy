def call(Map config) {

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"

    def buildDir = config.dir?.trim() ?: '.'

    sh """
      docker build --network=host -t ${imageTag} ${buildDir}
      docker tag ${imageTag} ${latest}
      docker push ${imageTag}
      docker push ${latest}
    """
}
