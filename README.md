# graphql-demo


# curl commands

curl --location --request POST 'http://localhost:8080/v1/get-all-departments' \
--header 'Content-Type: text/plain' \
--data-raw '{
    getAllDepartment {
        id
        departmentName
        hodName
        collegeName
        principalName
    }
}'


curl --location --request POST 'http://localhost:8080/v1/get-department-by-id' \
--header 'Content-Type: application/json' \
--data-raw '{
    findDepartmentId(id:"MCA-001") {
        id
        departmentName
        hodName
        collegeName
        principalName
    }
}'
