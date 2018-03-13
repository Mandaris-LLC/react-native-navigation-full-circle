pipeline {
  agent any
  stages {
    stage('Install npm packages') {
      steps {
      ansiColor('xterm') {
        sh '''#!/bin/bash -ex
        source ~/.bashrc
        npm install
        npm run clean'''
        }
      }
    }
    stage('Run All Tests') {
      parallel {
        stage('Run test-js') {
          steps {
            sh '''#!/bin/bash
            source ~/.bashrc
            npm run test-js'''
          }
        }
        stage('Run IOS tests') {
          steps {
            sh '''#!/bin/bash
            source ~/.bashrc
            npm run test-unit-ios -- --release
            npm run test-e2e-ios -- --release'''
          }
        }
        stage('Run android tests') {
          steps {
            sh '''#!/bin/bash
            source ~/.bashrc
            npm run test-unit-android -- --release
            npm run test-e2e-android -- --release'''
          }
        }
      }
    }
  }
}
