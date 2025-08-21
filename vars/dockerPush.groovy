def call(String project,String imgTag,String dockerHubUser){
  withCredentials([usernamePassword(credentialsId:"dockerhubcreds",
                passwordVariable:"dockerHubPass",usernameVariable:"dockerHubUser")]) 
  {
    sh "docker login -u ${dockerHubUser} -p ${dockerHubPass}" 
  }
  sh "docker push ${dockerHubUser}/${project}:${imgTag}"
  echo "Pushed to dockerhub"
}
