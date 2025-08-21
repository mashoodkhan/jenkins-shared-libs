def call(String project,String imgTag,String dockerHubUser){
   sh "docker build -t ${dockerHubUser}/${project}:${imgTag} ."
}
