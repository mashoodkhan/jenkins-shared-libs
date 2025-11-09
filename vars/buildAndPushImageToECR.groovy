def buildAndPushImageToECR(project, tag, awsAccountId, awsRegion, contextDir) {

    def ecrRepo = "${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com/${project}"

    // Build Image
    sh "docker build -t ${project}:${tag} ${contextDir}"

    // Login to ECR
    withAWS(credentials: 'aws-creds', region: awsRegion) {
        sh """
            aws ecr get-login-password --region ${awsRegion} \
            | docker login --username AWS --password-stdin ${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com
        """
    }

    // Tag & Push
    sh "docker tag ${project}:${tag} ${ecrRepo}:${tag}"
    sh "docker push ${ecrRepo}:${tag}"

    echo "✅ Pushed ${ecrRepo}:${tag} to AWS ECR"
}
