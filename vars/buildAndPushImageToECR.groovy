def call(project, tag, awsAccountId, awsRegion, contextDir) {

    def ecrRepo = "${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com/${project}"

    // Build
    sh "docker build -t ${project}:${tag} ${contextDir}"

    withAWS(credentials: 'aws-creds', region: awsRegion) {

        // create repo if not exists
        sh """
            aws ecr describe-repositories --repository-names ${project} \
            || aws ecr create-repository --repository-name ${project} --region ${awsRegion}
        """

        // Login Docker to ECR
        sh """
            aws ecr get-login-password --region ${awsRegion} \
            | docker login --username AWS --password-stdin ${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com
        """
    }

    // Tag & Push (this does not require AWS creds)
    sh "docker tag ${project}:${tag} ${ecrRepo}:${tag}"
    sh "docker push ${ecrRepo}:${tag}"

    echo "✅ Pushed ${ecrRepo}:${tag} to AWS ECR"
}
