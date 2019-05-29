Feedback Export Service
=

[![Docker Repository on Quay](https://quay.io/repository/ukhomeofficedigital/pttg-feedback-export/status "Docker Repository on Quay")](https://quay.io/repository/ukhomeofficedigital/pttg-feedback-export)

## Overview

This is the Feedback Export Service. It returns feedback in csv format.
 
## Find Us

* [GitHub]

## Technical Notes

The API is implemented using Spring Boot and exposes a RESTFul interface.

The endpoints are defined in `FeedbackResource.java`.

## Building

### ACP

This service is built by Gradle on [Drone] using [Drone yaml].

## Infrastructure

### ACP

This service is packaged as a Docker image and stored on [Quay.io]

This service is deployed by [Drone] onto a Kubernetes cluster using its [Kubernetes configuration]

## Running Locally

Check out the project and run the command `./gradlew bootRun` which will install gradle locally, download all dependencies, build the project and run it.

The API should then be available on http://localhost:8085, where:
- port 8085 is defined in `application.properties` with key `server.port`
- the paths for the endpoints are defined in `FeedbackResource.java`

## Dependencies

When deployed this service depends upon a database created by [pttg-postgres] and schema created by [pttg-feedback]. 

## Versioning

For the versions available, see the [tags on this repository].

## Authors

See the list of [contributors] who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENCE.md]
file for details.



[contributors]:                     https://github.com/UKHomeOffice/pttg-feedback-export/graphs/contributors
[Quay.io]:                          https://quay.io/repository/ukhomeofficedigital/pttg-feedback-export
[kubernetes configuration]:         https://github.com/UKHomeOffice/kube-pttg-feedback-export
[Drone yaml]:                       .drone.yml
[tags on this repository]:          https://github.com/UKHomeOffice/pttg-feedback-export/tags
[LICENCE.md]:                       LICENCE.md
[GitHub]:                           https://github.com/orgs/UKHomeOffice/teams/pttg
[Drone]:                            https://drone.acp.homeoffice.gov.uk/UKHomeOffice/pttg-feedback-export
[pttg-postgres]:                    https://github.com/orgs/UKHomeOffice/pttg-postgres
[pttg-feedback]:                    https://github.com/orgs/UKHomeOffice/pttg-feedback
