package org.hostelhub.utils

class DockerUtils {
    static String imageName(String user, String image, String tag) {
        return "${user}/${image}:${tag}"
    }
}
