pipeline {
  agent any
  stages {
    stage('Install npm packages') {
      steps {
      ansiColor('xterm') {
        sh 'source ~/.bashrc'
        }
        sh 'npm install'
        sh 'npm run clean'
      }
    }
    stage('Run All Tests') {
      parallel {
        stage('Run test-js') {
          steps {
            sh 'source ~/.bashrc'
            sh 'npm run test-js'
          }
        }
        stage('Run IOS tests') {
          steps {
            sh 'source ~/.bashrc'
            ansiColor('xterm') {
              sh 'npm run test-unit-ios -- --release'
            }
            sh 'npm run test-e2e-ios -- --release'
          }
        }
        stage('Run android tests') {
          steps {
            sh 'source ~/.bashrc'
            sh 'npm run test-unit-android -- --release'
            sh 'npm run test-e2e-android -- --release'
          }
        }
      }
    }
  }
}
