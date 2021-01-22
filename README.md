# Enrollee
## Fixing error for Lomok :After cloning the service add LOMBOK If we're using STS IDE, we need to get the Lombok jar first. The latest version is located on Maven Central, we're using lombok latest version jar 

## The run command tfor the service spring-boot:run 

## For test casses we are using JUNIT4 .TO add it go to  Project properties -> Java Build Path -> Add Library ->JUnit ->JUnit Library Version ->JUnit4

## For Class not fiund exception in test csses that you have to go to Build Path in your project's properties and move Maven Dependencies above JRE System Library.


Below are the CURLS for the service
####
###### TO ADD ENROLLEE

curl --location --request POST 'localhost:8585/enrollee/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Raja Shekar",
    "dateOfBirth" : "1993-04-16",
    "phoneNumber" :"1234567890"

}'

###### TO UPDATE ENROLLEE

curl --location --request PUT 'localhost:8585/enrollee/update' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id" : "9",
    "name" : "raj",
    "dateOfBirth" : "1993-04-16"

}'

###### TO DELETE ENROLLEE

curl --location --request DELETE 'localhost:8585/enrollee/10'


######TO GET ALL THE DETAILS OF ENROLLEE AS WELL AS DEPENDENTS OF AN ENROLLEE

curl --location --request GET 'localhost:8585/enrollee/9'

###### TO ADD DEPENDENT

curl --location --request POST 'localhost:8585/dependent/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "enrolleeId" : "8",
    "dateOfBirth" : "1993-04-05",
    "name" :"Kittu"

}'

###### TO UPATE DEPENDENT

curl --location --request PUT 'localhost:8585/dependent/update' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id" : "4",
    "dateOfBirth" : "1993-04-05",
    "name" :"NAG"

}'


###### TO DELETE DEPENDENT

curl --location --request DELETE 'localhost:8585/dependent/4' 

###### TO GET ALL THE DETAILS OF DEPENDENT
curl --location --request GET 'localhost:8585/dependent/3'
