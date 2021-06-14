#!/usr/bin/env just --justfile

docker:
    ./gradlew bootBuildImage --imageName=kotlin-graphql
    docker run -p8080:8080 kotlin-graphql
build:
    gcloud builds submit --pack=image=gcr.io/kotlin-graphql/kotlin-graphql
deploy: build
    gcloud run deploy \
    --image=gcr.io/kotlin-graphql/kotlin-graphql \
    --platform=managed \
    --allow-unauthenticated \
    --project=kotlin-graphql \
    --region=us-central1 \
    --memory=1Gi \
    kotlin-graphql