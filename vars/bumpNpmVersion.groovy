def call(Map config) {
    def dir = config.dir

    return sh(
        script: """
            cd ${dir}
            npm version patch --no-git-tag-version
            node -p "require('./package.json').version"
        """,
        returnStdout: true
    ).trim()
}
