#!/bin/sh

#############################################################################
# Compiles the package and runs all tests with different versions of Vert.x #
#############################################################################

vertx=( "2.1.5" "2.1.4" "2.1.3" "2.1.2" "2.1.1" "2.1" )

for v in "${vertx[@]}"
do
    mvn clean test -U -q -Dsurefire.printSummary=false -Dversion.vertx-core=$v > /dev/null
    if [ $? -ne 0 ];
    then
        echo "Vert.x($v) -> ERROR($?)"
    else
        echo "Vert.x($v) -> success"
    fi
done