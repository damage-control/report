#!/bin/bash

export MVN_COMMAND=$(which mvn)

gradle --info 1> build.output 2> build.error
RESULT=$?

echo 'build output:'
echo
cat build.output | grep -v -i '^\s*download.*' | grep -v '\s*K\{0,1\}B\s*$'
echo

if [[ $RESULT != 0 ]]; then
    echo 'build error:'
    echo
    cat build.error
    echo

    exit $RESULT
fi

exit 0
