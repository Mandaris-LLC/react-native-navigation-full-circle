#!/bin/bash -e

curl -o- -L https://yarnpkg.com/install.sh | bash
export PATH=$PATH:$HOME/.yarn/bin
