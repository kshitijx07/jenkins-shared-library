def call(Map config) {

    def imageTag = "${config.user}/${config.image}:${config.version}"
    def latest   = "${config.user}/${config.image}:latest"
    def dir      = config.dir ?: '.'

    sh """
        docker build --network=host -t ${imageTag} ${dir}
        docker tag ${imageTag} ${latest}
        docker push ${imageTag}
        docker push ${latest}
    """
}
