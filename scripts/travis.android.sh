./scripts/travis-fold.sh "npm install"
./scripts/travis-fold.sh "npm run clean"
./scripts/travis-fold.sh "npm run test-unit-android -- --release"
./scripts/travis-fold.sh "npm run test-e2e-android -- --release"
