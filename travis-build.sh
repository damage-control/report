#!/bin/bash

echo
echo 'building html-generator module...'
echo

gradle --info :html-generator:install 1> build.output 2> build.error
HTML_GENERATOR_RESULT=$?

echo 'build output:'
echo
cat build.output
echo

echo 'build error:'
echo
cat build.error
echo

exit $HTML_GENERATOR_RESULT
