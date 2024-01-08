#!/bin/bash

./gradlew :app:downloadApolloSchema \
--endpoint='http://localhost:8080/graphql' \
--schema=app/src/main/graphql/schema.graphqls
