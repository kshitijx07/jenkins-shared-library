def call(Map config) {
    def image = "${config.user}/${config.image}:${config.tag}"
    def latest = "${config.user}/${config.image}:latest"

    sh """
        docker build --network=host -t ${image} ${config.dir}
        docker tag ${image} ${latest}
        docker push ${image}
        docker push ${latest}
    """
}
