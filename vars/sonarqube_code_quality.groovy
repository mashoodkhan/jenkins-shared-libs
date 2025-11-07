def call() {
    try {
        timeout(time: 5, unit: "MINUTES") {  // Increase time, SonarQube may need a few mins
            def qg = waitForQualityGate(abortPipeline: false)
            echo "Quality Gate status: ${qg.status}"
        }
    } catch (e) {
        echo "⚠️ SonarQube Quality Gate check timed out or failed: ${e}"
    }
}
