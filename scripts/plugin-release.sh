#!/usr/bin/env bash

GROUP="io/github/m0pt0pmatt/survivalgames"
PROJECT_NAME="SurvivalGames"
VERSION=$(ls ${HOME}/.m2/repository/${GROUP}/${PROJECT_NAME} | sed 's/maven-metadata-local.xml//' | xargs)
if [ "$(echo ${VERSION} | grep -o SNAPHOT)" != "SNAPSHOT" ]; then

    echo "VERSION: $VERSION"

    # Create new release
    TAG_NAME="v$VERSION"
    NAME="$PROJECT_NAME v$VERSION"
    API_JSON="{\"tag_name\": \"$TAG_NAME\",\"target_commitish\": \"master\",\"name\": \"$NAME\",\"body\": \"Plugin release of version $VERSION from Travis build ${TRAVIS_BUILD_NUMBER}\",\"draft\": false,\"prerelease\": false}"
    curl --data "$API_JSON" https://api.github.com/repos/m0pt0pmatt/SurvivalGames/releases?access_token=${GH_TOKEN}

    # Upload assets to release
    JAR_NAME="${PROJECT_NAME}-${VERSION}.jar"
    ls ${HOME}
    ls ${HOME}/SurvivalGames/build/libs/
    curl --data "$(cat ${HOME}/SurvivalGames/build/libs/${JAR_NAME})" https://uploads.github.com/repos/m0pt0pmatt/SurvivalGames/releases/${VERSION}/assets?name=${JAR_NAME}&access_token=${GH_TOKEN}
fi

