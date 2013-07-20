#!/bin/bash

export MVN_COMMAND=$(which mvn)

echo "TRAVIS_BRANCH=${TRAVIS_BRANCH}"
echo "TRAVIS_PULL_REQUEST=${TRAVIS_PULL_REQUEST}"

run_gradle() {
    local task=$1

    gradle --info ${task} 1> build.output 2> build.error
    local result=$?

    echo 'build output:'
    echo
    cat build.output | grep -v -i 'download.*' | grep -v -i '^\s*download.*' | grep -v '\s*K\{0,1\}B\s*$' | cat -s
    echo

    if [[ ${result} != 0 ]]; then
        echo 'build error:'
        echo
        cat build.error
        echo

        exit ${result}
    fi
}

echo 'building artifacts...'
run_gradle

if [[ ${TRAVIS_BRANCH} = 'master' ]]; then
    echo 'uploading archives...'
    run_gradle uploadArchives
fi

exit 0
