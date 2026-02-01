def call(Map config) {
    if (!config.dir) {
        error "bumpNpmVersion: dir is REQUIRED"
    }

    return sh(
        script: """
            set -e
            cd ${config.dir}
            npm version patch --no-git-tag-version
            node -p "require('./package.json').version"
        """,
        returnStdout: true
    ).trim()
}
