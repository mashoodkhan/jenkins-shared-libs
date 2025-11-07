def buildAndPushDocker(project, tag, dockerUser, contextDir){
    sh "docker build -t ${dockerUser}/${project}:${tag} ${contextDir}"
    withCredentials([usernamePassword(credentialsId:"dockerhubcreds", 
                        usernameVariable:"DOCKER_USER", passwordVariable:"DOCKER_PASS")]) {
        sh "docker login -u $DOCKER_USER -p $DOCKER_PASS"
    }
    sh "docker push ${dockerUser}/${project}:${tag}"
    echo "Pushed ${dockerUser}/${project}:${tag} to DockerHub"
}
