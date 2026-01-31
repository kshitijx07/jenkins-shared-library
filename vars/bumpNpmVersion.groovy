def call(Map config) {
    def dir = config.dir

    sh """
        cd ${dir}
        npm version patch --no-git-tag-version
    """

    def version = sh(
        script: "cd ${dir} && node -p \"require('./package.json').version\"",
        returnStdout: true
    ).trim()

    return version
}
